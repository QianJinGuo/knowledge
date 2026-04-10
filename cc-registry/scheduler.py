#!/usr/bin/env python3
"""
cc-registry / scheduler.py
Claude Code Task Registry + Scheduling Engine
Pure Python — no external framework required.

Usage (interactive CLI):
    python scheduler.py

Supported commands:
    list                      — list all registered tasks
    status                    — show status of all task instances
    run <task_id> [key=value] — execute a task with optional parameters
    retry <instance_id>       — retry a failed/timeout instance
    scan                      — manually trigger a status-check sweep
    help                      — show this message
    exit / quit               — exit
"""

import json
import os
import sys
import time
import uuid
import importlib.util
import traceback
import threading
import logging
from datetime import datetime
from pathlib import Path

# ── paths ──────────────────────────────────────────────────────────────────────
BASE_DIR       = Path(__file__).parent
REGISTRY_FILE  = BASE_DIR / "task_registry.json"
STATUS_FILE    = BASE_DIR / "task_status.json"
TASKS_DIR      = BASE_DIR / "tasks"
LOGS_DIR       = BASE_DIR / "logs"

# ── logging ────────────────────────────────────────────────────────────────────
LOGS_DIR.mkdir(parents=True, exist_ok=True)
log_path = LOGS_DIR / f"scheduler_{datetime.now().strftime('%Y%m%d')}.log"

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[
        logging.FileHandler(log_path, encoding="utf-8"),
        logging.StreamHandler(sys.stdout),
    ],
)
logger = logging.getLogger("scheduler")

# ── file helpers ───────────────────────────────────────────────────────────────
_file_lock = threading.Lock()


def _read_json(path: Path, default):
    if not path.exists():
        return default
    with open(path, "r", encoding="utf-8") as f:
        try:
            return json.load(f)
        except json.JSONDecodeError:
            return default


def _write_json(path: Path, data):
    with _file_lock:
        with open(path, "w", encoding="utf-8") as f:
            json.dump(data, f, indent=2, ensure_ascii=False)


# ── registry helpers ───────────────────────────────────────────────────────────
def load_registry():
    return _read_json(REGISTRY_FILE, [])


def get_task_def(task_id: str):
    for t in load_registry():
        if t["task_id"] == task_id:
            return t
    return None


# ── status helpers ─────────────────────────────────────────────────────────────
def load_status():
    return _read_json(STATUS_FILE, [])


def save_status(records):
    _write_json(STATUS_FILE, records)


def get_instance(instance_id: str):
    for r in load_status():
        if r["instance_id"] == instance_id:
            return r
    return None


def update_instance(instance_id: str, **kwargs):
    records = load_status()
    for r in records:
        if r["instance_id"] == instance_id:
            r.update(kwargs)
            break
    save_status(records)


def create_instance(task_id: str, params: dict) -> str:
    instance_id = f"run_{uuid.uuid4().hex[:8]}"
    record = {
        "task_id":      task_id,
        "instance_id":  instance_id,
        "status":       "pending",
        "start_time":   None,
        "end_time":     None,
        "params":       params,
        "output":       None,
        "error":        None,
        "retry_count":  0,
    }
    records = load_status()
    records.append(record)
    save_status(records)
    logger.info("[CREATE] instance=%s task=%s params=%s", instance_id, task_id, params)
    return instance_id


# ── task execution ─────────────────────────────────────────────────────────────
def _load_task_module(entry: str):
    """Dynamically load a task module from its entry path."""
    path = BASE_DIR / entry
    if not path.exists():
        raise FileNotFoundError(f"Task entry not found: {path}")
    spec = importlib.util.spec_from_file_location("task_module", path)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def execute_task(instance_id: str):
    """Run a single task instance synchronously. Called in a worker thread."""
    rec = get_instance(instance_id)
    if rec is None:
        logger.error("[EXEC] instance %s not found", instance_id)
        return

    task_def = get_task_def(rec["task_id"])
    if task_def is None:
        update_instance(instance_id, status="failed", error="task definition not found",
                        end_time=int(time.time()))
        return

    update_instance(instance_id, status="running", start_time=int(time.time()))
    logger.info("[EXEC] START instance=%s task=%s", instance_id, rec["task_id"])

    try:
        module = _load_task_module(task_def["entry"])
        if not hasattr(module, "run"):
            raise AttributeError(f"{task_def['entry']} must define a run(params) function")
        output = module.run(rec.get("params", {}))
        update_instance(instance_id, status="success", output=output, end_time=int(time.time()))
        logger.info("[EXEC] SUCCESS instance=%s output=%s", instance_id, output)
    except Exception as exc:
        err_msg = traceback.format_exc()
        update_instance(instance_id, status="failed", error=err_msg, end_time=int(time.time()))
        logger.error("[EXEC] FAILED instance=%s error=%s", instance_id, exc)


def run_task_async(instance_id: str):
    """Launch task execution in a background thread."""
    t = threading.Thread(target=execute_task, args=(instance_id,), daemon=True)
    t.start()


# ── status sweep ───────────────────────────────────────────────────────────────
def scan_status(notify=True):
    """
    Inspect all running instances:
    - Mark timed-out instances as 'timeout'.
    - Auto-retry failed instances (up to 2 retries).
    Returns a summary string.
    """
    now = int(time.time())
    records = load_status()
    changed = []

    for rec in records:
        task_def = get_task_def(rec["task_id"])
        timeout_sec = task_def["timeout_seconds"] if task_def else 60

        if rec["status"] == "running":
            start = rec.get("start_time") or now
            if (now - start) > timeout_sec:
                rec["status"]   = "timeout"
                rec["end_time"] = now
                rec["error"]    = f"Exceeded timeout ({timeout_sec}s)"
                changed.append(f"  ⏰ TIMEOUT  {rec['instance_id']} ({rec['task_id']})")
                logger.warning("[SCAN] TIMEOUT instance=%s", rec["instance_id"])

        elif rec["status"] == "failed":
            if rec.get("retry_count", 0) < 2:
                rec["retry_count"] = rec.get("retry_count", 0) + 1
                rec["status"]      = "pending"
                rec["error"]       = None
                rec["end_time"]    = None
                changed.append(f"  🔄 RETRY   {rec['instance_id']} (attempt {rec['retry_count']})")
                logger.info("[SCAN] RETRY instance=%s attempt=%d",
                            rec["instance_id"], rec["retry_count"])

    save_status(records)

    # Kick off any pending instances that came from retries
    for rec in load_status():
        if rec["status"] == "pending":
            run_task_async(rec["instance_id"])

    if changed and notify:
        print("\n🔍 [Background Scan] changes detected:")
        for line in changed:
            print(line)
        print()

    return changed


# ── periodic scan thread ───────────────────────────────────────────────────────
_scan_interval = 15   # seconds between automatic sweeps


def _background_scan_loop():
    while True:
        time.sleep(_scan_interval)
        try:
            scan_status(notify=True)
        except Exception:
            logger.exception("[SCAN] background sweep error")


def start_background_scan():
    t = threading.Thread(target=_background_scan_loop, daemon=True)
    t.start()
    logger.info("[SCAN] Background scan started (interval=%ds)", _scan_interval)


# ── CLI helpers ────────────────────────────────────────────────────────────────
def cmd_list():
    registry = load_registry()
    if not registry:
        print("No tasks registered.")
        return
    print(f"\n{'ID':<20} {'Name':<30} {'Timeout':>8}  Entry")
    print("-" * 75)
    for t in registry:
        print(f"{t['task_id']:<20} {t['name']:<30} {t['timeout_seconds']:>7}s  {t['entry']}")
    print()


def cmd_status():
    records = load_status()
    if not records:
        print("No task instances found.")
        return
    print(f"\n{'Instance':<18} {'Task ID':<20} {'Status':<10} {'Retries':>7}  Started")
    print("-" * 75)
    status_icon = {
        "pending": "⏳", "running": "▶️", "success": "✅",
        "failed": "❌", "timeout": "⏰",
    }
    for r in records:
        start = datetime.fromtimestamp(r["start_time"]).strftime("%H:%M:%S") \
            if r.get("start_time") else "-"
        icon = status_icon.get(r["status"], "?")
        print(f"{r['instance_id']:<18} {r['task_id']:<20} "
              f"{icon} {r['status']:<8} {r.get('retry_count', 0):>7}  {start}")
    print()


def cmd_run(parts):
    if len(parts) < 2:
        print("Usage: run <task_id> [key=value ...]")
        return
    task_id = parts[1]
    task_def = get_task_def(task_id)
    if task_def is None:
        print(f"❌ Task '{task_id}' not found in registry.")
        return

    params = {}
    for kv in parts[2:]:
        if "=" in kv:
            k, v = kv.split("=", 1)
            # Try to coerce type
            try:
                params[k] = int(v)
            except ValueError:
                try:
                    params[k] = float(v)
                except ValueError:
                    params[k] = v
        else:
            print(f"⚠️  Ignoring malformed param: {kv}")

    instance_id = create_instance(task_id, params)
    print(f"✅ Task queued — instance_id={instance_id}  task={task_id}")
    run_task_async(instance_id)


def cmd_retry(parts):
    if len(parts) < 2:
        print("Usage: retry <instance_id>")
        return
    instance_id = parts[1]
    rec = get_instance(instance_id)
    if rec is None:
        print(f"❌ Instance '{instance_id}' not found.")
        return
    if rec["status"] not in ("failed", "timeout"):
        print(f"⚠️  Instance is '{rec['status']}' — only failed/timeout can be retried.")
        return
    retry_count = rec.get("retry_count", 0) + 1
    update_instance(instance_id, status="pending", retry_count=retry_count,
                    error=None, end_time=None)
    run_task_async(instance_id)
    print(f"🔄 Retry queued — instance_id={instance_id} (attempt {retry_count})")


def cmd_help():
    print(__doc__)


# ── initialisation ─────────────────────────────────────────────────────────────
def init():
    TASKS_DIR.mkdir(parents=True, exist_ok=True)
    LOGS_DIR.mkdir(parents=True, exist_ok=True)
    if not REGISTRY_FILE.exists():
        _write_json(REGISTRY_FILE, [])
        logger.info("[INIT] Created empty task_registry.json")
    if not STATUS_FILE.exists():
        _write_json(STATUS_FILE, [])
        logger.info("[INIT] Created empty task_status.json")
    logger.info("[INIT] Directories and config files ready")


# ── main loop ──────────────────────────────────────────────────────────────────
def main():
    init()
    start_background_scan()

    print("=" * 60)
    print("  Claude Code Task Registry — Scheduling Engine")
    print(f"  Base dir : {BASE_DIR}")
    print(f"  Scan interval: {_scan_interval}s  |  Type 'help' for commands")
    print("=" * 60)

    while True:
        try:
            raw = input("\ncc-registry> ").strip()
        except (EOFError, KeyboardInterrupt):
            print("\nExiting.")
            break

        if not raw:
            continue

        parts = raw.split()
        cmd   = parts[0].lower()

        if cmd in ("exit", "quit"):
            print("Goodbye.")
            break
        elif cmd == "list":
            cmd_list()
        elif cmd == "status":
            cmd_status()
        elif cmd == "run":
            cmd_run(parts)
        elif cmd == "retry":
            cmd_retry(parts)
        elif cmd == "scan":
            changes = scan_status(notify=False)
            if changes:
                print("Scan results:")
                for c in changes:
                    print(c)
            else:
                print("✅ No changes detected.")
        elif cmd == "help":
            cmd_help()
        else:
            print(f"Unknown command: '{cmd}'. Type 'help' for usage.")


if __name__ == "__main__":
    main()

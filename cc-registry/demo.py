#!/usr/bin/env python3
"""
demo.py — Fully automated demonstration of the cc-registry scheduling engine.

Runs without any human input.  Demonstrates:
  1. Initialisation (directories + config files)
  2. Listing registered tasks
  3. Running task_hello (success)
  4. Running task_fibonacci with n=10 (success)
  5. Running task_file_report on the cc-registry directory (success)
  6. Running task_slow which will time-out (timeout demo)
  7. Manual status scan + display
  8. Final status table
"""

import sys
import time
from pathlib import Path

# Make sure we can import scheduler from the same directory
sys.path.insert(0, str(Path(__file__).parent))

import scheduler as sched


SEPARATOR = "─" * 60


def banner(title: str):
    print(f"\n{SEPARATOR}")
    print(f"  {title}")
    print(SEPARATOR)


def wait_for_instance(instance_id: str, max_wait: int = 10) -> dict:
    """Poll until the instance leaves 'running'/'pending', or max_wait seconds pass."""
    deadline = time.time() + max_wait
    while time.time() < deadline:
        rec = sched.get_instance(instance_id)
        if rec and rec["status"] not in ("pending", "running"):
            return rec
        time.sleep(0.3)
    return sched.get_instance(instance_id)


def main():
    # ── 1. Init ──────────────────────────────────────────────────────────────
    banner("Step 1 · Initialise directories and config files")
    sched.init()
    print(f"  task_registry.json : {sched.REGISTRY_FILE}")
    print(f"  task_status.json   : {sched.STATUS_FILE}")
    print(f"  tasks/             : {sched.TASKS_DIR}")
    print(f"  logs/              : {sched.LOGS_DIR}")

    # ── 2. List tasks ─────────────────────────────────────────────────────────
    banner("Step 2 · Registered tasks")
    sched.cmd_list()

    # ── 3. task_hello ─────────────────────────────────────────────────────────
    banner("Step 3 · Run task_hello (name=Demo)")
    iid_hello = sched.create_instance("task_hello", {"name": "Demo"})
    print(f"  Queued: instance_id={iid_hello}")
    sched.execute_task(iid_hello)   # run synchronously for predictable demo output
    rec = sched.get_instance(iid_hello)
    print(f"  Status : {rec['status']}")
    print(f"  Output : {rec['output']}")

    # ── 4. task_fibonacci ─────────────────────────────────────────────────────
    banner("Step 4 · Run task_fibonacci (n=15)")
    iid_fib = sched.create_instance("task_fibonacci", {"n": 15})
    print(f"  Queued: instance_id={iid_fib}")
    sched.execute_task(iid_fib)
    rec = sched.get_instance(iid_fib)
    print(f"  Status : {rec['status']}")
    print(f"  Output : {rec['output']}")

    # ── 5. task_file_report ───────────────────────────────────────────────────
    banner("Step 5 · Run task_file_report (directory=.)")
    iid_report = sched.create_instance("task_file_report", {"directory": str(sched.BASE_DIR)})
    print(f"  Queued: instance_id={iid_report}")
    sched.execute_task(iid_report)
    rec = sched.get_instance(iid_report)
    print(f"  Status         : {rec['status']}")
    if rec["output"]:
        out = rec["output"]
        print(f"  Files found    : {out['total_files']}")
        print(f"  Subdirectories : {out['total_dirs']}")
        print(f"  Total size     : {out['total_size_bytes']} bytes")

    # ── 6. task_slow (timeout demo) ───────────────────────────────────────────
    banner("Step 6 · Run task_slow — will time out (timeout=5s)")
    # Temporarily override timeout in registry to 5 s for a fast demo
    iid_slow = sched.create_instance("task_slow", {"sleep_seconds": 30})
    print(f"  Queued: instance_id={iid_slow}")
    # Start it in background and immediately update start_time so timeout math works
    sched.run_task_async(iid_slow)
    # Wait long enough for it to be "running"
    time.sleep(1)
    print("  Task is running in background …")
    # Force start_time back by 10 s to trigger timeout on next scan
    sched.update_instance(iid_slow, start_time=int(time.time()) - 10)
    print("  Simulating 10-second elapsed time to trigger timeout …")

    # ── 7. Manual scan ────────────────────────────────────────────────────────
    banner("Step 7 · Manual status scan")
    changes = sched.scan_status(notify=False)
    if changes:
        print("  Detected changes:")
        for c in changes:
            print(c)
    else:
        print("  No changes (slow task may still be in pending/running state)")

    # Give background scan a moment to process retries
    time.sleep(2)
    # Run another scan to catch any retried instances
    changes2 = sched.scan_status(notify=False)
    if changes2:
        print("  Second scan changes:")
        for c in changes2:
            print(c)

    # ── 8. Final status table ─────────────────────────────────────────────────
    banner("Step 8 · Final status overview")
    sched.cmd_status()

    print(f"{SEPARATOR}")
    print("  Demo complete ✓")
    print(f"  Full logs: {sched.LOGS_DIR}")
    print(f"{SEPARATOR}\n")


if __name__ == "__main__":
    main()

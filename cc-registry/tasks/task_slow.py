"""
tasks/task_slow.py — Intentionally slow task used to demonstrate timeout detection.

Expected params:
    sleep_seconds (int, optional): how long to sleep, default 120
"""

import time


def run(params: dict) -> str:
    sleep_seconds = int(params.get("sleep_seconds", 120))
    print(f"[task_slow] Sleeping for {sleep_seconds}s …")
    time.sleep(sleep_seconds)
    return f"Completed after {sleep_seconds}s"

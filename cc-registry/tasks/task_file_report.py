"""
tasks/task_file_report.py — File report generator

Expected params:
    directory (str, optional): directory to scan, default "."
"""

import os
from pathlib import Path


def run(params: dict) -> dict:
    directory = params.get("directory", ".")
    target = Path(directory).expanduser().resolve()

    if not target.exists():
        raise FileNotFoundError(f"Directory not found: {target}")
    if not target.is_dir():
        raise NotADirectoryError(f"Not a directory: {target}")

    entries = list(target.iterdir())
    files   = [e for e in entries if e.is_file()]
    dirs    = [e for e in entries if e.is_dir()]

    total_size = sum(f.stat().st_size for f in files)

    report = {
        "directory":   str(target),
        "total_files": len(files),
        "total_dirs":  len(dirs),
        "total_size_bytes": total_size,
        "files": sorted(f.name for f in files),
        "subdirectories": sorted(d.name for d in dirs),
    }
    print(f"[task_file_report] {len(files)} files, {len(dirs)} dirs in {target}")
    return report

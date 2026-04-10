"""
tasks/task_fibonacci.py — Fibonacci calculator task

Expected params:
    n (int, optional): which Fibonacci number to compute (0-indexed), default 10
"""


def run(params: dict) -> dict:
    n = int(params.get("n", 10))
    if n < 0:
        raise ValueError(f"n must be >= 0, got {n}")

    a, b = 0, 1
    for _ in range(n):
        a, b = b, a + b

    result = {"n": n, "fibonacci": a}
    print(f"[task_fibonacci] F({n}) = {a}")
    return result

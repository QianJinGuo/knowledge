"""
tasks/task_hello.py — Hello World task

Expected params:
    name (str, optional): name to greet, default "World"
"""


def run(params: dict) -> str:
    name = params.get("name", "World")
    message = f"Hello, {name}! Task executed at runtime."
    print(f"[task_hello] {message}")
    return message

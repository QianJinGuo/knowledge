# cc-registry

**Claude Code Task Registry + 定时状态检查 · 调度引擎**

一个完全不依赖外部框架（无 LangGraph）的纯 Python 任务调度引擎。
通过代码执行 + 自我循环实现：任务注册、执行、定时巡检、失败重试与日志记录。

---

## 目录结构

```
cc-registry/
├── scheduler.py          # 主调度引擎（含 CLI）
├── demo.py               # 全自动演示脚本（一键跑起来）
├── task_registry.json    # 任务注册表
├── task_status.json      # 任务实例状态（自动维护）
├── tasks/                # 任务脚本目录
│   ├── task_hello.py
│   ├── task_fibonacci.py
│   ├── task_file_report.py
│   └── task_slow.py      # 用于演示超时检测
└── logs/                 # 执行日志（按日期滚动）
```

---

## 快速开始

```bash
# 进入目录
cd cc-registry

# 运行完整演示（无需任何交互）
python demo.py

# 启动交互式 CLI
python scheduler.py
```

---

## 数据结构

### task_registry.json（任务注册表）

```json
[
  {
    "task_id":         "string",
    "name":            "string",
    "description":     "string",
    "input_schema":    {},
    "entry":           "tasks/xxx.py",
    "requires":        [],
    "version":         "1.0.0",
    "timeout_seconds": 60
  }
]
```

### task_status.json（实例状态，自动维护）

```json
[
  {
    "task_id":      "xxx",
    "instance_id":  "run_xxx",
    "status":       "pending|running|success|failed|timeout",
    "start_time":   1712345678,
    "end_time":     null,
    "params":       {},
    "output":       null,
    "error":        null,
    "retry_count":  0
  }
]
```

---

## 交互式 CLI 指令

| 指令 | 说明 |
|------|------|
| `list` | 列出所有注册任务 |
| `status` | 显示所有任务实例状态 |
| `run <task_id> [key=value …]` | 执行任务（可附带参数） |
| `retry <instance_id>` | 重试失败/超时的实例 |
| `scan` | 手动触发一轮状态巡检 |
| `help` | 查看帮助 |
| `exit` / `quit` | 退出 |

**示例：**

```
cc-registry> list
cc-registry> run task_hello name=Claude
cc-registry> run task_fibonacci n=20
cc-registry> run task_file_report directory=.
cc-registry> status
cc-registry> retry run_a1b2c3d4
cc-registry> scan
```

---

## 调度机制

1. **初始化**：自动创建所需目录与配置文件（如不存在）。
2. **用户指令**：解析意图 → 匹配 `task_id` → 创建实例（`status=pending`）。
3. **任务执行**：在后台线程中加载 `tasks/xxx.py` 并调用 `run(params)`；成功置 `success`，异常置 `failed`。
4. **定时巡检**（核心）：后台线程每 **15 秒**自动执行一轮，检查：
   - `running` → 超时则置 `timeout`
   - `failed` → 自动重试（最多 2 次）
5. **日志**：每次执行、巡检、重试均写入 `logs/scheduler_YYYYMMDD.log`。

---

## 编写自定义任务

在 `tasks/` 下创建 Python 文件，实现 `run(params: dict)` 函数：

```python
# tasks/my_task.py

def run(params: dict):
    value = params.get("value", 42)
    return {"result": value * 2}
```

然后在 `task_registry.json` 中注册：

```json
{
  "task_id":         "my_task",
  "name":            "My Custom Task",
  "description":     "Doubles the input value",
  "input_schema":    {"value": {"type": "integer", "required": false, "default": 42}},
  "entry":           "tasks/my_task.py",
  "requires":        [],
  "version":         "1.0.0",
  "timeout_seconds": 30
}
```

---

## 安全约束

- 不执行高危操作
- 不无限死循环（巡检线程固定间隔，非忙等待）
- 不访问外部网络
- 所有任务实例可审计、可回溯（`task_status.json` + 日志文件）

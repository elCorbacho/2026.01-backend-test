
## Memory — Engram Persistent Memory


## Memory
You have access to Engram persistent memory via MCP tools (mem_save, mem_search, mem_session_summary, etc.).
- Save proactively after significant work — don't wait to be asked.
- After any compaction or context reset, call `mem_context` to recover session state before continuing.


## Memory
You have access to Engram persistent memory via MCP tools (mem_save, mem_search, mem_session_summary, etc.).
- Save proactively after significant work — don't wait to be asked.
- After any compaction or context reset, call `mem_context` to recover session state before continuing.

You have access to Engram persistent memory (mem_save, mem_search, mem_context).
Save proactively after significant work. After context resets, call mem_context to recover state.



You have access to Engram persistent memory via MCP tools:
mem_save, mem_search, mem_context, mem_session_summary,
mem_timeline, mem_get_observation, mem_stats, mem_session_start, mem_session_end.

### Rules

- Call `mem_session_start` at the beginning of every session.
- Save proactively with `mem_save` after significant work:
  bugfixes, architectural decisions, configurations, patterns discovered.
- Search with `mem_search` before starting work that may overlap previous sessions.
- Call `mem_session_summary` before ending the session. This is mandatory.
- After any context reset, call `mem_context` immediately to recover session state.

### Memory format for mem_save

- title: short descriptive title (e.g. "Fixed N+1 query in UserList")
- type: bugfix | decision | pattern | config | discovery
- content: What / Why / Where / Learned
```


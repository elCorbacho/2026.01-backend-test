# Proposal: Fix Console Log Encoding and Improve Readability

## Intent

Console logs currently show mojibake symbols (e.g., `ÔûÂ`, `Ôöé`) instead of intended separators/icons in HTTP/SQL lines. This makes operational output hard to read. We need to identify the encoding mismatch and provide a robust, clear log format for Windows terminals.

## Scope

### In Scope
- Diagnose why terminal output renders malformed symbols in log lines.
- Adjust Logback console patterns to be encoding-safe and readable.
- Improve visual log format for HTTP/SQL/application messages with clear separators.
- Ensure behavior works in typical local terminal environments (PowerShell/CMD).

### Out of Scope
- Changing business logic or API behavior.
- Reworking file logging/centralized observability stack.
- Deep performance tuning of logging volume.

## Approach

Review `logback-spring.xml` patterns and startup logging settings, then replace fragile Unicode glyph separators/icons with safer formatting (ASCII-compatible where needed), keeping color support optional. Validate by running the app/tests and confirming console output is legible for HTTP/SQL lines.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/logback-spring.xml` | Modified | Update console appenders/patterns to remove mojibake-prone symbols and improve clarity. |
| `src/main/resources/application.properties` | Optional Modified | Align ANSI/console behavior if needed for stable rendering in local terminals. |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Losing visual cues after symbol simplification | Medium | Keep structured prefixes (`HTTP`, `SQL`, level, logger) and optional colors. |
| Terminal-dependent behavior still varies | Medium | Prefer safe characters and validate on PowerShell/CMD defaults. |
| Excessive log verbosity | Low | Keep existing logger levels; only improve formatting. |

## Rollback Plan

Revert changes in `logback-spring.xml` (and optional property tweaks) to previous commit if output quality or compatibility worsens.

## Dependencies

- Existing Logback configuration (`logback-spring.xml`).
- Current local runtime terminal behavior (PowerShell/CMD encoding).

## Success Criteria

- [ ] HTTP/SQL/application log lines no longer show malformed symbols like `ÔûÂ` / `Ôöé`.
- [ ] Console output is readable and clearly structured (timestamp, level, category/logger, message).
- [ ] Logging format remains understandable across common local terminals in this project.

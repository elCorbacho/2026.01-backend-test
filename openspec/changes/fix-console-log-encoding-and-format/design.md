# Design: Fix Console Log Encoding and Improve Readability

## Technical Approach

Normalize console logging patterns in `logback-spring.xml` to encoding-safe output for local terminals (PowerShell/CMD), replacing fragile Unicode separators/icons with robust textual prefixes and ASCII-safe delimiters.  
Keep logger categories (APP/HTTP/SQL) and existing levels, and preserve UTF-8 charset in encoders.

## Architecture Decisions

### Decision: Use terminal-safe separators instead of decorative Unicode glyphs

**Choice**: Replace symbols like `│`, `▶`, box-drawing comments/separators with plain, readable tokens (`|`, `->`, `[HTTP]`, `[SQL]`).  
**Alternatives considered**:
- Keep Unicode glyphs and rely on terminal UTF-8/codepage configuration.
- Force a specific terminal profile for all users.  
**Rationale**: Current mojibake (`ÔûÂ`, `Ôöé`) proves environment variability. Safer characters provide predictable readability.

### Decision: Keep dedicated appenders per category

**Choice**: Retain `CONSOLE`, `APP_CONSOLE`, `HTTP_CONSOLE`, `SQL_CONSOLE` with improved patterns.  
**Alternatives considered**:
- Collapse all logs into one appender/pattern.
- Remove color support entirely.  
**Rationale**: Category separation is useful operationally; formatting can be fixed without losing structure.

### Decision: Preserve functional behavior and log levels

**Choice**: Only change formatting/encoding-friendly patterns, not logger levels/business logic.  
**Alternatives considered**:
- Reduce SQL/HTTP verbosity as part of same change.  
**Rationale**: Scope is encoding/readability; behavior/logging policy changes are independent concerns.

## Data Flow

```text
App emits log event
   |
   v
Logback logger category selection (APP / HTTP / SQL / root)
   |
   v
ConsoleAppender encoder pattern (ASCII-safe + UTF-8 charset)
   |
   v
Terminal renders readable line without mojibake artifacts
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/logback-spring.xml` | Modify | Replace mojibake-prone symbols in comments/patterns, improve readable structure and stable prefixes. |
| `src/main/resources/application.properties` | Optional modify | Keep/adjust ANSI behavior only if needed to reduce rendering issues in local consoles. |

## Interfaces / Contracts

No API or domain contract changes.

Logging line structure contract (example target):

```text
16:11:37.571 INFO  | [main] [HTTP] org.springframework... - GET /api/albums
16:11:05.550 DEBUG | [main] [SQL ] select * from album where ...
16:11:01.123 INFO  | [main] [APP ] ipss.web2.examen... - Started Application
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Manual runtime | Mojibake removal | Run app/tests and inspect HTTP/SQL lines for absence of `ÔûÂ`, `Ôöé`. |
| Manual runtime | Format readability | Validate timestamp + category + logger + message are clear. |
| Build/test regression | Functional stability | Run `mvnw.cmd test` and `mvnw.cmd -DskipTests package`. |

## Migration / Rollout

No migration required.  
Rollout is a logging configuration update only.

## Open Questions

- [ ] Keep ANSI colors enabled by default (`spring.output.ansi.enabled=ALWAYS`) or switch to `DETECT` for compatibility?
- [ ] Should SQL bind lines keep same prefix as SQL statements or a distinct `[BIND]` prefix?


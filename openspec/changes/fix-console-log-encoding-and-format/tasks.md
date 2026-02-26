# Tasks: Fix Console Log Encoding and Improve Readability

## Phase 1: Diagnosis / Baseline

- [x] 1.1 Capture baseline console output from current app run (`HTTP`/`SQL` lines) to confirm mojibake artifacts in local terminal.
- [x] 1.2 Identify exact problematic symbols/pattern fragments in `src/main/resources/logback-spring.xml` (separators/icons/comments) causing unreadable rendering.
- [x] 1.3 Confirm current ANSI setting and terminal assumptions from `src/main/resources/application.properties` and local runtime.

## Phase 2: Core Implementation (Logging Pattern Fix)

- [x] 2.1 Update `src/main/resources/logback-spring.xml` patterns in `CONSOLE`, `APP_CONSOLE`, `HTTP_CONSOLE`, and `SQL_CONSOLE` to use terminal-safe separators/prefixes.
- [x] 2.2 Replace glyph-dependent tokens (e.g., unicode arrows/box characters) with robust readable text tokens (e.g., `|`, `->`, `[HTTP]`, `[SQL]`).
- [x] 2.3 Keep `<charset>UTF-8</charset>` in appenders and ensure final patterns remain structured (timestamp, level/category, logger/thread, message).
- [x] 2.4 Keep existing logger category separation (`ipss.web2.examen`, `org.hibernate.SQL`, `org.hibernate.orm.jdbc.bind`, HTTP filter) without changing behavior scope.

## Phase 3: Compatibility / Optional Runtime Config

- [x] 3.1 Evaluate whether `spring.output.ansi.enabled` in `src/main/resources/application.properties` should remain `ALWAYS` or move to `DETECT` for better terminal compatibility.
- [x] 3.2 If changed, update `src/main/resources/application.properties` and document rationale in implementation summary.

## Phase 4: Verification

- [x] 4.1 Run `.\mvnw.cmd test` and verify no functional regression after logging config updates.
- [x] 4.2 Run `.\mvnw.cmd -DskipTests package` and verify build success.
- [x] 4.3 Manual check: run app and confirm no malformed symbols (`ÔûÂ`, `Ôöé`) appear in HTTP/SQL/application logs.
- [ ] 4.4 Manual check: confirm line format is clear and category prefixes are distinguishable in PowerShell/CMD output.

## Phase 5: Cleanup / Documentation

- [x] 5.1 Update `openspec/changes/fix-console-log-encoding-and-format/tasks.md` checkboxes during `opsx-apply`.
- [x] 5.2 Record final formatting decision (including ANSI choice) in apply summary for future maintainers.

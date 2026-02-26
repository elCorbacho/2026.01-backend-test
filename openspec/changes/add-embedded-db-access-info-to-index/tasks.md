# Tasks: Add Embedded DB Access Links and Credentials to Index Single-Page

## Phase 1: Foundation / Source Alignment

- [x] 1.1 Read `src/main/resources/application.properties` and capture local embedded DB values to display (`spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`, `spring.h2.console.path`).
- [x] 1.2 Define final placement for embedded DB info block in `src/main/resources/static/index.html` (inside `#setup`, `#recursos`, or both summary+detail).
- [x] 1.3 Confirm displayed content excludes all commented MySQL/RDS production credentials from `application.properties`.

## Phase 2: Core Implementation (One-Page UI)

- [x] 2.1 Add an embedded DB access block in `src/main/resources/static/index.html` using existing components (`card-base`, `feature-list`, `code-wrap`).
- [x] 2.2 Add direct link to `/h2-console` in the embedded DB block and ensure label clearly indicates local embedded DB console.
- [x] 2.3 Add readable local connection/login data in `index.html` (JDBC URL, user `sa`, password vacía).
- [x] 2.4 Add a small note in `index.html` stating that production credentials are not shown in this UI.

## Phase 3: Navigation / Integration

- [x] 3.1 If a dedicated anchor is created (e.g., `#db-embebida`), add desktop navbar link in `src/main/resources/static/index.html`.
- [x] 3.2 If a dedicated anchor is created, add mobile/offcanvas link with `data-bs-dismiss=\"offcanvas\"` in `src/main/resources/static/index.html`.
- [x] 3.3 If a dedicated anchor is created, include it in `secs` array in `src/main/resources/static/index.html` for active-nav behavior.
- [x] 3.4 Add/adjust footer quick link in `src/main/resources/static/index.html` pointing to embedded DB info location.

## Phase 4: Verification (Spec Scenarios)

- [ ] 4.1 Manual check: open `/` and verify embedded DB info block is visible in one-page flow.
- [ ] 4.2 Manual check: click `/h2-console` link and verify console opens.
- [ ] 4.3 Manual check: compare displayed JDBC URL/user/password against `src/main/resources/application.properties` local H2 values.
- [ ] 4.4 Manual check: verify no MySQL/RDS production credentials appear in the UI block.
- [ ] 4.5 Manual check: validate visual consistency (spacing/cards/typography) with adjacent `index.html` sections in light and dark themes.

## Phase 5: Cleanup / Documentation

- [x] 5.1 Update this `tasks.md` progress checkboxes during `opsx-apply`.
- [x] 5.2 Document final placement decision (setup/recursos/both) in change notes or implementation summary for future maintainers.

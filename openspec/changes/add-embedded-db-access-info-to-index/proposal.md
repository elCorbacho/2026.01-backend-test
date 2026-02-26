# Proposal: Add Embedded DB Access Links and Credentials to Index Single-Page

## Intent

Users need direct, clear access instructions for the embedded database from the one single-page `index.html`, including the login URL and credentials required to enter quickly during local development.

## Scope

### In Scope
- Add a dedicated block/section in `index.html` with embedded DB access info.
- Include direct links to embedded DB tools (e.g., H2 Console).
- Display required connection/login data (URL, user, password, JDBC URL) in a readable format.
- Keep styling consistent with the existing index one-page design.

### Out of Scope
- Changing database engine or authentication behavior.
- Exposing production credentials.
- Backend API changes unrelated to documentation/access UI.

## Approach

Extend `src/main/resources/static/index.html` with a new in-page UI block (cards/code blocks) under setup/resources/data context. Reuse existing visual components (`card-base`, `feature-list`, `code-wrap`) and internal anchors so users can find embedded DB access from desktop/mobile navigation.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modified | Add embedded DB links and credentials/instructions in one-page format. |
| `src/main/resources/application.properties` | Referenced | Use current embedded DB config values as source of truth for displayed data. |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Showing sensitive data unintentionally | Medium | Only show local/dev embedded DB credentials; never include production secrets. |
| Stale credentials in UI vs config | Medium | Align text with `application.properties` and keep values minimal. |
| UI clutter in index | Low | Keep content concise and grouped in existing section style. |

## Rollback Plan

Revert the `index.html` changes and remove the embedded DB access block/links, returning to the previous one-page state.

## Dependencies

- Existing static single-page structure in `index.html`.
- Embedded DB configuration currently defined in `src/main/resources/application.properties`.

## Success Criteria

- [ ] `index.html` includes visible embedded DB access links in one single-page flow.
- [ ] The page shows correct local embedded DB access data (console URL and login credentials).
- [ ] New content matches current index style and remains usable on desktop and mobile.

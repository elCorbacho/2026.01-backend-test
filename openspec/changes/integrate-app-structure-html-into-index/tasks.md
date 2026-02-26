# Tasks: Integrate Application Structure HTML into Index Single-Page

## Phase 1: Foundation / Content Mapping

- [x] 1.1 Identify and extract the “application structure” source content from `src/main/resources/static/*.html` that must be represented in `index.html`.
- [x] 1.2 Define the final anchor id and menu label for the new section in `src/main/resources/static/index.html` (`#estructura-app` or confirmed alternative).
- [x] 1.3 Confirm section placement order inside `index.html` relative to existing sections (`#datos`, `#vistas`, `#sdd`, `#recursos`).

## Phase 2: Core Implementation (Index Section Integration)

- [x] 2.1 Add a new `<div class="page-section" id="estructura-app">...</div>` block in `src/main/resources/static/index.html` with integrated application-structure content.
- [x] 2.2 Build the section using existing UI primitives in `index.html` (`section-label`, `section-title`, `card-base`, `feature-list`, `code-wrap`) to preserve design consistency.
- [x] 2.3 Ensure section copy is concise and avoids duplicating full standalone pages while preserving core structure information.
- [x] 2.4 Keep operational external links (e.g., `/swagger-ui.html`) unchanged and functional in `src/main/resources/static/index.html`.

## Phase 3: Navigation / Behavior Wiring

- [x] 3.1 Add desktop navbar link in `src/main/resources/static/index.html` pointing to the new section anchor.
- [x] 3.2 Add offcanvas/mobile navigation link in `src/main/resources/static/index.html` with `data-bs-dismiss="offcanvas"` for proper close behavior.
- [x] 3.3 Add footer quick-link in `src/main/resources/static/index.html` pointing to the new section anchor.
- [x] 3.4 Update the `secs` array in the index script (`src/main/resources/static/index.html`) to include the new section id for active-link highlighting.

## Phase 4: Verification (Spec Scenario Coverage)

- [ ] 4.1 Manual check: from `/`, click desktop nav item and verify in-page scroll reaches the new section.
- [ ] 4.2 Manual check: on mobile/offcanvas, click nav item and verify scroll + offcanvas closes.
- [ ] 4.3 Manual check: scroll through page and verify active nav highlighting works when the new section is in viewport.
- [ ] 4.4 Manual check: compare visual consistency (spacing/cards/colors/typography) of the new section against adjacent sections in light and dark theme.
- [ ] 4.5 Manual check: verify `/swagger-ui.html` link still opens correctly after integration.

## Phase 5: Cleanup / Documentation

- [x] 5.1 Update `openspec/changes/integrate-app-structure-html-into-index/tasks.md` checkboxes as tasks complete during `sdd-apply`.
- [x] 5.2 Decide and document whether any legacy standalone application-structure HTML should remain linked or be considered deprecated in a future change.

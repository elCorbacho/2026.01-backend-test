# Proposal: Integrate Application Structure HTML into Index Single-Page

## Intent

We need the “application structure” HTML content to live inside `index.html` as part of the one single-page experience, instead of requiring navigation to a separate standalone page. This keeps documentation centralized, consistent in style, and easier to maintain.

## Scope

### In Scope
- Add a dedicated section in `index.html` for “application structure”.
- Reuse existing `index.html` design system (tokens, cards, spacing, typography).
- Update internal navigation (desktop/mobile/footer) so the new content is reachable via in-page anchors.
- Ensure active-nav behavior includes the new section.

### Out of Scope
- Rebuilding backend APIs or controllers.
- Full redesign of all existing documentation pages.
- Replacing Swagger/OpenAPI external tooling links.

## Approach

Implement a new in-page section (e.g., `#estructura-app`) using current `index.html` components (`page-section`, `card-base`, `feature-list`, `code-wrap`). Move/summarize the relevant application-structure content from the source HTML into this section and wire menu links to the anchor. Keep the final page visually consistent with existing index style and behavior.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modified | Add new one-page section, update menus/footer anchors, and update JS section tracking. |
| `src/main/resources/static/*.html` (application-structure source) | Reviewed | Source content is referenced/migrated into index section as needed. |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Navigation regressions after adding new anchor section | Medium | Validate desktop nav, offcanvas nav, smooth scroll, and active section highlighting. |
| Visual inconsistency vs current landing design | Low | Reuse existing CSS classes and avoid introducing a parallel style system. |
| Duplicate/overlapping information across pages | Medium | Keep integrated content concise and link to tools where appropriate. |

## Rollback Plan

Revert `src/main/resources/static/index.html` to the previous commit and restore original links to standalone HTML pages if any navigation or layout issue appears in production.

## Dependencies

- Existing Bootstrap and theme logic already used by `index.html`.
- Current static HTML sources that contain application-structure content.

## Success Criteria

- [ ] `index.html` contains a dedicated “application structure” section in one-page format.
- [ ] Desktop and mobile navigation can reach the section via anchor link.
- [ ] Active navigation highlighting works correctly when the section is in view.
- [ ] The integrated content matches the visual style and spacing conventions of `index.html`.

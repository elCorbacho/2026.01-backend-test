# Design: Integrate Application Structure HTML into Index Single-Page

## Technical Approach

Implement the application-structure content as a new in-page section inside `src/main/resources/static/index.html` (anchor-based navigation), reusing the existing visual system and JS behaviors already present in the landing.  
This design maps directly to the spec requirements for: in-page section, style consistency, desktop/mobile navigation integration, active-link highlighting, and preserving operational external links.

## Architecture Decisions

### Decision: Keep a single static-document architecture

**Choice**: Integrate content directly in `index.html` as another `page-section` block (e.g., `id="estructura-app"`).  
**Alternatives considered**:
- Keep a standalone HTML page and link externally.
- Load external HTML dynamically via JS (fetch/iframe/partial injection).  
**Rationale**: The project already uses a static single-page composition with anchor sections; extending that pattern minimizes complexity and keeps behavior predictable.

### Decision: Reuse existing design primitives from index

**Choice**: Use existing classes (`page-section`, `card-base`, `feature-list`, `section-label`, `section-title`, `code-wrap`) and existing tokens (`--surface`, `--border`, `--text-muted`, etc.).  
**Alternatives considered**:
- Introduce a new stylesheet/theme for the new section.
- Copy full CSS from source standalone pages.  
**Rationale**: Prevents visual drift and avoids style conflicts; aligns with user requirement to respect the current index format/style.

### Decision: Extend existing navigation observer model

**Choice**: Add the new section id to the `secs` array used by the current `IntersectionObserver` active-nav logic.  
**Alternatives considered**:
- Separate observer only for the new section.
- Remove active nav state for docs sections.  
**Rationale**: Current code centralizes active-section behavior in a single list; extending that list is the least risky and most maintainable option.

## Data Flow

This change is UI-static and client-side only (no backend/API changes).

```text
User click nav/offcanvas link (#estructura-app)
        |
        v
Browser in-page anchor scroll (same document: index.html)
        |
        v
Section enters viewport
        |
        v
IntersectionObserver updates active nav link
```

External links (e.g., Swagger) continue unchanged:

```text
User click /swagger-ui.html
        |
        v
Normal browser navigation to SpringDoc UI
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modify | Add dedicated application-structure section, wire desktop/mobile/footer anchors, and include section id in active-nav section tracking. |
| `src/main/resources/static/*.html` (source reference pages) | No direct change required | Used only as content source/reference for migration/summarization into index. |

## Interfaces / Contracts

No backend contract changes.  
No API route changes under `/api/**`.

Front-end anchor contract additions in `index.html`:

```html
<!-- Desktop / mobile / footer -->
<a href="#estructura-app">Estructura App</a>

<!-- Section -->
<div class="page-section" id="estructura-app">...</div>
```

JS section tracking update:

```js
var secs = ['overview','setup','datos','estructura-app','vistas','sdd','recursos']
  .map(function(id){ return document.getElementById(id); })
  .filter(Boolean);
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Manual UI | Anchor navigation (desktop) | Click navbar item and verify smooth scroll to `#estructura-app`. |
| Manual UI | Anchor navigation (mobile) | Open offcanvas, click item, verify scroll + menu close. |
| Manual UI | Active nav highlight | Scroll through sections and verify link activation when section is in viewport. |
| Manual UI | Visual consistency | Compare spacing/cards/typography with neighboring index sections in light/dark mode. |
| Manual UI | External link integrity | Verify `/swagger-ui.html` still opens correctly. |

## Migration / Rollout

No migration required.  
Rollout is a static content update to `index.html`.

## Open Questions

- [ ] Confirm preferred section label text in nav: `Estructura App` vs `Estructura de la aplicación`.
- [ ] Confirm whether legacy standalone structure page should remain accessible or be removed later.


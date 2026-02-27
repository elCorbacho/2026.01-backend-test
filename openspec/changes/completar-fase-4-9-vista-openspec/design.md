# Design: Completar Tabla Comparativa e Integración — Vista OpenSpec y Skills Teams

## Technical Approach

This design completes Phases 4-8 by implementing two missing sections in `openspec-y-skills-teams.html`:

1. **Comparison Table** (replaces lines 532-545 placeholder): Responsive 2-column grid showing OpenSpec vs Skills Teams across 6 dimensions
2. **Integration Section** (replaces lines 547-560 placeholder): Narrative + workflow example explaining how both methodologies work together

The implementation follows existing code patterns from Phases 1-3:
- Bootstrap 5.3.3 responsive grid system
- CSS design tokens for light/dark theme support
- `.reveal` class for scroll-reveal animations
- `.card-base` for card styling
- Semantic HTML (h2 for section, h5/h6 for subsections)

---

## Architecture Decisions

### Decision: Use Bootstrap Grid for Comparison Table (vs. HTML Table)

**Choice**: Bootstrap `.row .col-12 .col-md-6` grid layout instead of semantic `<table>` element

**Alternatives considered**:
1. Semantic `<table>` with responsive CSS
2. CSS Grid (`display: grid`)
3. Flexbox row layout

**Rationale**:
- Aligns with existing project pattern (all sections use Bootstrap grid, not tables)
- Easier responsive behavior (1 column mobile → 2 columns desktop via col-12/col-md-6)
- More flexible styling for alternating backgrounds, padding, and borders
- Maintains visual consistency with other card-based layouts in the page
- Simplifies theming: use `var(--surface-2)` for alternating rows instead of table-specific styling

### Decision: Comparison Grid Structure (6 Rows as Content, Not Dynamic Data)

**Choice**: Hardcode 6 comparison rows in HTML with repeated `.row` divs instead of generating from data

**Alternatives considered**:
1. Single JavaScript function to generate rows from JSON data
2. Fetch comparison data from backend API
3. Use a template framework (Handlebars, etc.)

**Rationale**:
- This is a static informational view, not a dynamic data display
- HTML is lightweight and loads instantly (no API calls needed)
- Matches existing pattern in the page (all content is hardcoded HTML)
- Easier to maintain and edit in future (no template dependencies)
- Reduces complexity (no JS dependencies for table generation)

### Decision: Alternating Background Colors for Row Readability

**Choice**: Alternate `background: var(--surface-2)` on odd/even rows using CSS `:nth-child()` selector

**Alternatives considered**:
1. Borders between rows
2. Solid white/gray dividers
3. Subtle drop-shadow on cards
4. No visual separation

**Rationale**:
- Uses existing design token (`var(--surface-2)`)
- Respects light/dark theme automatically via CSS variables
- Improves readability without adding visual clutter
- Matches pattern already used in index.html for similar tables
- CSS-only solution (no extra markup needed)

### Decision: Workflow Example Format (Structured List + ASCII Diagram)

**Choice**:
- Narrative paragraphs explaining synergy (2-3 paragraphs)
- Step-by-step list showing a concrete workflow (e.g., "Adding a new API endpoint")
- ASCII diagram or structured table showing which methodology is used at each step

**Alternatives considered**:
1. Video or interactive diagram (requires video hosting / JS)
2. SVG diagram (requires SVG authoring tools)
3. Mermaid/PlantUML diagram (requires rendering library)
4. Pure text narrative (harder to scan)

**Rationale**:
- ASCII diagram is static, no dependencies, lightweight
- Structured list is easy to read and understand
- Fits project's documentation style (similar to code examples in earlier sections)
- Can be quickly updated in HTML if workflow changes
- Mobile-friendly (ASCII scales well on small screens)

### Decision: Mobile Layout (Stack Rows Vertically)

**Choice**:
- Desktop (≥768px): 2-column grid (OpenSpec | Skills Teams side-by-side)
- Mobile (<768px): 1-column stack (each row becomes a block with OpenSpec on top, Skills Teams below)

**Alternatives considered**:
1. Always single column (less visual impact)
2. Always two column (unusable on mobile)
3. Horizontal scroll on mobile (poor UX)
4. Collapsible accordion (more interactive, more complex)

**Rationale**:
- Responsive design per spec requirement
- Uses Bootstrap breakpoint system (`col-12 col-md-6`)
- Matches pattern used in Phase 3 (Skills Teams cases section)
- No additional CSS or JS needed (Bootstrap handles it)

---

## Data Flow

No external data flow involved. This is a static, client-side HTML+CSS+JS view.

```
User visits /openspec-y-skills-teams.html
    ↓
Browser downloads HTML
    ↓
Browser parses HTML + CSS + JS
    ↓
JavaScript initializes:
    ├─ Theme toggle (light/dark)
    ├─ Scroll progress bar
    ├─ Scroll-reveal animations (.reveal class)
    ├─ Copy-to-clipboard buttons (if any)
    └─ Active nav highlight (IntersectionObserver)
    ↓
Page renders with full styling
    ↓
User sees comparison table + integration section
```

---

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/static/openspec-y-skills-teams.html` | Modify | Replace lines 532-560 (comparison + integration placeholders) with actual content |

**No new files created** — all changes inline in existing HTML.

---

## Comparison Table: Detailed Structure

The comparison section will be structured as:

```html
<!-- SEC: Comparison Table -->
<div class="page-section" id="comparativa">
    <div class="container">
        <!-- Section header (reveal animation) -->
        <div class="reveal">
            <div class="section-label"><i class="bi bi-columns-gap"></i>Comparativa</div>
            <h2 class="section-title">OpenSpec vs Skills Agent Teams</h2>
            <p class="section-sub">Entender las diferencias y complementariedades...</p>
        </div>

        <!-- Comparison grid: 6 rows x 2 columns -->
        <div class="card-base reveal" style="margin-bottom: 32px;">
            <!-- Row 1: Purpose -->
            <div class="row g-0 mb-3">
                <div class="col-12 col-md-6 pe-md-2 mb-2 mb-md-0">
                    <div class="comparison-col" style="background: var(--surface-2); padding: 16px; border-radius: 8px;">
                        <h6 class="fw-bold mb-2">Purpose</h6>
                        <p>Plan, document, and specify changes before implementation...</p>
                    </div>
                </div>
                <div class="col-12 col-md-6 ps-md-2">
                    <div class="comparison-col" style="padding: 16px; border-radius: 8px;">
                        <h6 class="fw-bold mb-2">Purpose</h6>
                        <p>Research, implement, and verify tasks across the codebase...</p>
                    </div>
                </div>
            </div>

            <!-- Row 2: Workflow -->
            <!-- ... repeat pattern -->

            <!-- Row 3: Output -->
            <!-- ... repeat pattern -->

            <!-- Row 4: Use Cases -->
            <!-- ... repeat pattern -->

            <!-- Row 5: When to Choose This -->
            <!-- ... repeat pattern -->

            <!-- Row 6: How They Complement -->
            <!-- ... repeat pattern -->
        </div>
    </div>
</div>
```

**Key CSS classes and patterns**:
- `.page-section` — outer container with padding
- `.section-label`, `.section-title`, `.section-sub` — header styling (same as other sections)
- `.card-base` — wrapper for the entire table with border, shadow, padding
- `.reveal` — triggers scroll-reveal animation (IntersectionObserver)
- `.row .col-12 .col-md-6` — Bootstrap responsive grid
- `.comparison-col` — styling for individual comparison cells (padding, border-radius, background)

**Styling approach**:
- Alternating rows use `background: var(--surface-2)` for left column (OpenSpec)
- Right column (Skills Teams) uses default background (no alternating color)
- This creates visual hierarchy without making it confusing
- Both columns have same padding and border-radius for consistency

---

## Integration Section: Detailed Structure

The integration section will be structured as:

```html
<!-- SEC: Integration Example -->
<div class="page-section" id="integracion">
    <div class="container">
        <!-- Section header -->
        <div class="reveal">
            <div class="section-label"><i class="bi bi-link-45deg"></i>Integración</div>
            <h2 class="section-title">Cómo trabajan juntas</h2>
            <p class="section-sub">Un ejemplo práctico de cómo OpenSpec y Skills Teams se complementan...</p>
        </div>

        <!-- Narrative card -->
        <div class="card-base reveal" style="margin-bottom: 32px;">
            <h5 class="fw-bold mb-3">Sinergias</h5>
            <p>Paragraph 1: Explaining that OpenSpec defines WHAT and Skills Teams do HOW...</p>
            <p>Paragraph 2: Real-world example from this project (SDD workflow)...</p>
            <p>Paragraph 3: Benefits of using both together...</p>
        </div>

        <!-- Workflow example card -->
        <div class="card-base reveal" style="margin-bottom: 32px;">
            <h5 class="fw-bold mb-3">Ejemplo: Agregar un nuevo endpoint API</h5>
            <p style="color: var(--text-muted); margin-bottom: 16px; font-size: 0.9rem;">
                Este es un flujo típico que usa ambas metodologías:
            </p>
            <ol class="feature-list" style="list-style: decimal; padding-left: 20px;">
                <li><span><strong>Explore (Skills Teams):</strong> Investigar arquitectura actual de endpoints...</span></li>
                <li><span><strong>Propose (OpenSpec):</strong> Redactar proposal.md con intención...</span></li>
                <li><span><strong>Spec (OpenSpec):</strong> Definir requisitos Given/When/Then...</span></li>
                <li><span><strong>Design (OpenSpec):</strong> Documentar decisiones técnicas...</span></li>
                <li><span><strong>Apply (Skills Teams):</strong> General-purpose agent implementa el código...</span></li>
                <li><span><strong>Verify (OpenSpec/Skills Teams):</strong> Validar contra spec...</span></li>
            </ol>
        </div>

        <!-- ASCII diagram card -->
        <div class="card-base reveal">
            <h5 class="fw-bold mb-3">Diagrama de Flujo</h5>
            <div class="code-wrap mt-0">
                <div class="code-header">
                    <span class="code-dot r"></span><span class="code-dot y"></span><span class="code-dot g"></span>
                    <span class="code-file">workflow</span>
                </div>
                <pre>OpenSpec              Skills Teams
   │                      │
   ├─ Explore ────────────┤ (investigar)
   │                      │
   ├─ Propose             │
   │                      │
   ├─ Spec & Design       │
   │                      │
   │       Apply ─────────┤ (implementar)
   │                      │
   ├─ Verify ─────────────┤ (validar)
   │                      │
   └─────────────────────►│
</pre>
                <button class="btn-copy" style="top: 38px" onclick="copyCode('diagram-workflow',this)"><i class="bi bi-copy me-1"></i>Copiar</button>
            </div>
        </div>

        <!-- Key insights card -->
        <div class="card-base reveal">
            <h5 class="fw-bold mb-3">Insights Clave</h5>
            <ul class="feature-list">
                <li><i class="bi bi-check-circle-fill"></i><span><strong>OpenSpec es el "QUÉ":</strong> Define claramente qué se quiere lograr...</span></li>
                <li><i class="bi bi-check-circle-fill"></i><span><strong>Skills Teams son el "CÓMO":</strong> Herramientas para investigar, diseñar e implementar...</span></li>
                <li><i class="bi bi-check-circle-fill"></i><span><strong>Juntos son poderosos:</strong> OpenSpec evita sobre-ingeniería, Skills Teams aceleran ejecución...</span></li>
                <li><i class="bi bi-check-circle-fill"></i><span><strong>Trazabilidad completa:</strong> Cada decisión queda documentada desde propuesta hasta verificación...</span></li>
            </ul>
        </div>
    </div>
</div>
```

---

## Theme Support: CSS Implementation

Both new sections will use existing design tokens. No new CSS variables need to be added.

**Light theme** (already defined in `<style>` block):
- `--text: #0f172a` (dark text)
- `--text-muted: #5b6678` (secondary text)
- `--surface-1: #ffffff` (card background)
- `--surface-2: #eaf0f6` (alternating row background)
- `--border: #d7e0ea`

**Dark theme** (already defined in `html[data-bs-theme="dark"]`):
- `--text: #e6edf3` (light text)
- `--text-muted: #7d8590` (secondary text)
- `--surface-1: #161b22` (card background)
- `--surface-2: #21262d` (alternating row background)
- `--border: #30363d`

No additional CSS needed — use `var(--surface-2)` and it automatically respects the theme.

---

## Responsive Breakpoints Implementation

Using Bootstrap's standard breakpoints:

| Breakpoint | Width | Behavior |
|-----------|-------|----------|
| Mobile | <576px | `col-12` (full width) |
| Tablet | 768px | `col-md-6` transition point |
| Desktop | ≥1024px | `col-md-6` (2 equal columns) |

**Comparison table grid**:
```html
<div class="row g-0">
    <div class="col-12 col-md-6"><!-- OpenSpec column --></div>
    <div class="col-12 col-md-6"><!-- Skills Teams column --></div>
</div>
```

The `.g-0` removes Bootstrap's default gutter for tighter spacing.

---

## Navigation Integration

The navbar already has a link to `#comparativa` and `#integracion` (from Phase 2). The JavaScript IntersectionObserver (already implemented) will automatically:
1. Detect when each section becomes visible
2. Highlight the corresponding navbar link
3. Update the active state

**No changes to navbar or JavaScript needed** — it already works.

---

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| **Structural** | HTML validity, semantic tags (h2, h3, lists) | Validate HTML in browser, check DevTools Elements tab |
| **Visual Regression** | Comparison table layout on mobile/tablet/desktop, color contrast, alternating rows | Manual testing in DevTools responsive mode (375px, 768px, 1920px) |
| **Theme** | Light/dark theme colors, persistence across reload | Toggle theme button, reload page, verify colors persist |
| **Navigation** | Navbar link highlight, smooth scroll to sections | Click "Metodologías" → verify page loads; click section links → verify smooth scroll |
| **Accessibility** | Keyboard navigation (Tab key), semantic HTML, color contrast (≥4.5:1) | Use WebAIM contrast checker, test Tab navigation |
| **Build** | Maven compilation, Spring Boot startup, HTTP 200 response | Run `mvn clean install`, `mvn spring-boot:run`, test in browser |

**No automated tests needed** — this is a static informational view. Manual testing is sufficient and aligns with Phase 6 spec.

---

## Rollback Plan

If issues arise after implementation:

1. **Quick rollback** (revert to placeholder):
   ```bash
   git checkout src/main/resources/static/openspec-y-skills-teams.html
   ```

2. **If build fails**, clean and rebuild:
   ```bash
   mvn clean install
   ```

3. **If theme toggle breaks**, check if JavaScript was accidentally modified. If yes:
   - Revert the entire file
   - Carefully re-apply only HTML changes (no JS changes should be needed)

**No data migration needed** — this is a frontend-only change.

---

## Open Questions

- [ ] **Comparison table content**: Should we use exact text from the original proposal, or refine it? (Currently assuming we'll create clear, concise descriptions fitting each methodology's purpose)
- [ ] **Workflow example**: Should the "Agregar un nuevo endpoint API" example be used, or a different scenario? (Currently assuming this is representative of project work)
- [ ] **Integration insights**: Are the 4 key insights listed in this design sufficient, or should we add more? (Currently assuming 4 is good balance between informative and concise)

---

## Implementation Checklist (High-Level)

1. Identify and read lines 532-560 (placeholders) in `openspec-y-skills-teams.html`
2. Replace placeholder for "comparativa" section with actual table (6 rows)
3. Replace placeholder for "integracion" section with narrative + workflow + diagram + insights
4. Use Bootstrap grid (`row`, `col-12`, `col-md-6`) for responsiveness
5. Use CSS variables (`var(--text)`, `var(--surface-2)`, etc.) for theming
6. Use `.reveal` class for scroll animations
7. Use `.card-base` for card styling
8. Test in DevTools: 375px, 768px, 1920px
9. Test theme toggle
10. Run `mvn clean install`
11. Run `mvn spring-boot:run` and verify HTTP 200

---

## Notes

- This design builds on existing Phases 1-3 patterns (no new patterns introduced)
- All content is static HTML (no JavaScript required for table rendering)
- Theme support is automatic via CSS variables (no additional work needed)
- Responsiveness uses Bootstrap's native system (no custom media queries needed)
- The page will be semantically valid and accessible (no special accessibility work beyond using proper heading hierarchy)

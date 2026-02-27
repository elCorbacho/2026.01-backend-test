# Design: OpenSpec & Skills Agent Teams Comparison View

## Technical Approach

Create a new static HTML page (`openspec-y-skills-teams.html`) that is 100% visually and functionally consistent with the existing `index.html`. The approach reuses the design token system, Bootstrap 5 grid, and JavaScript utilities already present in the project. No new dependencies, libraries, or backend changes required.

**Key principle**: Copy, don't innovate. Mirror the exact structure and styling of `index.html` to guarantee consistency.

---

## Architecture Decisions

### Decision: Static HTML File vs. Template Engine

**Choice**: Create a new static HTML file (`openspec-y-skills-teams.html`) in `src/main/resources/static/`

**Alternatives considered**:
- Using a template engine (Thymeleaf) to generate HTML from a template
- Creating a new Spring Controller to serve dynamic content
- Adding a single-page app (React, Vue) component

**Rationale**:
- The project currently serves static HTML pages (index.html, sdd-como-funciona.html, etc.) without a template engine
- Static HTML is simpler to maintain, cache-friendly, and requires no backend processing
- Consistent with existing project patterns
- No performance penalty for educational content that doesn't change frequently
- Easier to version-control and audit changes

---

### Decision: CSS Strategy (Design Tokens)

**Choice**: Embed the exact same `<style>` block from `index.html` into the new page, including all CSS custom properties (design tokens) and responsive utilities.

**Alternatives considered**:
- Link to a shared `common.css` file
- Use only Bootstrap classes (no custom CSS)
- Create minimal CSS overrides

**Rationale**:
- Guarantees 100% visual consistency with index.html
- Avoids dependency on external stylesheets that could be changed
- Each page is self-contained and can be served independently
- Design tokens (`--primary`, `--surface-1`, `--text`, etc.) are embedded, so theme toggle works immediately
- Minor code duplication is acceptable for maintainability and decoupling

---

### Decision: Navigation Link Placement

**Choice**: Add a new `<li class="nav-item">` in the navbar's `<ul class="navbar-nav">` between "Vistas" and "Recursos".

**Link text**: `"Metodologías"` (short, descriptive)

**Href**: `/openspec-y-skills-teams.html` (relative path, consistent with navbar pattern)

**Alternatives considered**:
- Placing it at the end of the navbar
- Creating a dropdown menu
- Using a badge or icon-only link

**Rationale**:
- Logical placement: after "Vistas" (views about the system) and before "Recursos" (external resources)
- Short text keeps the navbar compact
- Consistent with existing nav-link styling and hover states
- Relative path ensures it works whether served from root or a subdirectory

---

### Decision: Page Structure and Sections

**Choice**: Mirror the `index.html` structure with:
- Sticky navbar with theme toggle
- Hero section with title and subtitle
- Multiple content sections with cards and grids
- Responsive grid layout (1 column on mobile, 2+ on desktop)
- Footer with links
- Back-to-top button
- Toast notifications for copy-to-clipboard

**Alternatives considered**:
- Minimal landing page with just text
- Single-column layout
- No interactive elements

**Rationale**:
- Users expect consistency with other pages in the site
- Hero section immediately communicates the page's purpose
- Multiple sections with cards create visual hierarchy and scannability
- Interactive elements (copy buttons, smooth scroll) improve UX
- Responsive design ensures readability on all devices

---

### Decision: Content Sections

**Choice**: Four main sections:
1. **OpenSpec Explanation** - what it is, key steps, benefits, example commands
2. **Skills Agent Teams Explanation** - what they are, available agents, use cases
3. **Comparison Table** - side-by-side comparison of both approaches
4. **Integration Example** - how they work together in this project

**Alternatives considered**:
- Just a comparison table
- Video demos
- Interactive tutorials

**Rationale**:
- Follows standard pedagogical flow: concept → concept → comparison → application
- Comprehensive but not overwhelming
- Comparison table helps users quickly understand differences
- Integration example bridges theory to practice
- All content is text-based and easily maintainable

---

### Decision: Comparison Table/Grid Layout

**Choice**: Responsive comparison using Bootstrap grid:
- **Desktop** (> 768px): 2-column grid (OpenSpec | Skills Teams)
- **Tablet** (576px - 768px): Full-width stacked rows with labels
- **Mobile** (< 576px): Single column, vertically stacked

**Styling**:
- Cards with `background: var(--surface-1)`, `border: 1px solid var(--border)`
- Row headers with `background: var(--surface-2)` for visual grouping
- Row borders for clarity
- Text aligned left for readability

**Alternatives considered**:
- HTML `<table>` element
- Two-column card layout (always 2 cols)
- Tabbed interface

**Rationale**:
- Bootstrap grid is already part of the project
- More flexible for responsive design than `<table>`
- Card styling matches other cards on index.html
- Alternating row backgrounds improve scannability
- Cleaner on mobile than forced 2-column layout

---

### Decision: Code Examples and Snippets

**Choice**: Include practical code/command examples using the same `code-wrap` pattern from `index.html`:
- Pre-formatted code blocks with monospace font (JetBrains Mono)
- Copy-to-clipboard button
- Syntax highlighting via CSS (accent colors)
- Example commands: `sdd-propose`, `sdd-spec`, agent invocation

**Alternatives considered**:
- No examples
- External syntax highlighter (Highlight.js, Prism)
- Animated code walkthroughs

**Rationale**:
- Practical examples improve learning
- Code block styling already exists in index.html (can copy)
- Copy button improves usability
- No new libraries needed
- Examples are from actual project workflow

---

### Decision: Accessibility and Semantics

**Choice**: Use proper semantic HTML:
- `<h1>` for main page title
- `<h2>` for section headings
- `<h3>` for subsection headings
- `<article>` or `<section>` for content blocks
- Descriptive link text (not "click here")
- `alt` attributes on images (if any)
- Keyboard navigation support (built-in with Bootstrap)

**Alternatives considered**:
- No semantic HTML, only `<div>` tags
- Minimal accessibility

**Rationale**:
- Improves SEO and accessibility for screen readers
- Helps with content structure and scannability
- No performance penalty
- Required by accessibility spec (WCAG 2.1 Level A minimum)

---

## Data Flow

Since this is a static page with no dynamic content, data flow is simple:

```
User Browser
     │
     ├─── HTTP GET /openspec-y-skills-teams.html
     │
   Spring Boot (serves static file)
     │
     └─── Returns HTML (CSS, JS embedded)
           │
           ├─── Browser renders HTML
           │
           ├─── JavaScript runs (theme toggle, smooth scroll, etc.)
           │
           └─── User interacts (clicks links, toggles theme)
```

**No data fetching, API calls, or server processing.**

---

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/static/openspec-y-skills-teams.html` | Create | New view with 4 sections: OpenSpec, Skills Teams, comparison, integration |
| `src/main/resources/static/index.html` | Modify | Add new nav-item link to `/openspec-y-skills-teams.html` in navbar |
| `target/classes/static/openspec-y-skills-teams.html` | Auto-generated | Maven copies static files during build (no manual change) |
| `target/classes/static/index.html` | Auto-generated | Maven copies modified index.html during build |

---

## Interfaces / Contracts

### HTML Structure (New Page)

```html
<!DOCTYPE html>
<html lang="es-CL" data-bs-theme="light">
<head>
  <!-- Meta tags, fonts, Bootstrap CSS, custom styles -->
  <style>
    /* Copy of design tokens and all CSS from index.html */
    :root { --primary: #0ea5a3; /* ... */ }
    /* ... rest of CSS ... */
  </style>
</head>
<body>
  <!-- Navbar (copy from index.html) -->
  <nav id="mainNav" class="navbar navbar-expand-lg sticky-top py-2">
    <!-- ... navbar content with new link ... -->
  </nav>

  <!-- Hero section -->
  <div id="hero-section">
    <!-- Title, subtitle, eyebrow -->
  </div>

  <!-- Main content sections -->
  <main>
    <!-- Section 1: OpenSpec -->
    <div class="page-section">
      <!-- Card with heading, paragraphs, code examples -->
    </div>

    <!-- Section 2: Skills Agent Teams -->
    <div class="page-section">
      <!-- Card with heading, paragraphs, code examples -->
    </div>

    <!-- Section 3: Comparison -->
    <div class="page-section">
      <!-- 2-column grid comparison -->
    </div>

    <!-- Section 4: Integration -->
    <div class="page-section">
      <!-- Example of how both work together -->
    </div>
  </main>

  <!-- Footer (copy from index.html) -->
  <footer class="site-footer">
    <!-- ... footer content ... -->
  </footer>

  <!-- Back to top button -->
  <button id="backTop">...</button>

  <!-- Toast notifications -->
  <div class="toast-container">...</div>

  <!-- JavaScript (copy from index.html, modified for new page) -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    // Theme toggle, back-to-top, scroll-reveal, copy-to-clipboard, smooth scroll
  </script>
</body>
</html>
```

### Navbar Link Structure (Modified in index.html)

```html
<!-- Add this line in index.html after "Vistas" link -->
<li class="nav-item"><a class="nav-link px-3 py-1" href="/openspec-y-skills-teams.html">Metodologías</a></li>
```

### CSS Custom Properties (Embedded in new page)

All design tokens from `index.html` are copied:
- `--primary`, `--primary-d`, `--primary-dim`, `--primary-glow`
- `--surface`, `--surface-1`, `--surface-2`
- `--border`, `--border-soft`
- `--text`, `--text-muted`
- `--radius`, `--radius-lg`

These automatically adapt for light/dark theme based on `html[data-bs-theme]` attribute.

---

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| **Visual** | Page renders correctly in light/dark theme | Manual browser testing (Chrome, Firefox, Safari, Edge) on desktop, tablet, mobile |
| **Responsive** | Layout adapts to mobile (< 576px), tablet (576-991px), desktop (> 991px) | Browser DevTools: test at 375px, 768px, 1920px viewport widths |
| **Navigation** | Navbar link works, theme toggle affects new page, smooth scroll works | Click navbar link and verify page loads; toggle theme and verify colors change; click internal links and verify smooth scroll |
| **Content** | All sections render, text is readable, code blocks display correctly | Visual inspection; verify no overflow, no text cutoff, code is monospace |
| **Accessibility** | Page is keyboard navigable, semantic HTML is used | Tab through page; inspect HTML for proper heading hierarchy; test with screen reader (NVDA/JAWS) |
| **Performance** | Page loads quickly, no layout shift | Lighthouse audit (target: > 90 Performance score); check time to interactive |
| **Cross-browser** | Page works in modern browsers | Test on Chrome, Firefox, Safari, Edge (desktop and mobile versions) |

---

## Migration / Rollout

**No migration required.**

This is a pure addition of a new static page with a navbar link. No data migration, database changes, or feature flags needed. The page is immediately available to all users after deployment.

---

## Build and Deployment

1. **Local Development**:
   - Add new file `src/main/resources/static/openspec-y-skills-teams.html`
   - Modify `src/main/resources/static/index.html`
   - Run `mvn clean install && mvn spring-boot:run`
   - Verify page loads at `http://localhost:8080/openspec-y-skills-teams.html`

2. **Maven Build**:
   - Maven's `maven-resources-plugin` automatically copies all files from `src/main/resources/static/` to `target/classes/static/`
   - Spring Boot serves these files as static resources via default `/resources/static/` mapping

3. **Deployment**:
   - Package with `mvn package` (creates JAR with static files included)
   - Deploy JAR to production
   - New page is immediately available

---

## Open Questions

- [ ] Should the comparison table compare by specific dimensions (Purpose, Workflow, Output, When to use, Integration)? Or different dimensions?
- [ ] Should we include a "When to use each one" section before the comparison table?
- [ ] Do we want links to external resources (SDD docs, Agent docs) or keep it self-contained?
- [ ] Should the page have an estimated read time or table of contents?

---

## Implementation Notes

### CSS Copy Strategy
The new page will have an embedded `<style>` block that is a **complete copy** of the styles from `index.html`. This is intentional for:
- Self-contained rendering
- No broken links to external stylesheets
- Easy to deploy independently
- Cache-friendly (each page loads everything it needs)

If styles are ever updated in the future, both pages should be updated to stay in sync. (This is a trade-off we accept.)

### JavaScript Compatibility
The JavaScript from `index.html` is mostly compatible with the new page:
- Theme toggle works (uses `data-bs-theme` attribute)
- Smooth scroll works (any `<a href="#id">` links)
- Back-to-top button works
- Copy-to-clipboard works (if code blocks have ID)

The section IDs in the observer script (`['overview','setup',...,'sdd']`) need to be updated to match new page sections, OR the script can be left as-is and just not activate active-link highlighting (acceptable).

### Mobile Menu
The offcanvas menu in navbar also needs the new link added, following the same pattern:
```html
<a href="/openspec-y-skills-teams.html" class="btn btn-ghost text-start" data-bs-dismiss="offcanvas">
  <i class="bi bi-diagram-3 me-2"></i>Metodologías
</a>
```

---

## Summary

| Aspect | Decision |
|--------|----------|
| **Page Type** | Static HTML (no template engine, no API calls) |
| **Styling** | Embedded CSS (copy of index.html design tokens) |
| **Navigation** | New navbar link + offcanvas menu item |
| **Layout** | Bootstrap 5 grid (responsive: 1 col mobile, 2+ col desktop) |
| **Content** | 4 sections: OpenSpec, Skills Teams, Comparison, Integration |
| **Interactivity** | Theme toggle, smooth scroll, back-to-top, copy-to-clipboard |
| **Build** | Maven copies static files (no special processing) |
| **Testing** | Manual visual + browser testing (responsive, theme, accessibility) |
| **Performance** | < 2s load time expected (static HTML, no external APIs) |

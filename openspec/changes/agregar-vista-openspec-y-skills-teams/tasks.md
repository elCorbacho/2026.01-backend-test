# Tasks: Agregar vista comparativa de OpenSpec y Skills Agent Teams

## Phase 1: Foundation & Structure Setup

- [x] 1.1 Create `src/main/resources/static/openspec-y-skills-teams.html` as empty file with basic HTML skeleton
- [x] 1.2 Copy the entire `<style>` block from `index.html` (lines 13-396) into the new file's `<head>`
- [x] 1.3 Copy the entire navbar HTML from `index.html` (lines 401-434) into new file, excluding nav-item links
- [x] 1.4 Copy the offcanvas mobile menu structure from `index.html` (lines 436-454) into new file
- [x] 1.5 Create hero section with title "OpenSpec y Skills Agent Teams", subtitle, and eyebrow badge
- [x] 1.6 Copy footer HTML from `index.html` (lines 1204-1225) into new file
- [x] 1.7 Copy back-to-top button from `index.html` (lines 1227-1230) into new file
- [x] 1.8 Copy toast notification container from `index.html` (lines 1232-1240) into new file
- [x] 1.9 Add all required Bootstrap and font CDN links from `index.html` (lines 8-12) to new file `<head>`

## Phase 2: Navbar & Navigation Links

- [x] 2.1 Add new `<li class="nav-item">` in navbar's `<ul class="navbar-nav mx-auto gap-1">` with text "Metodologías" and link `/openspec-y-skills-teams.html`
- [x] 2.2 Position new link between "Vistas" and "Recursos" items in navbar
- [x] 2.3 Add corresponding button in offcanvas mobile menu with same icon (bi-diagram-3) and text "Metodologías"
- [x] 2.4 Update `index.html` navbar to include the new link (same changes as 2.1-2.3)
- [x] 2.5 Verify navbar link style is consistent: `class="nav-link px-3 py-1"`

## Phase 3: Content Sections - Part A (OpenSpec & Skills Teams)

### Section 1: OpenSpec Explanation

- [x] 3.1 Create `<div class="page-section" id="openspec">` with section-label, section-title, section-sub
- [x] 3.2 Add card explaining "¿Qué es OpenSpec?" with definition and list of key benefits (at least 3)
- [x] 3.3 Add subsection "Flujo recomendado" with 8-step ordered list (Explore → Propose → Spec → Design → Tasks → Apply → Verify → Archive)
- [x] 3.4 Add code example block showing `sdd-propose "cambio-nombre"` with copy button (use code-wrap pattern from index.html)
- [x] 3.5 Add code example showing directory structure of `openspec/changes/`
- [x] 3.6 Add "Buenas prácticas" card with 4-5 best practices for using OpenSpec

### Section 2: Skills Agent Teams Explanation

- [x] 3.7 Create `<div class="page-section" id="skills-teams">` with section-label, section-title, section-sub
- [x] 3.8 Add card explaining "¿Qué son Skills Agent Teams?" with definition and overview
- [x] 3.9 Add list of available agents with brief description (general-purpose, Explore, Plan, frontend-ui-ux-expert, etc.)
- [x] 3.10 Add "Cómo funcionan" subsection describing agent invocation and tool access
- [x] 3.11 Add code example showing how to invoke an agent (e.g., task structure)
- [x] 3.12 Add "Casos de uso" card with 3-4 real examples from project context

## Phase 4: Content Sections - Part B (Comparison & Integration)

### Section 3: Comparison Table

- [ ] 4.1 Create `<div class="page-section" id="comparativa">` with section-label, section-title, section-sub
- [ ] 4.2 Create responsive comparison grid using Bootstrap grid system (2 columns for desktop, 1 column mobile)
- [ ] 4.3 Add comparison row headers: "Purpose" with 2-column content (OpenSpec | Skills Teams)
- [ ] 4.4 Add comparison row: "Workflow" with descriptions for each methodology
- [ ] 4.5 Add comparison row: "Output" with deliverables for each
- [ ] 4.6 Add comparison row: "Primary Use Case" with typical scenarios
- [ ] 4.7 Add comparison row: "When to Choose This" with decision criteria
- [ ] 4.8 Add comparison row: "Complementary with Other?" explaining how they work together
- [ ] 4.9 Style comparison rows: alternating `background: var(--surface-2)` for readability, borders, proper padding
- [ ] 4.10 Ensure comparison table is mobile-friendly (test at 375px viewport)

### Section 4: Integration Example

- [ ] 4.11 Create `<div class="page-section" id="integracion">` with section-label, section-title, section-sub
- [ ] 4.12 Add narrative explaining how OpenSpec and Skills Teams work together in this project
- [ ] 4.13 Add visual example: "A typical workflow" showing steps where each is used
- [ ] 4.14 Add code example or diagram showing integration flow (ASCII diagram or structured text)
- [ ] 4.15 Add "Key Insights" card with bullet points about synergy between both approaches

## Phase 5: Styling, Theme, & Responsiveness

- [ ] 5.1 Verify all section classes use: `.page-section`, `.section-label`, `.section-title`, `.section-sub` (match index.html pattern)
- [ ] 5.2 Verify all cards use: `class="card-base"` or `class="card-base h-100"` for consistency
- [ ] 5.3 Verify all code blocks use: `class="code-wrap"` with nested `code-header` and `code-file` (copy structure from index.html)
- [ ] 5.4 Verify code example button pattern: `onclick="copyCode('pre-{id}',this)"` with copy icon
- [ ] 5.5 Test light theme (`data-bs-theme="light"`): all colors visible, text readable, no contrast issues
- [ ] 5.6 Test dark theme (`data-bs-theme="dark"`): all colors adapt, text readable, proper contrast
- [ ] 5.7 Test responsive breakpoints manually:
  - [ ] 5.7a Mobile (375px): sections stack, grid 1 column, navbar collapses
  - [ ] 5.7b Tablet (768px): mixed layout, navbar visible, grid 1-2 columns
  - [ ] 5.7c Desktop (1920px): full layout, 2-column comparison grid, navbar fully expanded
- [ ] 5.8 Verify theme toggle button works: clicking it switches `data-bs-theme` and updates colors
- [ ] 5.9 Verify theme preference persists: toggle theme, reload page, theme is remembered
- [ ] 5.10 Verify back-to-top button appears after scrolling 300px and works correctly

## Phase 6: JavaScript Integration

- [ ] 6.1 Copy entire JavaScript block from `index.html` (lines 1243-1325) into new file `<script>` tag
- [ ] 6.2 Update section IDs in observer script to match new page sections (replace `['overview','setup','db-embebida',...,'sdd']` with new section IDs)
- [ ] 6.3 Test smooth scroll: click internal links (`<a href="#section-id">`) and verify smooth scroll behavior
- [ ] 6.4 Test copy-to-clipboard: click copy button on code blocks, verify toast notification appears
- [ ] 6.5 Test scroll-reveal animations: scroll page, verify `.reveal` elements fade in
- [ ] 6.6 Test scroll progress bar: scroll page, verify progress bar at top moves from 0% to 100%

## Phase 7: Testing Against Specifications

- [ ] 7.1 **Requirement: New View Page Must Exist** — Navigate to `/openspec-y-skills-teams.html`, verify HTTP 200 and page loads
- [ ] 7.2 **Requirement: Visual Design Consistency** — Verify colors match index.html in light theme (--primary #0ea5a3, --surface-1 #ffffff, etc.)
- [ ] 7.3 **Requirement: Visual Design Consistency (Dark)** — Verify colors match in dark theme (--surface #0d1117, --text #e6edf3, etc.)
- [ ] 7.4 **Requirement: Navigation Includes New Link** — Check navbar has "Metodologías" link pointing to `/openspec-y-skills-teams.html`, verify it's clickable
- [ ] 7.5 **Requirement: New Page Has Header Section** — Verify header with title, subtitle, eyebrow badge, proper spacing
- [ ] 7.6 **Requirement: OpenSpec Explanation** — Verify section contains definition, 8 steps, benefits, example command
- [ ] 7.7 **Requirement: Skills Agent Teams Explanation** — Verify section contains definition, agent list, use cases
- [ ] 7.8 **Requirement: Comparison Table** — Verify table compares Purpose, Workflow, Output, Use Cases, Integration (5+ rows)
- [ ] 7.9 **Requirement: Code Examples** — Verify at least 2 code blocks with proper styling and copy buttons
- [ ] 7.10 **Requirement: Footer Matches Index** — Verify footer has brand, links, copyright, styled consistently
- [ ] 7.11 **Requirement: Responsive Design (Mobile)** — Verify layout at 375px: no overflow, readable text, stacked layout
- [ ] 7.12 **Requirement: Responsive Design (Tablet)** — Verify layout at 768px: proper spacing, 1-2 column grid
- [ ] 7.13 **Requirement: Responsive Design (Desktop)** — Verify layout at 1920px: full 2-column grid, proper max-width
- [ ] 7.14 **Requirement: Theme Toggle Works** — Verify toggling theme affects page colors immediately
- [ ] 7.15 **Requirement: Accessibility - Semantic HTML** — Verify `<h1>` (title), `<h2>` (sections), `<h3>` (subsections), proper link text
- [ ] 7.16 **Requirement: Accessibility - Keyboard Navigation** — Verify Tab key navigates through all links and interactive elements

## Phase 8: Cross-Browser Testing

- [ ] 8.1 Test on Google Chrome (desktop): verify rendering, theme toggle, smooth scroll
- [ ] 8.2 Test on Mozilla Firefox (desktop): verify rendering, no display issues, compatibility
- [ ] 8.3 Test on Safari (desktop if available): verify rendering, font rendering, CSS compatibility
- [ ] 8.4 Test on Chrome Mobile (375px): verify responsive layout, touch interactions
- [ ] 8.5 Test on Firefox Mobile (375px): verify responsive layout, touch interactions
- [ ] 8.6 Verify no console errors in browser DevTools (F12)

## Phase 9: Build & Deployment

- [ ] 9.1 Run `mvn clean install` to build project and verify no errors
- [ ] 9.2 Verify Maven copies new file to `target/classes/static/openspec-y-skills-teams.html`
- [ ] 9.3 Verify Maven copies modified `index.html` to `target/classes/static/index.html`
- [ ] 9.4 Run `mvn spring-boot:run` to start local server
- [ ] 9.5 Navigate to `http://localhost:8080/openspec-y-skills-teams.html` and verify page loads
- [ ] 9.6 Navigate to `http://localhost:8080/` (index.html) and verify new navbar link is present and functional
- [ ] 9.7 Click navbar link and verify it navigates to new page correctly

## Phase 10: Final Verification & Cleanup

- [ ] 10.1 Perform full page screenshot comparison: new page vs. index.html (verify visual consistency)
- [ ] 10.2 Verify all links work: navbar, internal anchors, footer links, buttons
- [ ] 10.3 Verify no typos or grammar errors in Spanish content
- [ ] 10.4 Verify image/icon performance: no broken images, fast loading
- [ ] 10.5 Run Lighthouse audit (Chrome DevTools): target > 90 Performance score
- [ ] 10.6 Check page load time: target < 2 seconds on typical broadband
- [ ] 10.7 Verify git status: only 2 files modified (`index.html`, new file `openspec-y-skills-teams.html`)
- [ ] 10.8 Document any decisions or issues in change log (if project uses one)

---

## Task Summary

| Phase | Tasks | Focus |
|-------|-------|-------|
| Phase 1 | 9 tasks | Foundation: HTML skeleton, CSS tokens, navbar, footer |
| Phase 2 | 5 tasks | Navigation: add link to index.html navbar |
| Phase 3 | 12 tasks | Content Part A: OpenSpec & Skills Teams sections |
| Phase 4 | 15 tasks | Content Part B: Comparison table & integration example |
| Phase 5 | 10 tasks | Styling: responsiveness, theme, colors |
| Phase 6 | 6 tasks | JavaScript: interactivity, animations, copy-to-clipboard |
| Phase 7 | 16 tasks | Testing: verify against spec requirements |
| Phase 8 | 6 tasks | Cross-browser: Chrome, Firefox, Safari, mobile |
| Phase 9 | 7 tasks | Build & deployment: Maven, Spring Boot, local verification |
| Phase 10 | 8 tasks | Final verification: screenshots, links, performance, git |
| **TOTAL** | **94 tasks** | |

---

## Implementation Order & Dependencies

1. **Start with Phase 1 (Foundation)** — All other phases depend on the HTML skeleton and styling foundation being in place
2. **Then Phase 2 (Navigation)** — Navbar link should be added early so testing can verify navigation works
3. **Parallel: Phase 3 + Phase 4 (Content)** — Can write content sections independently
4. **Then Phase 5 (Styling)** — Once content is in place, fine-tune responsiveness and theme
5. **Then Phase 6 (JavaScript)** — Wire up interactivity and animations
6. **Then Phase 7 (Specification Testing)** — Verify all requirements are met
7. **Then Phase 8 (Cross-browser)** — Test on multiple browsers/devices
8. **Then Phase 9 (Build)** — Verify build process works
9. **Finally Phase 10 (Final Verification)** — Polish and clean up

---

## Verification Checklist (After All Tasks Complete)

- [ ] New file exists: `src/main/resources/static/openspec-y-skills-teams.html`
- [ ] `index.html` has new navbar link pointing to new page
- [ ] Page loads without errors (HTTP 200)
- [ ] All spec requirements verified (Spec requirement tests 7.1-7.16 pass)
- [ ] Light & dark theme works correctly
- [ ] Responsive layout works (mobile, tablet, desktop)
- [ ] All links are clickable and functional
- [ ] Code examples have copy buttons
- [ ] No console errors
- [ ] Build succeeds: `mvn clean install`
- [ ] Local server runs without errors
- [ ] Lighthouse score > 90 Performance
- [ ] Git status shows only expected changes

---

## Notes for Implementer

- **Copy patterns, don't reinvent**: Use exact classes and patterns from `index.html` (`.card-base`, `.code-wrap`, `.page-section`, etc.)
- **Design tokens are critical**: Always use `var(--primary)`, `var(--surface-1)`, etc. — NEVER hardcode colors
- **Responsive first**: Test at 375px, 768px, and 1920px frequently during implementation
- **Test early and often**: Don't wait until Phase 7 to test — verify each section as it's built
- **Theme toggle is your friend**: Toggle between light/dark theme while building to catch color issues immediately
- **Placeholder content OK initially**: You can add placeholder text and refine it, but structure and styling should be correct
- **Reuse, reuse, reuse**: Copy navbar, footer, styling from `index.html` — consistency is key

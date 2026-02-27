# Tasks: Completar Tabla Comparativa e Integración — Vista OpenSpec y Skills Teams

## Phase 1: Preparation & Content Definition

- [x] 1.1 Review existing section patterns in `src/main/resources/static/openspec-y-skills-teams.html` (lines 350-434) to confirm styling conventions (`.page-section`, `.section-label`, `.section-title`, `.section-sub`, `.card-base`, `.reveal`)
- [x] 1.2 Verify Bootstrap grid classes in use (`row`, `col-12`, `col-md-6`, `g-3`, `g-0`) and confirm responsive breakpoints (mobile <576px, tablet 768px, desktop 1024px+)
- [x] 1.3 Confirm design tokens are correctly defined in `<style>` block (lines 14-41): light theme colors and dark theme variables
- [x] 1.4 Review design.md to confirm comparison table structure (6 rows, 2 columns desktop, 1 column mobile, alternating backgrounds)
- [x] 1.5 Review design.md to confirm integration section structure (narrative + workflow list + ASCII diagram + key insights)
- [x] 1.6 Draft final text content for 6 comparison rows:
  - Row 1: Purpose (OpenSpec vs Skills Teams descriptions)
  - Row 2: Workflow (steps for each methodology)
  - Row 3: Output/Deliverables (what artifacts each produces)
  - Row 4: Use Cases (when to use each)
  - Row 5: When to Choose This (decision criteria)
  - Row 6: How They Complement (synergies)

---

## Phase 2: Comparison Table Implementation

- [x] 2.1 Locate placeholder for comparison section in `src/main/resources/static/openspec-y-skills-teams.html` (lines 532-545, currently contains `<div class="page-section" id="comparativa">...Tabla Comparativa (Phase 4)...`)
- [x] 2.2 Replace comparison placeholder section with complete HTML structure:
  - Keep `<div class="page-section" id="comparativa">`
  - Keep `<div class="container">`
  - Keep header reveal section: `<div class="section-label">`, `<h2 class="section-title">`, `<p class="section-sub">`
  - Replace placeholder card with actual 6-row comparison grid
- [x] 2.3 Implement row 1 (Purpose):
  - Create `<div class="row g-0 mb-3">` with 2 `<div class="col-12 col-md-6">` columns
  - Left column: OpenSpec Purpose description (background: var(--surface-2))
  - Right column: Skills Teams Purpose description (default background)
  - Add padding, border-radius 8px, proper spacing per design.md
- [x] 2.4 Implement row 2 (Workflow):
  - Same grid structure as row 1
  - Add OpenSpec workflow description (5-7 steps) in left column
  - Add Skills Teams workflow description in right column
- [x] 2.5 Implement row 3 (Output/Deliverables):
  - Same grid structure
  - OpenSpec: documents (proposal.md, specs, design, tasks, verify report)
  - Skills Teams: code implementations, analysis, research artifacts
- [x] 2.6 Implement row 4 (Use Cases):
  - Same grid structure
  - OpenSpec: planning features, designing architecture, breaking down complex tasks
  - Skills Teams: code search, bug investigation, refactoring, multi-step implementations
- [x] 2.7 Implement row 5 (When to Choose This):
  - Same grid structure
  - OpenSpec decision criteria: when clarity and traceability matter
  - Skills Teams decision criteria: when you need automated tool access and multi-step execution
- [x] 2.8 Implement row 6 (How They Complement):
  - Same grid structure
  - Explanation of synergy and how they work together
- [x] 2.9 Wrap entire comparison grid in `<div class="card-base reveal">` with `style="margin-bottom: 32px;"`
- [x] 2.10 Verify all text uses semantic tags: `<h6 class="fw-bold mb-2">` for row titles, `<p>` for descriptions, proper contrast with `var(--text)` and `var(--text-muted)`

---

## Phase 3: Integration Section Implementation

- [x] 3.1 Locate placeholder for integration section in `src/main/resources/static/openspec-y-skills-teams.html` (lines 547-560, currently contains `<div class="page-section" id="integracion">...Ejemplo de Integración (Phase 4)...`)
- [x] 3.2 Replace integration placeholder with complete HTML structure:
  - Keep `<div class="page-section" id="integracion">`
  - Keep `<div class="container">`
  - Keep header reveal section: `<div class="section-label">`, `<h2 class="section-title">`, `<p class="section-sub">`
  - Replace placeholder card with actual integration content
- [x] 3.3 Create narrative card (`<div class="card-base reveal">`) explaining synergies:
  - Paragraph 1: Explain that OpenSpec defines WHAT and Skills Teams are the HOW
  - Paragraph 2: Real-world example from project (e.g., "When implementing feature X in this project...")
  - Paragraph 3: Benefits of using both together
  - Add `style="margin-bottom: 32px;"` for spacing
- [x] 3.4 Create workflow example card (`<div class="card-base reveal">`) with title "Ejemplo: Agregar un nuevo endpoint API":
  - Add introductory paragraph explaining this is a typical workflow
  - Create ordered list (`<ol class="feature-list" style="list-style: decimal; padding-left: 20px;">`) with 6 steps:
    1. Explore (Skills Teams): Investigate current endpoint patterns
    2. Propose (OpenSpec): Draft proposal.md with intent
    3. Spec (OpenSpec): Define requirements with Given/When/Then
    4. Design (OpenSpec): Document technical decisions
    5. Apply (Skills Teams): Implement code
    6. Verify (both): Validate against spec
  - Each list item should have format: `<li><span><strong>{Phase}:</strong> {description}</span></li>`
  - Add `style="margin-bottom: 32px;"` for spacing
- [x] 3.5 Create ASCII workflow diagram card (`<div class="card-base reveal">`) with title "Diagrama de Flujo":
  - Use `<div class="code-wrap">` pattern from existing sections (see lines 392-400)
  - Include `<div class="code-header">` with Mac window dots and filename
  - Create `<pre id="diagram-workflow">` with ASCII diagram showing:
    - Left column: OpenSpec phases (Explore, Propose, Spec, Design, Verify)
    - Right column: Skills Teams phases (with arrows showing interaction)
  - Add copy button: `<button class="btn-copy" onclick="copyCode('diagram-workflow',this)">`
  - Pattern should match existing code blocks (lines 392-400, 406-418)
- [x] 3.6 Create key insights card (`<div class="card-base reveal">`) with title "Insights Clave":
  - Use `<ul class="feature-list">` with `<i class="bi bi-check-circle-fill"></i>` icons
  - Add 4 insights:
    1. OpenSpec is the "WHAT" — clarifies intent and requirements
    2. Skills Teams are the "HOW" — provides tools and automation
    3. Together they're powerful — avoid over-engineering, accelerate execution
    4. Complete traceability — every decision documented from proposal to verification
  - Each insight: `<li><i class="bi bi-check-circle-fill"></i><span><strong>{Title}:</strong> {description}</span></li>`

---

## Phase 4: Responsiveness Testing & Validation

- [x] 4.1 Test mobile layout (375px viewport) in Chrome DevTools:
  - Open DevTools (F12)
  - Toggle device emulation (Ctrl+Shift+M)
  - Set viewport to 375px width
  - Verify comparison table stacks to 1 column (each row shows OpenSpec above Skills Teams)
  - Verify NO horizontal scrolling occurs
  - Verify text is readable (no font-size < 12px)
  - Verify padding and spacing looks correct
  - Verify row titles and descriptions are visible
  - Take screenshot or note any visual issues

- [ ] 4.2 Test tablet layout (768px viewport) in Chrome DevTools:
  - Set viewport to 768px width
  - Verify navbar is visible (not collapsed)
  - Verify comparison table displays properly (transition point from 1 to 2 columns)
  - Verify integration section displays properly
  - Verify code blocks don't overflow
  - Verify spacing is balanced

- [ ] 4.3 Test desktop layout (1920px viewport) in Chrome DevTools:
  - Set viewport to 1920px width
  - Verify comparison table displays 2 columns side-by-side
  - Verify max-width container applies (content not stretched too wide)
  - Verify spacing and alignment look professional
  - Verify code blocks are readable

- [ ] 4.4 Test light theme:
  - Open page in browser
  - Verify light theme is active by default
  - Verify text color matches `var(--text)` (#0f172a dark)
  - Verify alternating row backgrounds are `var(--surface-2)` (#eaf0f6 light blue-gray)
  - Verify borders use `var(--border)` (#d7e0ea)
  - Verify all text is readable (high contrast)
  - Check for any color mismatches or inconsistencies

- [ ] 4.5 Test dark theme:
  - Click theme toggle button in navbar
  - Verify page colors update immediately (no page reload)
  - Verify text color matches `var(--text)` (#e6edf3 light)
  - Verify alternating row backgrounds are `var(--surface-2)` (#21262d darker)
  - Verify borders use `var(--border)` (#30363d)
  - Verify all text is readable (contrast ≥ 4.5:1)
  - Verify code blocks are readable in dark mode

- [ ] 4.6 Test theme persistence:
  - Open page with light theme
  - Switch to dark theme
  - Reload page (F5)
  - Verify dark theme persists (browser remembered preference in localStorage)
  - Switch to light theme
  - Reload page
  - Verify light theme persists

- [ ] 4.7 Test scroll animations (scroll-reveal):
  - Load page
  - Scroll down slowly
  - Verify comparison section fades in when it comes into view (`.reveal` animation)
  - Verify integration section fades in when it comes into view
  - Verify each card has smooth entrance animation

- [ ] 4.8 Test scroll progress bar:
  - Load page
  - Scroll down
  - Verify scroll progress bar (#scroll-progress) appears at top and grows as you scroll
  - Verify it reaches 100% when you reach page bottom
  - Verify it's visible and colored correctly (`var(--primary)`)

- [ ] 4.9 Test back-to-top button:
  - Load page
  - Scroll down beyond 300px
  - Verify back-to-top button appears at bottom-right
  - Click it
  - Verify page scrolls smoothly to top
  - Verify button disappears when at top of page

- [ ] 4.10 Test active section highlighting:
  - Load page
  - Click "Comparativa" link in navbar
  - Verify page scrolls to comparison section
  - Verify "Comparativa" link is highlighted in navbar
  - Scroll down to integration section
  - Verify navbar link switches to "Integración"
  - Verify IntersectionObserver is working correctly

---

## Phase 5: Accessibility & Semantic HTML Validation

- [ ] 5.1 Validate semantic HTML structure:
  - Open page in browser
  - Open DevTools (F12)
  - Go to Elements/Inspector tab
  - Verify `<h1>` tag exists once (page title "OpenSpec y Skills Agent Teams")
  - Verify comparison section has `<h2>` (section title)
  - Verify integration section has `<h2>` (section title)
  - Verify row titles use `<h6 class="fw-bold">` or similar semantic heading
  - Verify lists use `<ul>` or `<ol>` tags (not divs styled as lists)
  - Verify links have meaningful text (not "click here")
  - Verify code blocks use `<code>` or `<pre>` tags

- [ ] 5.2 Test keyboard navigation:
  - Load page
  - Press Tab key repeatedly
  - Verify all interactive elements are reachable: navbar links, buttons, copy buttons
  - Verify focus indicator is visible (underline, border, or highlight)
  - Verify tab order is logical (left-to-right, top-to-bottom)
  - Verify no Tab key traps (can always escape with Tab or Shift+Tab)

- [ ] 5.3 Validate color contrast (light theme):
  - Use WebAIM Contrast Checker (https://webaim.org/resources/contrastchecker/)
  - Check primary text color (`var(--text)` #0f172a) against background (`var(--surface-1)` #ffffff)
  - Verify contrast ratio ≥ 4.5:1 (WCAG AA)
  - Check secondary text color (`var(--text-muted)` #5b6678) against backgrounds
  - Verify all text combinations meet ≥ 4.5:1 standard
  - Document results

- [ ] 5.4 Validate color contrast (dark theme):
  - Use WebAIM Contrast Checker
  - Check primary text color (`var(--text)` #e6edf3) against background (`var(--surface-1)` #161b22)
  - Verify contrast ratio ≥ 4.5:1
  - Check secondary text color (`var(--text-muted)` #7d8590) against backgrounds
  - Verify all text combinations meet ≥ 4.5:1 standard
  - Document results

- [ ] 5.5 Check DevTools console for errors:
  - Load page with DevTools open
  - Go to Console tab
  - Verify NO red ERROR messages appear
  - Verify NO network 404 errors appear (missing CSS/JS files)
  - Warnings are acceptable, but errors are not
  - Document any issues found

---

## Phase 6: Build & Local Deployment

- [ ] 6.1 Run Maven clean build:
  ```bash
  cd c:\Users\histo\OneDrive\Escritorio\2026.01-backend-test
  mvn clean install
  ```
  - Verify build completes WITHOUT errors
  - Look for "BUILD SUCCESS" in console output
  - Verify `target/classes/static/openspec-y-skills-teams.html` exists after build

- [ ] 6.2 Verify file was copied to build output:
  - Check if `target/classes/static/openspec-y-skills-teams.html` exists
  - Verify it contains the new comparison table and integration sections
  - If not found, investigate Maven configuration

- [ ] 6.3 Start Spring Boot local server:
  ```bash
  mvn spring-boot:run
  ```
  - Wait for server to fully start
  - Verify console shows success message (e.g., "Started Application in X seconds")
  - Verify NO ERROR or FATAL logs appear
  - Verify server is listening on port 8080 (or configured port)

- [ ] 6.4 Test page load in browser:
  - Open browser
  - Navigate to `http://localhost:8080/openspec-y-skills-teams.html`
  - Verify page loads (HTTP 200 response)
  - Verify all content is visible: comparison table, integration section, navbar, footer
  - Verify no layout errors or missing content

- [ ] 6.5 Test navbar integration:
  - Load `http://localhost:8080/` (index.html)
  - Verify "Metodologías" link exists in navbar (from Phase 2 of previous change)
  - Click "Metodologías" link
  - Verify browser navigates to `/openspec-y-skills-teams.html`
  - Verify page loads correctly

- [ ] 6.6 Verify no console errors in deployed page:
  - Load `http://localhost:8080/openspec-y-skills-teams.html`
  - Open DevTools (F12)
  - Go to Console tab
  - Verify NO red ERROR messages
  - Verify NO network 404 errors
  - Reload page and check again

- [ ] 6.7 Test theme toggle in deployed page:
  - Load `http://localhost:8080/openspec-y-skills-teams.html`
  - Click theme toggle button
  - Verify theme switches immediately
  - Verify comparison table colors update correctly
  - Verify integration section colors update correctly
  - Toggle back and verify light theme restores

---

## Phase 7: Final Verification Against Spec

- [ ] 7.1 **Spec Requirement: Comparison Table Exists**
  - Verify table is visible with 6 rows comparing OpenSpec vs Skills Teams
  - Verify each row has clear titles (Purpose, Workflow, Output, Use Cases, When to Choose, Integration)

- [ ] 7.2 **Spec Requirement: Mobile Responsive (375px)**
  - Verify NO horizontal scrolling at 375px
  - Verify text is readable
  - Verify layout stacks properly
  - Verify tap targets are ≥44px

- [ ] 7.3 **Spec Requirement: Tablet Responsive (768px)**
  - Verify layout is properly formatted
  - Verify spacing is balanced
  - Verify no content overflow

- [ ] 7.4 **Spec Requirement: Desktop Responsive (1920px)**
  - Verify comparison table displays 2-column layout
  - Verify max-width applies
  - Verify spacing is balanced

- [ ] 7.5 **Spec Requirement: Light Theme Colors**
  - Verify primary text is dark (#0f172a or `var(--text)`)
  - Verify backgrounds are light (#ffffff or `var(--surface-1)`)
  - Verify alternating rows are light blue-gray (#eaf0f6 or `var(--surface-2)`)

- [ ] 7.6 **Spec Requirement: Dark Theme Colors**
  - Verify primary text is light (#e6edf3 or `var(--text)`)
  - Verify backgrounds are dark (#161b22 or `var(--surface-1)`)
  - Verify alternating rows are darker (#21262d or `var(--surface-2)`)

- [ ] 7.7 **Spec Requirement: Theme Toggle Works**
  - Verify clicking theme toggle switches theme immediately
  - Verify colors update correctly
  - Verify theme persists after reload

- [ ] 7.8 **Spec Requirement: Navigation Works**
  - Verify navbar link "Metodologías" navigates to page
  - Verify section links scroll smoothly
  - Verify back-to-top button works

- [ ] 7.9 **Spec Requirement: Semantic HTML**
  - Verify proper heading hierarchy (h1, h2, h3)
  - Verify links have meaningful text
  - Verify lists use semantic tags

- [ ] 7.10 **Spec Requirement: Keyboard Navigation**
  - Verify Tab key reaches all interactive elements
  - Verify focus is visible
  - Verify tab order is logical

- [ ] 7.11 **Spec Requirement: Color Contrast (WCAG AA)**
  - Verify all text has contrast ≥ 4.5:1 in light theme
  - Verify all text has contrast ≥ 4.5:1 in dark theme

- [ ] 7.12 **Spec Requirement: No Console Errors**
  - Verify NO red ERROR messages in DevTools console
  - Verify NO 404 network errors

- [ ] 7.13 **Spec Requirement: Build Success**
  - Verify `mvn clean install` completes with "BUILD SUCCESS"

- [ ] 7.14 **Spec Requirement: Local Server Runs**
  - Verify `mvn spring-boot:run` starts without errors
  - Verify HTTP 200 response to page requests

- [ ] 7.15 **Spec Requirement: Git Status**
  - Run `git status`
  - Verify only `src/main/resources/static/openspec-y-skills-teams.html` is modified
  - Verify no unexpected files changed

- [ ] 7.16 **Spec Requirement: No Page Build Issues**
  - Run `mvn clean install` one more time
  - Verify build succeeds
  - Verify no warnings or errors
  - Verify output files exist in `target/classes/static/`

---

## Phase 8: Cleanup & Documentation

- [ ] 8.1 Review all changes made to `src/main/resources/static/openspec-y-skills-teams.html`:
  - Verify indentation is consistent (2 or 4 spaces)
  - Verify no trailing whitespace
  - Verify HTML is properly formatted

- [ ] 8.2 Final manual review of HTML:
  - Check for typos in comparison table content
  - Check for typos in integration section content
  - Verify all links are correct
  - Verify all icon classes are correct (`bi-...`)

- [ ] 8.3 Verify `target/classes/static/` contains updated file:
  - Run `mvn clean install`
  - Check `target/classes/static/openspec-y-skills-teams.html` exists
  - Verify it contains new content (not old placeholder)

- [ ] 8.4 Final git check:
  - Run `git status`
  - Verify only `src/main/resources/static/openspec-y-skills-teams.html` shows as modified
  - Run `git diff src/main/resources/static/openspec-y-skills-teams.html` to review all changes
  - Verify changes look correct (no accidental deletions)

- [ ] 8.5 Document any issues or decisions made:
  - If any content was adjusted from design.md, note why
  - If any responsive layout issues were discovered and fixed, document them
  - If any accessibility adjustments were made, note them
  - Prepare notes for verification phase

---

## Summary by Phase

| Phase | Task Count | Focus |
|-------|-----------|-------|
| Phase 1: Preparation | 6 | Content review and drafting |
| Phase 2: Comparison Table | 10 | Implement 6-row responsive table |
| Phase 3: Integration Section | 6 | Implement narrative + workflow + diagram + insights |
| Phase 4: Responsiveness Testing | 10 | Test 375px/768px/1920px, light/dark theme, animations |
| Phase 5: Accessibility | 5 | Semantic HTML, keyboard nav, color contrast |
| Phase 6: Build & Deploy | 7 | Maven build, Spring Boot, local server testing |
| Phase 7: Final Verification | 16 | Verify all 16 spec requirements |
| Phase 8: Cleanup | 5 | Code review, git check, documentation |
| **TOTAL** | **65** | |

---

## Implementation Order & Dependencies

1. **Start with Phase 1** — All other phases depend on having clear content definitions
2. **Then Phase 2** — Implement comparison table (foundation for later testing)
3. **Then Phase 3** — Implement integration section (companion to comparison table)
4. **Parallel: Phase 4 & 5** — Test responsiveness and accessibility while building (find issues early)
5. **Then Phase 6** — Build and deploy locally (verify production readiness)
6. **Then Phase 7** — Final verification against spec (acceptance testing)
7. **Finally Phase 8** — Cleanup and document

---

## Verification Checklist (Before Marking "Done")

- [ ] All 65 tasks completed (Phases 1-8)
- [ ] Comparison table with 6 rows implemented ✅
- [ ] Integration section with narrative + workflow + diagram + insights implemented ✅
- [ ] Responsive at 375px, 768px, 1920px ✅
- [ ] Light & dark theme working ✅
- [ ] All 16 spec requirements verified ✅
- [ ] `mvn clean install` → BUILD SUCCESS ✅
- [ ] `mvn spring-boot:run` starts without errors ✅
- [ ] HTTP 200 response to page requests ✅
- [ ] No console errors (red or 404s) ✅
- [ ] Git status shows only expected change ✅
- [ ] Code review passed ✅

---

## Estimated Time

- Phase 1: 0.5 hours (content prep)
- Phase 2: 3 hours (comparison table HTML)
- Phase 3: 2 hours (integration section HTML)
- Phase 4: 2 hours (responsiveness testing)
- Phase 5: 1 hour (accessibility testing)
- Phase 6: 1.5 hours (build & deploy)
- Phase 7: 1.5 hours (final verification)
- Phase 8: 0.5 hours (cleanup)

**Total: ~12 hours** (matches proposal estimate)

---

## Notes

- Tasks are written to be completable in a linear fashion (each phase builds on previous)
- Each task is specific and verifiable (not vague)
- Testing is integrated throughout (not saved for the end)
- This breakdown follows the design.md structure exactly
- If any issues arise, refer back to spec.md and design.md for context

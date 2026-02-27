# Specification: OpenSpec vs Skills Teams Comparison View — Phases 4-8

## Purpose

Complete implementation of the OpenSpec and Skills Teams comparison web view. This spec defines requirements for:
- Phase 4: Comparison table and integration section
- Phase 5: Responsive design validation
- Phase 6: Specification testing and accessibility
- Phase 8: Build and local deployment

---

## ADDED Requirements

### Requirement: Comparison Table Implementation

The view **MUST** display a responsive comparison table with 6 rows comparing OpenSpec and Skills Agent Teams methodologies.

Each row **MUST** have:
- Left column: OpenSpec description
- Right column: Skills Teams description
- Alternating background color (`var(--surface-2)` for readability)
- Proper padding and borders for clarity

The 6 comparison rows **MUST** be:
1. **Purpose**: What problem does each solve?
2. **Workflow**: What are the typical steps?
3. **Output/Deliverables**: What concrete artifacts are produced?
4. **Use Cases**: When is each best used?
5. **When to Choose This**: Decision criteria for practitioners
6. **Complementary**: How do they work together?

#### Scenario: Desktop Layout - Two Columns

- GIVEN a desktop viewport (≥1024px width)
- WHEN the user views the comparison section
- THEN the comparison grid **MUST** display 2 columns side-by-side (OpenSpec | Skills Teams)
- AND each row **MUST** be fully visible without horizontal scrolling
- AND text **MUST** be readable (contrast ratio ≥ 4.5:1 per WCAG)

#### Scenario: Mobile Layout - Single Column (Stacked)

- GIVEN a mobile viewport (≤375px width)
- WHEN the user views the comparison section
- THEN the comparison grid **MUST** stack to 1 column
- AND OpenSpec description **MUST** appear above Skills Teams for each row
- AND NO content **MUST** overflow horizontally (no h-scroll needed)
- AND text size **MUST** remain readable (no font-size < 12px)

#### Scenario: Tablet Layout - Responsive Middle Ground

- GIVEN a tablet viewport (768px width)
- WHEN the user views the comparison section
- THEN the comparison **MAY** display either 1 or 2 columns based on available space
- AND content **MUST** not overflow
- AND row separators **MUST** be visually clear (e.g., light border or background change)

#### Scenario: Dark Theme Support

- GIVEN the user has dark theme enabled (`data-bs-theme="dark"`)
- WHEN the comparison table is rendered
- THEN text color **MUST** be `var(--text)` (light text for dark background)
- AND alternating background **MUST** use `var(--surface-2)` (darker surface)
- AND borders **MUST** use `var(--border)` (visible in dark mode)
- AND contrast **MUST** remain ≥ 4.5:1 (WCAG AA standard)

---

### Requirement: Integration Section Implementation

The view **MUST** include an "Integración" (Integration) section explaining how OpenSpec and Skills Teams work together.

The section **MUST** contain:
- **Narrative explanation**: How both methodologies complement each other in practice (2-3 paragraphs)
- **Practical example**: A concrete workflow example showing where each methodology is used
- **Visual aid**: Either an ASCII diagram or structured workflow description (e.g., step-by-step table or list showing OpenSpec steps → Skills Teams steps)
- **Key insights**: Bullet points about synergies between the two approaches

#### Scenario: Integration Narrative Content

- GIVEN the integration section is visible
- WHEN the user reads the content
- THEN the narrative **MUST** explain when to use OpenSpec (planning, specifying changes)
- AND the narrative **MUST** explain when to use Skills Teams (research, implementation, multi-step tasks)
- AND the narrative **MUST** describe real examples from the project context (e.g., "When adding a new API endpoint")

#### Scenario: Integration Workflow Example

- GIVEN the user scrolls to the integration example
- WHEN the example is rendered
- THEN the example **MUST** show a step-by-step workflow (minimum 5-6 steps)
- AND each step **MUST** indicate whether OpenSpec or Skills Teams (or both) is used
- AND the workflow **MUST** be realistic and based on project patterns observed in `openspec/changes/` directories
- AND the example **SHOULD** reference at least 2 different types of agents (e.g., Plan agent, Explore agent)

#### Scenario: Integration Layout Responsive

- GIVEN different viewport sizes (375px, 768px, 1920px)
- WHEN the integration section is rendered
- THEN content **MUST** remain readable and properly formatted
- AND card layouts **MUST** stack or reflow as needed
- AND no horizontal scrolling **MUST** be required

---

### Requirement: Responsive Design Across Breakpoints

The entire openspec-y-skills-teams view **MUST** be responsive and function correctly at mobile, tablet, and desktop breakpoints.

#### Scenario: Mobile Viewport (375px)

- GIVEN a mobile device with 375px viewport width
- WHEN the page loads
- THEN the navbar **MUST** collapse to mobile menu (offcanvas)
- AND all content **MUST** stack vertically in a single column
- AND no horizontal scrolling **MUST** occur
- AND touch-friendly spacing **MUST** be maintained (min. 44px tap targets)
- AND images/code blocks **MUST** scale appropriately

#### Scenario: Tablet Viewport (768px)

- GIVEN a tablet device with 768px viewport width
- WHEN the page loads
- THEN the navbar **MUST** be visible (not collapsed)
- AND content **MAY** display in 1 or 2 columns as appropriate
- AND code blocks **MUST** be readable without h-scroll
- AND section cards **MUST** be properly sized

#### Scenario: Desktop Viewport (1920px)

- GIVEN a desktop monitor with 1920px+ viewport width
- WHEN the page loads
- THEN the navbar **MUST** show all navigation items
- AND comparison grid **MUST** display in full 2-column layout
- AND max-width container **MUST** apply (≤ ~1200px) to prevent text from becoming too wide
- AND whitespace **MUST** be balanced (not cramped, not excessive)

#### Scenario: Dynamic Viewport Resize

- GIVEN the page is loaded at 1920px
- WHEN the user resizes the window to 375px
- THEN the layout **MUST** reflow smoothly (CSS media queries respond immediately)
- AND no console errors **MUST** appear
- AND interactive elements (buttons, links) **MUST** remain functional

---

### Requirement: Theme (Dark/Light Mode) Consistency

The comparison and integration sections **MUST** respect the theme toggle and maintain visual consistency.

#### Scenario: Light Theme Colors

- GIVEN light theme is active (`data-bs-theme="light"`)
- WHEN sections are rendered
- THEN primary text **MUST** be `var(--text)` (#0f172a dark gray)
- AND secondary text **MUST** be `var(--text-muted)` (#5b6678)
- AND card backgrounds **MUST** be `var(--surface-1)` (#ffffff white)
- AND alternating row backgrounds **MUST** be `var(--surface-2)` (#eaf0f6 light blue-gray)
- AND borders **MUST** be `var(--border)` (#d7e0ea)

#### Scenario: Dark Theme Colors

- GIVEN dark theme is active (`data-bs-theme="dark"`)
- WHEN sections are rendered
- THEN primary text **MUST** be `var(--text)` (#e6edf3 light gray)
- AND secondary text **MUST** be `var(--text-muted)` (#7d8590)
- AND card backgrounds **MUST** be `var(--surface-1)` (#161b22 dark)
- AND alternating row backgrounds **MUST** be `var(--surface-2)` (#21262d darker)
- AND borders **MUST** be `var(--border)` (#30363d dark gray)

#### Scenario: Theme Toggle Switch

- GIVEN the user clicks the theme toggle button in the navbar
- WHEN the page is already viewing the comparison section
- THEN the theme **MUST** switch immediately (no page reload needed)
- AND all colors **MUST** update correctly
- AND the selected theme **MUST** persist in localStorage for future visits
- AND no visual glitches **MUST** occur (e.g., flickering)

---

### Requirement: Accessibility (Semantic HTML & Keyboard Navigation)

The view **MUST** be accessible per WCAG 2.1 Level AA standards.

#### Scenario: Semantic HTML Structure

- GIVEN the page HTML is inspected
- WHEN checking for semantic tags
- THEN the page **MUST** have one `<h1>` tag (page title)
- AND section headings **MUST** use `<h2>` tags (sections: openspec, skills-teams, comparativa, integracion)
- AND subsection headings **MUST** use `<h3>` or `<h4>` tags (e.g., "Flujo recomendado")
- AND navigation links **MUST** use `<a>` tags with meaningful text (not just "Click here")
- AND code blocks **MUST** use `<code>` or `<pre>` tags
- AND lists **MUST** use `<ul>` or `<ol>` tags

#### Scenario: Keyboard Navigation

- GIVEN a keyboard-only user (no mouse)
- WHEN pressing Tab key
- THEN all interactive elements **MUST** be reachable (links, buttons, navbar)
- AND the focus indicator **MUST** be visible (underline, border, or highlight)
- AND the tab order **MUST** be logical (left-to-right, top-to-bottom)
- AND keyboard shortcuts (if any) **MUST** not interfere with browser defaults (e.g., Ctrl+S)

#### Scenario: Color Contrast

- GIVEN the view is rendered in both light and dark themes
- WHEN measuring text contrast
- THEN all text **MUST** have contrast ratio ≥ 4.5:1 (normal text) or ≥ 3:1 (large text)
- AND links **MUST** be distinguishable from surrounding text (not just by color)
- AND icons **MUST** have adequate size (≥ 16x16px) for clarity

---

### Requirement: Build and Deployment

The modified HTML file **MUST** build successfully with Maven and be deployable to Spring Boot.

#### Scenario: Maven Build Success

- GIVEN the developer runs `mvn clean install`
- WHEN the build process completes
- THEN the build **MUST** output `BUILD SUCCESS`
- AND no compilation errors **MUST** appear
- AND the file `target/classes/static/openspec-y-skills-teams.html` **MUST** exist after build
- AND no warnings **MUST** appear in the build log (optional but recommended)

#### Scenario: Spring Boot Local Server

- GIVEN the developer runs `mvn spring-boot:run`
- WHEN the server starts
- THEN the server **MUST** initialize without errors (no red ERROR logs)
- AND the server **MUST** listen on port 8080 (or configured port)
- AND the console **MUST** show "Started Application in X seconds" or similar success message

#### Scenario: HTTP Request to View

- GIVEN Spring Boot server is running locally
- WHEN a GET request is made to `http://localhost:8080/openspec-y-skills-teams.html`
- THEN the server **MUST** respond with HTTP 200 OK
- AND the HTML content **MUST** be returned in full
- AND the page **MUST** load in the browser without errors
- AND no 404 or 5xx errors **MUST** appear

#### Scenario: No Console Errors

- GIVEN the page is loaded in browser (with DevTools open)
- WHEN checking the console tab
- THEN NO JavaScript errors **MUST** appear (red error messages)
- AND NO network errors **MUST** appear (e.g., 404 for missing CSS/JS files)
- AND warnings **MAY** appear (acceptable, but errors are not)

---

### Requirement: Navigation Integration

The navbar "Metodologías" link **MUST** navigate to the view and indicate the active section.

#### Scenario: Link Navigation

- GIVEN the user is on index.html
- WHEN clicking the "Metodologías" link in the navbar
- THEN the browser **MUST** navigate to `/openspec-y-skills-teams.html`
- AND the page **MUST** load successfully (HTTP 200)
- AND the "Metodologías" link **MUST** appear active (highlighted) in the navbar

#### Scenario: Anchor Navigation (Smooth Scroll)

- GIVEN the user is on openspec-y-skills-teams.html
- WHEN clicking internal links like "Comparativa" in the navbar
- THEN the page **MUST** scroll smoothly to the corresponding section (`#comparativa`)
- AND the section **MUST** be highlighted (via active link styling)
- AND no page reload **MUST** occur

#### Scenario: Back to Index

- GIVEN the user is on openspec-y-skills-teams.html
- WHEN clicking the brand logo or home link (if present)
- THEN the browser **MUST** navigate back to index.html
- AND the page **MUST** load correctly

---

## MODIFIED Requirements

*No existing requirements are being modified. This is a new feature completion.*

---

## REMOVED Requirements

*No requirements are being removed.*

---

## Testing Acceptance Criteria (Derived from Phase 6)

The following acceptance criteria **MUST** all be verified before archiving this change:

| # | Requirement | Method | Pass Criteria |
|---|-------------|--------|---------------|
| 1 | Comparison table exists | Manual | Table visible with 6 rows comparing OpenSpec vs Skills Teams |
| 2 | Mobile responsiveness (375px) | DevTools emulation | No h-scroll, readable text, proper stacking |
| 3 | Tablet responsiveness (768px) | DevTools emulation | Layout properly formatted, no overflow |
| 4 | Desktop responsiveness (1920px) | DevTools emulation | 2-column layout, proper max-width, good spacing |
| 5 | Light theme colors | Manual inspection | Text readable, colors match design tokens |
| 6 | Dark theme colors | Manual inspection + toggle | Colors correct, contrast OK, no flashing |
| 7 | Theme persistence | Manual test | Toggle theme, reload page, theme is remembered |
| 8 | Navigation works | Manual test | Navbar link "Metodologías" navigates to page |
| 9 | Smooth scroll | Manual test | Clicking internal links scrolls smoothly |
| 10 | Back-to-top button | Manual test | Button appears after scrolling, works correctly |
| 11 | Semantic HTML | Code inspection | h1, h2, h3 tags used correctly |
| 12 | Keyboard navigation | Tab key test | All interactive elements reachable, logical order |
| 13 | Color contrast | WebAIM tool | All text ≥ 4.5:1 (or 3:1 for large text) |
| 14 | No console errors | DevTools console | No red ERROR messages |
| 15 | Maven build | Terminal | `mvn clean install` → BUILD SUCCESS |
| 16 | Local server | Terminal + browser | `mvn spring-boot:run` starts, HTTP 200 responses |

---

## Notes

- Design tokens are defined in `src/main/resources/static/openspec-y-skills-teams.html` (lines 14-41) and already include light/dark theme support
- Bootstrap 5.3.3 is already integrated; use Bootstrap grid classes (`col-12`, `col-md-6`) for responsiveness
- The JavaScript for theme toggle, scroll-reveal, copy-to-clipboard, and smooth scroll is already implemented
- This spec complements the existing Phase 1-3 implementation (foundation, navbar, OpenSpec/Skills Teams sections)

# Frontend UI Specification: OpenSpec & Skills Agent Teams Comparison View

## Purpose

Define requirements for a new educational frontend view that explains Spec-Driven Development (OpenSpec) and Skills Agent Teams, including a side-by-side comparison. The view MUST maintain visual consistency with the existing `index.html` design system.

---

## ADDED Requirements

### Requirement: New View Page Must Exist

The system MUST provide a new HTML file at `src/main/resources/static/openspec-y-skills-teams.html` that is publicly accessible via HTTP GET request without authentication.

#### Scenario: User navigates to OpenSpec & Skills Teams view

- GIVEN the user is on any page in the site
- WHEN the user requests `/openspec-y-skills-teams.html`
- THEN the page loads successfully with HTTP 200 status
- AND the page title is "OpenSpec y Skills Agent Teams" or similar in Spanish

#### Scenario: User can bookmark and share the view

- GIVEN the user is viewing the new page
- WHEN the user bookmarks or copies the URL
- THEN the URL `openspec-y-skills-teams.html` is clean, shareable, and directly navigable

---

### Requirement: Visual Design Must Match Index Exactly

The new view MUST use identical design tokens, colors, typography, and component styles as `index.html`. No new custom colors or fonts SHOULD be introduced.

#### Scenario: Light theme consistency

- GIVEN the user has light theme enabled (`data-bs-theme="light"`)
- WHEN they view the new page
- THEN all elements use the same color palette:
  - Primary color: #0ea5a3 (teal)
  - Surface backgrounds: #f3f7fb, #ffffff, #eaf0f6
  - Text: #0f172a (dark)
  - Borders: #d7e0ea
- AND typography uses Google Fonts: Inter (body), JetBrains Mono (code)

#### Scenario: Dark theme consistency

- GIVEN the user has dark theme enabled (`data-bs-theme="dark"`)
- WHEN they view the new page
- THEN all elements use dark theme tokens:
  - Surface: #0d1117, #161b22, #21262d
  - Text: #e6edf3
  - Borders: #30363d
- AND theme toggle button is present and functional

#### Scenario: Border radius and spacing consistency

- GIVEN the user views cards or containers on the new page
- THEN all rounded corners use `var(--radius)` (12px) or `var(--radius-lg)` (16px)
- AND padding/margins are consistent with index.html (multiples of 4px or 8px)

---

### Requirement: Navigation Must Include New Link

The navbar in `index.html` MUST be updated to include a visible link to the new OpenSpec & Skills Teams view.

#### Scenario: Navbar link is visible and clickable

- GIVEN the user views any page with the navbar (e.g., index.html)
- WHEN they look at the navbar `<ul class="navbar-nav">`
- THEN there is a new `<li class="nav-item">` with a link to `/openspec-y-skills-teams.html`
- AND the link text is "Metodologías" or "SDD & Skills" (short, descriptive)
- AND the link has class `nav-link` with proper hover/active states

#### Scenario: Link position is consistent

- GIVEN the navbar has links: API, Setup, DB embebida, Base de datos, Estructura app, Vistas, Recursos, SDD
- WHEN the new link is added
- THEN it is positioned after "Vistas" and before "Recursos" (or in a logical position)
- AND spacing between links is uniform using Bootstrap gap classes

---

### Requirement: New Page Must Have Header Section

The new view MUST have a prominent header explaining the overall purpose of the page.

#### Scenario: Header is visually prominent

- GIVEN the user arrives at the new page
- WHEN they see the top of the page
- THEN there is a header section with:
  - A title (e.g., "OpenSpec y Skills Agent Teams")
  - A subtitle explaining what the page contains
  - A decorative badge or eyebrow (like `index.html` hero section)
  - Proper spacing (padding 80px 0 64px or similar)

#### Scenario: Header is responsive

- GIVEN the user views the page on a mobile device (< 576px width)
- WHEN they see the header title
- THEN the font size scales responsively (using `clamp()` or media queries)
- AND the title remains readable and centered

---

### Requirement: OpenSpec Explanation Section

The page MUST include a dedicated section explaining what OpenSpec is and how it works.

#### Scenario: OpenSpec section contains key concepts

- GIVEN the user scrolls to the OpenSpec section
- WHEN they read the content
- THEN they find:
  - Definition: "Spec-Driven Development is a methodology where requirements are defined BEFORE implementation"
  - Key steps: Explore → Propose → Spec → Design → Tasks → Apply → Verify → Archive
  - At least 3 benefits of using OpenSpec
  - Real example or code snippet from the project (e.g., `sdd-propose` command)

#### Scenario: Content is organized in cards

- GIVEN the OpenSpec explanation section
- WHEN styled elements are used
- THEN cards use Bootstrap styling with `var(--surface-1)` background, `var(--border)` borders, and `var(--radius)` border-radius
- AND cards have consistent padding (1.25rem or similar)
- AND heading within cards uses `var(--primary)` color

---

### Requirement: Skills Agent Teams Explanation Section

The page MUST include a dedicated section explaining what Skills Agent Teams are and how they work.

#### Scenario: Skills Agent Teams section contains key concepts

- GIVEN the user scrolls to the Skills Agent Teams section
- WHEN they read the content
- THEN they find:
  - Definition: "Skills Agent Teams are specialized AI agents that collaborate to solve complex tasks"
  - List of available agents (e.g., general-purpose, Explore, Plan, frontend-ui-ux-expert, etc.)
  - How agents are invoked and what tools they have access to
  - At least 3 use cases or examples from the project

#### Scenario: Content is organized similarly to OpenSpec section

- GIVEN the Skills Agent Teams section
- WHEN styled elements are used
- THEN the same card styling and typography conventions apply as the OpenSpec section
- AND visual hierarchy is consistent

---

### Requirement: Comparison Table/Section

The page MUST provide a clear side-by-side comparison of OpenSpec and Skills Agent Teams.

#### Scenario: Comparison table shows key differences

- GIVEN the user scrolls to the comparison section
- WHEN they view the table or grid
- THEN it compares dimensions like:
  - Purpose (what each solves)
  - Workflow (how each operates)
  - Output (what each produces)
  - When to use each one
  - Integration (how they work together in this project)

#### Scenario: Comparison is visually scannable

- GIVEN the comparison table
- WHEN viewed on desktop or tablet
- THEN it uses a 2-column grid layout (OpenSpec | Skills Teams)
- AND each column is clearly labeled
- AND rows have alternating backgrounds (using `var(--surface-2)`) for readability
- AND text in comparison cells is concise (max 2-3 lines per cell)

#### Scenario: Comparison is responsive on mobile

- GIVEN the user views the comparison on a mobile device
- WHEN the viewport is < 768px
- THEN the table/grid stacks vertically (1 column per row showing both values)
- AND labels remain visible and readable
- AND styling maintains contrast

---

### Requirement: Code Examples and Snippets

The page SHOULD include practical code examples showing how to use OpenSpec and Skills.

#### Scenario: OpenSpec example shows a real command

- GIVEN the OpenSpec explanation section
- WHEN code examples are shown
- THEN they display actual commands like:
  ```
  sdd-propose "agregar-vista-openspec-y-skills-teams"
  sdd-spec agregar-vista-openspec-y-skills-teams
  sdd-apply agregar-vista-openspec-y-skills-teams
  ```
- AND code blocks use `background: var(--surface-2)` with `border-left: 2px solid var(--primary)`
- AND syntax uses `font-family: JetBrains Mono` with color `var(--primary)` or `#a1e8dd`

#### Scenario: Skills Agent example shows a practical use case

- GIVEN the Skills Agent Teams section
- WHEN code or command examples are shown
- THEN they show how to invoke agents, e.g.:
  ```
  Task tool: "Explore codebase"
  Agent: "Explore agent"
  Output: "List of relevant files and patterns"
  ```
- AND formatting is consistent with OpenSpec examples

---

### Requirement: Footer Must Match Index Footer

The page MUST include a footer identical in style and content to the `index.html` footer.

#### Scenario: Footer is present and styled correctly

- GIVEN the user scrolls to the bottom of the page
- WHEN they view the footer
- THEN it includes:
  - Copyright or branding information
  - Links to relevant resources (if any)
  - Proper background color: `var(--surface-1)`
  - Border-top: `1px solid var(--border)`
  - Padding: 28px 0 or similar
  - Text color: `var(--text-muted)`

---

### Requirement: Responsive Design

The page MUST be fully responsive and work correctly on all screen sizes (mobile, tablet, desktop).

#### Scenario: Mobile view (< 576px)

- GIVEN a user with a mobile device
- WHEN they view the page
- THEN:
  - Navbar collapses to hamburger menu (Bootstrap default)
  - Title font size is reduced using `clamp()`
  - Cards and comparison table stack vertically
  - No horizontal scrolling is required

#### Scenario: Tablet view (576px - 991px)

- GIVEN a user on a tablet
- WHEN they view the page
- THEN:
  - Comparison grid uses 1-2 columns (flexible)
  - Cards are readable and properly spaced
  - Navbar may still be horizontal or collapsed depending on content

#### Scenario: Desktop view (> 991px)

- GIVEN a user on a desktop
- WHEN they view the page
- THEN:
  - Comparison grid displays 2 columns side-by-side
  - All cards display with maximum width constraints
  - Navbar shows all links horizontally

---

### Requirement: Theme Toggle Must Work

The page MUST support light/dark theme switching using the existing theme toggle in the navbar.

#### Scenario: Theme toggle affects new page correctly

- GIVEN the user is on the new page
- WHEN they click the theme toggle button in the navbar
- THEN:
  - The `html[data-bs-theme]` attribute switches from "light" to "dark"
  - All colors update according to CSS custom properties
  - Text becomes light-colored on dark backgrounds
  - No elements are illegible or invisible in either theme

#### Scenario: Preference is persisted (if localStorage is used)

- GIVEN the user switches to dark theme
- WHEN they navigate away and return to the page (or any page)
- THEN their theme preference is remembered (via localStorage or similar)
- AND the page loads with the correct theme

---

### Requirement: Accessibility

The page MUST meet basic accessibility standards.

#### Scenario: Semantic HTML is used

- GIVEN the page source code
- WHEN reviewed for accessibility
- THEN:
  - Proper heading hierarchy: `<h1>` for main title, `<h2>` for sections, `<h3>` for subsections
  - Links have descriptive text (not "click here")
  - Images (if any) have `alt` attributes
  - Color is not the only visual cue (e.g., comparison uses text labels, not just colors)

#### Scenario: Keyboard navigation works

- GIVEN a user navigating with keyboard only (Tab, Enter)
- WHEN they tab through the page
- THEN:
  - All links and interactive elements are reachable via Tab
  - Focus is visually indicated
  - Tab order is logical (top to bottom, left to right)

---

## MODIFIED Requirements

### Requirement: Index.html Navbar Updated

The `index.html` navbar MUST be updated to include a link to the new view.

#### Scenario: New link added to navbar

- GIVEN the navbar in `index.html` with class `navbar-nav`
- WHEN the new link is added
- THEN it appears as a new `<li class="nav-item">` with a child `<a class="nav-link">` element
- AND it maintains consistent styling with other nav-links (color, hover state, spacing)

---

## Notes

- All colors MUST use CSS custom properties (`var(--primary)`, `var(--surface-1)`, etc.), NOT hardcoded hex values
- No inline styles SHOULD be used; all styling goes in `<style>` tag or external CSS
- Page MUST NOT require any server-side processing or API calls (static HTML)
- Page MUST load in < 2 seconds on a typical connection (no heavy assets)

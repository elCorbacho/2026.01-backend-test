# Frontend Specification: OpenSpec & Skills Agent Teams View (Phases 4-9)

## Purpose

This specification defines the requirements for completing the OpenSpec & Skills Agent Teams educational view. The view explains and compares two development methodologies used in the project, helping users understand when and how to use each approach.

**File**: `src/main/resources/static/openspec-y-skills-teams.html`

---

## Scope

### Phases Covered
- **Phase 4**: Comparison table + Integration section
- **Phase 5**: Responsiveness validation + Dark/Light theme
- **Phase 6**: Specification testing (16 requirements)
- **Phase 8**: Build & deployment verification

### Out of Scope
- Phase 7: Cross-browser exhaustive testing (Safari, Firefox mobile, etc.)
- Phase 9: Cosmetic final verification (screenshots, Lighthouse > 90, etc.)

---

## ADDED Requirements

### Requirement 4.1: Comparison Table Structure

The system **MUST** render a comparison table that contrasts OpenSpec and Skills Agent Teams methodologies.

**Details**:
- Table **MUST** contain exactly **6 rows** of comparison
- Rows **MUST** compare: Purpose, Workflow, Output, Use Cases, When to Choose, Integration Points
- Table **MUST** have 3 columns: Aspect, OpenSpec, Skills Agent Teams
- Table **MUST** use semantic HTML (`<table>`, `<thead>`, `<tbody>`, `<th>`, `<td>`)
- First row **MUST** be header row with th elements

#### Scenario: User views comparison table on desktop

- GIVEN user navigates to `/openspec-y-skills-teams.html`
- WHEN page loads successfully
- THEN comparison table is visible and properly structured
- AND table contains exactly 6 rows of data (plus header)
- AND all cells contain non-empty, meaningful content

#### Scenario: Table displays in correct order

- GIVEN comparison table is rendered
- WHEN inspecting rows in order
- THEN rows appear in this exact order: Purpose, Workflow, Output, Use Cases, When to Choose, Integration Points
- AND each row has left column describing the aspect
- AND OpenSpec column has content specific to OpenSpec methodology
- AND Skills Agent Teams column has content specific to Skills methodology

---

### Requirement 4.2: Table Responsive Design (Mobile)

The system **MUST** display the comparison table responsively on mobile devices (375px width).

**Details**:
- On mobile (**width ≤ 767px**), table **MUST** convert to single-column layout
- Each row **MUST** remain readable without horizontal overflow
- Content **MUST** remain fully visible without requiring sideways scrolling
- Bootstrap grid classes **MUST** use `col-12` for mobile layout

#### Scenario: User views table on mobile device (375px)

- GIVEN user views page on mobile device (375px width)
- WHEN page renders comparison table
- THEN table content fits within viewport
- AND no horizontal scrollbar appears
- AND text is readable and not truncated
- AND all 6 rows are visible (vertical scrolling allowed)

#### Scenario: Table transitions from mobile to tablet

- GIVEN page is displayed at mobile width (375px)
- WHEN user resizes browser to tablet width (768px)
- THEN table layout smoothly transitions
- AND desktop-like layout with 2 columns appears
- AND no content is lost or hidden

---

### Requirement 4.3: Table Responsive Design (Desktop)

The system **MUST** display the comparison table in a 2-column layout on desktop (1920px width).

**Details**:
- On desktop (**width ≥ 992px**), table **MUST** use Bootstrap `col-md-6` for two-column layout
- Header **MUST** span full width above columns
- Both columns **MUST** have equal visual weight
- Content **MUST** be centered with proper padding

#### Scenario: User views table on desktop (1920px)

- GIVEN user views page on desktop (1920px width)
- WHEN page renders comparison table
- THEN table displays in 2-column layout with header spanning full width
- AND columns are balanced and equally sized
- AND content is properly aligned and padded

---

### Requirement 4.4: Table Visual Styling

The system **MUST** apply consistent visual styling to the comparison table.

**Details**:
- Header row **MUST** have distinct background color: `var(--primary)` or high-contrast color
- Alternating rows **MUST** use background color: `var(--surface-2)` for even rows
- Cells **MUST** have padding of at least 1rem
- Text **MUST** use CSS variables for theming: `var(--text)`, `var(--text-secondary)`
- Borders **MUST** be subtle and consistent with design tokens

#### Scenario: User views styled comparison table

- GIVEN comparison table is rendered
- WHEN user inspects visual styling
- THEN header row has distinct, high-contrast appearance
- AND alternating rows have alternating backgrounds for readability
- AND all text colors comply with dark/light theme using CSS variables
- AND no hardcoded colors appear (all use design tokens)

---

### Requirement 4.5: Integration Section - Narrativa

The system **MUST** include an integration narrative explaining how OpenSpec and Skills Agent Teams complement each other.

**Details**:
- Section **MUST** be titled "Cómo Trabajan Juntas" or similar
- Narrative **MUST** contain at least 2-3 paragraphs explaining synergies
- Narrative **MUST** explain practical scenarios where both methodologies are used together
- Content **MUST** be educational and non-technical
- Paragraphs **MUST** use semantic HTML (`<p>`, `<h2>`, `<h3>`)

#### Scenario: User reads integration narrative

- GIVEN user scrolls to integration section
- WHEN section is visible
- THEN clear, well-written narrative is presented
- AND narrative explains how methodologies complement each other
- AND narrative includes at least one practical example
- AND text is properly formatted with headings and paragraphs

---

### Requirement 4.6: Integration Section - Visual Example

The system **MUST** provide a visual example or workflow diagram showing integration between methodologies.

**Details**:
- Visual **MAY** be ASCII diagram, SVG, or code block diagram
- Visual **MUST** show a step-by-step example of using both methodologies together
- Visual **MUST** be clear and understandable without additional explanation
- Visual **MUST** use consistent styling with rest of page
- Alternative: detailed code comments explaining workflow if visual is not practical

#### Scenario: User views integration example

- GIVEN integration section is rendered
- WHEN user views visual example or diagram
- THEN visual clearly shows how OpenSpec and Skills Teams work together
- AND example is step-by-step and easy to follow
- AND visual uses colors or formatting that matches page theme

---

### Requirement 4.7: Integration Insights (Cards)

The system **MUST** display key insights about integration in card format.

**Details**:
- **MUST** include at least 3 insight cards
- Each card **MUST** have title and description
- Cards **MUST** use Bootstrap card component
- Cards **MUST** be responsive (full-width mobile, multi-column desktop)
- Card titles **MUST** use `<h3>` or `<h4>` semantic heading

#### Scenario: User views insight cards

- GIVEN integration section is fully rendered
- WHEN user scrolls through insight cards
- THEN at least 3 cards are visible
- AND each card has clear title and description
- AND cards are properly spaced and aligned
- AND cards respond to viewport changes (mobile shows 1, desktop shows multiple)

---

### Requirement 5.1: Mobile Responsiveness (375px)

The system **MUST** render correctly on mobile devices (375px width).

**Details**:
- All content **MUST** fit within 375px viewport
- No horizontal overflow or scrollbar **MUST** appear
- Text **MUST** be readable (font size ≥ 14px)
- Touch targets **MUST** be ≥ 44px for interactive elements
- Images **MUST** scale proportionally

#### Scenario: Mobile device displays page correctly

- GIVEN user views page on mobile (375px)
- WHEN page is fully loaded
- THEN all content fits without horizontal scrolling
- AND text is readable
- AND interactive elements are touch-friendly
- AND images don't overflow

---

### Requirement 5.2: Tablet Responsiveness (768px)

The system **MUST** render correctly on tablet devices (768px width).

**Details**:
- Layout **MUST** transition to 2-column or mixed layout
- Content **MUST** be well-balanced across columns
- Navigation **MUST** remain accessible
- All sections **MUST** be fully readable

#### Scenario: Tablet device displays page with mixed layout

- GIVEN user views page on tablet (768px)
- WHEN page renders
- THEN 2-column layout is used where appropriate
- AND content is balanced and readable
- AND no element overlaps or breaks

---

### Requirement 5.3: Desktop Responsiveness (1920px)

The system **MUST** render correctly on desktop (1920px width).

**Details**:
- Layout **MUST** use full width appropriately
- Content **MUST** not be stretched excessively
- Maximum width **SHOULD** be set to prevent overly long line lengths (≤ 100 characters)
- Whitespace **MUST** be balanced

#### Scenario: Desktop displays page with full-width layout

- GIVEN user views page on desktop (1920px)
- WHEN page is fully loaded
- THEN layout uses available space effectively
- AND line lengths remain readable (not too wide)
- AND whitespace is balanced

---

### Requirement 5.4: Dark Theme Support

The system **MUST** support dark theme with CSS variables.

**Details**:
- All colors **MUST** use CSS custom properties (variables)
- Variables **MUST** be defined in `theme.css` or inline `<style>`
- Dark theme colors **MUST** meet WCAG AA contrast requirements
- No hardcoded colors (hex, rgb) **MUST** appear in content sections

#### Scenario: User enables dark theme

- GIVEN dark theme is enabled (via theme toggle)
- WHEN page renders in dark mode
- THEN all text is readable with sufficient contrast
- AND background colors are dark/neutral
- AND accent colors are visible and appropriate
- AND theme persists across refresh (using localStorage if applicable)

---

### Requirement 5.5: Light Theme Support

The system **MUST** support light theme with CSS variables.

**Details**:
- Light theme colors **MUST** be defined consistently
- Light backgrounds **MUST** meet WCAG AA contrast with dark text
- No hardcoded colors **MUST** appear in content sections
- Transition between light/dark **MUST** be smooth (no jarring color changes)

#### Scenario: User switches between light and dark themes

- GIVEN light theme is enabled
- WHEN page renders in light mode
- THEN all text is readable with sufficient contrast
- AND background colors are light/white
- AND transition from dark theme is smooth

---

### Requirement 5.6: Theme Persistence

The system **MUST** preserve user's theme preference.

**Details**:
- If theme toggle exists, selection **MUST** be saved (localStorage or session)
- **OR** theme **SHOULD** follow system preference (prefers-color-scheme media query)
- On page reload, saved theme **MUST** be applied
- Default theme **SHOULD** be light (or respect browser default)

#### Scenario: User selects dark theme and refreshes page

- GIVEN user has selected dark theme
- WHEN page is refreshed
- THEN dark theme is restored automatically
- AND no "flash" of light theme appears before switching

---

### Requirement 5.7: Scroll Progress Indicator

The system **SHOULD** display scroll progress indicator.

**Details**:
- Progress indicator **MAY** be horizontal bar at top or sidebar indicator
- Indicator **MUST** reflect current scroll position accurately
- Indicator **MUST** be visible but non-intrusive
- Indicator **SHOULD** use primary color from design tokens

#### Scenario: User scrolls page and views progress

- GIVEN user scrolls down the page
- WHEN scroll progress indicator exists
- THEN indicator accurately reflects scroll position
- AND indicator remains visible throughout scroll
- AND indicator doesn't interfere with content

---

### Requirement 5.8: Back-to-Top Button

The system **SHOULD** provide back-to-top navigation.

**Details**:
- Button **MAY** appear when user scrolls past certain threshold
- Button **MUST** smoothly scroll to top when clicked
- Button **SHOULD** use Bootstrap or custom styling consistent with design
- Button **SHOULD** have clear, accessible label (aria-label)

#### Scenario: User clicks back-to-top after scrolling

- GIVEN user has scrolled down page
- WHEN back-to-top button is clicked
- THEN page smoothly scrolls to top
- AND button becomes hidden when at top

---

### Requirement 5.9: Scroll-Reveal Animations

The system **SHOULD** include subtle scroll animations.

**Details**:
- Animations **SHOULD** be fade-in or slide-in on scroll
- Animations **MUST** not be distracting or performance-heavy
- Animations **SHOULD** respect `prefers-reduced-motion` media query
- Animations **SHOULD** enhance readability, not hinder it

#### Scenario: User scrolls and views reveal animations

- GIVEN user scrolls down page
- WHEN elements come into view
- THEN subtle fade-in or slide-in animation occurs
- AND animation completes quickly (< 500ms)
- AND animation respects motion preferences

---

### Requirement 6.1: Navigation Validation

The system **MUST** have working navigation.

**Details**:
- Link "Metodologías" in navbar **MUST** navigate to `/openspec-y-skills-teams.html`
- Link **MUST** work from homepage (`/` or `/index.html`)
- Link text **MUST** be clear and descriptive
- Active page **SHOULD** highlight in navbar

#### Scenario: User navigates from index to OpenSpec view

- GIVEN user is on home page (index.html)
- WHEN user clicks "Metodologías" link in navbar
- THEN page navigates to `/openspec-y-skills-teams.html`
- AND page loads without errors
- AND HTTP status is 200

---

### Requirement 6.2: Content Completeness

The system **MUST** have no placeholder text.

**Details**:
- No `Lorem ipsum` or placeholder phrases **MUST** exist
- No `[TODO]`, `[PLACEHOLDER]`, or similar markers **MUST** exist
- All section content **MUST** be real, meaningful, and educational
- All empty elements **MUST** be removed or filled

#### Scenario: User views page and finds real content

- GIVEN page is fully loaded
- WHEN user scrolls through all sections
- THEN no placeholder text or TODO markers are visible
- AND all content is informative and complete

---

### Requirement 6.3: Semantic HTML Structure

The system **MUST** use semantic HTML.

**Details**:
- Page **MUST** have single `<h1>` tag (page title)
- Heading hierarchy **MUST** be correct (`<h1>` → `<h2>` → `<h3>`, no skipping)
- Sections **MUST** use `<section>` tags with descriptive id/aria-label
- Lists **MUST** use `<ul>`, `<ol>`, `<li>` appropriately
- Navigation **MUST** use `<nav>` tag
- Content **MUST** use `<article>` or `<main>` as appropriate

#### Scenario: Validator checks semantic HTML

- GIVEN page source is analyzed
- WHEN validator checks semantic structure
- THEN single `<h1>` exists
- AND heading hierarchy is correct (no h1 → h3 jumps)
- AND semantic elements are used appropriately

---

### Requirement 6.4: Link Text Quality

The system **MUST** have descriptive link text.

**Details**:
- No link **MUST** use generic text like "click here" or "read more" alone
- Link text **MUST** describe destination or action
- Links **MUST** have `aria-label` if text is ambiguous
- External links **SHOULD** indicate they're external (icon or aria-label)

#### Scenario: Accessibility validator checks links

- GIVEN page is analyzed for accessibility
- WHEN validator checks link text quality
- THEN all links have meaningful, descriptive text
- AND no links use only generic phrases
- AND external links are clearly marked

---

### Requirement 6.5: Image Accessibility

The system **MUST** provide alt text for images.

**Details**:
- All `<img>` tags **MUST** have `alt` attribute with meaningful text
- Decorative images **MUST** have empty alt attribute (`alt=""`)
- Alt text **MUST** describe content (not "image of..." or "picture")
- Images **MUST** have proper `title` or context if needed

#### Scenario: Screen reader user accesses images

- GIVEN user uses screen reader to access page
- WHEN screen reader encounters images
- THEN meaningful alt text is provided for each image
- AND decorative images are skipped appropriately

---

### Requirement 6.6: Keyboard Navigation

The system **MUST** be fully keyboard navigable.

**Details**:
- All interactive elements **MUST** be reachable via Tab key
- Focus **MUST** be visible (outline or highlight)
- Tab order **MUST** be logical (left→right, top→bottom)
- Modals or dropdowns **MUST** trap focus appropriately
- Escape key **SHOULD** close modals/dropdowns if applicable

#### Scenario: Power user navigates page with keyboard only

- GIVEN user only uses keyboard (Tab, Enter, Escape)
- WHEN user navigates page
- THEN all interactive elements are reachable
- AND focus is always visible
- AND tab order makes sense

---

### Requirement 6.7: Color Contrast (WCAG AA)

The system **MUST** meet WCAG AA contrast standards.

**Details**:
- All text vs. background **MUST** have contrast ratio ≥ 4.5:1 (normal text)
- Large text (18pt+ or 14pt+ bold) **MAY** have contrast ratio ≥ 3:1
- UI components **MUST** have contrast ratio ≥ 3:1
- Dark theme **MUST** also meet these standards

#### Scenario: Accessibility checker validates contrast

- GIVEN page is analyzed with accessibility tool (e.g., axe, WCAG)
- WHEN validator checks color contrast
- THEN all text colors meet WCAG AA standards
- AND dark theme also complies

---

### Requirement 6.8: Form Accessibility (if applicable)

The system **SHOULD** have accessible forms (if contact form exists).

**Details**:
- Form inputs **MUST** have associated `<label>` tags
- Labels **MUST** use `for` attribute matching input `id`
- Form error messages **MUST** be linked to inputs via `aria-describedby`
- Required fields **MUST** be marked with aria-required or visual indicator

#### Scenario: Screen reader user fills form (if applicable)

- GIVEN form exists on page
- WHEN screen reader user interacts with form
- THEN all inputs have labels
- AND form structure is clear
- AND error messages are announced

---

### Requirement 8.1: Maven Build Success

The system **MUST** build successfully with Maven.

**Details**:
- Command `mvn clean install` **MUST** complete without errors
- Build output **MUST** display `BUILD SUCCESS`
- No warning messages related to missing resources **MUST** appear
- All static files **MUST** be copied to `target/classes/static/`

#### Scenario: Developer runs Maven build

- GIVEN developer runs `mvn clean install`
- WHEN build process executes
- THEN build completes without errors
- AND output shows "BUILD SUCCESS"
- AND no critical warnings appear

---

### Requirement 8.2: Static File Generation

The system **MUST** include HTML file in build output.

**Details**:
- File `src/main/resources/static/openspec-y-skills-teams.html` **MUST** exist
- File **MUST** be copied to `target/classes/static/openspec-y-skills-teams.html` during build
- File **MUST** be accessible from Spring Boot application
- No build step **MUST** modify HTML content (only copy)

#### Scenario: Verify static files in build output

- GIVEN `mvn clean install` has completed successfully
- WHEN examining build output directory
- THEN `target/classes/static/openspec-y-skills-teams.html` exists
- AND file is identical to source (no modifications)

---

### Requirement 8.3: Spring Boot Server Startup

The system **MUST** allow Spring Boot to start without errors.

**Details**:
- Command `mvn spring-boot:run` **MUST** start server without crashing
- No errors in logs related to static files **MUST** appear
- Server **MUST** bind to port 8080 successfully
- No `FileNotFoundException` for static resources **MUST** occur

#### Scenario: Developer starts Spring Boot server

- GIVEN developer runs `mvn spring-boot:run`
- WHEN server startup process executes
- THEN server starts successfully
- AND port 8080 is available and bound
- AND no errors appear in logs related to missing files

---

### Requirement 8.4: HTTP 200 Response

The system **MUST** serve HTML with HTTP 200 status.

**Details**:
- GET request to `http://localhost:8080/openspec-y-skills-teams.html` **MUST** return HTTP 200
- Response headers **MUST** include `Content-Type: text/html`
- Response body **MUST** contain valid HTML
- No redirects (30x) **MUST** occur (unless permanent redirect to same content)

#### Scenario: User requests page and receives 200 OK

- GIVEN server is running on localhost:8080
- WHEN user or browser requests `/openspec-y-skills-teams.html`
- THEN HTTP response code is 200 OK
- AND Content-Type header is text/html
- AND response contains valid HTML

---

### Requirement 8.5: Page Rendering in Browser

The system **MUST** render correctly in browser.

**Details**:
- Page **MUST** load completely without hanging
- Page **MUST** display all sections (header, comparison table, integration, footer)
- No JavaScript errors **MUST** appear in browser console
- All images and resources **MUST** load without 404 errors

#### Scenario: User opens page in browser

- GIVEN user navigates to `http://localhost:8080/openspec-y-skills-teams.html`
- WHEN page loads in browser
- THEN page renders completely
- AND all sections are visible and properly formatted
- AND no console errors appear

---

### Requirement 8.6: No Console Errors

The system **MUST** have clean browser console (F12 DevTools).

**Details**:
- No JavaScript errors **MUST** appear in console
- No 404 warnings for missing resources **MUST** appear
- No CORS errors **MUST** appear
- Warnings **MAY** exist if they're not critical to functionality

#### Scenario: Developer opens DevTools and checks console

- GIVEN page is loaded in browser
- WHEN developer opens F12 DevTools → Console tab
- THEN no red error messages appear
- AND no 404 errors for static resources appear
- AND no CORS warnings appear

---

### Requirement 8.7: Git Status Verification

The system **MUST** only have expected files modified.

**Details**:
- `git status` **MUST** show only `src/main/resources/static/openspec-y-skills-teams.html` as modified
- No accidental changes **MUST** appear in other files (config, pom.xml, etc.)
- If new files were created, only they **MUST** be untracked (not modified tracked files)

#### Scenario: Developer checks git status after build

- GIVEN changes have been made
- WHEN developer runs `git status`
- THEN only `src/main/resources/static/openspec-y-skills-teams.html` appears modified
- AND no other tracked files appear modified
- AND no accidental changes to configuration files

---

## Testing Scenarios Summary

### Happy Path
1. User navigates to page → page loads, renders correctly, no errors
2. User scrolls on desktop → all content visible, responsive, theme works
3. User on mobile → content fits without overflow, readable
4. User on dark theme → contrast good, colors theme-aware
5. User uses keyboard only → all elements reachable, focus visible

### Edge Cases
1. Extremely small mobile (320px) → still readable
2. Very large desktop (1440px+) → content doesn't stretch excessively
3. Theme toggle clicks rapidly → no visual glitches
4. Scroll to top from page end → smooth animation
5. Images missing (if any) → alt text displays, layout doesn't break

### Error States
1. Server returns 500 → graceful error page OR fallback content
2. Static file not found → 404 with helpful message
3. JavaScript disabled → page still readable (if framework requires JS, note as limitation)
4. Browser back button → state preserved correctly

---

## Design Token Variables

All colors **MUST** use these CSS variables (defined in `theme.css`):

```css
--primary: Main brand color
--secondary: Secondary brand color
--surface-1: Light surface background
--surface-2: Medium surface background
--surface-3: Dark surface background
--text: Primary text color
--text-secondary: Secondary text color
--error: Error/alert color
--success: Success color
--warning: Warning color
```

---

## Success Metrics

- ✅ All 28 requirements (Phase 4: 7, Phase 5: 9, Phase 6: 8, Phase 8: 7) are satisfied
- ✅ All scenarios pass manual testing
- ✅ No browser console errors
- ✅ WCAG AA accessibility compliance
- ✅ Responsive on 375px, 768px, 1920px
- ✅ Maven build success
- ✅ Spring Boot server starts without errors
- ✅ HTTP 200 response for main page and `/openspec-y-skills-teams.html`
- ✅ No unintended file modifications

---

**Document Version**: 1.0
**Last Updated**: 2026-02-26
**Status**: Ready for implementation (Phase 4 → Deployment)

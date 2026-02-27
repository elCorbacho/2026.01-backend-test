## ADDED Requirements

### Requirement: Semantic layout with header, main, aside y footer
La página SHALL usar elementos HTML5 `header`, `main`, `aside` y `footer` organizados dentro de un contenedor con `display: grid` de modo que el `header` esté arriba, el `aside` y el contenido principal compartan fila en desktop y `footer` permanezca al final.

#### Scenario: Desktop layout with header, aside, footer
- **WHEN** viewport is at least 1024px wide and the user loads `openspec-y-skills-teams.html`
- **THEN** the DOM shall include `header`, `aside`, `main` and `footer` elements arranged via CSS grid such that `aside` sits to the right of `main`, the `header` spans the width on top, and the `footer` spans the width below both sections.

### Requirement: Aside contextual navigation and summary
El `aside` SHALL contain quick links to the comparison and integration sections, una breve leyenda contextual (`<p>`) y acciones secundarias, y SHALL reuse `.card-base` para mantener consistencia visual.

#### Scenario: Aside content provides quick access to sections
- **WHEN** the user views the page on desktop or tablet
- **THEN** the `aside` shall show at least two anchor links pointing to `#comparativa` and `#integracion` y un párrafo descriptivo, y SHALL have `.card-base` styling.

### Requirement: Responsive ordering and spacing
For viewport widths below 768px the layout SHALL collapse to a single column, with `header` followed by `main` and then `aside`, and spacing shall be managed with `gap` variables such that there is at least `1.5rem` between sections.

#### Scenario: Mobile layout stacks sections
- **WHEN** viewport width is 375px (mobile emulation) and the page loads
- **THEN** the CSS grid SHALL reflow so `main` appears before `aside`, `gap` between rows is >=1.5rem, and no horizontal scrollbar appears.

### Requirement: Footer information and actions
The `footer` SHALL replicate key navigation links, mostrar el copyright y un CTA (por ejemplo, "Explorar API"), y deberá tener `aria-label="Pie de página"`.

#### Scenario: Footer exposes navigation y CTA
- **WHEN** a user scrolls to the bottom of the page
- **THEN** the `footer` SHALL contain at least three `<a>` links, una etiqueta CTA y un `aria-label`, y SHALL visually contrast con el resto de la página usando `var(--surface-2)` o `var(--surface-1)` según el tema.

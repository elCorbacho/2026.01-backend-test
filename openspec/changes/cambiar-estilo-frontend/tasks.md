## 1. Layout and semantic structure

- [x] 1.1 Refactor `openspec-y-skills-teams.html` to wrap content in a `div.layout-shell` using `header`, `main`, `aside`, and `footer` grid areas while keeping existing textual content.
- [x] 1.2 Add aside content with quick links to `#comparativa` y `#integracion`, un párrafo contextual, y mantenlo dentro de una `card-base` para conservar estilo.
- [x] 1.3 Update the footer to include al menos tres enlaces útiles, un CTA como “Explorar API” y el atributo `aria-label="Pie de página"`.

## 2. Styles, theme, and behavior

- [x] 2.1 Extend `/css/openspec-y-skills-teams.css` con variables (`--layout-gap`, `--aside-width`), grid definitions y media queries que posicionen `aside` a la derecha en desktop y debajo del main en mobile.
- [x] 2.2 Ajustar scripts/CSS para asegurar que el header sea sticky/fijo y el layout responde correctamente a los temas claro/oscuro existentes sin saltos de color.

## 3. Testing & documentation

- [x] 3.1 Validar en DevTools (375px, 768px, 1920px) que el layout respeta orden y gaps sin scroll horizontal y que los elementos `header/aside/footer` mantienen roles semánticos.
- [x] 3.2 Documentar en `verify-report.md` (y cualquier doc de frontend existente) los pasos de verificación y capturas necesarias para la nueva estructura.

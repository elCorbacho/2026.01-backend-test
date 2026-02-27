## Context

El sitio tiene varias páginas estáticas generadas en `src/main/resources/static/`, a las que se les ha aplicado normalización previa. Actualmente el contenido carece de una jerarquía semántica clara: los `nav`, `header`, `main` y `footer` se mezclan y no hay un `aside` que aporte contexto adicional. Los estilos se cargan desde `/css/openspec-y-skills-teams.css` y otros CSS específicos, con JavaScript minimalista. Debemos mantener la estructura actual del build Maven y no agregar dependencias nuevas.

## Goals / Non-Goals

**Goals:**
- Rediseñar la vista clave (`openspec-y-skills-teams.html`) (y cualquier página que reutilice el layout) para exponer un `header` fijo con navegación, un `aside` contextual para enlaces o resúmenes y un `footer` consistente.
- Reutilizar variables CSS ya definidas (`--surface-*`, `--text`, `--border`, etc.) y reforzar el grid con `display: grid` o `grid-template-areas` sin introducir librerías extra.
- Mantener el tema claro/oscuro existente y asegurar que el layout es responsive (desktop/tablet/mobile) y accesible (focus states, lecturas semánticas).
- Documentar la nueva estructura en el spec `frontend-layout-structure` para guiar futuras modificaciones.

**Non-Goals:**
- No tocaremos la lógica backend ni agregaremos API nuevas.
- No reemplazaremos Bootstrap CDN actual; nos apoyaremos en sus utilidades para grid y espaciado.
- No implementaremos un CMS; el contenido seguirá siendo estático y gestionado en HTML/CSS existentes.

## Decisions

1. **Layout semántico con grid** → Usaremos un contenedor que defina `grid-template-areas` (`header`, `aside`, `main`, `footer`) para que sea fácil reorganizar en móviles (columnas). Esto mantiene el DOM limpio y facilita la separación de estilos para cada región.
2. **Header persistente con nav y branding** → Conservaremos el `navbar` ya existente pero lo envolveremos en un `header` semántico y agregaremos un botón de menú para móviles dentro del `header` para mantener accesibilidad.
3. **Aside contextual** → Insertaremos un `aside` dentro del layout que muestre enlaces rápidos a secciones y resúmenes, reutilizando la clase `.card-base` para mantener el look & feel. El `aside` irá junto al `main` en desktop y se moverá debajo en mobile usando `order` y media queries.
4. **Footer informativo y acciones** → Rediseñaremos el `footer` para incluir enlaces clave y datos de contacto/resumen, manteniendo la tipografía y colores actuales, pero con mejor distribución de columnas.
5. **Estilos actualizados** → Incluir un bloque en `/css/openspec-y-skills-teams.css` con variables para `--layout-gap`, `--aside-width`, y mixins (custom properties) para hover/focus states, además de las media queries necesarias.

## Risks / Trade-offs

[Responsive complexity] → El nuevo grid puede romperse si el contenido del `aside` crece mucho. **Mitigación:** limitamos el ancho máximo y usamos `overflow-wrap` / `max-height` con scroll si es necesario.
[Theme flicker] → Modificar el layout podría causar flash al cambiar de tema. **Mitigación:** actualizamos el script existente para aplicar clases antes de mostrar la página (usa `html[data-bs-theme]`).
[Legacy CSS] → Algunos estilos globales (por ejemplo, `body` margin) podrían interferir. **Mitigación:** encapsulamos el layout dentro de una clase `.layout-shell` y reseteamos márgenes/padding allí.

## Migration Plan

1. Crear el nuevo spec `specs/frontend-layout-structure/spec.md` describiendo la estructura, clases y comportamientos esperados (sidebar collapse, header stickiness, footer links).
2. Actualizar `openspec-y-skills-teams.html` para usar la nueva estructura, ajustando enlaces y bloques para que vivan dentro de `header`, `aside`, `main` y `footer` *sin eliminar contenido existente*.
3. Modificar `/css/openspec-y-skills-teams.css` para definir el grid, los states (hover/focus) y responsividad, manteniendo atención en las variables del tema.
4. Verificar en DevTools que la nueva estructura se mantiene en Bootstrap breakpoints; si surgen regresiones, usar `@media (max-width: 768px)` y `@media (min-width: 1024px)` para ajustar.
5. Documentar los cambios en `tasks.md`, incluyendo pasos de verificación manual (DevTools, accesibilidad, console).

## Open Questions

1. ¿El `aside` debe incluir contenido dinámico (por ejemplo, pasos de comparación) o solo enlaces a secciones existentes?

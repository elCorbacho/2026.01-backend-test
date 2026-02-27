## Why

La experiencia actual del frontend carece de una estructura moderna y semántica donde el contenido principal esté anclado por un encabezado persistente, una barra lateral contextual y un pie de página cohesivo. Cambiar el estilo general ahora mejora la navegación, narrativa y accesibilidad del sitio sin tocar la lógica backend.

## What Changes

- Rediseñar las páginas clave para usar un layout basado en `header`, `aside`, `main` y `footer` con soporte responsive y temas claros/oscuro existentes.
- Establecer nuevos patrones CSS en `/css/openspec-y-skills-teams.css` (y otros estilos relevantes) que definan variables, grids y componentes para el header/aside/floor.
- Ajustar los HTML estáticos y scripts según sea necesario para reflejar la nueva estructura sin recargar scripts inline.

## Capabilities

### New Capabilities
- `frontend-layout-structure`: Define la nueva estructura semántica con header persistente, aside contextual y footer informativo, y documenta los requisitos de diseño y accesibilidad.

### Modified Capabilities
- None: no se cambian requisitos de capacidades existentes.

## Impact

- `src/main/resources/static/openspec-y-skills-teams.html` (estructura y estilos principales).
- `src/main/resources/static/css/openspec-y-skills-teams.css` y otros CSS/JS asociados para soportar el layout nuevo.
- Quizá otras páginas estáticas que compartirán el nuevo patrón de header/aside/footer si se reutilizan los estilos.

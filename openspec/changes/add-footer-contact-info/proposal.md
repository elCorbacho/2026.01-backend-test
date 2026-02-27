## Why

El frontend actualmente carece de un bloque persistente con los datos de contacto del desarrollador, lo que dificulta que clientes o testers sepan cómo comunicarse ante dudas o incidencias. Agregar un footer con los datos de Andrés Corbacho mejora la confianza y accesibilidad sin alterar el flujo principal de la aplicación.

## What Changes

- Añadir un footer global en todas las vistas principales con la información de contacto del desarrollador.
- Garantizar que el nuevo footer sigue la guía de estilos existente (tipografía, colores, espaciado) y se adapta tanto a desktop como móvil.
- Incluir en el footer el texto "Developer Andrés Corbacho" y el correo `histopat.cor@gmail.com`, con un enlace matcheado al formato `mailto:`.

## Capabilities

### New Capabilities
- `footer-contact-info`: proporciona un componente visual en todas las páginas que expone claramente el nombre del desarrollador y cómo contactarlo.

### Modified Capabilities
-

## Impact

- Frontend layout y hojas de estilo (CSS/SCSS) que definen la estructura base y paleta de la página principal.
- Posible impacto en componentes compartidos que controlan el layout general (p. ej. `AppLayout`, `MainLayout`).

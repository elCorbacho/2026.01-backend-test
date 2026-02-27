## Why

Los textos del frontend están en español, pero al ver la landing aparecen caracteres corruptos como "Enlaces Ãºtiles" en lugar de "Enlaces útiles", lo que rompe la legibilidad y da una mala impresión frente a usuarios chilenos. Necesitamos garantizar que las páginas se entregan con codificación UTF-8 y contexto de idioma español latino para que todas las vocales acentuadas se muestren correctamente.

## What Changes

- Revisar el `index.html` para declarar explícitamente el idioma `es-CL` y la cabecera/`<meta>` de tipo de contenido UTF-8 de forma predeterminada.
- Forzar desde el backend que todas las respuestas, incluyendo las estáticas, salgan con el `charset=UTF-8` y el idioma español latino (es-CL) en sus headers.
- Verificar que los títulos y bloques clave (por ejemplo el encabezado "Enlaces útiles") usan texto acentuado válido y no entidades corruptas.

## Capabilities

### New Capabilities
- `spanish-encoding`: garantiza que la UI se renderiza en español latino con `charset=UTF-8` para que los acentos se vean bien.

### Modified Capabilities
-

## Impact

- Frontend estático (`src/main/resources/static/index.html`) y su CSS asociado.
- Configuración MVC (`ipss.web2.examen.config.WebConfig`) para exponer filtros/encabezados nuevos.
- Posible ajuste en propiedades (`application.properties`) para dejar claro el idioma por defecto.

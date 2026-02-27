## Why

El frontend está desplegando texto con caracteres mal codificados (por ejemplo, “LÃ¡minas” en lugar de “Láminas”), lo cual confunde a los usuarios y contradice el requisito de que el sitio funcione exclusivamente en español.

## What Changes

- Asegurar que todas las páginas y activos del frontend usan UTF-8 en meta tags, headers y archivos estáticos para evitar que acentos como “á” se muestren como mojibake.
- Revisar y actualizar los textos visibles, plantillas y componentes clave para que estén en español correcto con acentos adecuados (por ejemplo, “Láminas”, “Álbum”, “Catálogo”).
- Reforzar la configuración del proyecto para declarar explícitamente el idioma español (p. ej., `lang="es"` en el HTML base y/o configuración del framework).
- Documentar el criterio de calidad de texto para futuros cambios y, si aplica, agregar pruebas visuales o locales básicas que verifiquen los textos principales.

## Capabilities

### New Capabilities
- `spanish-frontend-localization`: Garantiza que el frontend se sirva con codificación UTF-8, utilice el atributo de idioma español y muestre textos en español con acentos correctos en los elementos visibles por el usuario.

### Modified Capabilities
- Ninguna (sin cambios de especificación)

## Impact

- Archivos HTML/JSX estáticos o plantillas del frontend que contienen textos visibles.
- Configuración base del layout (meta tags `charset`, `lang`, etc.).
- Cualquier pipeline de construcción o despliegue que pudiera alterar el encoding de los activos.
- Documentación de estilo/idioma para garantizar consistencia.

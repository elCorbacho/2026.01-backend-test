## Why

La aplicación solo expone información vía API y no ofrece una vista web dedicada para visualizar la estructura de la base de datos de forma rápida y comprensible. Se requiere una página complementaria a `index` para facilitar entendimiento del modelo relacional durante desarrollo, validación y soporte.

## What Changes

- Agregar una nueva vista web complementaria a `index` para mostrar la estructura detallada de la base de datos.
- Incluir representación de tablas principales, campos, tipos y relaciones entre entidades (PK/FK).
- Mostrar restricciones relevantes (por ejemplo, unicidad y columnas de auditoría/soft delete) en formato legible.
- Exponer la vista mediante una ruta web dedicada dentro de la aplicación Spring Boot.

## Capabilities

### New Capabilities
- `database-structure-web-view`: Vista web de documentación estructural de BD (tablas, columnas, relaciones y restricciones) integrada al proyecto.

### Modified Capabilities
- _Ninguna._

## Impact

- Capa web (templates/controlador) para publicar nueva página HTML.
- Posible uso de metadatos estáticos o generados desde entidades para construir la vista.
- Sin cambios en contratos de API REST existentes.
- Mejora de experiencia para desarrolladores y revisores al inspeccionar el diseño de datos.

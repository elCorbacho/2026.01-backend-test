## Why

La poblacion nativa de datos no tiene un estandar uniforme entre entidades, lo que genera diferencias en volumen y calidad de registros semilla para desarrollo, pruebas y validaciones funcionales. Estandarizar ahora evita regresiones de datos base y reduce errores de negocio causados por datos incompletos o inconsistentes.

## What Changes

- Definir un criterio unificado para que cada entidad incluida en la poblacion nativa disponga exactamente de 10 registros activos y validos.
- Verificar que los datos semilla esten bien construidos, con campos obligatorios completos, formatos coherentes y relaciones referenciales consistentes.
- Identificar entidades con faltantes o exceso de registros respecto del objetivo de 10 y establecer acciones correctivas de poblacion.
- Establecer reglas de verificacion para prevenir que futuras actualizaciones de semillas rompan el estandar.
- Documentar los hallazgos de calidad de datos y las correcciones propuestas por entidad.

## Capabilities

### New Capabilities
- `native-db-seed-standardization`: Estandarizar la poblacion nativa para asegurar exactamente 10 registros validos por cada elemento/entidad objetivo y definir verificaciones de calidad de datos.

### Modified Capabilities
- Ninguna.

## Impact

- Datos base de desarrollo y pruebas iniciales del sistema.
- Inicializadores de datos nativos y scripts de poblacion usados por la aplicacion.
- Validaciones funcionales y pruebas que dependen de cardinalidad y consistencia de datos semilla.
- Documentacion operativa relacionada con carga inicial y control de calidad de datos.
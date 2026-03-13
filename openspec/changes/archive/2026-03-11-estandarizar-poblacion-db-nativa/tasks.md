## 1. Descubrimiento y alcance de entidades

- [x] 1.1 Inventariar todas las entidades consideradas poblacion nativa dentro del alcance del cambio.
- [x] 1.2 Definir por entidad los criterios de registro activo y los campos obligatorios que determinan calidad minima.
- [x] 1.3 Documentar baseline inicial por entidad (cantidad actual y observaciones de calidad).

## 2. Verificacion de cardinalidad (objetivo: 10)

- [x] 2.1 Ejecutar validacion de cardinalidad por entidad para detectar faltantes (<10), exactas (=10) y excedentes (>10).
- [x] 2.2 Registrar evidencia por entidad con conteos y estado de conformidad.
- [x] 2.3 Consolidar reporte de no conformidades de cardinalidad priorizado por impacto.

## 3. Verificacion de calidad de construccion

- [x] 3.1 Validar completitud de campos obligatorios en registros semilla por entidad.
- [x] 3.2 Validar coherencia de formato de datos segun reglas del dominio.
- [x] 3.3 Validar consistencia referencial en relaciones requeridas para los registros evaluados.
- [x] 3.4 Registrar no conformidades de calidad con clasificacion por tipo de error.

## 4. Remediacion de datos semilla

- [x] 4.1 Disenar plan de remediacion por entidad para completar faltantes hasta 10 registros activos y validos.
- [x] 4.2 Definir ajustes para normalizar entidades con exceso sin romper integridad ni reglas de unicidad.
- [x] 4.3 Definir correcciones de registros mal construidos para que cumplan calidad minima.
- [x] 4.4 Verificar idempotencia del proceso de poblacion estandarizada para evitar duplicados o degradacion.

## 5. Cierre y validacion final

- [x] 5.1 Ejecutar verificacion final de cumplimiento por entidad (10 registros activos y validos).
- [x] 5.2 Generar reporte final con entidades conformes, pendientes y recomendaciones de mantenimiento.
- [x] 5.3 Actualizar documentacion operativa con reglas de estandar y controles para prevenir regresiones.
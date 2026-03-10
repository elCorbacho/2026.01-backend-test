## Context

Actualmente el sistema REST solo contempla entidades para álbumes y láminas, sin soporte para marcas de bicicleta como catálogo independiente. La arquitectura usa MVC con DTOs manuales, mappers, soft delete en todas las entidades, y respuestas ApiResponseDTO. La base de datos es MySQL (Spring Data JPA), seed inicial se gestiona desde DataInitializer. El objetivo es habilitar gestión de marcas de bicicleta, listas, y posible futura extensión a modelos.

## Goals / Non-Goals

**Goals:**
- Agregar entidad `MarcaBicicleta` con campos: id, nombre, active.
- Exponer endpoint GET `/api/marcas-bicicleta` para listar marcas activas.
- Seed inicial configurable desde DataInitializer.
- Seguir convenciones de DTOs, Mapper manual y soft delete.
- Pruebas unitarias (@WebMvcTest) y de integración.

**Non-Goals:**
- Gestión de modelos de bicicleta (queda fuera en este cambio).
- Operaciones CRUD avanzadas (solo GET y seed inicial).
- Funcionalidad multi-catálogo/relaciones.

## Decisions

- Se reutiliza el patrón de MarcaAutomovil para entity, repository, service, controller y seed.
- Soft delete con campo active=true/false.
- Seed idempotente en DataInitializer, poblado solo si repo está vacío.
- Nombres en español, DTOs con validación (nombre requerido, longitud).
- ApiResponseDTO envuelve todas las respuestas.
- El endpoint se denomina `/api/marcas-bicicleta` (pluralizado, hyphen-case).

## Risks / Trade-offs

[Riesgo] Colisión de nombres en DB si existen entidades previas MarcaBicicleta → [Mitigación] Usar prefijos propios y confirmación al migrar.
[Riesgo] No cubrir todas las validaciones → [Mitigación] Test unitarios e integración con paths de error y happy path.
[Riesgo] Semilla de marcas incompleta → [Mitigación] Permitir ajuste manual y crecimiento futuro.
[Trade-off] No se implementa CRUD completo ahora (solo GET y seed) → [Mitigación] Foco en delivery incremental.

## Migration Plan
- Crear entidad, repo, controller y seed en DataInitializer.
- Agregar tests y documentar en Swagger.
- Si existe MarcaBicicleta previa, migrar datos según convención propuesta.
- Rollback: revertir archivos y eliminar tabla si la migración resulta no compatible.

## Open Questions
- ¿Qué volumen de marcas se poblará inicialmente? ¿Solo 5 conocidas? ¿Permitir ajuste por config?
- ¿Se requiere paginación en el endpoint GET desde el inicio?
- ¿En qué momento se expandirá a modelos de bicicleta (feature futuro)?
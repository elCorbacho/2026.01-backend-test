# Tasks: API Presidentes de Chile

## Phase 1: Foundation

- [x] 1.1 Crear entidad `src/main/java/ipss/web2/examen/models/PresidenteChile.java` con auditing, soft delete y columnas del diseño.
- [x] 1.2 Crear repositorio `src/main/java/ipss/web2/examen/repositories/PresidenteChileRepository.java` con `findByActiveTrue()`.
- [x] 1.3 Definir DTO `src/main/java/ipss/web2/examen/dtos/PresidenteChileResponseDTO.java` (campos y anotaciones Lombok).
- [x] 1.4 Crear mapper manual `src/main/java/ipss/web2/examen/mappers/PresidenteChileMapper.java` para entidad → DTO.
- [x] 1.5 (DB) Agregar tabla `presidente_chile` con columnas y `is_active` compatibles con la entidad.

## Phase 2: Core Implementation

- [x] 2.1 Crear servicio `src/main/java/ipss/web2/examen/services/PresidenteChileService.java` con método `obtenerPresidentesChile()` read-only.
- [x] 2.2 Implementar lógica de servicio para consultar `findByActiveTrue()` y mapear a DTO.

## Phase 3: Integration / Wiring

- [x] 3.1 Crear controlador `src/main/java/ipss/web2/examen/controllers/api/PresidenteChileController.java` con `GET /api/presidentes-chile`.
- [x] 3.2 Envolver respuesta con `ApiResponseDTO.ok(...)` y mensaje estándar.
- [x] 3.3 Verificar registro automático de endpoint en OpenAPI/Swagger.

## Phase 4: Testing / Verification

- [x] 4.1 Unit test para `PresidenteChileMapper` (mapeo entidad → DTO).
- [x] 4.2 Unit test para `PresidenteChileService.obtenerPresidentesChile()` (mock repository).
- [x] 4.3 Integration test `GET /api/presidentes-chile` verificando `success = true` y lista vacía cuando no hay activos.
- [x] 4.4 Integration test de error inesperado para validar `errorCode = INTERNAL_SERVER_ERROR`.

## Phase 5: Cleanup / Documentation

- [x] 5.1 (Opcional) Documentar la creación de tabla en README o nota técnica.
- [x] 5.2 (Opcional) Cargar datos de ejemplo en `DataInitializer` si el frontend lo requiere.

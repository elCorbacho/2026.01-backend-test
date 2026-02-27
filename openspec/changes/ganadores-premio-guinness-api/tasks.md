# Tasks: API de Ganadores del Premio Guinness

## Phase 1: Foundation / Modelado

- [x] 1.1 Crear entidad `src/main/java/ipss/web2/examen/models/GanadorGuinness.java` con campos `nombre`, `categoria`, `record`, `anio`, `createdAt`, `updatedAt`, `active` y anotaciones JPA + auditing.
- [x] 1.2 Crear DTO `src/main/java/ipss/web2/examen/dtos/GanadorGuinnessResponseDTO.java` con Lombok (`@Data`, `@Builder`, etc.).
- [x] 1.3 Crear mapper manual `src/main/java/ipss/web2/examen/mappers/GanadorGuinnessMapper.java` con método `toDTO`.
- [x] 1.4 Crear repository `src/main/java/ipss/web2/examen/repositories/GanadorGuinnessRepository.java` con `findByActiveTrue()`.

## Phase 2: Core Implementation

- [x] 2.1 Crear servicio `src/main/java/ipss/web2/examen/services/GanadorGuinnessService.java` con método `obtenerGanadoresGuinness()` y `@Transactional(readOnly = true)`.
- [x] 2.2 Implementar lógica de consulta en service para retornar solo activos y mapear a DTO.

## Phase 3: Integración / Wiring

- [x] 3.1 Crear controlador `src/main/java/ipss/web2/examen/controllers/api/GanadorGuinnessController.java` con GET `/api/ganadores-guinness`.
- [x] 3.2 En el controller, envolver respuesta en `ApiResponseDTO` con mensaje de éxito.

## Phase 4: Testing / Verificación

- [x] 4.1 (Unit) Test del mapper: entidad -> DTO (campos `nombre`, `categoria`, `record`, `anio`).
- [x] 4.2 (Unit) Test del service: retorna solo `active = true`.
- [x] 4.3 (Integration) Test del endpoint GET `/api/ganadores-guinness` con respuesta 200 y wrapper `ApiResponseDTO`.

## Phase 5: Cleanup / Documentación

- [x] 5.1 Añadir comentarios en español en los nuevos archivos para mantener convención del proyecto.



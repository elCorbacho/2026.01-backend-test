## Context

El sistema expone actualmente recursos relacionados con álbumes y láminas, con una arquitectura en capas bien definida (Controller → Service → Repository → Model) y convenciones claras de auditoría, soft delete y envoltura de respuestas (`ApiResponseDTO`). No existe todavía un modelo ni endpoints dedicados para campeones de jockey, pero se requiere enriquecer el dominio para poder mostrar rankings y estadísticas históricas, sin impactar de forma rompiente los contratos existentes.

## Goals / Non-Goals

**Goals:**
- Introducir una nueva entidad JPA `CampeonJockey` coherente con el resto de modelos (auditoría, soft delete).
- Exponer un endpoint GET para listar todos los campeones de jockey activos en un recurso dedicado (`/api/campeones-jockey` o similar).
- Añadir capa de servicio y repositorio específicos para este nuevo dominio, siguiendo las mismas prácticas que álbumes y láminas.
- Poblado inicial de datos de campeones de jockey mediante un inicializador de datos reutilizando el enfoque de `DataInitializer`.

**Non-Goals:**
- No se modificarán las reglas de negocio ni contratos actuales de álbumes, láminas ni catálogo.
- No se implementarán por ahora operaciones de escritura (POST/PUT/DELETE) para campeones de jockey; el alcance es de solo lectura con datos precargados.
- No se abordará seguridad avanzada ni control de acceso específico para este recurso más allá de lo ya configurado globalmente.

## Decisions

- **Nueva entidad `CampeonJockey`:** Se modelará como una entidad JPA en el paquete `models`, con campos `id`, `nombreJockey`, `pais` (opcional), `titulo`, `anio`, `createdAt`, `updatedAt`, `active`. Seguirá las mismas anotaciones de auditoría y soft delete que `Album` y `Lamina`.
- **Repositorio dedicado:** Se creará `CampeonJockeyRepository` en `repositories` extendiendo `JpaRepository<CampeonJockey, Long>` e incluyendo al menos un método `findByActiveTrue()` para recuperar únicamente registros activos.
- **Capa de servicio:** Se implementará `CampeonJockeyService` y su implementación concreta, con un método principal `obtenerCampeonesActivos()` anotado con `@Transactional(readOnly = true)`, que delega en el repositorio y mapea a DTOs.
- **DTOs y mapeo manual:** Se añadirán `CampeonJockeyResponseDTO` (y si se requiere en el futuro, un RequestDTO), junto con un `CampeonJockeyMapper` manual en el paquete `mappers`, siguiendo el estilo de `AlbumMapper` y `LaminaMapper`.
- **Controlador REST:** Se creará `CampeonJockeyController` en `controllers/api` con un endpoint GET `/api/campeones-jockey` que retornará `ResponseEntity<ApiResponseDTO<List<CampeonJockeyResponseDTO>>>`, manejando mensajes y errores de forma consistente con el `GlobalExceptionHandler`.
- **Inicialización de datos:** Se ampliará o complementará `DataInitializer` (en `config`) para insertar un set fijo de campeones de jockey si la tabla está vacía durante el arranque de la aplicación.
- **Documentación:** El nuevo endpoint se describirá automáticamente en Swagger/OpenAPI mediante las anotaciones de SpringDoc ya presentes en el proyecto.

## Risks / Trade-offs

- **Aumento del modelo de dominio:** Añadir una entidad adicional incrementa la complejidad del modelo, pero el impacto es acotado y alineado con el crecimiento natural del dominio deportivo.
- **Datos iniciales estáticos:** El poblado de datos a través de `DataInitializer` implica que los campeones configurados serán estáticos salvo futuras operaciones de mantenimiento; como mitigación, el modelo y servicio se diseñan ya preparados para futuras operaciones de escritura.
- **Dependencia de la estructura existente:** La implementación se apoya fuertemente en patrones ya usados (mappers manuales, `ApiResponseDTO`, soft delete); si en el futuro se cambia el patrón global, será necesario ajustar también este nuevo recurso.


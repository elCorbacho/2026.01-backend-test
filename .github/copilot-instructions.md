# Guía para agentes de IA en este proyecto

# My Project Documentation

<memories hint="Manage via memory tool">
<memory path="github/copilot/memories/preferences.txt">
- Prefers TypeScript for new projects
- Uses ESLint for code quality
</memory>

<memory path="/memories/context.txt">
Project is a VS Code extension for AI agent memory management.
</memory>
</memories>


## Contexto y arquitectura
- Proyecto: API REST Spring Boot 3.5.9 (Java 21) para gestionar **álbumes** y **láminas** de colección.
- Capas principales:
  - `controllers/api`: controladores REST finos, sólo orquestan peticiones y respuestas.
  - `services`: lógica de negocio y reglas específicas de dominio.
  - `repositories`: acceso a datos con Spring Data JPA.
  - `dtos` + `mappers`: DTOs de entrada/salida y conversión explícita con mappers.
  - `models`: entidades JPA con soft delete (`active`) y auditoría (`createdAt`, `updatedAt`).
  - `exceptions`: excepciones de dominio y `GlobalExceptionHandler` centralizado.

## Flujo típico de una feature
- Para nuevas operaciones expuestas por API sigue siempre este flujo:
  1. **Entidad/relación** en `models` (con campos `active`, `createdAt`, `updatedAt` y relaciones JPA claras).
  2. **Repositorio** en `repositories` con métodos derivados (ej. `findByActiveTrue`, `findByAlbumAndActiveTrue`).
  3. **DTOs** de request/response en `dtos` con validación Jakarta (`@NotBlank`, `@Size`, `@Min`, ...).
  4. **Mapper** en `mappers` con métodos `toEntity`, `toResponseDTO` y, si aplica, `updateEntity`.
  5. **Servicio** en `services` que use repositorios y mappers, lance excepciones de dominio y NO devuelva entidades JPA.
  6. **Controlador REST** en `controllers/api` que reciba/envíe sólo DTOs y envuelva todas las respuestas en `ApiResponseDTO`.

## Convenciones de controladores REST
- Todas las respuestas deben usar `ApiResponseDTO<T>` (ver `AlbumController` y `LaminaUserController`).
  - Rellena siempre: `success`, `message`, `data` (si aplica), `timestamp`.
  - Evita construir respuestas "a mano" fuera de este patrón.
- Usa `@Valid` sobre los cuerpos de petición (`@RequestBody`) y DTOs con anotaciones de validación.
- Mantén los controladores delgados: **cero** lógica de negocio o acceso a repositorios, delega en servicios.
- Reutiliza mensajes y estructura existentes como referencia para nuevos endpoints (ej. mensajes de éxito al crear/actualizar/eliminar).

## Lógica de negocio y servicios
- Los servicios son responsables de:
  - Aplicar reglas de dominio (validar existencia de catálogo, detectar láminas repetidas, calcular estado del álbum, etc.).
  - Gestionar soft delete: marcar `active = false` en vez de borrar filas (`eliminarAlbum`, `eliminarLamina`).
  - Usar métodos de repositorios que ya respetan `active` cuando se quiere sólo datos activos (`findByActiveTrue`, `findByAlbumAndActiveTrue`).
- Sigue el patrón de `AlbumService` y `LaminaService` para nuevos servicios:
  - Inyecta repositorios y mappers vía constructor (`@RequiredArgsConstructor`).
  - Anota la clase con `@Service` y `@Transactional` y usa `@Transactional(readOnly = true)` en métodos de sólo lectura.
  - Lanza `ResourceNotFoundException` cuando un recurso no exista; evita devolver `null`.

## Manejo de errores
- El manejo centralizado está en `GlobalExceptionHandler`:
  - Usa `ResourceNotFoundException` cuando falte un recurso (404).
  - Usa `InvalidOperationException` para reglas de negocio incumplidas (400) en lugar de `RuntimeException` genérico.
  - Para errores de validación de DTOs, confía en `MethodArgumentNotValidException` + anotaciones Jakarta.
- Si necesitas un nuevo tipo de error, define una excepción específica en `exceptions` y añádela al `GlobalExceptionHandler` con:
  - `errorCode` claro y estable.
  - Mensaje amigable para el cliente.
  - Estructura de `errors` consistente cuando se incluya información de campos.

## Persistencia, soft delete y auditoría
- Todas las entidades deben:
  - Estar anotadas con `@EntityListeners(AuditingEntityListener.class)` y campos `@CreatedDate` y `@LastModifiedDate`.
  - Incluir un campo `Boolean active` o equivalente para soportar soft delete.
  - Usar relaciones JPA coherentes (ej. `Album` ↔ `Lamina` / `LaminaCatalogo`).
- Evita eliminar filas físicamente; sigue el patrón de `eliminarAlbum`/`eliminarLamina` para nuevas entidades.
- La auditoría JPA se habilita vía `JpaAuditingConfig`; no repliques lógica manual de timestamps.

## Carga masiva y catálogo de láminas
- La lógica de catálogo y estado está centralizada en `LaminaService`:
  - Usa `crearCatalogo` y repositorios de catálogo para validar que una lámina está definida antes de agregarla.
  - Para operaciones masivas, sigue el patrón de `agregarLaminasMasivo` y `procesarLaminaIndividual` (respuestas detalladas por ítem, conteos de éxito/error).
- No repliques validaciones de catálogo en controladores; reutiliza métodos de servicio existentes o extrae helpers dentro del servicio.

## Workflows de desarrollo
- Construir y ejecutar tests:
  - `./mvnw clean install` o `mvnw.cmd clean install` en Windows.
  - `./mvnw test` para correr sólo tests.
- Ejecutar la aplicación localmente:
  - `./mvnw spring-boot:run` o `mvnw.cmd spring-boot:run`.
- Swagger/OpenAPI:
  - La documentación interactiva está disponible en `/swagger-ui.html` (usa `springdoc-openapi` ya configurado).

## Qué evitar al modificar/crear código
- No exponer entidades JPA directamente en respuestas; usa siempre DTOs + mappers.
- No acceder a repositorios desde controladores.
- No lanzar excepciones genéricas si existe ya una excepción de dominio adecuada.
- No romper el contrato de `ApiResponseDTO` (los clientes esperan su estructura actual).


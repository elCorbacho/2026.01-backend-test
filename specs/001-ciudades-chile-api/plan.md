# Implementation Plan: Consulta de Ciudades de Chile

**Branch**: `001-ciudades-chile-api` | **Date**: 2026-03-11 | **Spec**: `specs/001-ciudades-chile-api/spec.md`
**Input**: Feature specification from `/specs/001-ciudades-chile-api/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Agregar un nuevo recurso de Ciudades de Chile con consultas de solo lectura (listado general y búsqueda por id), junto con carga inicial de 10 registros para entorno H2 local. La implementación seguirá arquitectura en capas del proyecto (Controller -> Service -> Repository -> Model), respuestas envueltas en `ApiResponseDTO<T>`, manejo de errores con excepciones custom y pruebas de controlador/servicio.

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: Java 21  
**Primary Dependencies**: Spring Boot 3.5.x, Spring Web, Spring Data JPA, Jakarta Validation, Lombok  
**Storage**: H2 en memoria para desarrollo/pruebas (JPA/Hibernate)  
**Testing**: JUnit 5, Spring Boot Test, Mockito, MockMvc  
**Target Platform**: API REST en servidor JVM
**Project Type**: Monolito backend web-service  
**Performance Goals**: Respuestas de lectura estables para colección pequeña (10+ registros iniciales)  
**Constraints**: Cumplir soft delete, ApiResponseDTO, y excepciones custom del proyecto  
**Scale/Scope**: Un nuevo recurso de dominio (CiudadChile) con 2 endpoints GET y semilla inicial de 10 datos

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

Verify the plan honours all five Core Principles (constitution v1.0.0):

- [x] **I. Layered Architecture** — No repository injected into a Controller; all business logic in Services.
- [x] **II. Soft Delete** — Any delete operation sets `active = false`; no `repository.delete()` / SQL `DELETE`.
- [x] **III. Response Envelope & DTOs** — All endpoints return `ResponseEntity<ApiResponseDTO<T>>`; correct DTO type used per operation.
- [x] **IV. Test Coverage** — Controller (`@WebMvcTest`), Service (`@ExtendWith(MockitoExtension.class)`), and integration (`@SpringBootTest`) tests are planned.
- [x] **V. Custom Exceptions** — Only `ResourceNotFoundException`, `InvalidOperationException`, or `EndpointNotFoundException` are used; no generic exceptions thrown.

> If a check fails, document the justification in the **Complexity Tracking** table below.

## Project Structure

### Documentation (this feature)

```text
specs/001-ciudades-chile-api/
├── plan.md
├── spec.md
└── tasks.md
```

### Source Code (repository root)
<!--
  ACTION REQUIRED: Replace the placeholder tree below with the concrete layout
  for this feature. Delete unused options and expand the chosen structure with
  real paths (e.g., apps/admin, packages/something). The delivered plan must
  not include Option labels.
-->

```text
src/main/java/ipss/web2/examen/
├── controllers/api/
├── services/
├── repositories/
├── models/
├── dtos/
├── mappers/
└── config/

src/test/java/ipss/web2/examen/
├── controllers/api/
├── services/
└── integration/
```

**Structure Decision**: Se utilizara la estructura monolitica Spring Boot existente en `src/main/java/ipss/web2/examen` y `src/test/java/ipss/web2/examen`, agregando los nuevos artefactos de CiudadChile por capa.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |

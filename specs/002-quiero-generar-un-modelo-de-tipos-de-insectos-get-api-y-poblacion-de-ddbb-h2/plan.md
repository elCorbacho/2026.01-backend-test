# Implementation Plan: Catalogo de tipos de insectos

**Branch**: `[002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2]` | **Date**: 2026-03-11 | **Spec**: `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/spec.md`
**Input**: Feature specification from `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/spec.md`

## Summary

Implementar un nuevo catalogo de tipos de insectos con dos consultas de lectura (listado y detalle), usando el flujo en capas existente (controller/service/repository/entity), respuestas envueltas en `ApiResponseDTO`, y poblacion inicial idempotente para entorno H2 de desarrollo/pruebas.

## Technical Context

**Language/Version**: Java 21 (Spring Boot 3.x)
**Primary Dependencies**: Spring Web, Spring Data JPA, Spring Validation, Lombok
**Storage**: H2 en memoria para desarrollo/pruebas (con soporte de perfil productivo en MySQL)
**Testing**: JUnit 5 + Spring Test (`@WebMvcTest`, `@SpringBootTest`) + Mockito
**Target Platform**: Servicio web backend JVM (Windows/Linux/macOS)
**Project Type**: Web service REST monolitico
**Performance Goals**: Responder consultas de listado y detalle en <2 segundos en carga normal de pruebas
**Constraints**: Arquitectura por capas obligatoria, soft delete obligatorio, DTO de respuesta obligatorio, no excepciones genericas
**Scale/Scope**: Un nuevo dominio catalogo (TipoInsecto), 2 endpoints GET, seed inicial minimo de 10 registros, cobertura de pruebas controller/service/integration

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Pre-Research Gate

- [x] **I. Layered Architecture** — Se planifica `Controller -> Service -> Repository -> Entity` sin acceso directo a repositorio desde controller.
- [x] **II. Soft Delete** — La entidad incluye `active`; consultas filtran solo activos; no se planifican eliminaciones fisicas.
- [x] **III. Response Envelope & DTOs** — Endpoints planificados con `ResponseEntity<ApiResponseDTO<T>>` y DTOs de salida dedicados.
- [x] **IV. Test Coverage** — Se planifican pruebas WebMvc, Service unitarias e Integracion para seed/flujo GET.
- [x] **V. Custom Exceptions** — Se planifica `ResourceNotFoundException` para detalle inexistente/inactivo y validaciones con excepciones del proyecto.

### Post-Design Gate

- [x] **I. Layered Architecture** — `data-model.md`, `contracts/` y `quickstart.md` mantienen capas y separacion de responsabilidades.
- [x] **II. Soft Delete** — Modelo y contrato de lectura incluyen filtro por activos y evitan operaciones de hard delete.
- [x] **III. Response Envelope & DTOs** — Contrato documenta envelope uniforme de exito/error.
- [x] **IV. Test Coverage** — Quickstart y plan de pruebas contemplan controller/service/integration para escenarios criticos.
- [x] **V. Custom Exceptions** — Contrato de error para 404 y 400 usa codigos descriptivos y flujo del manejador global.

## Project Structure

### Documentation (this feature)

```text
specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/
|-- plan.md
|-- research.md
|-- data-model.md
|-- quickstart.md
|-- contracts/
|   `-- insect-types-api.yaml
`-- tasks.md
```

### Source Code (repository root)

```text
src/main/java/ipss/web2/examen/
|-- controllers/api/
|-- services/
|-- repositories/
|-- models/
|-- dtos/
|-- mappers/
|-- config/
`-- exceptions/

src/test/java/ipss/web2/examen/
|-- controllers/api/
|-- services/
|-- integration/
|-- config/
`-- exceptions/
```

**Structure Decision**: Se usa la estructura backend monolitica existente en `src/main/java/ipss/web2/examen/` y `src/test/java/ipss/web2/examen/`, agregando solo archivos del nuevo dominio en carpetas ya estandarizadas.

## Complexity Tracking

Sin violaciones de constitucion en esta planificacion.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| Ninguna | N/A | N/A |

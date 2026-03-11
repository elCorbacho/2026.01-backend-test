# Implementation Plan: Estandarizar Respuestas API

**Branch**: `003-estandarizar-respuestas-api` | **Date**: 2026-03-11 | **Spec**: `/specs/003-estandarizar-respuestas-api/spec.md`
**Input**: Feature specification from `/specs/003-estandarizar-respuestas-api/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Estandarizar las respuestas HTTP de exito y error en endpoints REST para que todos expongan un contrato uniforme usando `ApiResponseDTO<T>` y manejo centralizado en `GlobalExceptionHandler`. El enfoque prioriza consistencia transversal (paginado/no paginado, validacion, negocio y no encontrado), alineacion de documentacion OpenAPI y verificaciones automatizables sobre endpoints representativos.

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: Java 21 (preview enabled in compiler plugin)  
**Primary Dependencies**: Spring Boot 3.5.11 (`web`, `data-jpa`, `validation`, `actuator`), springdoc-openapi 2.8.5, Lombok  
**Storage**: H2 en desarrollo local, MySQL 8.0 en produccion (JPA/Hibernate)  
**Testing**: JUnit 5 + Spring Boot Test (`@WebMvcTest`, `@SpringBootTest`) + Mockito  
**Target Platform**: API REST server Spring Boot (JVM 21) en entorno local y despliegue Linux/AWS
**Project Type**: Monolito backend web-service (Spring MVC + JPA)  
**Performance Goals**: Mantener latencia funcional actual sin regresion y garantizar contrato uniforme en al menos 95% de endpoints auditados (SC-001, SC-002)  
**Constraints**: Mantener compatibilidad semantica incremental, no romper soft delete, no exponer errores internos, sin implementacion fuera de artefactos de planificacion  
**Scale/Scope**: 22 controladores API activos aprox., estandar aplicable a respuestas de exito/error en endpoints paginados y no paginados

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

Verify the plan honours all five Core Principles (constitution v1.0.0):

- [x] **I. Layered Architecture** — El plan mantiene cambios en contrato/respuesta sobre capas Controller + ExceptionHandler, sin salto de capas ni repositorios en controladores.
- [x] **II. Soft Delete** — No se altera semantica de eliminacion; el estandar de respuesta se aplica sin introducir borrado fisico.
- [x] **III. Response Envelope & DTOs** — El objetivo central es unificar en `ResponseEntity<ApiResponseDTO<T>>` y convenciones DTO ya existentes.
- [x] **IV. Test Coverage** — Se planifican pruebas representativas de controlador, servicio y verificacion de contrato de error/exito.
- [x] **V. Custom Exceptions** — El contrato de error estandar se basa en excepciones personalizadas y `GlobalExceptionHandler`.

**Gate Status (Pre-Research)**: PASS

> If a check fails, document the justification in the **Complexity Tracking** table below.

## Project Structure

### Documentation (this feature)

```text
specs/003-estandarizar-respuestas-api/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── api-response-envelope.yaml
│   └── error-codes.md
└── tasks.md             # Fase 2 (fuera de este comando)
```

### Source Code (repository root)
<!--
  ACTION REQUIRED: Replace the placeholder tree below with the concrete layout
  for this feature. Delete unused options and expand the chosen structure with
  real paths (e.g., apps/admin, packages/something). The delivered plan must
  not include Option labels.
-->

```text
src/
├── main/java/ipss/web2/examen/
│   ├── controllers/api/
│   ├── dtos/
│   ├── exceptions/
│   ├── mappers/
│   ├── repositories/
│   └── services/
└── test/java/ipss/web2/examen/
  ├── controllers/
  ├── services/
  ├── integration/
  └── exceptions/

specs/003-estandarizar-respuestas-api/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
└── contracts/
```

**Structure Decision**: Se usa la estructura monolitica Spring Boot existente bajo `src/main/java/ipss/web2/examen` y `src/test/java/ipss/web2/examen`, con artefactos Speckit en `specs/003-estandarizar-respuestas-api`.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Phase 0 Research Plan

- Resolver definicion operacional del contrato estandar de exito/error para endpoints heterogeneos.
- Definir convencion unificada para errores de validacion, negocio, no encontrado e inesperado.
- Definir estrategia de verificacion automatizable para cumplimiento de contrato en endpoints representativos.

## Phase 1 Design Outputs

- `research.md`: decisiones y tradeoffs para estandarizacion.
- `data-model.md`: entidades de contrato (`RespuestaAPIEstandar`, `DetalleErrorAPI`, `ContratoEndpoint`) y reglas.
- `contracts/api-response-envelope.yaml`: contrato OpenAPI reusable del sobre estandar.
- `contracts/error-codes.md`: contrato funcional de codigos de error.
- `quickstart.md`: guia de validacion de comportamiento esperado.

## Shared Controller Response Contract Guidance

- Controladores deben retornar siempre `ResponseEntity<ApiResponseDTO<T>>`.
- Para respuestas exitosas usar los factory methods de `ApiResponseDTO` (`ok`, `created`) para garantizar `timestamp` y semántica uniforme.
- Para errores no construir envelopes manualmente en controladores; delegar a `GlobalExceptionHandler`.
- `GlobalExceptionHandler` debe construir envelopes de error con helpers reutilizables para evitar divergencias entre validación, negocio, no encontrado e interno.
- No envolver excepciones de negocio/control en `Exception` genérica dentro del controlador; propagar excepciones personalizadas.
- Mantener códigos HTTP semánticos existentes (200/201/400/404/409/500) mientras se normaliza el sobre de respuesta.
- En endpoints paginados y no paginados se conservan los mismos campos de nivel superior (`success`, `message`, `timestamp`) y el payload específico en `data`.

## Constitution Check (Post-Design Re-evaluation)

- [x] **I. Layered Architecture** — El diseno mantiene responsabilidades en Controller/Service/ExceptionHandler sin saltos de capa.
- [x] **II. Soft Delete** — La propuesta no altera semantica de eliminacion logica ni consultas activas.
- [x] **III. Response Envelope & DTOs** — El contrato definido exige `ApiResponseDTO<T>` en exito y error.
- [x] **IV. Test Coverage** — Quickstart incluye validacion de escenarios 2xx, 400, 404 y error inesperado; base para tareas de pruebas.
- [x] **V. Custom Exceptions** — Contrato de errores alineado a excepciones personalizadas ya establecidas.

**Gate Status (Post-Design)**: PASS

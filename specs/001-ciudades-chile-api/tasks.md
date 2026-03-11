# Tasks: Consulta de Ciudades de Chile

**Input**: Design documents from `/specs/001-ciudades-chile-api/`
**Prerequisites**: plan.md (required), spec.md (required for user stories)

**Tests**: No se agregan tareas de pruebas dedicadas en esta iteracion porque no fueron solicitadas explicitamente en la especificacion.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Preparar artefactos de especificacion y alineacion de rutas para implementacion del nuevo recurso.

- [x] T001 Alinear metadata del plan de feature en specs/001-ciudades-chile-api/plan.md
- [x] T002 Definir alcance final y criterios de borde en specs/001-ciudades-chile-api/spec.md para consumo durante implementacion

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Base de dominio y capa de datos que bloquea cualquier endpoint de lectura

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T003 Crear entidad CiudadChile con campos id, nombre, poblacion, active y auditoria en src/main/java/ipss/web2/examen/models/CiudadChile.java
- [x] T004 [P] Crear DTO de salida para ciudad en src/main/java/ipss/web2/examen/dtos/CiudadChileResponseDTO.java
- [x] T005 [P] Crear mapper entidad-DTO en src/main/java/ipss/web2/examen/mappers/CiudadChileMapper.java
- [x] T006 Crear repositorio con consultas activas y busqueda por id activo en src/main/java/ipss/web2/examen/repositories/CiudadChileRepository.java
- [x] T007 Crear servicio base de lectura (listar y obtener por id) con excepciones custom en src/main/java/ipss/web2/examen/services/CiudadChileService.java

**Checkpoint**: Foundation ready - user story implementation can now begin

---

## Phase 3: User Story 1 - Consultar listado de ciudades (Priority: P1) 🎯 MVP

**Goal**: Entregar consulta general de ciudades de Chile con nombre y poblacion

**Independent Test**: Ejecutar consulta de listado y verificar coleccion de ciudades con campos id, nombre y poblacion

### Implementation for User Story 1

- [x] T008 [US1] Implementar endpoint GET listado general de ciudades en src/main/java/ipss/web2/examen/controllers/api/CiudadChileController.java
- [x] T009 [US1] Ajustar servicio para respuesta de listado ordenado y mapeado a DTO en src/main/java/ipss/web2/examen/services/CiudadChileService.java
- [x] T010 [US1] Estandarizar mensaje y envoltorio ApiResponseDTO para listado en src/main/java/ipss/web2/examen/controllers/api/CiudadChileController.java

**Checkpoint**: User Story 1 functional and independently testable

---

## Phase 4: User Story 2 - Consultar ciudad por identificador (Priority: P2)

**Goal**: Entregar consulta de detalle por id con manejo de no encontrado

**Independent Test**: Solicitar un id existente y uno inexistente, validando exito y not-found respectivamente

### Implementation for User Story 2

- [x] T011 [US2] Implementar endpoint GET por id en src/main/java/ipss/web2/examen/controllers/api/CiudadChileController.java
- [x] T012 [US2] Implementar metodo de servicio obtenerPorId con ResourceNotFoundException en src/main/java/ipss/web2/examen/services/CiudadChileService.java
- [x] T013 [US2] Incorporar consulta findByIdAndActiveTrue en flujo de servicio-repositorio en src/main/java/ipss/web2/examen/repositories/CiudadChileRepository.java

**Checkpoint**: User Stories 1 and 2 both work independently

---

## Phase 5: User Story 3 - Cargar 10 registros iniciales en H2 (Priority: P3)

**Goal**: Disponer de 10 ciudades de referencia en ambiente local para pruebas funcionales inmediatas

**Independent Test**: Reiniciar la aplicacion en limpio y verificar que el listado general devuelve 10 registros iniciales

### Implementation for User Story 3

- [x] T014 [US3] Crear inicializador de datos para 10 ciudades de Chile en H2 en src/main/java/ipss/web2/examen/config/CiudadChileDataInitializer.java
- [x] T015 [US3] Definir dataset semilla de 10 ciudades con poblacion en src/main/java/ipss/web2/examen/config/CiudadChileDataInitializer.java
- [x] T016 [US3] Garantizar idempotencia de carga inicial para no duplicar datos en src/main/java/ipss/web2/examen/config/CiudadChileDataInitializer.java

**Checkpoint**: All user stories independently functional

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cerrar consistencia tecnica y documentacion cruzada

- [x] T017 [P] Registrar nuevo endpoint en documentacion API en docs/api/ciudades-chile.md
- [x] T018 Actualizar guia principal del proyecto con nuevo recurso de ciudades en readme.md
- [x] T019 Ejecutar validacion completa de pruebas del backend en src/test/java/ipss/web2/examen/

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: Depend on Foundational completion
- **Polish (Phase 6)**: Depends on all user stories completion

### User Story Dependencies

- **User Story 1 (P1)**: Starts after Foundational phase completion
- **User Story 2 (P2)**: Starts after Foundational phase completion; reuses controller and service from US1
- **User Story 3 (P3)**: Starts after Foundational phase completion; independent from US2

### Parallel Opportunities

- T004 and T005 can run in parallel after T003
- T014 and T017 can run in parallel with non-overlapping files
- Documentation updates can run in parallel with final test execution

---

## Parallel Example: User Story 1

```bash
Task: "T008 Implementar endpoint GET listado general en src/main/java/ipss/web2/examen/controllers/api/CiudadChileController.java"
Task: "T009 Ajustar servicio de listado en src/main/java/ipss/web2/examen/services/CiudadChileService.java"
```

## Parallel Example: User Story 2

```bash
Task: "T011 Implementar endpoint GET por id en src/main/java/ipss/web2/examen/controllers/api/CiudadChileController.java"
Task: "T013 Incorporar consulta findByIdAndActiveTrue en src/main/java/ipss/web2/examen/repositories/CiudadChileRepository.java"
```

## Parallel Example: User Story 3

```bash
Task: "T014 Crear inicializador de datos en src/main/java/ipss/web2/examen/config/CiudadChileDataInitializer.java"
Task: "T015 Definir dataset semilla de 10 ciudades en src/main/java/ipss/web2/examen/config/CiudadChileDataInitializer.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational
3. Complete Phase 3: User Story 1
4. Validate listado general de ciudades

### Incremental Delivery

1. Entregar US1 para consulta general
2. Extender con US2 para detalle por id
3. Completar US3 con datos iniciales de H2
4. Aplicar polish final y validacion integral

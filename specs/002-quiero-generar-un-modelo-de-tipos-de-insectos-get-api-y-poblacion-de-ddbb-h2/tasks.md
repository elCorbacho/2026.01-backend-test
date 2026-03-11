# Tasks: Catalogo de tipos de insectos

**Input**: Design documents from `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de pruebas porque el plan/quickstart exige cobertura en controller, service e integracion.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Preparar estructura minima del dominio TipoInsecto y puntos de prueba.

- [x] T001 Create domain class placeholders for `TipoInsecto` in `src/main/java/ipss/web2/examen/models/TipoInsecto.java`, `src/main/java/ipss/web2/examen/repositories/TipoInsectoRepository.java`, `src/main/java/ipss/web2/examen/dtos/TipoInsectoResponseDTO.java`, `src/main/java/ipss/web2/examen/mappers/TipoInsectoMapper.java`, `src/main/java/ipss/web2/examen/services/TipoInsectoService.java`, and `src/main/java/ipss/web2/examen/controllers/api/TipoInsectoController.java`
- [x] T002 [P] Create test class placeholders in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`, `src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java`, and `src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java`
- [x] T003 [P] Align API contract examples and operation names in `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/contracts/insect-types-api.yaml`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Base compartida obligatoria antes de cualquier historia de usuario.

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [x] T004 Implement `TipoInsecto` entity with audit fields, indexes, and `active` soft-delete flag in `src/main/java/ipss/web2/examen/models/TipoInsecto.java`
- [x] T005 Implement active-only query methods in `src/main/java/ipss/web2/examen/repositories/TipoInsectoRepository.java`
- [x] T006 [P] Implement immutable output DTO in `src/main/java/ipss/web2/examen/dtos/TipoInsectoResponseDTO.java`
- [x] T007 [P] Implement entity-to-DTO mapper methods in `src/main/java/ipss/web2/examen/mappers/TipoInsectoMapper.java`
- [x] T008 Implement shared service utilities (id validation and active lookup with custom exceptions) in `src/main/java/ipss/web2/examen/services/TipoInsectoService.java`
- [x] T009 Implement controller base mapping and envelope response style in `src/main/java/ipss/web2/examen/controllers/api/TipoInsectoController.java`

**Checkpoint**: Foundation ready - user story implementation can now begin

---

## Phase 3: User Story 1 - Consultar tipos de insectos (Priority: P1) 🎯 MVP

**Goal**: Entregar endpoint GET de listado de tipos de insecto activos con respuesta consistente.

**Independent Test**: Llamar GET `/api/tipos-insecto` y validar que retorna solo registros activos, incluyendo coleccion vacia cuando corresponda.

### Tests for User Story 1

- [x] T010 [P] [US1] Add WebMvc test for successful list response in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`
- [x] T011 [P] [US1] Add WebMvc test for empty list response in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`
- [x] T012 [P] [US1] Add service unit tests for active-only filtering in `src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java`

### Implementation for User Story 1

- [x] T013 [US1] Implement service method to list active insect types in `src/main/java/ipss/web2/examen/services/TipoInsectoService.java`
- [x] T014 [US1] Implement controller GET list endpoint `/api/tipos-insecto` in `src/main/java/ipss/web2/examen/controllers/api/TipoInsectoController.java`
- [x] T015 [US1] Ensure success envelope message and data shape for list endpoint in `src/main/java/ipss/web2/examen/controllers/api/TipoInsectoController.java`

**Checkpoint**: User Story 1 should be fully functional and testable independently

---

## Phase 4: User Story 2 - Consultar detalle de tipo de insecto (Priority: P2)

**Goal**: Entregar endpoint GET por id con manejo de no encontrado/inactivo e id invalido.

**Independent Test**: Llamar GET `/api/tipos-insecto/{id}` con id valido, inexistente e invalido y validar respuestas 200/404/400 segun contrato.

### Tests for User Story 2

- [x] T016 [P] [US2] Add WebMvc test for detail success response in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`
- [x] T017 [P] [US2] Add WebMvc test for detail not-found response in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`
- [x] T018 [P] [US2] Add WebMvc test for invalid id request handling in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`
- [x] T019 [P] [US2] Add service unit tests for active detail lookup and not-found exception in `src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java`

### Implementation for User Story 2

- [x] T020 [US2] Implement service method to fetch active insect type by id in `src/main/java/ipss/web2/examen/services/TipoInsectoService.java`
- [x] T021 [US2] Implement controller GET detail endpoint `/api/tipos-insecto/{id}` in `src/main/java/ipss/web2/examen/controllers/api/TipoInsectoController.java`
- [x] T022 [US2] Wire custom exception paths and error codes for invalid/not-found detail requests in `src/main/java/ipss/web2/examen/services/TipoInsectoService.java` and `src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java`

**Checkpoint**: User Stories 1 and 2 should both work independently

---

## Phase 5: User Story 3 - Disponibilidad de datos iniciales (Priority: P3)

**Goal**: Cargar datos iniciales idempotentes de tipos de insecto al arranque en entorno de pruebas.

**Independent Test**: Iniciar aplicacion en base limpia y confirmar minimo 10 registros; reiniciar y confirmar que no se duplican.

### Tests for User Story 3

- [x] T023 [P] [US3] Add integration test for initial insect seed population in `src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java`
- [x] T024 [P] [US3] Add integration test for idempotent re-run of insect seed in `src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java`

### Implementation for User Story 3

- [x] T025 [US3] Add insect seed data creation flow in `src/main/java/ipss/web2/examen/config/DataInitializer.java`
- [x] T026 [US3] Implement duplicate-prevention checks for insect seed records in `src/main/java/ipss/web2/examen/config/DataInitializer.java`
- [x] T027 [US3] Ensure seed data attributes align with domain validation and active state in `src/main/java/ipss/web2/examen/config/DataInitializer.java`

**Checkpoint**: All user stories should now be independently functional

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de calidad transversal y validacion final de la feature.

- [x] T028 [P] Update feature usage and verification notes in `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/quickstart.md`
- [x] T029 Run focused test suite for new domain in `src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java`, `src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java`, and `src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java`
- [x] T030 Run full project validation command from `mvnw.cmd` and record outcomes in `specs/002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2/quickstart.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies; start immediately
- **Foundational (Phase 2)**: Depends on Phase 1; blocks all user stories
- **User Stories (Phase 3-5)**: Depend on Phase 2 completion
- **Polish (Phase 6)**: Depends on completion of all target user stories

### User Story Dependencies

- **US1 (P1)**: Starts after Phase 2; no dependency on other stories
- **US2 (P2)**: Starts after Phase 2; reuses foundational model/service/controller baseline
- **US3 (P3)**: Starts after Phase 2; can run in parallel with US1/US2, but validates overall readiness

### Within Each User Story

- Test tasks first, then implementation tasks
- Service logic before controller wiring
- Story-specific checkpoint must pass before marking story complete

### Parallel Opportunities

- Phase 1: T002 and T003
- Phase 2: T006 and T007
- US1: T010, T011, T012
- US2: T016, T017, T018, T019
- US3: T023 and T024
- Phase 6: T028 can run before T029/T030

---

## Parallel Example: User Story 1

```bash
Task: "Add WebMvc test for successful list response in src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java"
Task: "Add WebMvc test for empty list response in src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java"
Task: "Add service unit tests for active-only filtering in src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java"
```

## Parallel Example: User Story 2

```bash
Task: "Add WebMvc test for detail success response in src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java"
Task: "Add WebMvc test for detail not-found response in src/test/java/ipss/web2/examen/controllers/api/TipoInsectoControllerWebMvcTest.java"
Task: "Add service unit tests for active detail lookup and not-found exception in src/test/java/ipss/web2/examen/services/TipoInsectoServiceTest.java"
```

## Parallel Example: User Story 3

```bash
Task: "Add integration test for initial insect seed population in src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java"
Task: "Add integration test for idempotent re-run of insect seed in src/test/java/ipss/web2/examen/config/DataInitializerInsectosIntegrationTest.java"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1
2. Complete Phase 2 (blocking)
3. Complete Phase 3 (US1)
4. Validate US1 independently with tests and endpoint check

### Incremental Delivery

1. Build foundation (Phase 1-2)
2. Deliver US1 (MVP)
3. Add US2 for detail/error coverage
4. Add US3 for idempotent startup data readiness
5. Finish polish with complete test/build validation

### Parallel Team Strategy

1. Team aligns on Phase 1-2
2. After Phase 2: one developer on US1, another on US2, another on US3
3. Merge after each story checkpoint passes independently

---

## Notes

- [P] tasks = different files or independent work streams
- [USx] labels provide traceability to user stories
- Keep each story independently demonstrable
- Avoid cross-story coupling beyond shared foundational files

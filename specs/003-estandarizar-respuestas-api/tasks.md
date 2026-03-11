# Tasks: Estandarizar Respuestas API

**Input**: Design documents from `/specs/003-estandarizar-respuestas-api/`
**Prerequisites**: `plan.md`, `spec.md`, `research.md`, `data-model.md`, `contracts/api-response-envelope.yaml`, `contracts/error-codes.md`, `quickstart.md`

**Tests**: Included by request. Test tasks are tied to acceptance scenarios and success criteria (SC-001, SC-002).

**Organization**: Tasks are grouped by phase, then by user story (US1, US2, US3) to support independent implementation and validation.

## Format: `[ID] [P?] [Story] Description`

- `[P]`: Can run in parallel (different files, no dependency on incomplete tasks)
- `[Story]`: User story label (`[US1]`, `[US2]`, `[US3]`) for story-phase tasks only

---

## Phase 1: Setup (Shared Planning Inputs)

**Purpose**: Prepare baseline artifacts and endpoint audit scope for response-standardization-only work.

- [x] T001 Create endpoint audit checklist for envelope compliance in specs/003-estandarizar-respuestas-api/quickstart.md
- [x] T002 Create baseline endpoint sample matrix (representative GET list, GET by id, POST, PUT, DELETE) in specs/003-estandarizar-respuestas-api/research.md
- [x] T003 [P] Validate reusable envelope schema references in specs/003-estandarizar-respuestas-api/contracts/api-response-envelope.yaml

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish shared response envelope and centralized conventions before user story implementation.

**CRITICAL**: No user story implementation starts before this phase is complete.

- [x] T004 Confirm and align base response envelope fields (`success`, `message`, `data`, `errorCode`, `errors`, `timestamp`) in src/main/java/ipss/web2/examen/dtos/ApiResponseDTO.java
- [x] T005 [P] Standardize canonical error-code mapping rules in specs/003-estandarizar-respuestas-api/contracts/error-codes.md
- [x] T006 Implement/align centralized error envelope builder behavior in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T007 [P] Add shared controller response contract guidance for envelope usage in specs/003-estandarizar-respuestas-api/plan.md
- [x] T008 Add foundational MVC test helpers for top-level envelope assertions in src/test/java/ipss/web2/examen/controllers/api/ApiResponseEnvelopeTestSupport.java

**Checkpoint**: Foundation ready for user story implementation.

---

## Phase 3: User Story 1 - Respuesta uniforme en exito (Priority: P1) 🎯 MVP

**Goal**: Ensure successful responses across representative endpoints share one envelope contract.

**Independent Test**: Call representative endpoints from at least three domains and verify identical top-level success envelope fields and semantics.

### Tests for User Story 1

- [x] T009 [P] [US1] Add success-envelope contract test for representative GET list endpoint in src/test/java/ipss/web2/examen/controllers/api/AlbumControllerWebMvcTest.java (Scenario US1-1, SC-001)
- [x] T010 [P] [US1] Add success-envelope contract test for representative GET by id endpoint in src/test/java/ipss/web2/examen/controllers/api/PresidenteChileControllerWebMvcTest.java (Scenario US1-2, SC-001)
- [x] T011 [P] [US1] Add success-envelope contract test for representative POST endpoint in src/test/java/ipss/web2/examen/controllers/api/TipoAveControllerWebMvcTest.java (Scenario US1-2, SC-001)

### Implementation for User Story 1

- [x] T012 [US1] Standardize GET list success response envelope in src/main/java/ipss/web2/examen/controllers/api/AlbumController.java
- [x] T013 [US1] Standardize GET by id success response envelope in src/main/java/ipss/web2/examen/controllers/api/PresidenteChileController.java
- [x] T014 [US1] Standardize POST success response envelope and status semantics in src/main/java/ipss/web2/examen/controllers/api/TipoAveController.java
- [x] T015 [US1] Standardize PUT/PATCH success response envelope for representative update endpoint in src/main/java/ipss/web2/examen/controllers/api/TransportistaController.java
- [x] T016 [US1] Standardize DELETE/soft-delete success response envelope for representative delete endpoint in src/main/java/ipss/web2/examen/controllers/api/TransportistaController.java
- [x] T017 [US1] Add paginated success envelope consistency checks in src/main/java/ipss/web2/examen/controllers/api/AlbumController.java (FR-005)

**Checkpoint**: US1 delivers uniform success envelope on representative operations.

---

## Phase 4: User Story 2 - Respuesta uniforme en errores (Priority: P1)

**Goal**: Ensure validation, business, not-found, and unexpected errors follow a single predictable error envelope.

**Independent Test**: Trigger validation, invalid-operation/business, not-found, and unexpected errors; verify uniform error envelope and deterministic `errorCode`.

### Tests for User Story 2

- [x] T018 [P] [US2] Add validation-error envelope test (400) in src/test/java/ipss/web2/examen/controllers/api/AlbumControllerWebMvcTest.java (Scenario US2-1, SC-002)
- [x] T019 [P] [US2] Add business-error envelope test (400) in src/test/java/ipss/web2/examen/services/AlbumServiceTest.java (Scenario US2-2, SC-002)
- [x] T020 [P] [US2] Add not-found envelope test (404) in src/test/java/ipss/web2/examen/controllers/api/AlbumControllerWebMvcTest.java (Scenario US2-3, SC-002)
- [x] T021 [P] [US2] Add unexpected-error envelope integration test (500) in src/test/java/ipss/web2/examen/integration/ApiResponseEnvelopeIntegrationTest.java (Edge Case: unexpected error)

### Implementation for User Story 2

- [x] T022 [US2] Normalize `MethodArgumentNotValidException` mapping to standard envelope in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T023 [US2] Normalize invalid-operation exception mapping to standard envelope in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T024 [US2] Normalize resource-not-found exception mapping to standard envelope in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T025 [US2] Normalize type-mismatch and endpoint-not-found mappings to standard envelope in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T026 [US2] Normalize catch-all internal error mapping (no internal leak) to standard envelope in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java
- [x] T027 [US2] Align errorCode constants and naming to contract categories in src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java and specs/003-estandarizar-respuestas-api/contracts/error-codes.md

**Checkpoint**: US2 delivers uniform error envelope with deterministic error codes.

---

## Phase 5: User Story 3 - Documentacion alineada al contrato (Priority: P2)

**Goal**: Ensure API documentation matches the standardized runtime envelope contract for success and error responses.

**Independent Test**: Compare Swagger/OpenAPI docs with runtime responses from representative endpoints and verify consistency.

### Tests for User Story 3

- [x] T028 [P] [US3] Add OpenAPI documentation consistency test for representative endpoints in src/test/java/ipss/web2/examen/integration/OpenApiEnvelopeContractTest.java (Scenario US3-1)
- [x] T029 [P] [US3] Add quickstart verification script/checklist updates for documented vs runtime envelopes in specs/003-estandarizar-respuestas-api/quickstart.md (SC-001, SC-002)

### Implementation for User Story 3

- [x] T030 [US3] Update reusable OpenAPI envelope schemas for success and error responses in specs/003-estandarizar-respuestas-api/contracts/api-response-envelope.yaml
- [x] T031 [US3] Add/align Swagger response annotations for representative controllers in src/main/java/ipss/web2/examen/controllers/api/AlbumController.java
- [x] T032 [US3] Add/align Swagger response annotations for representative controllers in src/main/java/ipss/web2/examen/controllers/api/TipoAveController.java
- [x] T033 [US3] Add/align Swagger response annotations for representative controllers in src/main/java/ipss/web2/examen/controllers/api/PresidenteChileController.java
- [x] T034 [US3] Update feature documentation to reflect final envelope and error-code conventions in specs/003-estandarizar-respuestas-api/spec.md

**Checkpoint**: US3 aligns published documentation with runtime contract behavior.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Final compliance pass for response standardization scope.

- [x] T035 [P] Run representative quickstart validation flow and record observed outcomes in specs/003-estandarizar-respuestas-api/quickstart.md
- [x] T036 Compute and record audited compliance ratio for success/error envelope coverage in specs/003-estandarizar-respuestas-api/research.md (SC-001, SC-002)
- [x] T037 [P] Cleanup duplicate or inconsistent response-building code paths in src/main/java/ipss/web2/examen/controllers/api/
- [x] T038 Final task traceability pass linking FR-001..FR-009 to implemented tasks in specs/003-estandarizar-respuestas-api/tasks.md

## FR Traceability Matrix (T038)

| Functional Requirement | Implemented by Tasks |
|------------------------|----------------------|
| FR-001 Uniform success envelope | T004, T008, T009, T010, T011, T012, T013, T014, T015, T016, T017 |
| FR-002 Uniform error envelope | T006, T018, T020, T021, T022, T023, T024, T025, T026 |
| FR-003 Deterministic error identifier (`errorCode`) | T005, T023, T024, T025, T026, T027 |
| FR-004 Semantic compatibility for existing endpoints | T012, T013, T014, T015, T016, T034 |
| FR-005 Same contract for paginated/non-paginated responses | T009, T010, T012, T013, T017 |
| FR-006 Standardize validation/invalid operation/not found | T018, T019, T020, T022, T023, T024, T025 |
| FR-007 Documentation aligned with standardized success/error states | T028, T029, T030, T031, T032, T033, T034 |
| FR-008 Automated verification of representative contract compliance | T008, T009, T010, T011, T018, T020, T021, T028, T029, T035, T036 |
| FR-009 Preserve clear business messages with uniform structure | T012, T013, T014, T015, T016, T022, T023, T024, T026, T034, T037 |

---

## Dependencies & Execution Order

### Phase Dependencies

- Setup (Phase 1): no dependencies
- Foundational (Phase 2): depends on Setup; blocks all user stories
- User Stories (Phase 3-5): depend on Foundational completion
- Polish (Phase 6): depends on completion of target user stories

### User Story Dependencies

- US1 (P1): starts after Phase 2; independent of US2 and US3
- US2 (P1): starts after Phase 2; independent of US1 for error-path standardization
- US3 (P2): depends on US1 and US2 contract stabilization for doc alignment

### Task-Level Critical Ordering

- T004 -> T006 -> T022..T026 (envelope and handler foundation before error normalization)
- T008 -> T009..T011 and T018..T021 (shared test support before contract assertions)
- T012..T017 should complete before T030..T034 (runtime behavior before documentation finalization)
- T035 and T036 execute after US1/US2 completion for measurable compliance checks

---

## Parallel Execution Examples

### User Story 1

- Run T009, T010, T011 in parallel (different controller test files)
- Run T012, T013, T014 in parallel if owners are split by controller

### User Story 2

- Run T018, T019, T020, T021 in parallel (different test scopes)
- Run T022 and T025 in parallel only after T006 is merged or coordinated

### User Story 3

- Run T028 and T029 in parallel (integration doc-check + quickstart checklist)
- Run T031, T032, T033 in parallel (controller-specific Swagger updates)

---

## Implementation Strategy

### MVP First (US1)

1. Complete Phase 1 and Phase 2.
2. Deliver Phase 3 (US1) and validate independent success envelope consistency.
3. Demo/deploy if needed.

### Incremental Delivery

1. Add US1 (success consistency).
2. Add US2 (error consistency).
3. Add US3 (documentation alignment).
4. Run Phase 6 compliance and traceability checks.

### Scope Guardrails

- Keep all work limited to response envelope standardization and related docs/tests.
- Do not introduce unrelated feature work or domain behavior changes.
- Preserve existing HTTP status semantics and business messages where possible.

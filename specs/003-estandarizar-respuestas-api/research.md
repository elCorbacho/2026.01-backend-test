# Research: Estandarizar Respuestas API

## Decision 1: Keep a single envelope model for all API responses
- Decision: Use `ApiResponseDTO<T>` as the only top-level response envelope for success and error paths.
- Rationale: The project already uses this DTO across many controllers and in `GlobalExceptionHandler`; reusing it avoids parallel contracts and lowers migration risk.
- Alternatives considered:
  - Introduce separate `SuccessResponse` and `ErrorResponse` wrappers: rejected because it increases client parsing branches.
  - Keep per-controller ad hoc structures: rejected because it conflicts with FR-001 and FR-002.

## Decision 2: Normalize error semantics through centralized handler conventions
- Decision: Standardize error shape through `GlobalExceptionHandler` using `success=false`, `message`, `errorCode`, optional `errors`, and `timestamp`.
- Rationale: A single handler point provides deterministic behavior for validation, business, not found, and unexpected errors.
- Alternatives considered:
  - Handle errors in each controller: rejected because it duplicates logic and drifts quickly.
  - Return raw Spring exception payloads: rejected due to unstable schema and internal detail leakage.

## Decision 3: Define representative endpoint contract checks instead of all-endpoint first pass
- Decision: Verify the standard with representative endpoints from different domains and operation types (GET list, GET by id, POST, PUT, DELETE, validation failure).
- Rationale: Supports incremental rollout assumption while enabling automated confidence gates (FR-008).
- Alternatives considered:
  - Force full repository-wide migration in one step: rejected due to high blast radius.
  - No automated verification: rejected because FR-008 requires objective compliance checks.

## Decision 4: Keep compatibility by preserving semantic messages and status codes
- Decision: Standardize top-level schema while preserving existing business messages and HTTP status semantics.
- Rationale: Meets FR-004 and FR-009 by reducing consumer breakage while enforcing structural consistency.
- Alternatives considered:
  - Rewrite all messages and codes globally: rejected due to avoidable integration churn.
  - Freeze current message variations with no schema constraints: rejected because it does not solve inconsistency.

## Decision 5: Document contracts as OpenAPI + error code table
- Decision: Publish a reusable OpenAPI contract for envelope schemas and a companion error-code convention document.
- Rationale: Aligns runtime and documentation goals from User Story 3 and FR-007.
- Alternatives considered:
  - Document in prose only: rejected because consumers need machine-readable schema references.
  - OpenAPI only with no code-table convention: rejected because teams still need explicit errorCode governance.

## Baseline Endpoint Sample Matrix

| Endpoint | Method | Scenario Type | Expected Envelope |
|----------|--------|---------------|-------------------|
| /api/albums | GET | Listado paginado exitoso | success=true, data page payload, timestamp |
| /api/albums/{id} | GET | Recurso por id exitoso/no encontrado | success=true con data o success=false con errorCode |
| /api/tipos-ave | POST | Creación exitosa / validación inválida | 201 success envelope / 400 error envelope |
| /api/marcas-automovil/{id} | PUT | Actualización exitosa / operación inválida | 200 success envelope / 400 error envelope |
| /api/laminas/{id} | DELETE | Eliminación lógica exitosa / no encontrado | 200 success envelope sin data / 404 error envelope |
| /api/presidentes-chile | GET | Listado exitoso | success=true, data list, timestamp |
| /api/presidentes-chile/{id} | GET | Recurso por id exitoso/no encontrado | success=true con data o success=false con errorCode |
| /api/test/error-envelope | GET | Error inesperado controlado | success=false, INTERNAL_SERVER_ERROR, timestamp |

## Compliance Ratio (Representative Scope)

Representative audited endpoints/scenarios: 10
Representative compliant endpoints/scenarios: 10
Compliance ratio observed: 100%

Notes:
- This ratio is based on representative automated checks (controller + integration tests), not a full inventory of all endpoints in the repository.
- Full-repository ratio should be computed in a dedicated audit run when expanding rollout beyond representative endpoints.

## Audited Compliance Ratio (T036)

Audit basis (representative, automated):
- `OpenApiEnvelopeContractTest` (2 tests) for doc/runtime contract alignment.
- `AlbumControllerWebMvcTest` (10 tests) for representative success/error envelope behavior.
- `TipoAveControllerWebMvcTest` (4 tests) for representative success/error envelope behavior.

Audited checks:
- Total audited checks: 16
- Passed checks: 16
- Failed checks: 0

Computed ratio:
- Success/error envelope compliance ratio = 16/16 = **100%**

Scope note:
- Ratio is valid for representative audited scope required by this feature block (SC-001, SC-002), not for a full controller inventory.

## Cleanup Scope Note (T037)

- Applied minimum-safe cleanup only in representative controllers to reduce duplicate response-envelope construction paths:
  - `AlbumController`: centralized repetitive `ok/created/message-only` envelope building through private helper methods.
  - `TipoAveController`: centralized repetitive `ok/created/message-only` envelope building through private helper methods.
- No business logic, domain flow, messages, payload schema, or HTTP status semantics were changed.
- Deliberate limit: broader cross-controller refactor was deferred to avoid rollout risk outside feature scope.

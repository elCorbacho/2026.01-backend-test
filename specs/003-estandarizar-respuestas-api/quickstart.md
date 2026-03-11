# Quickstart: Validate Standardized API Responses

## Goal
Verify that representative endpoints return a uniform envelope for success and error scenarios.

## Endpoint Audit Checklist (Envelope Compliance)
- [x] Representative GET list endpoint validated (`/api/albums`, `/api/presidentes-chile`)
- [x] Representative GET by id endpoint validated (`/api/albums/{id}`, `/api/presidentes-chile/{id}`)
- [x] Representative POST endpoint validated (`/api/tipos-ave`)
- [x] Representative PUT endpoint validated (`/api/marcas-automovil/{id}`)
- [x] Representative DELETE endpoint validated (`/api/laminas/{id}`)
- [x] Representative validation error envelope validated (400)
- [x] Representative not-found envelope validated (404)
- [x] Representative unexpected-error envelope validated (500)
- [x] Top-level fields are stable in all cases (`success`, `message`, `timestamp`; plus `data` or `errorCode`)
- [x] No internal stack traces leaked in error messages

## Preconditions
- Project builds and runs with Maven Wrapper.
- API server is available at http://localhost:8080.
- H2 profile is active for local validation.

## 1) Start application
```powershell
.\mvnw.cmd spring-boot:run
```

## 2) Validate success envelope (GET list)
```powershell
curl.exe -s http://localhost:8080/api/albums | jq .
```
Expected:
- `success=true`
- `message` present
- `data` present (paginated payload)
- `timestamp` present

## 3) Validate success envelope (GET by id)
```powershell
curl.exe -s http://localhost:8080/api/albums/1 | jq .
```
Expected:
- same top-level fields as step 2
- `data` represents a single resource

## 4) Validate not-found error envelope (404)
```powershell
curl.exe -s -i http://localhost:8080/api/albums/999999
```
Expected body fields:
- `success=false`
- `message` present
- `errorCode` present
- `timestamp` present

## 5) Validate validation error envelope (400)
```powershell
curl.exe -s -i -X POST http://localhost:8080/api/albums ^
  -H "Content-Type: application/json" ^
  -d "{}"
```
Expected body fields:
- `success=false`
- `message` present
- `errorCode=VALIDATION_ERROR` (or mapped validation code)
- `errors` object present
- `timestamp` present

## 6) Validate invalid endpoint envelope (404)
```powershell
curl.exe -s -i http://localhost:8080/api/no-existe
```
Expected body fields:
- `success=false`
- `errorCode` in endpoint-not-found family

## 7) Representative compliance checklist
- Compare top-level schema for all responses checked above.
- Ensure no response leaks internal exception details.
- Confirm status codes remain semantically correct (200/201/400/404/409/500).

## 8) Runtime vs Documentation Verification
- [x] Verify OpenAPI includes representative endpoints (`/api/albums`, `/api/tipos-ave`, `/api/presidentes-chile`)
- [x] Verify OpenAPI includes envelope fields (`success`, `message`, `timestamp`, `errorCode`)
- [x] Verify runtime tests assert same top-level fields for success and error paths

### Suggested verification command (US3)
```powershell
.\mvnw.cmd test -Dtest=OpenApiEnvelopeContractTest
```

Expected:
- `/v3/api-docs` documents representative responses (`200`, `201`, `400`, `404`, `500`) for `/api/albums`, `/api/albums/{id}`, `/api/tipos-ave`, `/api/presidentes-chile/{id}`.
- Runtime checks confirm success envelopes include `success/message/timestamp` and error envelopes include `success/message/errorCode/timestamp`.

## Observed Outcomes (Latest Validation)
- Representative controller tests and integration tests validate standardized envelope for success and error paths.
- Error responses use deterministic codes (`VALIDATION_ERROR`, `INVALID_OPERATION`, `RESOURCE_NOT_FOUND`, `INVALID_PARAMETER_TYPE`, `DATA_INTEGRITY_VIOLATION`, `ENDPOINT_NOT_FOUND`, `INTERNAL_SERVER_ERROR`).
- OpenAPI endpoint `/v3/api-docs` contains representative paths and envelope fields used by runtime assertions.

### Representative Validation Run (T035)
- Date: 2026-03-11
- Command used:
```powershell
.\mvnw.cmd test "-Dtest=OpenApiEnvelopeContractTest,AlbumControllerWebMvcTest,TipoAveControllerWebMvcTest"
```
- Focused outcomes:
  - `OpenApiEnvelopeContractTest`: 2 run, 0 failures, 0 errors
  - `AlbumControllerWebMvcTest`: 10 run, 0 failures, 0 errors
  - `TipoAveControllerWebMvcTest`: 4 run, 0 failures, 0 errors
  - Aggregate focused scope: 16 run, 0 failures, 0 errors
- Observed contract behavior:
  - Success envelope assertions passed in representative GET/POST/update/delete flows.
  - Error envelope assertions passed for validation and not-found representative flows.
  - OpenAPI/runtime envelope alignment assertions passed for representative endpoints.

## Exit Criteria
- Representative success and error scenarios use the same envelope shape.
- Error categories expose deterministic errorCode values.
- Contract documents in `contracts/` are consistent with runtime behavior.

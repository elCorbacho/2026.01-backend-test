# Error Code Contract

This document defines errorCode conventions for standardized API responses.

## Rules
- Use UPPER_SNAKE_CASE for all errorCode values.
- Keep errorCode stable over time for client-side handling.
- Include business-safe message text; never leak stack traces.
- Return `errors` map only when additional detail is needed (for example validation fields).
- Use `ENDPOINT_NOT_FOUND` as the canonical code for unknown routes/handlers (`INVALID_ENDPOINT` is treated as deprecated alias and should not be emitted by new mappings).

## Required Categories
- Validation errors (400): `VALIDATION_ERROR`, `INVALID_PARAMETER_TYPE`, `INVALID_PAGE`, `INVALID_SIZE`
- Business operation errors (400): `INVALID_OPERATION` and domain-specific operation codes
- Resource not found (404): `RESOURCE_NOT_FOUND` or specific domain code such as `ALBUM_NOT_FOUND`
- Invalid endpoint (404): `ENDPOINT_NOT_FOUND`
- Data integrity conflict (409): `DATA_INTEGRITY_VIOLATION`
- Unexpected error (500): `INTERNAL_SERVER_ERROR`

## Canonical Exception Mapping

| Exception Type | HTTP | Canonical errorCode |
|----------------|------|---------------------|
| MethodArgumentNotValidException | 400 | VALIDATION_ERROR |
| MethodArgumentTypeMismatchException | 400 | INVALID_PARAMETER_TYPE |
| InvalidOperationException | 400 | INVALID_OPERATION (or domain override) |
| ResourceNotFoundException | 404 | RESOURCE_NOT_FOUND (or domain override) |
| NoHandlerFoundException / EndpointNotFoundException / NoResourceFoundException | 404 | ENDPOINT_NOT_FOUND |
| DataIntegrityViolationException | 409 | DATA_INTEGRITY_VIOLATION |
| RuntimeException / Exception (catch-all) | 500 | INTERNAL_SERVER_ERROR |

## Envelope Shape for Errors
- success: false
- message: string
- errorCode: string
- errors: object (optional)
- timestamp: ISO-8601 date-time

## Compliance Notes
- Every exception mapped by `GlobalExceptionHandler` must emit one canonical errorCode.
- New custom exceptions must define deterministic errorCode mapping before rollout.
- Canonical handler-level constants must remain aligned to this contract set: `VALIDATION_ERROR`, `INVALID_OPERATION`, `RESOURCE_NOT_FOUND`, `INVALID_PARAMETER_TYPE`, `DATA_INTEGRITY_VIOLATION`, `ENDPOINT_NOT_FOUND`, `INTERNAL_SERVER_ERROR`.

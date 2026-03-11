# Data Model: Estandarizar Respuestas API

## Entity: RespuestaAPIEstandar
Represents the common top-level envelope returned by all API endpoints.

Fields:
- success (Boolean, required): true for successful operations, false for failures.
- message (String, required): human-readable outcome summary.
- data (Generic object, optional): business payload for success responses.
- errorCode (String, optional): stable machine-readable code for failures.
- errors (Map<String, Object>, optional): field-level or contextual details for invalid requests.
- timestamp (LocalDateTime, required): response generation time.

Validation rules:
- success must always be present.
- if success=true, data may be null only for operations without payload (for example logical delete confirmation).
- if success=false, errorCode must be present.
- if validation fails, errors should include field-level keys where possible.

State transitions:
- Success path: envelope contains success=true and optional data.
- Error path: envelope contains success=false and error metadata.

## Entity: DetalleErrorAPI
Represents normalized error metadata embedded in the envelope.

Fields:
- errorCode (String, required): canonical uppercase snake case code.
- message (String, required): business-safe explanation.
- errors (Map<String, Object>, optional): granular validation or endpoint context.
- timestamp (LocalDateTime, required): error creation time.

Validation rules:
- errorCode must be stable and non-empty.
- internal stack traces or sensitive details are forbidden.

State transitions:
- Validation error (400): include field map in errors.
- Invalid operation (400): include errorCode and message.
- Resource not found (404): include errorCode and message.
- Unexpected error (500): include generic message and INTERNAL_SERVER_ERROR.

## Entity: ContratoEndpoint
Represents the expected success/error response contract for each endpoint family.

Fields:
- endpoint (String, required): path template (for example /api/albums/{id}).
- method (String, required): HTTP method.
- successStatusCodes (Set<Integer>, required): allowed success status codes.
- successSchemaRef (String, required): schema reference for success envelope.
- errorStatusCodes (Set<Integer>, required): standardized error statuses.
- errorSchemaRef (String, required): schema reference for error envelope.
- validationRules (List<String>, optional): input constraints expected by endpoint.

Validation rules:
- each endpoint must reference shared envelope schemas.
- success and error contracts must be documented and testable.

State transitions:
- Draft -> Approved when documented in contracts and quickstart verification.
- Approved -> Compliant when representative tests/assertions validate schema shape.

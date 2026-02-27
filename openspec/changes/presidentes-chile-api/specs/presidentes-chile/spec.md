# Presidentes Chile Specification

## Purpose

Definir el comportamiento del recurso "Presidentes de Chile" para exponer un listado de presidentes activos mediante un endpoint GET, usando la respuesta envolvente estándar del sistema.

## Requirements

### Requirement: Listar presidentes activos

El sistema **MUST** exponer el endpoint `GET /api/presidentes-chile` que retorne solo presidentes con `active = true`.

#### Scenario: Listado exitoso de presidentes activos (happy path)

- GIVEN que existen presidentes con `active = true`
- WHEN el cliente invoca `GET /api/presidentes-chile`
- THEN el sistema responde 200
- AND la respuesta contiene una lista con los presidentes activos

#### Scenario: No existen presidentes activos (edge case)

- GIVEN que no existen presidentes con `active = true`
- WHEN el cliente invoca `GET /api/presidentes-chile`
- THEN el sistema responde 200
- AND la respuesta contiene una lista vacía

### Requirement: Formato de respuesta estándar

El sistema **MUST** envolver la respuesta en `ApiResponseDTO<List<PresidenteChileResponseDTO>>` con `success = true`, `message` descriptivo y `data` con la lista de presidentes.

#### Scenario: Respuesta con formato ApiResponseDTO (happy path)

- GIVEN que el endpoint retorna presidentes activos
- WHEN el cliente invoca `GET /api/presidentes-chile`
- THEN la respuesta incluye `success = true`
- AND incluye `data` como lista de `PresidenteChileResponseDTO`

#### Scenario: Error inesperado en la consulta (edge case)

- GIVEN que ocurre un error no controlado al consultar presidentes
- WHEN el cliente invoca `GET /api/presidentes-chile`
- THEN el sistema responde con `success = false`
- AND el `errorCode` corresponde a `INTERNAL_SERVER_ERROR`

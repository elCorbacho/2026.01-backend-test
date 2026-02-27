# Ganadores Guinness Specification

## Purpose

Definir el comportamiento del recurso de ganadores del premio Guinness expuesto por la API REST.

## Requirements

### Requirement: Listado de ganadores Guinness

El sistema MUST exponer el endpoint GET `/api/ganadores-guinness` que retorna la lista de ganadores activos en el formato de respuesta estándar `ApiResponseDTO<List<GanadorGuinnessResponseDTO>>`.

#### Scenario: Listado con ganadores activos
- GIVEN existen ganadores Guinness con `active = true`
- WHEN el cliente realiza GET `/api/ganadores-guinness`
- THEN la respuesta MUST ser 200
- AND el campo `data` MUST contener una lista con los ganadores activos
- AND cada elemento MUST incluir los campos `nombre`, `categoria`, `record` y `anio`

#### Scenario: Listado vacío
- GIVEN no existen ganadores Guinness activos
- WHEN el cliente realiza GET `/api/ganadores-guinness`
- THEN la respuesta MUST ser 200
- AND el campo `data` MUST ser una lista vacía

### Requirement: Exclusión de ganadores inactivos

El sistema MUST excluir de los listados cualquier ganador con `active = false`.

#### Scenario: Mezcla de activos e inactivos
- GIVEN existen ganadores Guinness activos e inactivos
- WHEN el cliente realiza GET `/api/ganadores-guinness`
- THEN la respuesta MUST incluir solo los ganadores activos
- AND la lista MUST NOT incluir elementos con `active = false`

### Requirement: Wrapper de respuesta consistente

El sistema MUST envolver la respuesta en `ApiResponseDTO` con los campos `success`, `message`, `data`, `errorCode`, `errors`, y `timestamp`.

#### Scenario: Respuesta estándar
- GIVEN un listado exitoso de ganadores Guinness
- WHEN el cliente realiza GET `/api/ganadores-guinness`
- THEN la respuesta MUST contener `success = true`
- AND `data` MUST contener la lista de ganadores
- AND `errorCode` MUST ser null

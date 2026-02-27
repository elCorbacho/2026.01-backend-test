# Regiones Specification

## Purpose

Documentar el comportamiento del nuevo endpoint `GET /api/regiones` para exponer una lista canónica de regiones de Chile que el frontend y otros clientes puedan consumir sin almacenar sus propios datos.

## Requirements

### Requirement: Obtener regiones activas ordenadas
The system MUST return only the regions whose `active` flag is true, ordered by their official `codigo` in ascending order.

#### Scenario: Listado de regiones activas
- GIVEN que existen regiones con `active = true` y `active = false` en la base de datos
- WHEN el cliente invoca `GET /api/regiones`
- THEN la respuesta contiene únicamente las regiones activas
- AND las regiones aparecen ordenadas por `codigo` ascendente

#### Scenario: No hay regiones activas registradas
- GIVEN que ninguna región en la base de datos tiene `active = true`
- WHEN el cliente invoca `GET /api/regiones`
- THEN la respuesta devuelve una lista vacía
- AND el endpoint sigue respondiendo con HTTP 200

### Requirement: Respuesta envuelta en `ApiResponseDTO`
The system MUST wrap every response in `ApiResponseDTO<T>` con `success = true`, `message = null`, `errorCode = null` y `data` apuntando a la lista de regiones retornada.

#### Scenario: Respuesta con datos
- GIVEN que hay al menos una región activa en la base de datos
- WHEN el cliente invoca `GET /api/regiones`
- THEN la respuesta tiene `success = true` y `data` contiene la lista de regiones activas
- AND `message` e `errorCode` son `null`

#### Scenario: Respuesta vacía
- GIVEN que no hay regiones activas en la base de datos
- WHEN el cliente invoca `GET /api/regiones`
- THEN la respuesta sigue teniendo `success = true` y `data` es una lista vacía

### Requirement: No exponer regiones inactivas accidentalmente
The system MUST NOT include regions con `active = false` en el payload aunque existan en la tabla.

#### Scenario: Regiones inactivas ignoradas
- GIVEN que existen registros de regiones con `active = false` en la base de datos
- WHEN el cliente invoca `GET /api/regiones`
- THEN ninguna región inactiva aparece en la lista devuelta

#### Scenario: Todos los registros están inactivos
- GIVEN que todos los registros de la tabla `region` tienen `active = false`
- WHEN el cliente invoca `GET /api/regiones`
- THEN la lista devuelta está vacía y el status HTTP es 200

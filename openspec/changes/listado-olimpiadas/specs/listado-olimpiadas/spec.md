# Listado Olimpiadas Specification

## Purpose

Definir el comportamiento del nuevo recurso `ListadoOlimpiadas`, incluyendo CRUD, soft delete y poblacion inicial, manteniendo respuestas envueltas en `ApiResponseDTO<T>`.

## Requirements

### Requirement: Crear listado de olimpiadas

The system MUST allow clients to create a new `ListadoOlimpiadas` resource and return the created representation wrapped in `ApiResponseDTO<T>`.

#### Scenario: Creacion exitosa

- GIVEN a valid request for `ListadoOlimpiadas`
- WHEN the client submits the create request
- THEN the system returns the created resource wrapped in `ApiResponseDTO<T>`
- AND the resource is persisted as active

#### Scenario: Creacion con datos invalidos

- GIVEN an invalid request for `ListadoOlimpiadas`
- WHEN the client submits the create request
- THEN the system returns a validation error response

### Requirement: Listar listados activos

The system MUST return only active `ListadoOlimpiadas` resources when listing.

#### Scenario: Listado exitoso

- GIVEN there are active `ListadoOlimpiadas` resources
- WHEN the client requests the list
- THEN the system returns only active resources wrapped in `ApiResponseDTO<T>`

#### Scenario: Listado sin resultados

- GIVEN there are no active `ListadoOlimpiadas` resources
- WHEN the client requests the list
- THEN the system returns an empty list wrapped in `ApiResponseDTO<T>`

### Requirement: Obtener listado por ID

The system MUST return a `ListadoOlimpiadas` resource by ID when it exists and is active.

#### Scenario: Obtener por ID exitoso

- GIVEN an active `ListadoOlimpiadas` exists with the requested ID
- WHEN the client requests the resource by ID
- THEN the system returns the resource wrapped in `ApiResponseDTO<T>`

#### Scenario: Obtener por ID inexistente

- GIVEN no active `ListadoOlimpiadas` exists with the requested ID
- WHEN the client requests the resource by ID
- THEN the system returns a not found error response

### Requirement: Actualizar listado de olimpiadas

The system MUST allow clients to update an existing active `ListadoOlimpiadas` and return the updated representation.

#### Scenario: Actualizacion exitosa

- GIVEN an active `ListadoOlimpiadas` exists and a valid update request
- WHEN the client submits the update
- THEN the system returns the updated resource wrapped in `ApiResponseDTO<T>`

#### Scenario: Actualizacion de recurso inexistente

- GIVEN no active `ListadoOlimpiadas` exists with the requested ID
- WHEN the client submits the update
- THEN the system returns a not found error response

### Requirement: Eliminar listado de olimpiadas (soft delete)

The system MUST perform a soft delete for `ListadoOlimpiadas` resources and MUST NOT physically remove them.

#### Scenario: Eliminacion exitosa

- GIVEN an active `ListadoOlimpiadas` exists with the requested ID
- WHEN the client requests deletion
- THEN the system marks the resource as inactive
- AND the resource no longer appears in list results

#### Scenario: Eliminacion de recurso inexistente

- GIVEN no active `ListadoOlimpiadas` exists with the requested ID
- WHEN the client requests deletion
- THEN the system returns a not found error response

### Requirement: Auditoria de cambios

The system MUST record created and last-modified timestamps for `ListadoOlimpiadas` resources.

#### Scenario: Auditoria en creacion

- GIVEN a successful create request
- WHEN the resource is persisted
- THEN the system records a created timestamp
- AND the system records a last-modified timestamp

#### Scenario: Auditoria en actualizacion

- GIVEN an existing active resource
- WHEN the resource is updated
- THEN the system updates the last-modified timestamp

### Requirement: Poblacion inicial de datos

The system MUST load initial `ListadoOlimpiadas` data only when no records exist.

#### Scenario: Poblacion inicial ejecutada

- GIVEN no `ListadoOlimpiadas` records exist
- WHEN the application initializes data
- THEN the system inserts the initial dataset

#### Scenario: Poblacion inicial omitida

- GIVEN `ListadoOlimpiadas` records already exist
- WHEN the application initializes data
- THEN the system does not insert duplicate records

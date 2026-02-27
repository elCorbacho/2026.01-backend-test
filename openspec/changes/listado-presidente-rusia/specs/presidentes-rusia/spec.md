# Presidentes Rusia Specification

## Purpose

Definir el comportamiento del recurso API para listar y registrar presidentes de Rusia, con datos iniciales y respuestas estandarizadas.

## Requirements

### Requirement: Listado de presidentes de Rusia

El sistema **MUST** exponer un endpoint GET en /api/listado-presidente-rusia que retorne todos los presidentes activos con ApiResponseDTO.

#### Scenario: Listado exitoso
- GIVEN existen presidentes de Rusia activos en la base de datos
- WHEN el cliente solicita GET /api/listado-presidente-rusia
- THEN el sistema retorna HTTP 200 con ApiResponseDTO.success=true
- AND la respuesta incluye una lista de presidentes con sus campos principales

#### Scenario: Listado vacío
- GIVEN no existen presidentes activos en la base de datos
- WHEN el cliente solicita GET /api/listado-presidente-rusia
- THEN el sistema retorna HTTP 200 con ApiResponseDTO.success=true
- AND la lista de presidentes está vacía

### Requirement: Creación de presidente de Rusia

El sistema **MUST** exponer un endpoint POST en /api/listado-presidente-rusia que permita registrar un presidente con datos válidos.

#### Scenario: Creación exitosa
- GIVEN el cliente envía un request válido
- WHEN el cliente solicita POST /api/listado-presidente-rusia
- THEN el sistema retorna HTTP 201 con ApiResponseDTO.success=true
- AND la respuesta incluye el presidente creado

#### Scenario: Creación con datos inválidos
- GIVEN el cliente envía un request inválido (campos requeridos faltantes)
- WHEN el cliente solicita POST /api/listado-presidente-rusia
- THEN el sistema retorna HTTP 400 con ApiResponseDTO.success=false
- AND el error incluye detalles de validación

### Requirement: Población inicial de datos

El sistema **SHALL** cargar datos iniciales de presidentes de Rusia al iniciar la aplicación si la tabla está vacía.

#### Scenario: Seed inicial en BD vacía
- GIVEN la tabla de presidentes de Rusia está vacía
- WHEN la aplicación inicia
- THEN el sistema crea los registros iniciales

#### Scenario: Seed omitido si existen datos
- GIVEN la tabla ya tiene registros
- WHEN la aplicación inicia
- THEN el sistema no crea nuevos registros

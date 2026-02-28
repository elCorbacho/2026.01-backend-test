# Equipos Futbol España Specification

## Purpose
Gestionar y consultar equipos de fútbol de España en el sistema, permitiendo obtener la lista de equipos activos y asegurar la población inicial automática.

## Requirements

### Requirement: Listar equipos activos
El sistema MUST exponer un endpoint GET que devuelva todos los equipos de fútbol de España con el campo activo=true.

#### Scenario: Consulta exitosa
- GIVEN existen equipos activos en la base de datos
- WHEN el usuario accede al endpoint GET /api/equipos-futbol-espana
- THEN el sistema devuelve la lista completa de equipos activos

#### Scenario: Sin equipos activos
- GIVEN no existen equipos activos en la base de datos
- WHEN el usuario accede al endpoint GET /api/equipos-futbol-espana
- THEN el sistema devuelve una lista vacía

### Requirement: Población inicial automática
El sistema MUST poblar la tabla equipos_futbol_espana con al menos 5 equipos principales al iniciar la aplicación.

#### Scenario: Arranque inicial
- GIVEN la base de datos está vacía
- WHEN la aplicación arranca por primera vez
- THEN el sistema crea automáticamente los equipos principales en la tabla

#### Scenario: Arranque con datos existentes
- GIVEN la tabla ya tiene equipos
- WHEN la aplicación arranca
- THEN el sistema NO duplica los equipos existentes

### Requirement: Respuesta envolvente
El sistema SHOULD devolver la respuesta en el formato ApiResponseDTO<T> para mantener consistencia con el resto de la API.

#### Scenario: Formato de respuesta
- GIVEN el usuario accede al endpoint GET
- WHEN la respuesta es generada
- THEN el sistema envuelve la lista en ApiResponseDTO<T> con los campos estándar

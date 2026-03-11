# Requirements Document

## Introduction

This document defines the requirements for the Exoplanetas API feature, which provides REST endpoints to manage exoplanet data. The system will allow users to retrieve and create exoplanet records through a RESTful API, following the project's established architectural patterns with layered architecture, soft delete, pagination support, and H2 in-memory database initialization.

## Glossary

- **Exoplaneta_System**: The complete system managing exoplanet data including API endpoints, business logic, persistence, and data initialization
- **Exoplaneta_API**: The REST API controller layer exposing HTTP endpoints for exoplanet operations
- **Exoplaneta_Service**: The business logic layer handling validation, transformation, and orchestration of exoplanet operations
- **Exoplaneta_Repository**: The data access layer providing CRUD operations for exoplanet entities
- **Exoplaneta_Entity**: The JPA entity representing an exoplanet record in the database
- **Exoplaneta_DTO**: Data Transfer Objects for request and response payloads
- **Data_Initializer**: Component responsible for populating the H2 database with seed exoplanet data at application startup
- **API_Response_Envelope**: The standardized ApiResponseDTO wrapper for all API responses
- **Soft_Delete**: The pattern of marking records as inactive rather than physically removing them from the database

## Requirements

### Requirement 1: Exoplanet Entity Management

**User Story:** As a developer, I want a JPA entity to represent exoplanets, so that I can persist exoplanet data in the database following project conventions.

#### Acceptance Criteria

1. THE Exoplaneta_Entity SHALL include an auto-generated Long identifier field
2. THE Exoplaneta_Entity SHALL include a nombre field with maximum length of 100 characters
3. THE Exoplaneta_Entity SHALL include a tipo field with maximum length of 50 characters to classify the exoplanet type
4. THE Exoplaneta_Entity SHALL include a distancia_anos_luz field as a Double to store distance from Earth
5. THE Exoplaneta_Entity SHALL include a masa_relativa_jupiter field as a Double to store mass relative to Jupiter
6. THE Exoplaneta_Entity SHALL include a descubierto_anio field as an Integer to store the discovery year
7. THE Exoplaneta_Entity SHALL include createdAt and updatedAt timestamp fields with automatic auditing
8. THE Exoplaneta_Entity SHALL include an active Boolean field defaulting to true for soft delete support
9. THE Exoplaneta_Entity SHALL use snake_case naming for all database columns
10. THE Exoplaneta_Entity SHALL define indexes on nombre, active, and descubierto_anio columns

### Requirement 2: Exoplanet Data Access Layer

**User Story:** As a service layer, I want repository methods to access exoplanet data, so that I can perform CRUD operations following soft delete patterns.

#### Acceptance Criteria

1. THE Exoplaneta_Repository SHALL extend Spring Data JpaRepository interface
2. THE Exoplaneta_Repository SHALL provide a findByActiveTrue method returning all active exoplanets
3. THE Exoplaneta_Repository SHALL provide a findByIdAndActiveTrue method to retrieve a single active exoplanet by identifier
4. THE Exoplaneta_Repository SHALL provide a findByActiveTrueOrderByNombreAsc method with Pageable parameter for paginated sorted results

### Requirement 3: Exoplanet Data Transfer Objects

**User Story:** As an API consumer, I want well-defined request and response structures, so that I can interact with the API predictably with proper validation.

#### Acceptance Criteria

1. THE Exoplaneta_DTO SHALL include an ExoplanetaRequestDTO record for POST operations
2. THE ExoplanetaRequestDTO SHALL enforce nombre as required with @NotBlank annotation
3. THE ExoplanetaRequestDTO SHALL enforce tipo as required with @NotBlank annotation
4. THE ExoplanetaRequestDTO SHALL enforce distancia_anos_luz as required with @NotNull and @Min(0) annotations
5. THE ExoplanetaRequestDTO SHALL enforce masa_relativa_jupiter as required with @NotNull and @Min(0) annotations
6. THE ExoplanetaRequestDTO SHALL enforce descubierto_anio as required with @NotNull, @Min(1990), and @Max(2100) annotations
7. THE Exoplaneta_DTO SHALL include an ExoplanetaResponseDTO record exposing all entity fields except active status
8. THE ExoplanetaResponseDTO SHALL include id, nombre, tipo, distanciaAnosLuz, masaRelativaJupiter, descubiertoAnio, createdAt, and updatedAt fields

### Requirement 4: Exoplanet Entity-DTO Mapping

**User Story:** As a service layer, I want mapper methods to convert between entities and DTOs, so that I can maintain separation between persistence and API layers.

#### Acceptance Criteria

1. THE Exoplaneta_Mapper SHALL provide a toResponseDTO method converting Exoplaneta_Entity to ExoplanetaResponseDTO
2. THE Exoplaneta_Mapper SHALL provide a toEntity method converting ExoplanetaRequestDTO to Exoplaneta_Entity
3. THE Exoplaneta_Mapper SHALL be annotated as a Spring @Component for dependency injection
4. THE Exoplaneta_Mapper SHALL perform manual field mapping without using mapping frameworks

### Requirement 5: Retrieve All Exoplanets

**User Story:** As an API consumer, I want to retrieve all active exoplanets with pagination, so that I can browse the exoplanet catalog efficiently.

#### Acceptance Criteria

1. WHEN a GET request is sent to /api/exoplanetas, THE Exoplaneta_API SHALL return all active exoplanets
2. THE Exoplaneta_Service SHALL validate page parameter is greater than or equal to 0
3. THE Exoplaneta_Service SHALL validate size parameter is between 1 and 100 inclusive
4. IF page parameter is invalid, THEN THE Exoplaneta_Service SHALL throw InvalidOperationException with error code INVALID_PAGE_NUMBER
5. IF size parameter is invalid, THEN THE Exoplaneta_Service SHALL throw InvalidOperationException with error code INVALID_PAGE_SIZE
6. THE Exoplaneta_API SHALL return results wrapped in API_Response_Envelope with HTTP status 200
7. THE Exoplaneta_API SHALL return paginated results in ExoplanetaPageResponseDTO format
8. THE Exoplaneta_Service SHALL sort results by nombre in ascending order

### Requirement 6: Retrieve Single Exoplanet by ID

**User Story:** As an API consumer, I want to retrieve a specific exoplanet by its identifier, so that I can view detailed information about one exoplanet.

#### Acceptance Criteria

1. WHEN a GET request is sent to /api/exoplanetas/{id}, THE Exoplaneta_API SHALL return the exoplanet with the specified identifier
2. IF the exoplanet identifier does not exist or is inactive, THEN THE Exoplaneta_Service SHALL throw ResourceNotFoundException with resource "Exoplaneta", field "id", and the provided value
3. THE Exoplaneta_API SHALL return the result wrapped in API_Response_Envelope with HTTP status 200
4. THE Exoplaneta_API SHALL return the exoplanet as ExoplanetaResponseDTO

### Requirement 7: Create New Exoplanet

**User Story:** As an API consumer, I want to create a new exoplanet record, so that I can add newly discovered exoplanets to the catalog.

#### Acceptance Criteria

1. WHEN a POST request is sent to /api/exoplanetas with valid ExoplanetaRequestDTO, THE Exoplaneta_API SHALL create a new exoplanet record
2. THE Exoplaneta_Service SHALL validate all required fields according to ExoplanetaRequestDTO constraints
3. IF validation fails, THEN THE Exoplaneta_API SHALL return HTTP status 400 with validation error details
4. THE Exoplaneta_Service SHALL set the active field to true for new exoplanets
5. THE Exoplaneta_Service SHALL persist the new exoplanet using Exoplaneta_Repository
6. THE Exoplaneta_API SHALL return the created exoplanet wrapped in API_Response_Envelope with HTTP status 201
7. THE Exoplaneta_API SHALL return the created exoplanet as ExoplanetaResponseDTO

### Requirement 8: Database Initialization with Seed Data

**User Story:** As a developer, I want the H2 database to be populated with initial exoplanet data at startup, so that I can test the API with realistic data immediately.

#### Acceptance Criteria

1. WHEN the application starts, THE Data_Initializer SHALL check if exoplanet records exist in the database
2. IF no exoplanet records exist, THEN THE Data_Initializer SHALL create at least 5 diverse exoplanet records
3. THE Data_Initializer SHALL include exoplanets of different types such as "Gas gigante", "Súper Tierra", "Terrestre", and "Neptuniano"
4. THE Data_Initializer SHALL include exoplanets with varied distances, masses, and discovery years
5. THE Data_Initializer SHALL set all initialized exoplanets with active field as true
6. THE Data_Initializer SHALL execute within a @Transactional context
7. THE Data_Initializer SHALL log the number of exoplanets created at INFO level

### Requirement 9: API Documentation

**User Story:** As an API consumer, I want OpenAPI/Swagger documentation for exoplanet endpoints, so that I can understand how to use the API without reading source code.

#### Acceptance Criteria

1. THE Exoplaneta_API SHALL include @Operation annotations describing each endpoint's purpose
2. THE Exoplaneta_API SHALL include @ApiResponse annotations documenting success and error responses
3. THE Exoplaneta_API SHALL include @Parameter annotations describing path and query parameters
4. THE ExoplanetaRequestDTO SHALL include @Schema annotations describing field constraints and examples

### Requirement 10: Error Handling

**User Story:** As an API consumer, I want consistent error responses, so that I can handle errors predictably in my client application.

#### Acceptance Criteria

1. WHEN ResourceNotFoundException is thrown, THEN THE Exoplaneta_System SHALL return HTTP status 404 with error details
2. WHEN InvalidOperationException is thrown, THEN THE Exoplaneta_System SHALL return HTTP status 400 with error code and message
3. WHEN Bean Validation fails, THEN THE Exoplaneta_System SHALL return HTTP status 400 with field-level validation errors
4. THE Exoplaneta_System SHALL use GlobalExceptionHandler to process all exceptions consistently
5. THE Exoplaneta_System SHALL never expose internal exception stack traces in API responses

### Requirement 11: Service Layer Transaction Management

**User Story:** As a system administrator, I want proper transaction boundaries, so that data integrity is maintained during concurrent operations.

#### Acceptance Criteria

1. THE Exoplaneta_Service SHALL be annotated with @Transactional(readOnly = true) at class level
2. THE Exoplaneta_Service SHALL override with @Transactional(readOnly = false) for the create operation
3. WHEN a transaction fails, THEN THE Exoplaneta_Service SHALL rollback all changes automatically

### Requirement 12: REST API Conventions

**User Story:** As a developer, I want the exoplanet API to follow project conventions, so that it integrates seamlessly with existing endpoints.

#### Acceptance Criteria

1. THE Exoplaneta_API SHALL use kebab-case for URL paths (/api/exoplanetas)
2. THE Exoplaneta_API SHALL use camelCase for method names with Spanish verbs (obtenerTodos, obtenerPorId, crear)
3. THE Exoplaneta_API SHALL return all responses wrapped in ApiResponseDTO envelope
4. THE Exoplaneta_API SHALL use @RestController and @RequestMapping annotations
5. THE Exoplaneta_API SHALL inject dependencies through constructor using @RequiredArgsConstructor

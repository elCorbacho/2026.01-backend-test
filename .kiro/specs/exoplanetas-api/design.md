# Design Document: Exoplanetas API

## Overview

The Exoplanetas API feature provides RESTful endpoints for managing exoplanet data within the existing Spring Boot 3 application. This feature follows the established architectural patterns of the project, implementing a strict layered architecture (Controller → Service → Repository → Entity) with soft delete support, pagination, and comprehensive error handling.

The system will expose three primary endpoints:
- GET /api/exoplanetas - Retrieve paginated list of active exoplanets
- GET /api/exoplanetas/{id} - Retrieve a single exoplanet by identifier
- POST /api/exoplanetas - Create a new exoplanet record

All responses will be wrapped in the standardized `ApiResponseDTO<T>` envelope, and the H2 in-memory database will be initialized with seed exoplanet data at application startup.

## Architecture

### Layered Architecture

The implementation follows the project's strict layered flow:

```
┌─────────────────────────────────────┐
│   ExoplanetaController (API Layer)  │
│   - REST endpoints                  │
│   - Request validation              │
│   - Response wrapping               │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   ExoplanetaService (Business)      │
│   - Business logic                  │
│   - Pagination validation           │
│   - Transaction management          │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   ExoplanetaRepository (Data)       │
│   - JPA queries                     │
│   - Soft delete support             │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│   Exoplaneta (Entity)               │
│   - JPA entity mapping              │
│   - Audit fields                    │
└─────────────────────────────────────┘
```

### Data Flow

**Read Operations (GET):**
1. Controller receives HTTP request with query parameters
2. Service validates pagination parameters (page ≥ 0, 1 ≤ size ≤ 100)
3. Repository queries database with Pageable, filtering by active=true
4. Mapper converts entities to ResponseDTOs
5. Service returns PageResponseDTO or single ResponseDTO
6. Controller wraps result in ApiResponseDTO with HTTP 200

**Create Operations (POST):**
1. Controller receives HTTP request with RequestDTO body
2. Bean Validation validates required fields and constraints
3. Service receives validated RequestDTO
4. Mapper converts RequestDTO to Entity (sets active=true)
5. Repository persists entity
6. Mapper converts saved entity to ResponseDTO
7. Controller wraps result in ApiResponseDTO with HTTP 201

### Transaction Management

- Service class annotated with `@Transactional(readOnly = true)` at class level
- Read operations (obtenerTodos, obtenerPorId) inherit readOnly=true
- Write operation (crear) overrides with `@Transactional(readOnly = false)`
- Automatic rollback on unchecked exceptions

### Soft Delete Pattern

All queries filter by `active = true`:
- `findByActiveTrue()` - Returns all active records
- `findByIdAndActiveTrue(Long id)` - Returns single active record or empty
- Never physically delete records from database
- Inactive records remain in database for audit purposes

## Components and Interfaces

### 1. Exoplaneta Entity

**Package:** `ipss.web2.examen.models`

**Responsibilities:**
- JPA entity representing exoplanet table
- Automatic audit field management (createdAt, updatedAt)
- Soft delete support via active field

**Key Fields:**
- `id` (Long) - Auto-generated primary key
- `nombre` (String, max 100) - Exoplanet name
- `tipo` (String, max 50) - Classification type
- `distanciaAnosLuz` (Double) - Distance from Earth in light years
- `masaRelativaJupiter` (Double) - Mass relative to Jupiter
- `descubiertoAnio` (Integer) - Discovery year
- `active` (Boolean, default true) - Soft delete flag
- `createdAt` (LocalDateTime) - Creation timestamp
- `updatedAt` (LocalDateTime) - Last modification timestamp

**Annotations:**
- `@Entity`, `@Table(name = "exoplaneta")`
- `@EntityListeners(AuditingEntityListener.class)`
- Lombok: `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
- `@Index` on nombre, active, descubierto_anio columns

**Database Column Naming:**
All columns use snake_case: `nombre`, `tipo`, `distancia_anos_luz`, `masa_relativa_jupiter`, `descubierto_anio`, `is_active`, `created_at`, `updated_at`

### 2. ExoplanetaRepository

**Package:** `ipss.web2.examen.repositories`

**Interface:** Extends `JpaRepository<Exoplaneta, Long>`

**Methods:**
```java
Optional<Exoplaneta> findByIdAndActiveTrue(Long id);
Page<Exoplaneta> findByActiveTrueOrderByNombreAsc(Pageable pageable);
```

**Responsibilities:**
- Data access abstraction
- Soft delete query support
- Pagination support via Spring Data

### 3. Data Transfer Objects (DTOs)

**Package:** `ipss.web2.examen.dtos`

#### ExoplanetaRequestDTO
**Type:** Record or class with Bean Validation

**Fields:**
- `nombre` (String) - `@NotBlank`, `@Size(max=100)`
- `tipo` (String) - `@NotBlank`, `@Size(max=50)`
- `distanciaAnosLuz` (Double) - `@NotNull`, `@Min(0)`
- `masaRelativaJupiter` (Double) - `@NotNull`, `@Min(0)`
- `descubiertoAnio` (Integer) - `@NotNull`, `@Min(1990)`, `@Max(2100)`

**Responsibilities:**
- Input validation for POST requests
- Bean Validation constraint enforcement

#### ExoplanetaResponseDTO
**Type:** Class with Lombok `@Data`, `@Builder`

**Fields:**
- `id` (Long)
- `nombre` (String)
- `tipo` (String)
- `distanciaAnosLuz` (Double)
- `masaRelativaJupiter` (Double)
- `descubiertoAnio` (Integer)
- `createdAt` (LocalDateTime)
- `updatedAt` (LocalDateTime)

**Note:** Does NOT include `active` field (internal implementation detail)

**Responsibilities:**
- API response representation
- Hides internal fields from clients

#### ExoplanetaPageResponseDTO
**Type:** Record

**Fields:**
```java
record ExoplanetaPageResponseDTO(
    List<ExoplanetaResponseDTO> content,
    int page,
    int size,
    long totalElements,
    int totalPages
)
```

**Responsibilities:**
- Paginated response wrapper
- Provides pagination metadata

### 4. ExoplanetaMapper

**Package:** `ipss.web2.examen.mappers`

**Annotation:** `@Component`

**Methods:**

```java
ExoplanetaResponseDTO toResponseDTO(Exoplaneta entity)
```
- Converts entity to response DTO
- Maps all fields except active
- Returns null if entity is null

```java
Exoplaneta toEntity(ExoplanetaRequestDTO requestDTO)
```
- Converts request DTO to new entity
- Sets active = true
- Does not set id, createdAt, updatedAt (managed by JPA)

**Responsibilities:**
- Manual field mapping between layers
- No mapping framework dependencies
- Null-safe conversions

### 5. ExoplanetaService

**Package:** `ipss.web2.examen.services`

**Annotations:**
- `@Service`
- `@RequiredArgsConstructor`
- `@Transactional(readOnly = true)` at class level

**Dependencies:**
- `ExoplanetaRepository` (injected via constructor)
- `ExoplanetaMapper` (injected via constructor)

**Methods:**

```java
@Transactional(readOnly = true)
ExoplanetaPageResponseDTO obtenerTodos(Integer page, Integer size)
```
- Validates page ≥ 0, throws InvalidOperationException("INVALID_PAGE_NUMBER") if invalid
- Validates 1 ≤ size ≤ 100, throws InvalidOperationException("INVALID_PAGE_SIZE") if invalid
- Defaults: page=0, size=10
- Creates Pageable with Sort.by("nombre").ascending()
- Queries repository.findByActiveTrueOrderByNombreAsc(pageable)
- Maps entities to ResponseDTOs
- Returns ExoplanetaPageResponseDTO with content and metadata

```java
@Transactional(readOnly = true)
ExoplanetaResponseDTO obtenerPorId(Long id)
```
- Queries repository.findByIdAndActiveTrue(id)
- Throws ResourceNotFoundException("Exoplaneta", "id", id) if not found
- Maps entity to ResponseDTO
- Returns ExoplanetaResponseDTO

```java
@Transactional(readOnly = false)
ExoplanetaResponseDTO crear(ExoplanetaRequestDTO requestDTO)
```
- Converts requestDTO to entity via mapper
- Saves entity via repository
- Maps saved entity to ResponseDTO
- Returns ExoplanetaResponseDTO

**Responsibilities:**
- Business logic orchestration
- Pagination parameter validation
- Transaction boundary management
- Exception handling

### 6. ExoplanetaController

**Package:** `ipss.web2.examen.controllers.api`

**Annotations:**
- `@RestController`
- `@RequestMapping("/api/exoplanetas")`
- `@RequiredArgsConstructor`
- OpenAPI annotations for documentation

**Dependencies:**
- `ExoplanetaService` (injected via constructor)

**Endpoints:**

```java
@GetMapping
ApiResponseDTO<ExoplanetaPageResponseDTO> obtenerTodos(
    @RequestParam(required = false) Integer page,
    @RequestParam(required = false) Integer size
)
```
- Calls service.obtenerTodos(page, size)
- Returns ApiResponseDTO.ok(result, "Exoplanetas obtenidos exitosamente")
- HTTP 200

```java
@GetMapping("/{id}")
ApiResponseDTO<ExoplanetaResponseDTO> obtenerPorId(@PathVariable Long id)
```
- Calls service.obtenerPorId(id)
- Returns ApiResponseDTO.ok(result, "Exoplaneta obtenido exitosamente")
- HTTP 200

```java
@PostMapping
ApiResponseDTO<ExoplanetaResponseDTO> crear(
    @Valid @RequestBody ExoplanetaRequestDTO requestDTO
)
```
- Bean Validation automatically validates requestDTO
- Calls service.crear(requestDTO)
- Returns ApiResponseDTO.created(result, "Exoplaneta creado exitosamente")
- HTTP 201

**Responsibilities:**
- HTTP request/response handling
- Request parameter extraction
- Response envelope wrapping
- OpenAPI documentation

### 7. ExoplanetaDataInitializer

**Package:** `ipss.web2.examen.config`

**Annotations:**
- `@Component`
- `@RequiredArgsConstructor`

**Interface:** Implements `CommandLineRunner`

**Dependencies:**
- `ExoplanetaRepository` (injected via constructor)

**Behavior:**
- Executes on application startup
- Checks if exoplaneta table is empty (count() == 0)
- If empty, creates at least 5 diverse exoplanet records
- Includes variety: "Gas gigante", "Súper Tierra", "Terrestre", "Neptuniano"
- Varies distances, masses, and discovery years
- Sets active=true for all records
- Logs count of inserted records at INFO level

**Example Data:**
- Kepler-442b (Súper Tierra, 1206 light years, 2.34 Jupiter masses, 2015)
- HD 209458 b (Gas gigante, 159 light years, 0.69 Jupiter masses, 1999)
- TRAPPIST-1e (Terrestre, 39 light years, 0.62 Jupiter masses, 2017)
- GJ 1214 b (Neptuniano, 48 light years, 6.55 Jupiter masses, 2009)
- 51 Pegasi b (Gas gigante, 50 light years, 0.47 Jupiter masses, 1995)

**Responsibilities:**
- Database seeding for development/testing
- Ensures data availability on startup
- Idempotent execution (checks before inserting)

## Data Models

### Entity Relationship Diagram

```
┌─────────────────────────────────────┐
│          Exoplaneta                 │
├─────────────────────────────────────┤
│ PK  id: Long                        │
│     nombre: String(100)             │
│     tipo: String(50)                │
│     distancia_anos_luz: Double      │
│     masa_relativa_jupiter: Double   │
│     descubierto_anio: Integer       │
│     is_active: Boolean              │
│     created_at: LocalDateTime       │
│     updated_at: LocalDateTime       │
└─────────────────────────────────────┘
```

### Database Schema

**Table:** `exoplaneta`

**Columns:**
| Column Name            | Type          | Constraints                    | Index |
|------------------------|---------------|--------------------------------|-------|
| id                     | BIGINT        | PRIMARY KEY, AUTO_INCREMENT    | Yes   |
| nombre                 | VARCHAR(100)  | NOT NULL                       | Yes   |
| tipo                   | VARCHAR(50)   | NOT NULL                       | No    |
| distancia_anos_luz     | DOUBLE        | NOT NULL                       | No    |
| masa_relativa_jupiter  | DOUBLE        | NOT NULL                       | No    |
| descubierto_anio       | INTEGER       | NOT NULL                       | Yes   |
| is_active              | BOOLEAN       | NOT NULL, DEFAULT TRUE         | Yes   |
| created_at             | TIMESTAMP     | NOT NULL                       | No    |
| updated_at             | TIMESTAMP     | NOT NULL                       | No    |

**Indexes:**
- PRIMARY KEY on `id`
- INDEX on `nombre` (for sorting and searching)
- INDEX on `is_active` (for soft delete filtering)
- INDEX on `descubierto_anio` (for potential year-based queries)

### Field Constraints

**Business Rules:**
- `nombre`: Required, max 100 characters, should be unique in practice
- `tipo`: Required, max 50 characters, typical values: "Gas gigante", "Súper Tierra", "Terrestre", "Neptuniano"
- `distancia_anos_luz`: Required, must be ≥ 0 (cannot be negative)
- `masa_relativa_jupiter`: Required, must be ≥ 0 (relative to Jupiter's mass)
- `descubierto_anio`: Required, must be between 1990 and 2100 (first exoplanet discovered in 1992, upper bound for validation)
- `active`: Required, defaults to true, never null

**Validation Enforcement:**
- Bean Validation at DTO level (RequestDTO)
- Database constraints at schema level (NOT NULL)
- Service layer validation for pagination parameters


## Correctness Properties

A property is a characteristic or behavior that should hold true across all valid executions of a system—essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.

### Property 1: Blank nombre validation rejection

*For any* ExoplanetaRequestDTO with a blank (null, empty, or whitespace-only) nombre field, the system should reject the request with a validation error.

**Validates: Requirements 3.2**

### Property 2: Blank tipo validation rejection

*For any* ExoplanetaRequestDTO with a blank (null, empty, or whitespace-only) tipo field, the system should reject the request with a validation error.

**Validates: Requirements 3.3**

### Property 3: Invalid distancia validation rejection

*For any* ExoplanetaRequestDTO with a null or negative distancia_anos_luz value, the system should reject the request with a validation error.

**Validates: Requirements 3.4**

### Property 4: Invalid masa validation rejection

*For any* ExoplanetaRequestDTO with a null or negative masa_relativa_jupiter value, the system should reject the request with a validation error.

**Validates: Requirements 3.5**

### Property 5: Invalid year validation rejection

*For any* ExoplanetaRequestDTO with a null, less than 1990, or greater than 2100 descubierto_anio value, the system should reject the request with a validation error.

**Validates: Requirements 3.6**

### Property 6: Active-only filtering

*For any* database state containing both active and inactive exoplanets, when retrieving all exoplanets via GET /api/exoplanetas, the system should return only exoplanets where active=true.

**Validates: Requirements 5.1**

### Property 7: Page parameter validation

*For any* negative page parameter value, the service should throw InvalidOperationException with error code "INVALID_PAGE_NUMBER".

**Validates: Requirements 5.2, 5.4**

### Property 8: Size parameter validation

*For any* size parameter value less than 1 or greater than 100, the service should throw InvalidOperationException with error code "INVALID_PAGE_SIZE".

**Validates: Requirements 5.3, 5.5**

### Property 9: Alphabetical sorting by nombre

*For any* set of active exoplanets, when retrieving all exoplanets via GET /api/exoplanetas, the returned list should be sorted by nombre in ascending alphabetical order.

**Validates: Requirements 5.8**

### Property 10: Retrieval by ID round-trip

*For any* valid exoplaneta created in the system, retrieving it by its ID should return an ExoplanetaResponseDTO with the same nombre, tipo, distancia_anos_luz, masa_relativa_jupiter, and descubierto_anio values.

**Validates: Requirements 6.1**

### Property 11: Not found exception for invalid ID

*For any* ID that does not exist in the database or corresponds to an inactive exoplaneta, calling obtenerPorId should throw ResourceNotFoundException with resource="Exoplaneta", field="id", and the provided ID value.

**Validates: Requirements 6.2**

### Property 12: Create operation round-trip

*For any* valid ExoplanetaRequestDTO, after creating the exoplaneta via POST /api/exoplanetas, retrieving it by the returned ID should yield an ExoplanetaResponseDTO with matching nombre, tipo, distancia_anos_luz, masa_relativa_jupiter, and descubierto_anio values.

**Validates: Requirements 7.1, 7.7**

### Property 13: New exoplanetas are active

*For any* valid ExoplanetaRequestDTO, after creating the exoplaneta via the service, the persisted entity should have active=true.

**Validates: Requirements 7.4**

### Property 14: No stack trace exposure

*For any* exception thrown during API operations, the HTTP response body should not contain Java stack traces or internal implementation details.

**Validates: Requirements 10.5**

### Property 15: Transaction rollback on failure

*For any* create operation that fails after beginning a transaction (e.g., due to constraint violation or runtime exception), no exoplaneta record should be persisted in the database.

**Validates: Requirements 11.3**

## Error Handling

### Exception Strategy

The Exoplanetas API leverages the existing `GlobalExceptionHandler` to provide consistent error responses across all endpoints. All exceptions are mapped to appropriate HTTP status codes with descriptive error messages and codes.

### Custom Exceptions

**ResourceNotFoundException**
- **Usage:** Thrown when an exoplaneta ID does not exist or is inactive
- **HTTP Status:** 404 Not Found
- **Constructor:** `ResourceNotFoundException(String resource, String field, Object value)`
- **Example:** `new ResourceNotFoundException("Exoplaneta", "id", 123L)`
- **Error Code:** Automatically generated or specified

**InvalidOperationException**
- **Usage:** Thrown when pagination parameters are invalid
- **HTTP Status:** 400 Bad Request
- **Constructor:** `InvalidOperationException(String message, String errorCode)`
- **Examples:**
  - `new InvalidOperationException("El parametro 'page' debe ser mayor o igual a 0", "INVALID_PAGE_NUMBER")`
  - `new InvalidOperationException("El parametro 'size' debe estar entre 1 y 100", "INVALID_PAGE_SIZE")`

### Bean Validation Errors

When `@Valid` annotation triggers validation failures on `ExoplanetaRequestDTO`:
- **HTTP Status:** 400 Bad Request
- **Response:** ApiResponseDTO with field-level error details
- **Error Code:** "VALIDATION_ERROR"
- **Errors Map:** Contains field names as keys and validation messages as values

Example validation error response:
```json
{
  "success": false,
  "message": "Errores de validación",
  "errorCode": "VALIDATION_ERROR",
  "errors": {
    "nombre": "no debe estar en blanco",
    "distanciaAnosLuz": "debe ser mayor o igual a 0"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

### Error Response Format

All error responses follow the `ApiResponseDTO` structure:
- `success`: false
- `message`: Human-readable error description
- `errorCode`: Machine-readable error identifier (SCREAMING_SNAKE_CASE)
- `errors`: Optional map of field-level errors (for validation failures)
- `timestamp`: Error occurrence timestamp
- `data`: null

### Error Codes

| Error Code              | HTTP Status | Trigger Condition                          |
|-------------------------|-------------|--------------------------------------------|
| EXOPLANETA_NOT_FOUND    | 404         | ID does not exist or is inactive           |
| INVALID_PAGE_NUMBER     | 400         | page parameter < 0                         |
| INVALID_PAGE_SIZE       | 400         | size parameter < 1 or > 100                |
| VALIDATION_ERROR        | 400         | Bean Validation constraint violation       |
| INTERNAL_SERVER_ERROR   | 500         | Unexpected runtime exception               |

### Exception Flow

```
Controller Method
      │
      ├─ Bean Validation (@Valid)
      │  └─ Failure → GlobalExceptionHandler → 400 + VALIDATION_ERROR
      │
      ├─ Service Method Call
      │  │
      │  ├─ Pagination Validation
      │  │  └─ Failure → InvalidOperationException → GlobalExceptionHandler → 400
      │  │
      │  ├─ Repository Query
      │  │  └─ Not Found → ResourceNotFoundException → GlobalExceptionHandler → 404
      │  │
      │  └─ Unexpected Error → RuntimeException → GlobalExceptionHandler → 500
      │
      └─ Success → ApiResponseDTO.ok() or .created() → 200/201
```

### Security Considerations

- Never expose database schema details in error messages
- Never include SQL queries in error responses
- Never return Java stack traces to clients
- Log full exception details server-side for debugging
- Return generic "Internal Server Error" message for unexpected exceptions

## Testing Strategy

### Dual Testing Approach

The Exoplanetas API will be validated using both unit tests and property-based tests to ensure comprehensive coverage:

**Unit Tests:**
- Specific examples demonstrating correct behavior
- Edge cases and boundary conditions
- Integration points between components
- Error handling scenarios

**Property-Based Tests:**
- Universal properties that hold for all inputs
- Comprehensive input coverage through randomization
- Validation of correctness properties defined in this document

Both testing approaches are complementary and necessary. Unit tests catch concrete bugs with specific examples, while property-based tests verify general correctness across a wide range of inputs.

### Property-Based Testing Configuration

**Library:** fast-check (for JavaScript/TypeScript) or jqwik (for Java)

For this Java Spring Boot project, we will use **jqwik** as the property-based testing library.

**Configuration:**
- Minimum 100 iterations per property test (due to randomization)
- Each property test must reference its design document property
- Tag format: `// Feature: exoplanetas-api, Property {number}: {property_text}`

**Example Property Test Structure:**
```java
@Property
@Label("Feature: exoplanetas-api, Property 7: Page parameter validation")
void invalidPageParameterShouldThrowException(@ForAll @Negative int page) {
    assertThatThrownBy(() -> exoplanetaService.obtenerTodos(page, 10))
        .isInstanceOf(InvalidOperationException.class)
        .hasMessageContaining("page")
        .hasFieldOrPropertyWithValue("errorCode", "INVALID_PAGE_NUMBER");
}
```

### Test Coverage by Layer

#### 1. Entity Tests (Unit)
**File:** `ExoplanetaTest.java`

**Coverage:**
- Builder pattern creates valid entities
- Default values (active=true)
- Lombok-generated methods (equals, hashCode, toString)

**Example Tests:**
- `testExoplanetaBuilder_CreatesValidEntity()`
- `testExoplanetaBuilder_DefaultsActiveToTrue()`

#### 2. Repository Tests (Integration)
**File:** `ExoplanetaRepositoryTest.java`

**Annotations:** `@DataJpaTest`

**Coverage:**
- `findByIdAndActiveTrue` returns active exoplaneta
- `findByIdAndActiveTrue` returns empty for inactive exoplaneta
- `findByActiveTrueOrderByNombreAsc` returns only active exoplanetas
- `findByActiveTrueOrderByNombreAsc` sorts by nombre ascending
- Pagination works correctly

**Example Tests:**
- `testFindByIdAndActiveTrue_ReturnsExoplaneta()`
- `testFindByIdAndActiveTrue_ReturnsEmptyForInactive()`
- `testFindByActiveTrueOrderByNombreAsc_ReturnsSortedActiveOnly()`
- `testPagination_ReturnsCorrectPageSize()`

#### 3. Mapper Tests (Unit)
**File:** `ExoplanetaMapperTest.java`

**Coverage:**
- `toResponseDTO` maps all fields correctly
- `toResponseDTO` handles null input
- `toEntity` maps all fields correctly
- `toEntity` sets active=true

**Example Tests:**
- `testToResponseDTO_MapsAllFields()`
- `testToResponseDTO_HandlesNull()`
- `testToEntity_MapsAllFieldsAndSetsActive()`

#### 4. Service Tests (Unit + Property)
**File:** `ExoplanetaServiceTest.java`

**Annotations:** `@ExtendWith(MockitoExtension.class)`

**Unit Test Coverage:**
- `obtenerTodos` with valid pagination returns results
- `obtenerTodos` with default parameters (null) uses defaults
- `obtenerPorId` with valid ID returns exoplaneta
- `obtenerPorId` with invalid ID throws ResourceNotFoundException
- `crear` with valid DTO creates and returns exoplaneta

**Property Test Coverage (15 properties):**
- Property 1-5: Validation rejection properties
- Property 6: Active-only filtering
- Property 7-8: Pagination validation
- Property 9: Alphabetical sorting
- Property 10-11: Retrieval properties
- Property 12-13: Create operation properties
- Property 14: No stack trace exposure
- Property 15: Transaction rollback

**Example Unit Tests:**
- `testObtenerTodos_ValidPagination_ReturnsResults()`
- `testObtenerPorId_ValidId_ReturnsExoplaneta()`
- `testObtenerPorId_InvalidId_ThrowsResourceNotFoundException()`
- `testCrear_ValidDTO_CreatesExoplaneta()`

**Example Property Tests:**
- `testProperty7_NegativePageThrowsException(@ForAll @Negative int page)`
- `testProperty8_InvalidSizeThrowsException(@ForAll int size)` with constraint
- `testProperty9_ResultsAreSortedByNombre(@ForAll List<Exoplaneta> exoplanetas)`
- `testProperty12_CreateRoundTrip(@ForAll ExoplanetaRequestDTO dto)`

#### 5. Controller Tests (Unit)
**File:** `ExoplanetaControllerTest.java`

**Annotations:** `@WebMvcTest(ExoplanetaController.class)`

**Important:** Must import `GlobalExceptionHandler` for error path testing

**Coverage:**
- GET /api/exoplanetas returns 200 with paginated results
- GET /api/exoplanetas with invalid page returns 400
- GET /api/exoplanetas with invalid size returns 400
- GET /api/exoplanetas/{id} returns 200 with exoplaneta
- GET /api/exoplanetas/{id} with invalid ID returns 404
- POST /api/exoplanetas with valid DTO returns 201
- POST /api/exoplanetas with invalid DTO returns 400 with validation errors
- All responses wrapped in ApiResponseDTO

**Example Tests:**
- `testObtenerTodos_ReturnsOkWithPaginatedResults()`
- `testObtenerTodos_InvalidPage_ReturnsBadRequest()`
- `testObtenerPorId_ValidId_ReturnsOk()`
- `testObtenerPorId_InvalidId_ReturnsNotFound()`
- `testCrear_ValidDTO_ReturnsCreated()`
- `testCrear_InvalidDTO_ReturnsBadRequestWithValidationErrors()`

#### 6. Data Initializer Tests (Integration)
**File:** `ExoplanetaDataInitializerTest.java`

**Annotations:** `@SpringBootTest`

**Coverage:**
- Initializer creates at least 5 exoplanetas when database is empty
- Initializer does not duplicate data on subsequent runs
- All initialized exoplanetas have active=true
- Initialized exoplanetas include diverse types
- Initialized exoplanetas have varied distances, masses, and years

**Example Tests:**
- `testDataInitializer_CreatesAtLeastFiveExoplanetas()`
- `testDataInitializer_AllInitializedExoplanetasAreActive()`
- `testDataInitializer_IncludesDiverseTypes()`
- `testDataInitializer_IdempotentExecution()`

#### 7. Integration Tests (End-to-End)
**File:** `ExoplanetaIntegrationTest.java`

**Annotations:** `@SpringBootTest(webEnvironment = RANDOM_PORT)`

**Coverage:**
- Full request-response cycle for all endpoints
- Database persistence verification
- Transaction rollback on errors
- Complete error handling flow

**Example Tests:**
- `testFullCycle_CreateAndRetrieveExoplaneta()`
- `testPagination_WorksAcrossMultiplePages()`
- `testSoftDelete_InactiveExoplanetasNotReturned()`
- `testTransactionRollback_OnConstraintViolation()`

### Test Execution

**Run all tests:**
```bash
./mvnw test
```

**Run only service tests:**
```bash
./mvnw test -Dtest="ipss.web2.examen.services.ExoplanetaServiceTest"
```

**Run only controller tests:**
```bash
./mvnw test -Dtest="ipss.web2.examen.controllers.api.ExoplanetaControllerTest"
```

**Run all exoplaneta-related tests:**
```bash
./mvnw test -Dtest="**/*Exoplaneta*Test"
```

### Test Data Generation

For property-based tests, use jqwik's `@ForAll` annotation with custom arbitraries:

**Example Arbitrary for ExoplanetaRequestDTO:**
```java
@Provide
Arbitrary<ExoplanetaRequestDTO> validExoplanetaRequests() {
    return Combinators.combine(
        Arbitraries.strings().alpha().ofMinLength(1).ofMaxLength(100),  // nombre
        Arbitraries.of("Gas gigante", "Súper Tierra", "Terrestre", "Neptuniano"),  // tipo
        Arbitraries.doubles().greaterOrEqual(0.0).lessThan(100000.0),  // distancia
        Arbitraries.doubles().greaterOrEqual(0.0).lessThan(100.0),  // masa
        Arbitraries.integers().between(1990, 2100)  // año
    ).as((nombre, tipo, distancia, masa, anio) -> 
        new ExoplanetaRequestDTO(nombre, tipo, distancia, masa, anio)
    );
}
```

### Coverage Goals

- **Line Coverage:** Minimum 80%
- **Branch Coverage:** Minimum 75%
- **Property Test Iterations:** Minimum 100 per property
- **Critical Paths:** 100% coverage (create, retrieve, pagination, error handling)

### Continuous Integration

All tests must pass before merging:
- Unit tests execute on every commit
- Integration tests execute on pull requests
- Property-based tests execute with full iteration count in CI
- Coverage reports generated and tracked over time

---

## Summary

This design document provides a comprehensive technical blueprint for implementing the Exoplanetas API feature. The implementation follows established Spring Boot patterns with strict layered architecture, soft delete support, comprehensive validation, and robust error handling. The dual testing strategy combining unit tests and property-based tests ensures both concrete correctness and general behavioral guarantees across all valid inputs.

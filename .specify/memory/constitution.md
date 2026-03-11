<!-- Sync Impact Report
Version change: (initial) → 1.0.0
Added sections: All (initial constitution — no prior version existed)
Modified principles: N/A
Removed sections: N/A
Templates requiring updates:
  - .specify/templates/plan-template.md ✅ updated (Constitution Check gates filled in)
  - .specify/templates/spec-template.md ✅ reviewed (no changes required)
  - .specify/templates/tasks-template.md ✅ reviewed (no changes required)
  - .specify/templates/checklist-template.md ✅ reviewed (no changes required)
Follow-up TODOs: None — all placeholders resolved.
-->

# 2026.01-backend-test Constitution

## Core Principles

### I. Strict Layered Architecture (NON-NEGOTIABLE)

Every feature MUST follow the layered flow: **Controller → Service → Repository → Entity**.

- Controllers MUST NOT inject or call repositories directly.
- Controllers MUST NOT contain business logic; all logic lives in Services.
- Services MUST own all `@Transactional` boundaries.
- Mappers MUST be used to convert between entities and DTOs; no JPA entity may be returned from a Controller.
- Dependency direction is strictly downward: no layer may depend on a layer above it.

**Rationale**: Enforces separation of concerns, testability, and maintainability across the growing domain model.

### II. Soft Delete Always (NON-NEGOTIABLE)

Entities are NEVER physically deleted. All deletes MUST set `active = false`.

- Every entity MUST include a boolean `active` field defaulting to `true`.
- All queries MUST filter by `findByActiveTrue()` or `findByIdAndActiveTrue(Long id)`.
- Hard-delete operations (`repository.delete()`, `deleteById()`, SQL `DELETE`) are FORBIDDEN.
- Any endpoint described as "delete" MUST set `active = false` and return the updated resource state.

**Rationale**: Preserves audit history, supports recovery, and prevents accidental permanent data loss.

### III. Response Envelope & DTO Discipline (NON-NEGOTIABLE)

All API endpoints MUST return `ResponseEntity<ApiResponseDTO<T>>`.

- Use factory methods: `ApiResponseDTO.success(data)` for 200, `ApiResponseDTO.created(data)` for 201.
- DTO types are role-specific and MUST NOT be mixed:
  - `*RequestDTO` — inbound API body; MUST carry Bean Validation annotations (`@NotBlank`, `@Size`, `@Min`, `@Max`, etc.).
  - `*ResponseDTO` — outbound single-entity view; MUST be immutable.
  - `*SummaryDTO` — lightweight computed view.
  - `*PageResponseDTO` — paginated results with page metadata.
  - `*PatchRequestDTO` — partial update; MUST include `@JsonInclude(NON_NULL)` and `@AssertTrue hasAnyField()`.
- All types MUST be explicit; `var` and raw/unparameterized generics are FORBIDDEN.

**Rationale**: Guarantees a consistent, predictable API contract for all consumers regardless of endpoint.

### IV. Test Coverage (NON-NEGOTIABLE)

Every new Controller, Service, and critical integration path MUST have automated tests.

- **Controller tests**: Use `@WebMvcTest`; mock the service layer; MUST import `GlobalExceptionHandler`.
  - Minimum: happy path (2xx), resource not found (404), validation failure (400).
- **Service tests**: Use `@ExtendWith(MockitoExtension.class)`; instantiate service manually with real mappers.
  - Cover business logic, soft-delete filtering, and exception-raising paths.
- **Integration tests**: Use `@SpringBootTest(webEnvironment = RANDOM_PORT)`; reset DB via
  `repository.deleteAll()` in `@BeforeEach`.
- Tests MUST NOT call `repository.delete()` or bypass soft-delete patterns.

**Rationale**: Prevents regressions and validates that the layered contract holds end-to-end.

### V. Custom Exceptions & Centralized Error Handling

Generic exceptions (`RuntimeException`, `Exception`, `IllegalArgumentException`, etc.)
MUST NOT be thrown from application code.

- Use only the project's custom exceptions:
  - `ResourceNotFoundException(resource, field, value)` → HTTP 404
  - `InvalidOperationException(message, errorCode)` → HTTP 400
  - `EndpointNotFoundException(method, path)` → HTTP 404
- Error codes MUST be `SCREAMING_SNAKE_CASE` and descriptive (e.g., `ALBUM_NOT_FOUND`).
- `GlobalExceptionHandler` (`@RestControllerAdvice`) is the SINGLE point of exception-to-response translation.
- Stack traces and internal messages MUST NOT leak in API responses.

**Rationale**: Provides safe, consistent, and actionable error responses without information leakage.

## API & Data Standards

### Naming Conventions

| Element     | Convention               | Example                             |
|-------------|--------------------------|-------------------------------------|
| Classes     | PascalCase               | `AlbumService`, `LaminaController`  |
| Methods     | camelCase, Spanish verb  | `crearAlbum`, `obtenerPorId`        |
| Variables   | camelCase                | `albumRepository`, `requestDTO`     |
| DB columns  | snake_case               | `release_year`, `is_active`         |
| URL paths   | kebab-case               | `/api/listado-presidente-rusia`     |
| Error codes | SCREAMING_SNAKE_CASE     | `ALBUM_NOT_FOUND`                   |

### Pagination

- Services MUST validate: `page ≥ 0`, `1 ≤ size ≤ 100`.
- Out-of-range parameters MUST throw `InvalidOperationException` with code `INVALID_PAGINATION`.
- Paginated responses MUST use a `*PageResponseDTO` wrapper containing page metadata.

### Data Integrity

- `hibernate.globally_quoted_identifiers=true` MUST remain enabled to prevent SQL reserved-word conflicts.
- JPA auditing (`@CreatedDate`, `@LastModifiedDate`) MUST be applied to all entities via
  `@EntityListeners(AuditingEntityListener.class)`.
- `@Index` annotations MUST be added on all frequently-queried columns.

## Development Workflow

### Build & Run

```bash
# Build (skip tests)
./mvnw clean package -DskipTests        # Linux/macOS
mvnw.cmd clean package -DskipTests      # Windows

# Run locally (H2 in-memory, port 8080)
./mvnw spring-boot:run

# Full build + tests
./mvnw clean install
```

### Testing

```bash
# All tests
./mvnw test

# Single class
./mvnw test -Dtest=AlbumControllerWebMvcTest

# Single method
./mvnw test -Dtest=AlbumControllerWebMvcTest#testGetAlbumById
```

### Environment Profiles

- **Local**: H2 in-memory (`jdbc:h2:mem:web2examen`). DDL: `create-drop`.
- **Production**: MySQL 8.0 on AWS RDS (`db-ipss.cb2es8a2cxpo.us-east-2.rds.amazonaws.com`).
  DDL: `validate`. Switch via `application.properties` (uncomment MySQL block, comment H2 block).
- Lombok annotation processing MUST be enabled in both the IDE and Maven.

## Governance

This constitution supersedes all other coding practices and style guides in this repository.

- All pull requests MUST be verified against the five Core Principles before merge.
- Principle violations are treated as bugs, not style preferences, and MUST block merge.
- Amendments require: written rationale, semantic version bump, and an update to this file.
- Complexity beyond what the principles permit MUST be documented in the per-plan
  Complexity Tracking table with justification for why a simpler alternative was rejected.
- For day-to-day runtime development guidance, refer to `AGENTS.md`.

**Version**: 1.0.0 | **Ratified**: 2026-03-11 | **Last Amended**: 2026-03-11

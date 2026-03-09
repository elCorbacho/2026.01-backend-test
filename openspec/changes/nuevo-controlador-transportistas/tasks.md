# Tasks: Un nuevo modelo controlador con población de base de datos nativa para transportistas

## Phase 1: Foundation / Infrastructure

- [x] 1.1 Create `src/main/java/.../models/Transportista.java` with JPA annotations.
- [x] 1.2 Add `TransportistaRepository` by extending `JpaRepository`.
- [x] 1.3 Modify `src/main/java/.../config/DataInitializer.java` to seed transportista data during development.

## Phase 2: Core Implementation

- [x] 2.1 Create `src/main/java/.../controllers/api/TransportistaController.java` with CRUD endpoints.
    - `GET /api/transportistas`
    - `GET /api/transportistas/{id}`
    - `POST /api/transportistas`
    - `PUT /api/transportistas/{id}`
    - `DELETE /api/transportistas/{id}`
- [x] 2.2 Implement service methods in `TransportistaService` to handle all business logic.
- [x] 2.3 Automate Swagger/OpenAPI generation for all controller mappings.

## Phase 3: Testing / Verification

- [x] 3.1 Write unit tests for `Transportista` model annotations and constraints.
- [x] 3.2 Write unit tests for `TransportistaService` business logic with mocked repository.
- [x] 3.3 Write integration tests to validate CRUD operations against the H2 database.
- [ ] 3.4 Write E2E tests for Swagger documentation availability at `/swagger-ui.html`.

## Phase 4: Cleanup / Documentation

- [x] 4.1 Ensure inline documentation for all public methods in `TransportistaController` and `TransportistaService`.
- [x] 4.2 Document database seeding constraints within `DataInitializer`.
- [x] 4.3 Verify code formatting and apply consistent conventions.

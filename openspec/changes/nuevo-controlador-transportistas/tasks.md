# Tasks: Un nuevo modelo controlador con población de base de datos nativa para transportistas

## Phase 1: Foundation / Infrastructure

- [ ] 1.1 Create `src/main/java/.../models/Transportista.java` with JPA annotations.
- [ ] 1.2 Add `TransportistaRepository` by extending `JpaRepository`.
- [ ] 1.3 Modify `src/main/java/.../config/DataInitializer.java` to seed transportista data during development.

## Phase 2: Core Implementation

- [ ] 2.1 Create `src/main/java/.../controllers/api/TransportistaController.java` with CRUD endpoints.
    - `GET /transportistas/{id}`
    - `POST /transportistas`
    - `PUT /transportistas/{id}`
    - `DELETE /transportistas/{id}`
- [ ] 2.2 Implement service methods in `TransportistaService` to handle all business logic.
- [ ] 2.3 Automate Swagger/OpenAPI generation for all controller mappings.

## Phase 3: Testing / Verification

- [ ] 3.1 Write unit tests for `Transportista` model annotations and constraints.
- [ ] 3.2 Write unit tests for `TransportistaService` business logic with mocked repository.
- [ ] 3.3 Write integration tests to validate CRUD operations against the H2 database.
- [ ] 3.4 Write E2E tests for Swagger documentation availability at `/swagger-ui.html`.

## Phase 4: Cleanup / Documentation

- [ ] 4.1 Ensure inline documentation for all public methods in `TransportistaController` and `TransportistaService`.
- [ ] 4.2 Document database seeding constraints within `DataInitializer`.
- [ ] 4.3 Verify code formatting and apply consistent conventions.

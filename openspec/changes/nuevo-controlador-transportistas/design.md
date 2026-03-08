# Design: Un nuevo modelo controlador con población de base de datos nativa para transportistas

## Technical Approach
This feature introduces a `Transportista` model with full CRUD (Create, Read, Update, Delete) capabilities and integrates database initialization during development. The approach leverages Spring Boot with JPA/Hibernate for model/database operations and uses an extended `DataInitializer` for seamless development setup.

## Architecture Decisions

### Decision: Use JPA for Data Persistence
**Choice**: Employ Spring Data JPA for database persistence.
**Alternatives considered**: Plain JDBC, MyBatis.
**Rationale**: Ease of use, native integration with Spring Boot, and alignment with existing project patterns.

### Decision: REST Controller for CRUD Operations
**Choice**: A dedicated REST controller for CRUD via Spring MVC.
**Alternatives considered**: GraphQL, gRPC.
**Rationale**: REST is well-suited for this functionality, minimizing complexity while achieving interoperability with standard tools.

### Decision: Development Data Initialization
**Choice**: Extend `DataInitializer` for bootstrap data in dev environments.
**Alternatives considered**: Manual inserts, Flyway/Liquibase scripts.
**Rationale**: Faster iteration without requiring external scripts.

## Data Flow

```
API Client ──→ TransportistaController ──→ TransportistaService ──→ TransportistaRepository
     ↑                                                                                  │
     └────────────────────────────Swagger UI Documentation ─────────────────────────────┘
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/.../models/Transportista.java` | Create | JPA entity for transportistas. |
| `src/main/java/.../controllers/api/TransportistaController.java` | Create | CRUD REST controller for transportistas. |
| `src/main/java/.../config/DataInitializer.java` | Modify | Add logic for development data seeding. |

## Interfaces / Contracts

A new entity contract for the `Transportista` model:
```java
@Entity
public class Transportista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String identificador;

    private String contacto;

    // Getters and setters omitted for brevity
}
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|--------------|----------|
| Unit | Model validation | Use JUnit to validate annotations and constraints. |
| Unit | Service operations | Mock repository layer to test service logic. |
| Integration | CRUD operations | Test actual API endpoints with an embedded H2 database. |
| E2E | API Documentation | Use Selenium to verify Swagger UI paths and contents. |

## Migration / Rollout
No migration required. The feature is only seeded in non-production environments.

## Open Questions
- [ ] Should transportista CRUD operations have role-based restrictions (e.g., admin-only)?
- [ ] Is there a need for data validation pipelines in the initializer?

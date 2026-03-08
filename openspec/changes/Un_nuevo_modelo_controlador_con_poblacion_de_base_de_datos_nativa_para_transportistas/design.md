# Design: Un nuevo modelo controlador con población de base de datos nativa para transportistas

## Technical Approach

The transportista database population will be executed using a newly developed Spring Boot controller. This new controller will:
- Intercept HTTP requests to initiate database population operations.
- Validate input data before committing any changes.
- Execute bulk data operations through the JPA repository layer while maintaining the performance constraints outlined in our specs.

The system ensures that the process logs outcomes of operations, providing insights into successes or any encountered failures.

## Architecture Decisions

### Decision: Introduce a dedicated population controller

**Choice**: Create a specific controller under the `controllers/transportistas/` domain.
**Alternatives considered**: Extend existing controllers or develop CLI tooling instead.
**Rationale**: This aligns with RESTful API design principles, enabling manageability. Also, HTTP triggers offer flexibility on when and how population runs occur.

### Decision: Centralize bulk population logic

**Choice**: Encapsulate logic within `service/transportistas/TransportistaPopulationService`.
**Alternatives considered**: Direct data manipulation within controllers.
**Rationale**: Promotes modularity, eases testing and possible future reuse.

## Data Flow

User trigger through `POST /api/transportistas/populate`:

    HTTP Request ───→ Controller ───→ Service ───→ Repository ⇆ SQL Database

Response from repository is logged for processing statistics or errors.

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/controllers/transportistas/PopulationController.java` | Create | Handles incoming populate endpoint requests |
| `src/main/java/ipss/web2/examen/services/transportistas/PopulationService.java` | Create | Contains database population business logic. |
| `src/main/java/ipss/web2/examen/repositories/TransportistaRepository.java` | Modify | Add methods for batch insert operations. |

## Interfaces / Contracts

### TransportistaPopulationRequest

```java
public class TransportistaPopulationRequest {
    private List<TransportistaDTO> transportistas;
    // getters/setters
}
```

### PopulationController Endpoint

- Method: `POST /api/transportistas/populate`
    - Request Body:
    ```json
    {
      "transportistas": [{"id": 1, "name": "Sample"}]
    }
    ```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|--------------|----------|
| Unit | Correct bulk insertion | Mock repositories |
| Unit | Error returned on invalid data | Mock service response |
| Integration | End-to-end insertion via REST | Use embedded H2 database |
| Integration | validations throw as expected | Custom validators |
| E2E | Performance constraints/external hits verified | Setup test suites. |

## Migration / Rollout

There is no migration involved. The change introduces a non-breaking addition to existing APIs. Rollout will be directly tied via DevOps pipelines post successful E2E test outcomes.

## Open Questions

- [ ] Do we need integration with existing transportista statistics endpoint?
- [ ] Are rate limits required on population-trigger-like endpoints to mitigate misuse?
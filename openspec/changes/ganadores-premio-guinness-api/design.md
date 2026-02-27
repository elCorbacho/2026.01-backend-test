# Design: API de Ganadores del Premio Guinness

## Technical Approach

Implementar un recurso nuevo siguiendo el patrón MVC existente. Se crea la entidad `GanadorGuinness` con soft delete y auditoría, un repository con `findByActiveTrue()`, un service con lectura `@Transactional(readOnly = true)` y un controller que expone GET `/api/ganadores-guinness` y devuelve `ApiResponseDTO<List<GanadorGuinnessResponseDTO>>`. Se reutiliza el estilo de DTOs/mappers manuales existente (ej. GanadorAlbum).

## Architecture Decisions

### Decision: Mantener DTOs y mapper manual
**Choice**: Crear `GanadorGuinnessResponseDTO` y `GanadorGuinnessMapper` manual.
**Alternatives considered**: MapStruct o exponer entidades directamente.
**Rationale**: El proyecto usa mappers manuales y DTOs para mantener consistencia y encapsular modelo interno.

### Decision: Endpoint de solo lectura
**Choice**: Exponer solo GET `/api/ganadores-guinness` en esta entrega.
**Alternatives considered**: CRUD completo.
**Rationale**: La propuesta y specs limitan el alcance a consulta, reduciendo cambios y riesgos.

### Decision: Soft delete con filtro por active
**Choice**: Entidad con campo `active` y repository `findByActiveTrue()`.
**Alternatives considered**: Eliminación física o filtros en el service.
**Rationale**: El sistema ya usa soft delete en todas las entidades.

## Data Flow

Solicitud HTTP GET

    Cliente ? GanadorGuinnessController ? GanadorGuinnessService ? GanadorGuinnessRepository ? DB
                                   ?
                               GanadorGuinnessMapper ? DTOs ? ApiResponseDTO

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/GanadorGuinness.java` | Create | Entidad JPA con `active`, `createdAt`, `updatedAt` |
| `src/main/java/ipss/web2/examen/repositories/GanadorGuinnessRepository.java` | Create | `findByActiveTrue()` |
| `src/main/java/ipss/web2/examen/dtos/GanadorGuinnessResponseDTO.java` | Create | DTO de salida con campos del ganador |
| `src/main/java/ipss/web2/examen/mappers/GanadorGuinnessMapper.java` | Create | Mapper manual entidad?DTO |
| `src/main/java/ipss/web2/examen/services/GanadorGuinnessService.java` | Create | Lógica de consulta con `@Transactional(readOnly = true)` |
| `src/main/java/ipss/web2/examen/controllers/api/GanadorGuinnessController.java` | Create | Controlador REST con GET `/api/ganadores-guinness` |

## Interfaces / Contracts

### DTO de respuesta
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GanadorGuinnessResponseDTO {
    private String nombre;
    private String categoria;
    private String record;
    private Integer anio;
}
```

### Endpoint
```
GET /api/ganadores-guinness
Response: ApiResponseDTO<List<GanadorGuinnessResponseDTO>>
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Mapper y service | Tests de conversión y filtrado active=true |
| Integration | Endpoint GET | MockMvc validando status y wrapper ApiResponseDTO |
| E2E | N/A | No requerido en este cambio |

## Migration / Rollout

No migration required.

## Open Questions

- [ ] ¿Campos definitivos del ganador Guinness (nombre/categoría/record/año) o deben ajustarse a otra estructura?

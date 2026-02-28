# Design: Modelo, controlador y API GET con población inicial para equipos_futball_espana

## Technical Approach
Implementar la entidad JPA, repositorio, servicio y controlador REST siguiendo el patrón MVC del proyecto. Usar un DataInitializer para poblar la tabla con equipos principales al arrancar la aplicación. El endpoint GET devolverá equipos activos usando ApiResponseDTO.

## Architecture Decisions

### Decision: Usar MVC y DataInitializer
**Choice**: Crear entidad, repositorio, servicio, controlador y DataInitializer.
**Alternatives considered**: Controlador directo sin service/repository, carga manual de datos.
**Rationale**: Consistencia con el resto del proyecto y facilidad de mantenimiento.

### Decision: Sin DTO específico
**Choice**: Devolver entidad directamente en la respuesta.
**Alternatives considered**: Crear DTO y mapper.
**Rationale**: Para GET simple, la entidad es suficiente; si se agregan campos sensibles, se podrá crear DTO.

## Data Flow

    Cliente ──→ EquipoFutbolEspanaController ──→ EquipoFutbolEspanaService ──→ EquipoFutbolEspanaRepository ──→ DB

    (Arranque) ──→ EquipoFutbolEspanaDataInitializer ──→ EquipoFutbolEspanaRepository ──→ DB

## File Changes
| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/EquipoFutbolEspana.java` | Create | Entidad JPA |
| `src/main/java/ipss/web2/examen/repositories/EquipoFutbolEspanaRepository.java` | Create | Repositorio Spring Data JPA |
| `src/main/java/ipss/web2/examen/services/EquipoFutbolEspanaService.java` | Create | Servicio de negocio |
| `src/main/java/ipss/web2/examen/controllers/api/EquipoFutbolEspanaController.java` | Create | Controlador REST |
| `src/main/java/ipss/web2/examen/config/EquipoFutbolEspanaDataInitializer.java` | Create | Inicializador de datos |

## Interfaces / Contracts
```java
@Entity
@Table(name = "equipo_futbol_espana")
public class EquipoFutbolEspana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ciudad;
    private Integer fundacion;
    private String estadio;
    private Boolean activo = true;
}
```

## Testing Strategy
| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Servicio y repositorio | Testear métodos de consulta |
| Integration | Controlador GET | Testear endpoint y formato de respuesta |
| E2E | Arranque y población | Verificar equipos iniciales tras el arranque |

## Migration / Rollout
No migration required. La tabla y datos se crean automáticamente si no existen.

## Open Questions
- [ ] ¿Se requiere DTO para ocultar campos en la respuesta?
- [ ] ¿Se debe permitir paginación o filtros adicionales en el endpoint GET?

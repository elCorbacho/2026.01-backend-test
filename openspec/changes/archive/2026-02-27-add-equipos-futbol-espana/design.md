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
@Entity
@Table(name = "equipo_futbol_espana")
public class EquipoFutbolEspana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
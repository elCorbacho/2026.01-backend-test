# Design: Catálogo de Marcas de Automóvil

## Technical Approach
Añadir una nueva entidad `MarcaAutomovil` persistida con auditoría suave y exponerla mediante el patrón existente entidad → repositorio → servicio → controlador. El servicio usa un método de solo lectura que invoca `MarcaAutomovilRepository.findByActiveTrueOrderByNombreAsc()` y el controlador envuelve el resultado en `ApiResponseDTO`. `DataInitializer` recibe el repositorio y, si la tabla está vacía, recorre un arreglo de 30 marcas conocidas para insertar cada registro con `active=true` y mantener `TARGET_SEED_COUNT`.

## Architecture Decisions

### Decision: Usar el pipeline REST existente
**Choice**: Reutilizar el patrón entidad/repo/servicio/controlador y `ApiResponseDTO` para mantener consistencia.
**Alternatives considered**: Crear un endpoint estático o utilizar un bean configurado con lista en memoria.
**Rationale**: Mantiene los datos trazables, auditables y reutilizables en pruebas, y aprovecha las convenciones ya implementadas en el código.

### Decision: Seed en DataInitializer con guardias de conteo
**Choice**: Añadir el seed al `DataInitializer` junto a los otros modelos, usando `if (marcaRepo.count() == 0)`.
**Alternatives considered**: Crear un `CommandLineRunner` nuevo o un script SQL separado.
**Rationale**: Facilita el mantenimiento centralizado de datos semilla bajo el mismo componente responsable de inicializar la base de datos y evita duplicación de configuración.

### Decision: DTO con timestamps y mappers manuales
**Choice**: Crear `MarcaAutomovilResponseDTO` que incluya `id`, `nombre`, `paisOrigen`, `descripcion`, `createdAt`, `updatedAt` y mapear con un componente manual.
**Alternatives considered**: Usar la entidad directamente en las respuestas.
**Rationale**: Alinear con la convención de exposiciones previas y permitir ocultar campos o transformar nombres cuando sea necesario.

## Data Flow

    Client --> MarcaAutomovilController --> MarcaAutomovilService --> MarcaAutomovilRepository --> MySQL
                         ↑                                                  ↓
                         |------------------ ApiResponseDTO ------------------|

Cuando `DataInitializer` arranca:

    DataInitializer ---(marcaRepo.count()==0)---> popula array de 30 nombres --> marcaRepo.save()

## File Changes
| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/MarcaAutomovil.java` | Create | Nueva entidad JPA con campos `nombre`, `paisOrigen`, `descripcion`, `active`, `createdAt`, `updatedAt`. Usa Lombok y auditoría. |
| `src/main/java/ipss/web2/examen/repositories/MarcaAutomovilRepository.java` | Create | Extiende `JpaRepository` y expone `List<MarcaAutomovil> findByActiveTrueOrderByNombreAsc()`. |
| `src/main/java/ipss/web2/examen/dtos/MarcaAutomovilResponseDTO.java` | Create | DTO para la respuesta con timestamps y soft delete. |
| `src/main/java/ipss/web2/examen/mappers/MarcaAutomovilMapper.java` | Create | Convierte de entidad a DTO. |
| `src/main/java/ipss/web2/examen/services/MarcaAutomovilService.java` | Create | Servicio `obtenerMarcasActivas()` (@Transactional(readOnly=true)) que delega al repositorio y ejecuta el mapper. |
| `src/main/java/ipss/web2/examen/controllers/api/MarcaAutomovilController.java` | Create | Controlador REST con `GET /api/marcas` y respuesta `ApiResponseDTO<List<MarcaAutomovilResponseDTO>>`. |
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modify | Inyecta `MarcaAutomovilRepository`, agrega método `poblarMarcasAutomovil()` que recorre la lista de nombres y guarda cada registro con `active=true` cuando la tabla está vacía. |

## Interfaces / Contracts

```java
public interface MarcaAutomovilRepository extends JpaRepository<MarcaAutomovil, Long> {
    List<MarcaAutomovil> findByActiveTrueOrderByNombreAsc();
}
```

```java
@Builder
public class MarcaAutomovilResponseDTO {
    private Long id;
    private String nombre;
    private String paisOrigen;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

```java
@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaAutomovilController {
    private final MarcaAutomovilService service;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MarcaAutomovilResponseDTO>>> listarMarcas() {
        List<MarcaAutomovilResponseDTO> marcas = service.obtenerMarcasActivas();
        return ResponseEntity.ok(ApiResponseDTO.ok(marcas, "Marcas recuperadas"));
    }
}
```

## Testing Strategy
| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | `MarcaAutomovilMapper` conversion | Instanciar entidad y verificar DTO resultante. |
| Unit | `MarcaAutomovilService` usa el repositorio y el mapper | Mockear repo y mapper, confirmar lista ordenada y solo activos. |
| Integration | `DataInitializer` seed y controlador GET | `@SpringBootTest` con contexto real; verificar `GET /api/marcas` devuelve 30 marcas y no duplica. |

## Migration / Rollout
No migration required más allá del DDL (MySQL crea la tabla al ejecutar el schema). Documentar el DDL si se mantiene fuera del formato automático.

## Open Questions
- [ ] ¿Se requerirá paginación o filtros adicionales para este endpoint en el futuro?
- [ ] ¿Debemos almacenar también campos como `logoUrl` o `codigoIso` cuando se amplíe el catálogo?

# Design: Regiones de Chile

## Technical Approach

La API expone un nuevo recurso de solo lectura. Se introduce la entidad `RegionChile` con auditoría y bandera `active`, un repositorio con consulta ordenada por código, un servicio `RegionService` que obtiene `List<RegionResponseDTO>` y mapea manualmente con un nuevo `RegionMapper`, y un controlador `RegionController` bajo `/api/regiones` que envuelve el resultado en `ApiResponseDTO.ok`. La carga inicial de datos reutilizará el `DataInitializer` existente para poblar la tabla si está vacía, manteniendo la misma convención de soft delete y `@Transactional(readOnly = true)` en el servicio.

## Architecture Decisions

### Decision: Manual mapper component
**Choice**: Añadir `RegionMapper` similar a los mappers existentes (`PaisDistribucionMapper`, `GanadorAlbumMapper`).
**Alternatives considered**: Introducir MapStruct o reutilizar un mapper genérico.
**Rationale**: La base ya usa mappers manuales con Lombok y la lógica es mínima (entidad ↔ DTO). Mantener el mismo patrón evita introducir nuevas dependencias ni ajustar la infraestructura de pruebas.

### Decision: Servicio dedicado con `@Transactional(readOnly = true)`
**Choice**: Crear `RegionService` que expone `obtenerRegionesActivas()` y delega al repositorio.
**Alternatives considered**: Reutilizar un servicio existente (p. ej. `PaisDistribucionService`) y extenderlo con regiones.
**Rationale**: Las regiones son un dominio separado y deben tener su propio ciclo de vida. Un servicio dedicado mantiene las capas separadas y preserva `ApiResponseDTO`/`ResponseEntity` en el controlador.

## Data Flow

1. `RegionController` recibe `GET /api/regiones`.
2. Controller llama a `RegionService.obtenerRegionesActivas()`.
3. Servicio invoca `RegionRepository.findByActiveTrueOrderByCodigoAsc()` (o equivalente) y mapea cada `RegionChile` a `RegionResponseDTO` mediante `RegionMapper`.
4. Controller envuelve la lista en `ApiResponseDTO.ok(data, "Regiones recuperadas")` y responde `200`.

```
Controller ──→ Service ──→ Repository (findByActiveTrueOrderByCodigoAsc)
        │             │
        └── ApiResponseDTO.ok(data)◂┘
```

## File Changes
| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/RegionChile.java` | Create | Nueva entidad JPA con campos `id`, `codigo`, `nombre`, auditoría y `is_active`. |
| `src/main/java/ipss/web2/examen/dtos/RegionResponseDTO.java` | Create | DTO readonly con `codigo`, `nombre`. |
| `src/main/java/ipss/web2/examen/mappers/RegionMapper.java` | Create | Componente manual para mapear entidad ↔ DTO. |
| `src/main/java/ipss/web2/examen/repositories/RegionRepository.java` | Create | `JpaRepository<RegionChile, Long>` con método `findByActiveTrueOrderByCodigoAsc()`. |
| `src/main/java/ipss/web2/examen/services/RegionService.java` | Create | Servicio con `obtenerRegionesActivas()` marcado `@Transactional(readOnly = true)`. |
| `src/main/java/ipss/web2/examen/controllers/api/RegionController.java` | Create | Controller `@RequestMapping("/api/regiones")` que retorna `ApiResponseDTO<List<RegionResponseDTO>>`. |
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modify | Registrar llamada opcional para cargar regiones si la tabla está vacía (p. ej. `regionRepository.count()`). |

## Interfaces / Contracts

```
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
}
```

`RegionService` expone `List<RegionResponseDTO> obtenerRegionesActivas()` y `RegionController` responde `ResponseEntity<ApiResponseDTO<List<RegionResponseDTO>>>`.

## Testing Strategy
| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | `RegionService` (filtra solo `active = true`, orden) | Mock `RegionRepository` y verificar que mapea usando `RegionMapper`. |
| Unit | `RegionController` estructura del `ApiResponseDTO` | Mock `RegionService` y validar la respuesta HTTP. |
| Integration | `GET /api/regiones` contra DB en memoria | `@SpringBootTest` o `@DataJpaTest` con `RegionRepository`. Insertar regiones activas/inactivas y verificar que solo se devuelven las activas. |

## Migration / Rollout
No migration requerida; se puede poblar la tabla `region` desde `DataInitializer` si está vacía. Documentarlo en el README o en la `DataInitializer` para que los datos oficiales se sincronicen automáticamente.

## Open Questions
- [ ] ¿Deseamos exponer un DTO simplificado (solo `codigo` y `nombre`) o incluir más atributos en futuras iteraciones?
- [ ] ¿Necesitamos paginación para `/api/regiones` o basta con devolver todas las regiones por defecto?

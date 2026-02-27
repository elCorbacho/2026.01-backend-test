# Design: API Presidentes de Chile

## Technical Approach

Implementar un nuevo recurso siguiendo el patrón MVC existente (Controller → Service → Repository → Model) con respuestas envueltas en `ApiResponseDTO`. El endpoint `GET /api/presidentes-chile` devolverá una lista de presidentes activos usando una entidad JPA con auditoría y soft delete, un mapper manual y un servicio de solo lectura, alineado con la especificación del dominio.

## Architecture Decisions

### Decision: Usar entidad JPA con auditing y soft delete

**Choice**: Crear `PresidenteChile` con `@CreatedDate`, `@LastModifiedDate` y columna `is_active`.
**Alternatives considered**: Tabla sin auditoría o sin soft delete.
**Rationale**: El proyecto usa auditing y soft delete en todas las entidades (ver `Album`, `GanadorGuinness`). Mantener consistencia evita divergencias y simplifica consultas por `active`.

### Decision: Respuesta estándar con ApiResponseDTO

**Choice**: Envolver la lista en `ApiResponseDTO.ok(...)` desde el controlador.
**Alternatives considered**: Retornar `List<PresidenteChileResponseDTO>` directo.
**Rationale**: Convención establecida en todos los endpoints de la API y requerida por la especificación.

### Decision: Mapper manual para DTO

**Choice**: Implementar `PresidenteChileMapper` manual.
**Alternatives considered**: MapStruct u otras librerías automáticas.
**Rationale**: El proyecto usa mappers manuales en `mappers/` y no usa MapStruct.

## Data Flow

```
HTTP GET /api/presidentes-chile
        │
        ▼
PresidenteChileController
        │  ApiResponseDTO.ok(list)
        ▼
PresidenteChileService (readOnly)
        │
        ▼
PresidenteChileRepository.findByActiveTrue()
        │
        ▼
PresidenteChile (JPA Entity)  →  PresidenteChileMapper  →  PresidenteChileResponseDTO
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/PresidenteChile.java` | Create | Entidad JPA con auditoría y soft delete |
| `src/main/java/ipss/web2/examen/repositories/PresidenteChileRepository.java` | Create | Repositorio con `findByActiveTrue()` |
| `src/main/java/ipss/web2/examen/services/PresidenteChileService.java` | Create | Servicio de solo lectura para listar presidentes activos |
| `src/main/java/ipss/web2/examen/controllers/api/PresidenteChileController.java` | Create | Endpoint GET `/api/presidentes-chile` |
| `src/main/java/ipss/web2/examen/dtos/PresidenteChileResponseDTO.java` | Create | DTO de respuesta |
| `src/main/java/ipss/web2/examen/mappers/PresidenteChileMapper.java` | Create | Mapper manual entidad → DTO |
| `db schema` | Modify | Agregar tabla `presidente_chile` con columnas y `is_active` |

## Interfaces / Contracts

### Endpoint

- **GET** `/api/presidentes-chile`
- **Response**: `ApiResponseDTO<List<PresidenteChileResponseDTO>>`

### DTO (borrador)

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresidenteChileResponseDTO {
    private Long id;
    private String nombre;
    private LocalDate periodoInicio;
    private LocalDate periodoFin;
    private String partido;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### Entity (borrador)

```java
@Entity
@Table(name = "presidente_chile")
@EntityListeners(AuditingEntityListener.class)
public class PresidenteChile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin")
    private LocalDate periodoFin;

    @Column(name = "partido", length = 120)
    private String partido;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean active = true;
}
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Mapper `PresidenteChileMapper` | Verificar mapeo entidad → DTO |
| Unit | Service `obtenerPresidentesChile()` | Mock repository y validar filtrado por `active` |
| Integration | Controller `GET /api/presidentes-chile` | `@SpringBootTest` + `MockMvc` verificando `ApiResponseDTO` |
| E2E | (Opcional) | Validar respuesta desde cliente (Postman/Bruno) |

## Migration / Rollout

Se requiere crear la tabla `presidente_chile` en la base de datos antes de desplegar, ya que `ddl-auto=validate` fallará si la tabla no existe. No se requiere migración de datos existentes.

## Open Questions

- [ ] ¿Qué campos son obligatorios para el frontend (por ejemplo, `periodoFin` puede ser null)?
- [ ] ¿Se debe incluir carga inicial de datos en `DataInitializer`?

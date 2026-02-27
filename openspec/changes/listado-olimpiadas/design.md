# Design: Listado Olimpiadas

## Technical Approach

Seguir el patron MVC existente para recursos simples (como `Cancion` y `MinaChile`), incorporando una entidad JPA con soft delete y auditoria, DTOs con validaciones, mapper manual y endpoints REST con respuestas `ApiResponseDTO<T>`. La poblacion inicial se agregara a `DataInitializer` con verificacion de registros existentes.

## Architecture Decisions

### Decision: Mantener el patron MVC en capas

**Choice**: Crear controlador, servicio, repositorio, DTOs y mapper manual para `ListadoOlimpiadas`.
**Alternatives considered**: Usar endpoints directos con repositorio o MapStruct.
**Rationale**: El proyecto estandariza la logica de negocio en servicios y usa mappers manuales.

### Decision: Soft delete con `is_active`

**Choice**: Implementar soft delete mediante el campo `is_active` y filtrar listados con `findByActiveTrue()`.
**Alternatives considered**: Eliminacion fisica (DELETE real).
**Rationale**: Todo el sistema usa soft delete y auditoria; mantiene consistencia con reglas de negocio.

### Decision: Poblacion inicial condicionada

**Choice**: Insertar datos semilla solo si `count()` es 0.
**Alternatives considered**: Insertar siempre o usar scripts externos.
**Rationale**: `DataInitializer` ya aplica esta estrategia para otros recursos.

## Data Flow

Cliente HTTP
    │
    ▼
ListadoOlimpiadasController
    │ (validacion @Valid)
    ▼
ListadoOlimpiadasService
    │ (reglas negocio / soft delete)
    ▼
ListadoOlimpiadasRepository ──→ Base de datos
    ▲
    │ (mapeo DTO ↔ entidad)
ListadoOlimpiadasMapper

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/ListadoOlimpiadas.java` | Create | Entidad JPA con auditoria y `is_active` |
| `src/main/java/ipss/web2/examen/repositories/ListadoOlimpiadasRepository.java` | Create | Repositorio JPA con metodos `findByActiveTrue()` |
| `src/main/java/ipss/web2/examen/services/ListadoOlimpiadasService.java` | Create | CRUD, soft delete y busqueda por ID |
| `src/main/java/ipss/web2/examen/controllers/api/ListadoOlimpiadasController.java` | Create | Endpoints REST `/api/listado-olimpiadas` |
| `src/main/java/ipss/web2/examen/dtos/ListadoOlimpiadasRequestDTO.java` | Create | DTO request con validaciones |
| `src/main/java/ipss/web2/examen/dtos/ListadoOlimpiadasResponseDTO.java` | Create | DTO response con auditoria |
| `src/main/java/ipss/web2/examen/mappers/ListadoOlimpiadasMapper.java` | Create | Mapper manual entidad ↔ DTO |
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modify | Agregar poblacion inicial del nuevo recurso |

## Interfaces / Contracts

### Endpoints

- `POST /api/listado-olimpiadas`
- `GET /api/listado-olimpiadas`
- `GET /api/listado-olimpiadas/{id}`
- `PUT /api/listado-olimpiadas/{id}`
- `DELETE /api/listado-olimpiadas/{id}` (soft delete)

### DTOs (estructura base)

```java
public class ListadoOlimpiadasRequestDTO {
    // Campos de dominio TBD
}

public class ListadoOlimpiadasResponseDTO {
    private Long id;
    // Campos de dominio TBD
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### Entidad (estructura base)

```java
@Entity
@Table(name = "listado_olimpiadas")
public class ListadoOlimpiadas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Campos de dominio TBD
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @Builder.Default
    private Boolean active = true;
}
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Unit | Mapper y reglas de servicio | Pruebas unitarias para toEntity/toResponseDTO y soft delete |
| Integration | CRUD con validaciones | MockMvc contra el controlador con datos en BD de prueba |
| E2E | No aplica por ahora | Fuera de alcance en este proyecto |

## Migration / Rollout

Requiere crear la tabla `listado_olimpiadas` antes de desplegar (ddl-auto=validate). No se requiere rollout gradual.

## Open Questions

- [ ] Cuales son los campos exactos del modelo `ListadoOlimpiadas` y sus validaciones.
- [ ] Confirmar la ruta base de API (`/api/listado-olimpiadas` vs otra convención).
- [ ] Definir dataset inicial (cantidad y valores).

# Proposal: API de Ganadores del Premio Guinness

## Intent

Agregar un recurso nuevo para gestionar y consultar ganadores del premio Guinness, exponiendo un endpoint GET que permita listar los ganadores activos con el formato de respuesta estándar del proyecto.

## Scope

### In Scope
- Crear entidad JPA `GanadorGuinness` con soft delete y auditoría.
- Crear repository, service y mapper manuales para el recurso.
- Exponer endpoint GET `/api/ganadores-guinness` que liste ganadores activos.

### Out of Scope
- Operaciones POST/PUT/DELETE para ganadores.
- Paginación, filtros o búsqueda avanzada.
- Integración con fuentes externas o scraping.

## Approach

Seguir el patrón MVC en capas existente: modelo JPA con `active`, repository con `findByActiveTrue`, service con método de lectura `@Transactional(readOnly = true)` y controller que devuelve `ResponseEntity<ApiResponseDTO<List<GanadorGuinnessResponseDTO>>>`. Crear DTOs y mapper manual similar a los módulos de álbumes/laminas.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/` | New | Entidad `GanadorGuinness` |
| `src/main/java/ipss/web2/examen/repositories/` | New | `GanadorGuinnessRepository` |
| `src/main/java/ipss/web2/examen/services/` | New | `GanadorGuinnessService` |
| `src/main/java/ipss/web2/examen/controllers/api/` | New | `GanadorGuinnessController` |
| `src/main/java/ipss/web2/examen/dtos/` | New | Request/Response DTOs |
| `src/main/java/ipss/web2/examen/mappers/` | New | `GanadorGuinnessMapper` |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Inconsistencias con convenciones (nombres, wrappers) | Low | Reutilizar patrones de controladores existentes |
| Error de mapping de campos fecha/numero | Medium | Validar DTOs y tipos en entidad |

## Rollback Plan

Eliminar las clases del recurso y quitar el endpoint del controller; no se tocan entidades existentes ni datos previos.

## Dependencies

- Ninguna adicional; usa Spring Data JPA y Lombok existentes.

## Success Criteria

- [ ] GET `/api/ganadores-guinness` responde 200 con `ApiResponseDTO<List<GanadorGuinnessResponseDTO>>`.
- [ ] Solo se listan registros con `active = true`.
- [ ] Se mantiene el estilo de nombres en español y comentarios en español.

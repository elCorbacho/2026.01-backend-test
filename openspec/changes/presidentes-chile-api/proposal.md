# Proposal: API Presidentes de Chile

## Intent

Agregar un recurso de "Presidentes de Chile" con un nuevo modelo, capa de servicio y controlador, exponiendo un endpoint GET para listar presidentes activos. Esto habilita consumir la información desde el frontend y alinea el proyecto con la arquitectura MVC y respuesta envolvente estándar.

## Scope

### In Scope
- Nuevo modelo JPA `PresidenteChile` con campos base, auditoría y soft delete.
- DTOs de respuesta (y request si se requiere futura extensión) para presidentes.
- Servicio y repositorio para listar presidentes activos.
- Controlador REST con endpoint GET `/api/presidentes-chile` que retorna `ApiResponseDTO<List<PresidenteChileResponseDTO>>`.

### Out of Scope
- Endpoints de creación, edición o eliminación (CRUD completo).
- Importación automática de datos desde fuentes externas.
- UI/Frontend para visualización.

## Approach

Crear una entidad `PresidenteChile` en `models/` con campos como `nombre`, `periodoInicio`, `periodoFin`, `partido`, `descripcion`, `active`, `createdAt`, `updatedAt`. Implementar repositorio con `findByActiveTrue()`, servicio con método de solo lectura (`@Transactional(readOnly = true)`), mapper manual para DTOs y controlador en `controllers/api/` con método GET que envuelve la respuesta usando `ApiResponseDTO<T>`.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `ipss/web2/examen/models/PresidenteChile.java` | New | Entidad JPA con soft delete y auditing |
| `ipss/web2/examen/repositories/PresidenteChileRepository.java` | New | Consultas Spring Data (findByActiveTrue) |
| `ipss/web2/examen/services/PresidenteChileService.java` | New | Lógica para listar presidentes activos |
| `ipss/web2/examen/controllers/api/PresidenteChileController.java` | New | Endpoint GET `/api/presidentes-chile` |
| `ipss/web2/examen/dtos/PresidenteChileResponseDTO.java` | New | DTO de salida |
| `ipss/web2/examen/mappers/PresidenteChileMapper.java` | New | Mapper manual entidad -> DTO |
| `db schema` | Modified | Crear tabla `presidente_chile` con columnas y índices |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Inconsistencia con `ddl-auto=validate` si falta tabla | Medium | Incluir script de creación y documentar pasos de DB |
| Campos insuficientes para el frontend | Low | Validar con requerimientos antes de implementar |
| Nomenclatura distinta a convenciones del proyecto | Low | Mantener nombres en español y patrones existentes |

## Rollback Plan

Revertir los nuevos archivos (modelo, DTO, mapper, servicio, repositorio y controlador) y eliminar la tabla `presidente_chile` en la base de datos. Desregistrar el endpoint si fue agregado a documentación.

## Dependencies

- Creación/actualización de la tabla `presidente_chile` en MySQL.
- (Opcional) Datos iniciales en `DataInitializer` si se requiere carga de ejemplo.

## Success Criteria

- [ ] GET `/api/presidentes-chile` responde 200 con `ApiResponseDTO<List<PresidenteChileResponseDTO>>`.
- [ ] Solo se listan presidentes con `active = true`.
- [ ] Swagger/OpenAPI muestra el endpoint y el esquema de respuesta.

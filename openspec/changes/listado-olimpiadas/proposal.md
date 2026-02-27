# Proposal: Listado Olimpiadas

## Intent

Incorporar un nuevo recurso de dominio para gestionar listados de olimpiadas con su API y datos iniciales, manteniendo las convenciones del proyecto (MVC, soft delete, ApiResponseDTO).

## Scope

### In Scope
- Definir el modelo `ListadoOlimpiadas` con sus campos, validaciones y auditoria
- Crear controlador, servicio, repositorio, DTOs y mapper para CRUD del recurso
- Poblacion inicial de base de datos en `DataInitializer`

### Out of Scope
- Migraciones de esquema automatizadas (se asume gestion externa o manual)
- Integraciones con terceros o reportes avanzados

## Approach

Agregar una entidad JPA con soft delete y auditoria, exponer endpoints REST en `controllers/api` usando `ApiResponseDTO<T>`, y cargar datos semilla en `DataInitializer`. Se reutiliza el patron de mappers manuales y DTOs existentes.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/ListadoOlimpiadas.java` | New | Entidad JPA con auditoria y soft delete |
| `src/main/java/ipss/web2/examen/repositories/ListadoOlimpiadasRepository.java` | New | Repositorio Spring Data JPA |
| `src/main/java/ipss/web2/examen/services/ListadoOlimpiadasService.java` | New | Logica de negocio y validaciones |
| `src/main/java/ipss/web2/examen/controllers/api/ListadoOlimpiadasController.java` | New | Endpoints REST para CRUD |
| `src/main/java/ipss/web2/examen/dtos/*ListadoOlimpiadas*.java` | New | DTOs request/response |
| `src/main/java/ipss/web2/examen/mappers/ListadoOlimpiadasMapper.java` | New | Mapper manual entidad <-> DTO |
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modified | Insercion de datos semilla |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Campos del modelo no definidos | Med | Alinear con negocio antes de implementar |
| Conflictos con ddl-auto=validate | Med | Coordinar creacion de tabla en BD antes de desplegar |
| Datos semilla duplicados | Low | Validar existencia antes de insertar |

## Rollback Plan

Eliminar endpoints y clases del recurso, y retirar los registros semilla insertados para `ListadoOlimpiadas` en la base de datos. Si existiera tabla nueva, revertirla con script manual.

## Dependencies

- Creacion/actualizacion de la tabla `listado_olimpiadas` en la base de datos

## Success Criteria

- [ ] API expone CRUD de `ListadoOlimpiadas` con respuestas `ApiResponseDTO<T>`
- [ ] Entidad persiste correctamente con auditoria y soft delete
- [ ] Datos iniciales se cargan sin duplicados

# Proposal: Listado de Presidentes de Rusia (API + modelo + seed)

## Intent

Agregar un nuevo recurso API para listar y registrar presidentes de Rusia, incluyendo modelo JPA, capa de servicio, endpoints GET/POST y carga inicial de datos para que la API funcione sin datos manuales.

## Scope

### In Scope
- Entidad JPA ListadoPresidenteRusia con auditoría y soft delete.
- DTOs de request/response y mapper manual.
- Repositorio, servicio y controlador REST con endpoints **GET** y **POST**.
- Data initializer (CommandLineRunner) con población inicial de presidentes de Rusia.
- Rutas API bajo /api/listado-presidente-rusia con ApiResponseDTO<T>.

### Out of Scope
- Endpoints PUT/DELETE o búsquedas avanzadas.
- Migraciones SQL manuales o cambios de infraestructura.
- Cambios de frontend o documentación extendida.

## Approach

Replicar el patrón existente de módulos (ej. PresidenteChile): entidad + repositorio + servicio + mapper + DTO + controller, y un initializer que carga datos si la tabla está vacía. Seguir convenciones del proyecto (nombres en español, respuestas con ApiResponseDTO, soft delete is_active).

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| src/main/java/ipss/web2/examen/models/ | New | Nueva entidad ListadoPresidenteRusia |
| src/main/java/ipss/web2/examen/dtos/ | New | DTOs request/response |
| src/main/java/ipss/web2/examen/mappers/ | New | Mapper manual |
| src/main/java/ipss/web2/examen/repositories/ | New | Repositorio JPA |
| src/main/java/ipss/web2/examen/services/ | New | Servicio con lógica de listado/creación |
| src/main/java/ipss/web2/examen/controllers/api/ | New | Controlador REST GET/POST |
| src/main/java/ipss/web2/examen/config/ | New | Data initializer para poblar BD |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Datos iniciales incompletos o erróneos | Med | Confirmar dataset mínimo y validar campos obligatorios |
| Conflictos de nombres/tabla o encoding | Low | Usar convención existente y verificar en pruebas |
| Duplicación de registros en seed | Low | Insertar solo si count()==0 |

## Rollback Plan

Eliminar las clases nuevas (entidad, repo, service, mapper, DTO, controller, initializer) y borrar la tabla correspondiente si fue creada en BD. Revertir el commit elimina por completo el recurso.

## Dependencies

- Ninguna adicional; reutiliza Spring Boot, JPA y patrones existentes.

## Success Criteria

- [ ] GET /api/listado-presidente-rusia devuelve lista con ApiResponseDTO.
- [ ] POST /api/listado-presidente-rusia crea un registro válido.
- [ ] La aplicación arranca con datos iniciales cuando la tabla está vacía.

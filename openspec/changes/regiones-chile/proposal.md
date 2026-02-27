# Proposal: Regiones de Chile

## Intent
Crear estructuras dedicadas para representar las regiones de Chile y exponerlas mediante un endpoint `GET`, de modo que el front y los clientes consuman la lista oficial sin replicar lógica o datos hardcodeados en cada equipo.

## Scope

### In Scope
- Definir un modelo JPA `Region`/`RegionChile` que capture el código oficial, nombre y estado activo de cada región.
- Añadir un endpoint `GET /api/regiones` en un nuevo `RegionController` que devuelva la lista de regiones activas envolviendo la respuesta en `ApiResponseDTO`.
- Implementar el servicio/repository asociado para consultar las regiones desde la base de datos (o datos internos) ordenadas por código.

### Out of Scope
- Cualquier operación `POST`, `PUT` o `DELETE` sobre regiones.
- Internacionalizar los nombres por ahora; se entrega en español como único idioma.
- Migraciones automatizadas (solo se documentará el esquema esperado y se aprovechará el `data initializer` si es necesario).

## Approach
Crear una entidad `RegionChile` alineada a la convención de soft delete (`active`) y registrar un repositorio Spring Data (p.ej. `RegionRepository`). El nuevo `RegionService` expondrá `obtenerRegionesChileActivas()` y el controlador `RegionController` manejará `GET /api/regiones` delegando al servicio y envolviendo la respuesta en `ApiResponseDTO<List<RegionResponseDTO>>`. Se puede reutilizar el `DataInitializer` para pre-cargar las regiones si no existen.

## Affected Areas
| Area | Impact | Description |
|------|--------|-------------|
| `ipss.web2.examen.models` | Nuevo | `RegionChile`/`Region` incluida con campos `codigo`, `nombre`, `active`, `createdAt`, `updatedAt`. |
| `ipss.web2.examen.repositories` | Nuevo | `RegionRepository extends JpaRepository<Region, Long>` con consulta de regiones activas. |
| `ipss.web2.examen.services` | Nuevo | `RegionService` con lógica de lectura usando repositorio y mapeo a DTO. |
| `ipss.web2.examen.controllers.api` | Nuevo | `RegionController` con `GET /api/regiones` devolviendo `ApiResponseDTO<List<RegionResponseDTO>>`. |
| `config/DataInitializer` (opcional) | Modificado | Añadir carga inicial de regiones si no existen registros. |

## Risks
| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Duplicar la fuente de verdad de regiones (datos inconsistentes entre servicio y UI) | Medium | Centralizar la carga desde la entidad y endpoint único, documentando la nueva API para frontend. |
| El endpoint retorna regiones inactivas o dependientes de soft delete mal reportadas | Low | Filtrar por `active = true` en el repositorio y validar la lista mediante pruebas de integración o `@DataJpaTest`. |

## Rollback Plan
Si el endpoint causa regresiones, revertir el commit del cambio y eliminar el controller/servicio asociados; como solo se agregan lecturas, basta con no desplegar los nuevos artefactos y restaurar el build previo.

## Dependencies
- Ninguna externa; si se requiere carga inicial, usar el `DataInitializer` existente para poblar la tabla `region`.

## Success Criteria
- [ ] `GET /api/regiones` responde con `200` y la lista ordenada de regiones activas en `ApiResponseDTO`.
- [ ] Todas las regiones persistidas respetan el campo `active` y aparecen sólo si están activas.
- [ ] No rompemos el contrato de las respuestas existentes (encapsuladas en `ApiResponseDTO`).

# Proposal: Catálogo de Marcas de Automóvil

## Intent
Agregar un recurso REST que exponga una lista persistida de marcas de automóviles (nombre, país de origen, descripción) y poblar la base con al menos 30 registros activos para que cualquier consumidor pueda usarlas como referencia o filtro.

## Scope
### In Scope
- Crear la entidad JPA `MarcaAutomovil` con auditoría (`createdAt`, `updatedAt`) y soft delete (`is_active`).
- Implementar DTO, mapper, servicio y controlador REST para un GET `/api/marcas` que devuelva las marcas activas ordenadas por nombre dentro de `ApiResponseDTO`.
- Registrar el repositorio `MarcaAutomovilRepository` con consultas `findByActiveTrueOrderByNombreAsc` y usarlo en el nuevo servicio.
- Extender `DataInitializer` para que, cuando la tabla de marcas esté vacía, se inserten 30 nombres conocidos (Toyota, Ford, Chevrolet, Honda, BMW, Mercedes-Benz, Audi, Volkswagen, Nissan, Hyundai, Kia, Subaru, Mazda, Tesla, Renault, Peugeot, Fiat, Citroën, Volvo, Jaguar, Land Rover, Porsche, Lamborghini, Ferrari, Aston Martin, Bentley, Rolls-Royce, Bugatti, Alfa Romeo, Mitsubishi, Suzuki) con `active=true`.

### Out of Scope
- Operaciones CRUD distintas de la lectura (POST, PUT, DELETE) para marcas.
- Relacionar marcas con otros modelos existentes (álbumes, tiendas, etc.).
- Cambios en el front-end o en archivos estáticos para consumir el nuevo endpoint.

## Approach
Sigue el patrón habitual (entidad → repositorio → servicio → controlador) y reutiliza `ApiResponseDTO` y los mappers manuales. El `DataInitializer` agrega el nuevo método de seed garantizando `TARGET_SEED_COUNT = 30` y solo corre cuando no existen registros, evitando duplicados.

## Affected Areas
| Area | Impact | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/MarcaAutomovil.java` | New | Nueva entidad con campos `id`, `nombre`, `paisOrigen`, `descripcion`, auditoría y `active`. |
| `src/main/java/ipss/web2/examen/repositories/MarcaAutomovilRepository.java` | New | `JpaRepository` con `List<MarcaAutomovil> findByActiveTrueOrderByNombreAsc()`. |
| `src/main/java/ipss/web2/examen/dtos/MarcaAutomovilResponseDTO.java` | New | Define el contrato JSON del GET, incluyendo timestamps. |
| `src/main/java/ipss/web2/examen/mappers/MarcaAutomovilMapper.java` | New | Convierte entre entidad y DTO. |
| `src/main/java/ipss/web2/examen/services/MarcaAutomovilService.java` | New | Lógica de negocio con `obtenerMarcasActivas()` (`@Transactional(readOnly = true)`) usando el mapper. |
| `src/main/java/ipss/web2/examen/controllers/api/MarcaAutomovilController.java` | New | Expone `GET /api/marcas` y devuelve `ApiResponseDTO<List<MarcaAutomovilResponseDTO>>`. |
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modified | Inyectar nuevo repositorio y agregar `poblarMarcasAutomovil()` que inserta 30 marcas conocidas cuando el conteo es cero. |

## Risks
| Risk | Likelihood | Mitigation |
|------|------------|------------|
| El seed de marcas no alcanza 30 registros y el guard `TARGET_SEED_COUNT` queda desincronizado. | Medium | Validar que el array de marcas tiene exactamente 30 nombres y que el bucle respeta `TARGET_SEED_COUNT`. Añadir aserción o unidad simple si es necesario. |
| Inyección de dependencias en `DataInitializer` rompe el constructor existente al agregar otro repositorio. | Low | Reordenar argumentos y usar `@RequiredArgsConstructor`; aprovechar la misma sección de inicialización y probar localmente `mvnw -DskipTests package`. |
| El nuevo endpoint no filtra por `active=true` y regresa marcas inactivas. | Low | El repositorio consulta únicamente por `active=true`; asegurar que el servicio no modifica el resultado ni vuelve a peticiones sin filtro. |

## Rollback Plan
Revertir el commit específico que introduce la entidad/controlador/seed. Como medida previa, remover la inyección del repositorio en `DataInitializer` y eliminar la tabla `marca_automovil` de la base (si ya se creó) para restaurar el estado previo.

## Dependencies
- Ninguna externa: reutiliza las dependencias existentes de Spring Data JPA, Lombok y los `ApiResponseDTO`/DTO/mapper ya presentes.

## Success Criteria
- [ ] `GET /api/marcas` responde `200 OK` con un `ApiResponseDTO` cuya lista contiene 30 marcas activas ordenadas por nombre.
- [ ] La base de datos se inicializa con 30 marcas (el comando `DataInitializer` inserta los nombres cuando `marca_automovil` está vacía). 
- [ ] Los nuevos DTO/mappers/servicios siguen la convención actual y los controladores regresan `ApiResponseDTO`.

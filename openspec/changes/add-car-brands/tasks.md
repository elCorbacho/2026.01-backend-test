# Tasks: Catálogo de Marcas de Automóvil

## Phase 1: Foundation / Infrastructure
- [x] 1.1 Crear `MarcaAutomovil.java` en `src/main/java/ipss/web2/examen/models/` con campos `id`, `nombre`, `paisOrigen`, `descripcion`, `active`, `createdAt` y `updatedAt`, con Lombok (`@Builder`, `@Getter/@Setter`, constructores) y `@EntityListeners(AuditingEntityListener.class)`.
- [x] 1.2 Crear `MarcaAutomovilRepository.java` en `src/main/java/ipss/web2/examen/repositories/` que extienda `JpaRepository<MarcaAutomovil, Long>` y declare `List<MarcaAutomovil> findByActiveTrueOrderByNombreAsc()`.
- [x] 1.3 Crear `MarcaAutomovilResponseDTO.java` en `src/main/java/ipss/web2/examen/dtos/` con los campos de respuesta (`id`, `nombre`, `paisOrigen`, `descripcion`, `createdAt`, `updatedAt`) y Lombok.
- [x] 1.4 Crear `MarcaAutomovilMapper.java` en `src/main/java/ipss/web2/examen/mappers/` para convertir la entidad a DTO.

## Phase 2: Core Implementation
- [x] 2.1 Implementar `MarcaAutomovilService.java` en `src/main/java/ipss/web2/examen/services/` con un método `obtenerMarcasActivas()` anotado `@Transactional(readOnly = true)` que use el repositorio y el mapper para devolver la lista ordenada.
- [x] 2.2 Crear `MarcaAutomovilController.java` en `src/main/java/ipss/web2/examen/controllers/api/` con `GET /api/marcas` que llama al servicio y encapsula el resultado en `ApiResponseDTO.ok(..., "Marcas recuperadas").

## Phase 3: Integration / Wiring
- [x] 3.1 Modificar `DataInitializer.java` para inyectar `MarcaAutomovilRepository` y agregar el método `poblarMarcasAutomovil()` que verifica `if (marcaRepository.count() == 0)` y recorre la lista de 30 marcas conocidas guardando cada entidad con `active=true`.
- [x] 3.2 Asegurar que `DataInitializer.run(...)` invoque `poblarMarcasAutomovil()` después de los seeds dependientes de otras tablas (por ejemplo, al final tras los repositorios actuales).

## Phase 4: Testing
- [x] 4.1 Crear un test unitario (puede ser en `services/MarcaAutomovilServiceTest.java`) que simule el repositorio y el mapper para validar que `obtenerMarcasActivas()` retorna solo activos ordenados.
- [x] 4.2 Añadir una prueba de integración que arranque el contexto (`@SpringBootTest`) o use `@WebMvcTest` + `@AutoConfigureMockMvc` para ejecutar `GET /api/marcas` y verificar que la respuesta contiene 30 marcas cuando se corre el seed y que una segunda invocación no duplica registros.
- [x] 4.3 Verificar que el seed respeta `TARGET_SEED_COUNT` (p. ej., contar filas tras ejecutar `DataInitializer` o reusar `DataInitializerPopulationIntegrationTest` para incluir marca automovil).

## Phase 5: Cleanup / Documentation
- [x] 5.1 Actualizar la documentación (`readme.md` o archivos relevantes) si existe una sección que liste los endpoints disponibles para incluir `/api/marcas` y su descripción.
- [x] 5.2 Revisar imports/lombok/formatter y asegurarse de que el nuevo controlador y servicios tienen comentarios en español según convención.

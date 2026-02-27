# Tasks: Listado Olimpiadas

## Phase 1: Foundation

- [x] 1.1 Crear entidad `ListadoOlimpiadas` en `src/main/java/ipss/web2/examen/models/ListadoOlimpiadas.java` con auditoria y `is_active`.
- [x] 1.2 Crear repositorio `ListadoOlimpiadasRepository` en `src/main/java/ipss/web2/examen/repositories/ListadoOlimpiadasRepository.java` con `findByActiveTrue()`.
- [x] 1.3 Crear DTOs `ListadoOlimpiadasRequestDTO` y `ListadoOlimpiadasResponseDTO` en `src/main/java/ipss/web2/examen/dtos/` con validaciones.
- [x] 1.4 Crear mapper manual `ListadoOlimpiadasMapper` en `src/main/java/ipss/web2/examen/mappers/ListadoOlimpiadasMapper.java`.

## Phase 2: Core Implementation

- [x] 2.1 Implementar servicio `ListadoOlimpiadasService` en `src/main/java/ipss/web2/examen/services/ListadoOlimpiadasService.java` con CRUD, soft delete y manejo de `ResourceNotFoundException`.
- [x] 2.2 Definir metodo `obtenerListadoPorId` que valide recurso activo y escenarios de error.
- [x] 2.3 Definir metodo `eliminarListado` que marque `active=false` y persista el cambio.

## Phase 3: Integration / Wiring

- [x] 3.1 Crear controlador `ListadoOlimpiadasController` en `src/main/java/ipss/web2/examen/controllers/api/ListadoOlimpiadasController.java` con endpoints REST.
- [x] 3.2 Envolver todas las respuestas en `ApiResponseDTO<T>` y usar `@Valid` en POST/PUT.
- [x] 3.3 Agregar poblacion inicial en `src/main/java/ipss/web2/examen/config/DataInitializer.java` verificando `count()` antes de insertar.

## Phase 4: Testing / Verification

- [x] 4.1 Unit: probar `ListadoOlimpiadasMapper` en `src/test/java/...` para `toEntity` y `toResponseDTO`.
- [x] 4.2 Unit: probar `ListadoOlimpiadasService` para soft delete y not found (spec: eliminar listado, obtener por ID inexistente).
- [x] 4.3 Integration: MockMvc para escenarios de creacion exitosa e invalida (spec: Creacion exitosa / datos invalidos).
- [x] 4.4 Integration: MockMvc para listado vacio y obtener por ID inexistente (spec: Listado sin resultados / Obtener por ID inexistente).

# Tasks: Listado de Presidentes de Rusia (API + modelo + seed)

## Phase 1: Foundation (Modelo, DTOs, Repo)

- [x] 1.1 Crear entidad ListadoPresidenteRusia en src/main/java/ipss/web2/examen/models/ListadoPresidenteRusia.java con auditoría, soft delete y columnas principales.
- [x] 1.2 Crear DTOs en src/main/java/ipss/web2/examen/dtos/:
  - ListadoPresidenteRusiaRequestDTO (campos requeridos)
  - ListadoPresidenteRusiaResponseDTO (campos de salida)
- [x] 1.3 Crear repositorio ListadoPresidenteRusiaRepository en src/main/java/ipss/web2/examen/repositories/ con findByActiveTrue().
- [x] 1.4 Crear mapper manual ListadoPresidenteRusiaMapper en src/main/java/ipss/web2/examen/mappers/ para convertir entidad a response DTO.

## Phase 2: Core Implementation (Service + Seed)

- [x] 2.1 Crear servicio ListadoPresidenteRusiaService en src/main/java/ipss/web2/examen/services/ con:
  - método obtenerPresidentesRusia() (lista activos)
  - método crearPresidenteRusia() (creación con request DTO)
- [x] 2.2 Implementar data initializer ListadoPresidenteRusiaDataInitializer en src/main/java/ipss/web2/examen/config/ que inserte datos iniciales si count()==0.

## Phase 3: Integration / Wiring (Controller)

- [x] 3.1 Crear controlador REST ListadoPresidenteRusiaController en src/main/java/ipss/web2/examen/controllers/api/:
  - GET /api/listado-presidente-rusia retorna lista con ApiResponseDTO
  - POST /api/listado-presidente-rusia crea un registro y retorna 201

## Phase 4: Testing / Verification

- [x] 4.1 Crear pruebas de mapper en src/test/java/ipss/web2/examen/mappers/ListadoPresidenteRusiaMapperTest.java (similar a PresidenteChileMapperTest).
- [x] 4.2 Crear pruebas de servicio en src/test/java/ipss/web2/examen/services/ListadoPresidenteRusiaServiceTest.java (listar y crear).
- [x] 4.3 Crear pruebas WebMvc en src/test/java/ipss/web2/examen/controllers/api/ListadoPresidenteRusiaControllerWebMvcTest.java (GET + POST).
- [ ] 4.4 (Opcional) Prueba de integración en src/test/java/ipss/web2/examen/integration/ListadoPresidenteRusiaControllerIntegrationTest.java.

## Phase 5: Cleanup / Docs

- [x] 5.1 Validar consistencia de nombres, comentarios en español y uso de ApiResponseDTO.

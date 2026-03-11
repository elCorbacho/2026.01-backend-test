# Implementation Plan: Exoplanetas API

## Overview

This implementation plan breaks down the Exoplanetas API feature into discrete coding tasks following the established Spring Boot 3 architecture. The implementation follows a bottom-up approach: Entity → Repository → DTOs → Mapper → Service → Controller → Data Initializer, with testing integrated at each layer. All tasks build incrementally, ensuring no orphaned code and early validation of core functionality.

## Tasks

- [ ] 1. Set up Exoplaneta entity and repository layer
  - [ ] 1.1 Create Exoplaneta JPA entity
    - Create `src/main/java/ipss/web2/examen/models/Exoplaneta.java`
    - Define all fields: id, nombre, tipo, distanciaAnosLuz, masaRelativaJupiter, descubiertoAnio, active, createdAt, updatedAt
    - Add JPA annotations: @Entity, @Table(name = "exoplaneta"), @EntityListeners(AuditingEntityListener.class)
    - Add Lombok annotations: @Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor
    - Define @Index annotations on nombre, active, and descubierto_anio columns
    - Use snake_case for all @Column name mappings
    - Set active field default to true
    - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 1.10_

  - [ ]* 1.2 Write unit tests for Exoplaneta entity
    - Create `src/test/java/ipss/web2/examen/models/ExoplanetaTest.java`
    - Test builder pattern creates valid entities
    - Test default active value is true
    - Test Lombok-generated methods
    - _Requirements: 1.1, 1.8_

  - [ ] 1.3 Create ExoplanetaRepository interface
    - Create `src/main/java/ipss/web2/examen/repositories/ExoplanetaRepository.java`
    - Extend JpaRepository<Exoplaneta, Long>
    - Add method: Optional<Exoplaneta> findByIdAndActiveTrue(Long id)
    - Add method: Page<Exoplaneta> findByActiveTrueOrderByNombreAsc(Pageable pageable)
    - _Requirements: 2.1, 2.2, 2.3, 2.4_

  - [ ]* 1.4 Write integration tests for ExoplanetaRepository
    - Create `src/test/java/ipss/web2/examen/repositories/ExoplanetaRepositoryTest.java`
    - Use @DataJpaTest annotation
    - Test findByIdAndActiveTrue returns active exoplaneta
    - Test findByIdAndActiveTrue returns empty for inactive exoplaneta
    - Test findByActiveTrueOrderByNombreAsc returns only active exoplanetas sorted by nombre
    - Test pagination works correctly
    - _Requirements: 2.2, 2.3, 2.4_

- [ ] 2. Implement DTOs and mapper layer
  - [ ] 2.1 Create ExoplanetaRequestDTO
    - Create `src/main/java/ipss/web2/examen/dtos/ExoplanetaRequestDTO.java`
    - Define as record or class with fields: nombre, tipo, distanciaAnosLuz, masaRelativaJupiter, descubiertoAnio
    - Add Bean Validation annotations: @NotBlank on nombre and tipo, @NotNull and @Min(0) on distanciaAnosLuz and masaRelativaJupiter, @NotNull @Min(1990) @Max(2100) on descubiertoAnio
    - Add @Schema annotations for OpenAPI documentation
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 3.6, 9.4_

  - [ ] 2.2 Create ExoplanetaResponseDTO
    - Create `src/main/java/ipss/web2/examen/dtos/ExoplanetaResponseDTO.java`
    - Define with Lombok @Data and @Builder
    - Include fields: id, nombre, tipo, distanciaAnosLuz, masaRelativaJupiter, descubiertoAnio, createdAt, updatedAt
    - Do NOT include active field
    - _Requirements: 3.7, 3.8_

  - [ ] 2.3 Create ExoplanetaPageResponseDTO
    - Create `src/main/java/ipss/web2/examen/dtos/ExoplanetaPageResponseDTO.java`
    - Define as record with fields: List<ExoplanetaResponseDTO> content, int page, int size, long totalElements, int totalPages
    - _Requirements: 5.7_

  - [ ] 2.4 Create ExoplanetaMapper component
    - Create `src/main/java/ipss/web2/examen/mappers/ExoplanetaMapper.java`
    - Annotate with @Component
    - Implement toResponseDTO(Exoplaneta entity) method with null-safe mapping
    - Implement toEntity(ExoplanetaRequestDTO requestDTO) method, setting active=true
    - Use manual field mapping, no mapping frameworks
    - _Requirements: 4.1, 4.2, 4.3, 4.4_

  - [ ]* 2.5 Write unit tests for ExoplanetaMapper
    - Create `src/test/java/ipss/web2/examen/mappers/ExoplanetaMapperTest.java`
    - Test toResponseDTO maps all fields correctly
    - Test toResponseDTO handles null input
    - Test toEntity maps all fields and sets active=true
    - _Requirements: 4.1, 4.2_

- [ ] 3. Implement service layer with business logic
  - [ ] 3.1 Create ExoplanetaService class
    - Create `src/main/java/ipss/web2/examen/services/ExoplanetaService.java`
    - Annotate with @Service, @RequiredArgsConstructor, @Transactional(readOnly = true)
    - Inject ExoplanetaRepository and ExoplanetaMapper via constructor
    - Implement obtenerTodos(Integer page, Integer size) method
    - Validate page >= 0, throw InvalidOperationException("INVALID_PAGE_NUMBER") if invalid
    - Validate 1 <= size <= 100, throw InvalidOperationException("INVALID_PAGE_SIZE") if invalid
    - Default page=0, size=10 if null
    - Query repository with Pageable sorted by nombre ascending
    - Map entities to ResponseDTOs and return ExoplanetaPageResponseDTO
    - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5, 5.7, 5.8, 11.1_

  - [ ] 3.2 Add obtenerPorId method to ExoplanetaService
    - Implement obtenerPorId(Long id) method with @Transactional(readOnly = true)
    - Query repository.findByIdAndActiveTrue(id)
    - Throw ResourceNotFoundException("Exoplaneta", "id", id) if not found
    - Map entity to ResponseDTO and return
    - _Requirements: 6.1, 6.2, 6.4_

  - [ ] 3.3 Add crear method to ExoplanetaService
    - Implement crear(ExoplanetaRequestDTO requestDTO) method with @Transactional(readOnly = false)
    - Convert requestDTO to entity via mapper (sets active=true)
    - Save entity via repository
    - Map saved entity to ResponseDTO and return
    - _Requirements: 7.1, 7.2, 7.4, 7.5, 7.7, 11.2_

  - [ ]* 3.4 Write unit tests for ExoplanetaService
    - Create `src/test/java/ipss/web2/examen/services/ExoplanetaServiceTest.java`
    - Use @ExtendWith(MockitoExtension.class)
    - Mock ExoplanetaRepository and inject real ExoplanetaMapper
    - Test obtenerTodos with valid pagination returns results
    - Test obtenerTodos with null parameters uses defaults
    - Test obtenerTodos with negative page throws InvalidOperationException
    - Test obtenerTodos with invalid size throws InvalidOperationException
    - Test obtenerPorId with valid ID returns exoplaneta
    - Test obtenerPorId with invalid ID throws ResourceNotFoundException
    - Test crear with valid DTO creates and returns exoplaneta
    - _Requirements: 5.2, 5.3, 5.4, 5.5, 6.2, 7.1_

  - [ ]* 3.5 Write property test for blank nombre validation
    - **Property 1: Blank nombre validation rejection**
    - **Validates: Requirements 3.2**
    - Add to ExoplanetaServiceTest or create separate property test class
    - Use jqwik @Property annotation with @ForAll generating blank strings
    - Verify validation error is thrown for blank nombre
    - Minimum 100 iterations

  - [ ]* 3.6 Write property test for blank tipo validation
    - **Property 2: Blank tipo validation rejection**
    - **Validates: Requirements 3.3**
    - Use jqwik @Property with @ForAll generating blank strings
    - Verify validation error is thrown for blank tipo
    - Minimum 100 iterations

  - [ ]* 3.7 Write property test for invalid distancia validation
    - **Property 3: Invalid distancia validation rejection**
    - **Validates: Requirements 3.4**
    - Use jqwik @Property with @ForAll generating null or negative doubles
    - Verify validation error is thrown
    - Minimum 100 iterations

  - [ ]* 3.8 Write property test for invalid masa validation
    - **Property 4: Invalid masa validation rejection**
    - **Validates: Requirements 3.5**
    - Use jqwik @Property with @ForAll generating null or negative doubles
    - Verify validation error is thrown
    - Minimum 100 iterations

  - [ ]* 3.9 Write property test for invalid year validation
    - **Property 5: Invalid year validation rejection**
    - **Validates: Requirements 3.6**
    - Use jqwik @Property with @ForAll generating invalid years (null, <1990, >2100)
    - Verify validation error is thrown
    - Minimum 100 iterations

  - [ ]* 3.10 Write property test for active-only filtering
    - **Property 6: Active-only filtering**
    - **Validates: Requirements 5.1**
    - Use jqwik @Property with @ForAll generating mixed active/inactive exoplanetas
    - Verify obtenerTodos returns only active=true records
    - Minimum 100 iterations

  - [ ]* 3.11 Write property test for page parameter validation
    - **Property 7: Page parameter validation**
    - **Validates: Requirements 5.2, 5.4**
    - Use jqwik @Property with @ForAll @Negative int page
    - Verify InvalidOperationException with error code "INVALID_PAGE_NUMBER" is thrown
    - Minimum 100 iterations

  - [ ]* 3.12 Write property test for size parameter validation
    - **Property 8: Size parameter validation**
    - **Validates: Requirements 5.3, 5.5**
    - Use jqwik @Property with @ForAll int size constrained to <1 or >100
    - Verify InvalidOperationException with error code "INVALID_PAGE_SIZE" is thrown
    - Minimum 100 iterations

  - [ ]* 3.13 Write property test for alphabetical sorting
    - **Property 9: Alphabetical sorting by nombre**
    - **Validates: Requirements 5.8**
    - Use jqwik @Property with @ForAll generating list of exoplanetas
    - Verify returned list is sorted by nombre ascending
    - Minimum 100 iterations

  - [ ]* 3.14 Write property test for retrieval by ID round-trip
    - **Property 10: Retrieval by ID round-trip**
    - **Validates: Requirements 6.1**
    - Use jqwik @Property with @ForAll generating valid exoplaneta
    - Create exoplaneta, retrieve by ID, verify all fields match
    - Minimum 100 iterations

  - [ ]* 3.15 Write property test for not found exception
    - **Property 11: Not found exception for invalid ID**
    - **Validates: Requirements 6.2**
    - Use jqwik @Property with @ForAll generating non-existent IDs
    - Verify ResourceNotFoundException is thrown with correct parameters
    - Minimum 100 iterations

  - [ ]* 3.16 Write property test for create operation round-trip
    - **Property 12: Create operation round-trip**
    - **Validates: Requirements 7.1, 7.7**
    - Use jqwik @Property with @ForAll generating valid ExoplanetaRequestDTO
    - Create exoplaneta, retrieve by returned ID, verify all fields match
    - Minimum 100 iterations

  - [ ]* 3.17 Write property test for new exoplanetas are active
    - **Property 13: New exoplanetas are active**
    - **Validates: Requirements 7.4**
    - Use jqwik @Property with @ForAll generating valid ExoplanetaRequestDTO
    - Create exoplaneta, verify persisted entity has active=true
    - Minimum 100 iterations

- [ ] 4. Checkpoint - Ensure service layer tests pass
  - Ensure all tests pass, ask the user if questions arise.

- [ ] 5. Implement controller layer with REST endpoints
  - [ ] 5.1 Create ExoplanetaController class
    - Create `src/main/java/ipss/web2/examen/controllers/api/ExoplanetaController.java`
    - Annotate with @RestController, @RequestMapping("/api/exoplanetas"), @RequiredArgsConstructor
    - Inject ExoplanetaService via constructor
    - Add OpenAPI @Tag annotation for documentation
    - _Requirements: 12.1, 12.4, 12.5_

  - [ ] 5.2 Add GET /api/exoplanetas endpoint
    - Implement obtenerTodos(@RequestParam Integer page, @RequestParam Integer size) method
    - Mark parameters as required=false
    - Call service.obtenerTodos(page, size)
    - Return ApiResponseDTO.ok(result, "Exoplanetas obtenidos exitosamente")
    - Add @Operation and @ApiResponse annotations for OpenAPI
    - _Requirements: 5.1, 5.6, 9.1, 9.2, 12.2, 12.3_

  - [ ] 5.3 Add GET /api/exoplanetas/{id} endpoint
    - Implement obtenerPorId(@PathVariable Long id) method
    - Call service.obtenerPorId(id)
    - Return ApiResponseDTO.ok(result, "Exoplaneta obtenido exitosamente")
    - Add @Operation, @ApiResponse, and @Parameter annotations for OpenAPI
    - _Requirements: 6.1, 6.3, 9.1, 9.2, 9.3, 12.2, 12.3_

  - [ ] 5.4 Add POST /api/exoplanetas endpoint
    - Implement crear(@Valid @RequestBody ExoplanetaRequestDTO requestDTO) method
    - Call service.crear(requestDTO)
    - Return ApiResponseDTO.created(result, "Exoplaneta creado exitosamente")
    - Add @Operation and @ApiResponse annotations for OpenAPI
    - _Requirements: 7.1, 7.3, 7.6, 9.1, 9.2, 12.2, 12.3_

  - [ ]* 5.5 Write unit tests for ExoplanetaController
    - Create `src/test/java/ipss/web2/examen/controllers/api/ExoplanetaControllerTest.java`
    - Use @WebMvcTest(ExoplanetaController.class)
    - Import GlobalExceptionHandler for error path testing
    - Mock ExoplanetaService
    - Test GET /api/exoplanetas returns 200 with paginated results
    - Test GET /api/exoplanetas with invalid page returns 400
    - Test GET /api/exoplanetas with invalid size returns 400
    - Test GET /api/exoplanetas/{id} returns 200 with exoplaneta
    - Test GET /api/exoplanetas/{id} with invalid ID returns 404
    - Test POST /api/exoplanetas with valid DTO returns 201
    - Test POST /api/exoplanetas with invalid DTO returns 400 with validation errors
    - Verify all responses wrapped in ApiResponseDTO
    - _Requirements: 5.6, 6.3, 7.3, 7.6, 10.1, 10.2, 10.3_

  - [ ]* 5.6 Write property test for no stack trace exposure
    - **Property 14: No stack trace exposure**
    - **Validates: Requirements 10.5**
    - Use jqwik @Property with @ForAll generating various exception scenarios
    - Verify HTTP response body does not contain Java stack traces
    - Minimum 100 iterations

- [ ] 6. Implement data initialization
  - [ ] 6.1 Create ExoplanetaDataInitializer component
    - Create `src/main/java/ipss/web2/examen/config/ExoplanetaDataInitializer.java`
    - Annotate with @Component, @RequiredArgsConstructor
    - Implement CommandLineRunner interface
    - Inject ExoplanetaRepository via constructor
    - In run() method, check if exoplaneta table is empty (count() == 0)
    - If empty, create at least 5 diverse exoplanet records with varied types, distances, masses, and years
    - Include types: "Gas gigante", "Súper Tierra", "Terrestre", "Neptuniano"
    - Set active=true for all records
    - Log count of inserted records at INFO level
    - _Requirements: 8.1, 8.2, 8.3, 8.4, 8.5, 8.7_

  - [ ]* 6.2 Write integration tests for ExoplanetaDataInitializer
    - Create `src/test/java/ipss/web2/examen/config/ExoplanetaDataInitializerTest.java`
    - Use @SpringBootTest annotation
    - Test initializer creates at least 5 exoplanetas when database is empty
    - Test all initialized exoplanetas have active=true
    - Test initialized exoplanetas include diverse types
    - Test idempotent execution (does not duplicate on subsequent runs)
    - _Requirements: 8.2, 8.3, 8.4, 8.5_

  - [ ]* 6.3 Write property test for transaction rollback on failure
    - **Property 15: Transaction rollback on failure**
    - **Validates: Requirements 11.3**
    - Use jqwik @Property with @ForAll generating scenarios that cause transaction failures
    - Verify no exoplaneta record is persisted when transaction fails
    - Minimum 100 iterations

- [ ] 7. Integration testing and final wiring
  - [ ]* 7.1 Write end-to-end integration tests
    - Create `src/test/java/ipss/web2/examen/integration/ExoplanetaIntegrationTest.java`
    - Use @SpringBootTest(webEnvironment = RANDOM_PORT)
    - Use @BeforeEach to reset database via repository.deleteAll()
    - Test full cycle: create exoplaneta via POST, retrieve via GET by ID, verify data matches
    - Test pagination works across multiple pages
    - Test soft delete: inactive exoplanetas not returned
    - Test complete error handling flow for validation errors, not found, and invalid parameters
    - _Requirements: 5.1, 6.1, 7.1, 10.1, 10.2, 10.3, 11.3_

- [ ] 8. Final checkpoint - Ensure all tests pass
  - Ensure all tests pass, ask the user if questions arise.

## Notes

- Tasks marked with `*` are optional and can be skipped for faster MVP
- Each task references specific requirements for traceability
- Property-based tests use jqwik library with minimum 100 iterations
- All property tests are marked as optional sub-tasks
- Checkpoints ensure incremental validation at reasonable breaks
- Implementation follows strict layered architecture: Controller → Service → Repository → Entity
- All responses use ApiResponseDTO envelope following project conventions
- Soft delete pattern: never physically delete records, use active=false
- Transaction management: readOnly=true for reads, readOnly=false for writes

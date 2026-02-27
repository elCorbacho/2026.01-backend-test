# Tasks: Regiones de Chile

## Phase 1: Foundation / Infrastructure
- [x] 1.1 Create `src/main/java/ipss/web2/examen/models/RegionChile.java` with `@Entity`, auditing, fields `id`, `codigo`, `nombre`, `is_active`, `createdAt`, `updatedAt`, and default `active = true` to match existing soft-delete conventions.
- [x] 1.2 Create `src/main/java/ipss/web2/examen/dtos/RegionResponseDTO.java` containing `id`, `codigo`, `nombre` and Lombok annotations (`@Data`, `@Builder`, constructors) so the controller can return a read-only view.
- [x] 1.3 Create `src/main/java/ipss/web2/examen/mappers/RegionMapper.java` as a Spring `@Component` that maps `RegionChile` to `RegionResponseDTO` (and optionally back), reusing the manual mapper style used elsewhere.

## Phase 2: Core Implementation
- [x] 2.1 Create `src/main/java/ipss/web2/examen/repositories/RegionRepository.java` extending `JpaRepository<RegionChile, Long>` with `List<RegionChile> findByActiveTrueOrderByCodigoAsc()` to fulfill the "listado ordenado" requirement.
- [x] 2.2 Create `src/main/java/ipss/web2/examen/services/RegionService.java` marked `@Service @RequiredArgsConstructor` with `@Transactional(readOnly = true)` exposing `List<RegionResponseDTO> obtenerRegionesActivas()` that calls the repository/mapping chain.

## Phase 3: Integration / Wiring
- [x] 3.1 Create `src/main/java/ipss/web2/examen/controllers/api/RegionController.java` with `@RestController @RequestMapping("/api/regiones")` and a `GET` handler returning `ResponseEntity<ApiResponseDTO<List<RegionResponseDTO>>>` wrapping the service output.`
- [x] 3.2 Modify `src/main/java/ipss/web2/examen/config/DataInitializer.java` to inject `RegionRepository` (and/or `RegionService`) and populate the `region` table with the canonical list when `regionRepository.count()` is zero, ensuring `active = true` as described in the proposal.

## Phase 4: Testing / Verification
- [x] 4.1 Write unit tests for `RegionService` that mock `RegionRepository` to verify it returns only `active` regions sorted by `codigo` (covers "Listado de regiones activas" and "No hay regiones activas" escenarios).
- [x] 4.2 Write a unit test for `RegionController` (using `@WebMvcTest` or plain unit test with mock service) to ensure `GET /api/regiones` wraps results in `ApiResponseDTO` with `success = true` and `message`/`errorCode` null (covers the "Respuesta con datos" and "Respuesta vacía" requirements).
- [x] 4.3 Add an integration test (`@SpringBootTest`/`@WebMvcTest` + in-memory DB) that inserts active/inactive `RegionChile` records and calls `GET /api/regiones`, asserting the returned list excludes inactive records (covering "Regiones inactivas ignoradas" edge cases) and responds `200`.

## Phase 5: Cleanup / Documentation
- [x] 5.1 Document the new endpoint in any relevant README or API list, mentioning that `GET /api/regiones` returns only active regions wrapped in `ApiResponseDTO`.

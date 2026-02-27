# Tasks: Poblar Base de Datos con 30 Registros por Entidad

## Phase 1: Foundation and Initializer Wiring

- [x] 1.1 Update imports and constructor dependencies in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to include repositories for `GanadorGuinness`, `GanadorAlbum`, `GanadorPremioAlbum`, `DemoWidget`, `PaisDistribucion`, `TestModel`, `EmpresaInsumos`, and `TiendaLamina`.
- [x] 1.2 Extend `run()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to execute the 8 new population methods with `repository.count() == 0` guards and maintain dependency order (GanadorAlbum after Album exists).
- [x] 1.3 Define static seed datasets/constants in `src/main/java/ipss/web2/examen/config/DataInitializer.java` for each new entity so each method can insert exactly 30 records with deterministic values.

## Phase 2: Implement Population Methods for Independent Entities

- [x] 2.1 Add `poblarGanadorGuinness()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to create 30 valid records (nombre, categoria, record, anio), ensuring required fields and `active=true`.
- [x] 2.2 Add `poblarDemoWidget()` and `poblarTestModel()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to create 30 records each with valid field lengths and `active=true`.
- [x] 2.3 Add `poblarPaisDistribucion()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to create 30 records with valid `nombre` and optional `codigoIso`/`descripcion`, always `active=true`.
- [x] 2.4 Add `poblarEmpresaInsumos()` and `poblarTiendaLamina()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to create 30 records each with required fields populated, optional fields handled, and `active=true`.
- [x] 2.5 Add `poblarGanadorPremioAlbum()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to create 30 records with valid artista/album/premio/anio/genero constraints and `active=true`.

## Phase 3: Implement Dependent Entity and Idempotency Guarantees

- [x] 3.1 Add `poblarGanadorAlbum()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to load existing `Album` rows, assign valid album references, and create exactly 30 `GanadorAlbum` records.
- [x] 3.2 Add defensive checks in `poblarGanadorAlbum()` in `src/main/java/ipss/web2/examen/config/DataInitializer.java` to fail fast or skip with clear logging when there are no albums, preventing invalid FK inserts.
- [x] 3.3 Verify each of the 8 population methods in `src/main/java/ipss/web2/examen/config/DataInitializer.java` is idempotent (no inserts when count > 0) and logs inserted totals.

## Phase 4: Automated Verification Against Specs

- [x] 4.1 Create `src/test/java/ipss/web2/examen/config/DataInitializerPopulationIntegrationTest.java` to assert each target repository count is exactly 30 after startup (GanadorGuinness, GanadorAlbum, GanadorPremioAlbum, DemoWidget, PaisDistribucion, TestModel, EmpresaInsumos, TiendaLamina).
- [x] 4.2 Add assertions in `src/test/java/ipss/web2/examen/config/DataInitializerPopulationIntegrationTest.java` that all seeded records in the 8 entities have `active=true` by default.
- [x] 4.3 Add FK integrity assertions in `src/test/java/ipss/web2/examen/config/DataInitializerPopulationIntegrationTest.java` to validate every `GanadorAlbum` has non-null `album` and that referenced album IDs exist.
- [x] 4.4 Add idempotency test in `src/test/java/ipss/web2/examen/config/DataInitializerPopulationIntegrationTest.java` to execute initializer twice and confirm counts remain 30 per entity without duplicates.

## Phase 5: Execution Verification and Change Validation

- [x] 5.1 Run `./mvnw.cmd test` from repository root and ensure `DataInitializerPopulationIntegrationTest` passes with no FK or constraint errors.
- [x] 5.2 Run the application once and validate startup logs plus repository/database counts for the 8 entities remain at 30 and consistent with spec requirements.

## Phase 6: Verify Gap Closure

- [x] 6.1 Expand `src/test/java/ipss/web2/examen/config/DataInitializerPopulationIntegrationTest.java` with scenario-focused assertions for required fields, max lengths, optional-null handling, audit timestamps, and seed diversity across all 8 entities.
- [x] 6.2 Add explicit persistence tests for edge scenarios from specs: special characters, optional null fields, ISO code length variants, and LocalDate preservation.
- [x] 6.3 Re-run `./mvnw.cmd clean test` and confirm green suite after added coverage.

## 1. Domain + persistence

- [x] 1.1 Create the `GanadorAlbum` entity (fields: `id`, `album`, `artista`, `premio`, `anio`, `active`, `createdAt`, `updatedAt`) and the `ganador_album` table mapping.
- [x] 1.2 Add `GanadorAlbumRepository` with `List<GanadorAlbum> findByAlbumIdAndActiveTrue(Long albumId)` and any necessary query hints.

## 2. Service + DTO mapping

- [x] 2.1 Implement `GanadorAlbumDTO`, `GanadorAlbumMapper`, and `GanadorAlbumService.obtenerGanadoresPorAlbum(Long albumId)` that loads winners, maps them, and throws `ResourceNotFoundException` if the album is missing.
- [x] 2.2 Ensure the service verifies album existence (e.g., via `AlbumRepository`) and uses `ApiResponseDTO` helpers if available.

## 3. Controller + API

- [x] 3.1 Add the GET `/api/albumes/{albumId}/ganadores` method (in `AlbumController` or a new controller) that calls the service and wraps the list in `ApiResponseDTO` with `success=true`.
- [x] 3.2 Document the endpoint in OpenAPI comments (controller-level annotations) and ensure it returns 404 when the album is not found.

## 4. Validation + packaging

- [x] 4.1 Run and validate `./mvnw.cmd clean package` (or relevant tests) to confirm the new beans compile and the endpoint is discoverable.
- [x] 4.2 Update any README or docs if needed (navigation, spec) to mention the new winners endpoint.

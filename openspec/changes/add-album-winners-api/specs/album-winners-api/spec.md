## ADDED Requirements

### Requirement: Album winners persist with album association
The system SHALL persist each album winner as an entity linked to an existing album (via `album_id` FK) including artist, prize, and year, plus soft-delete auditing fields.

#### Scenario: Winner record stored
- **WHEN** a `GanadorAlbum` is saved for album 42 with `active=true`
- **THEN** the database contains a row in `ganador_album` with `album_id=42`, the provided artist/premio/anio, and the record is marked active

### Requirement: GET /api/albums/{albumId}/ganadores returns latest winners
The API SHALL provide `GET /api/albums/{albumId}/ganadores` that returns an `ApiResponseDTO<List<GanadorAlbumDTO>>` with `success=true` and the list of active winners filtered by album.

#### Scenario: Fetch winners for album
- **WHEN** a client requests `/api/albums/42/ganadores` and album 42 exists with 3 active winners
- **THEN** the response status is 200, `success=true`, and `data` contains exactly those three winners ordered by `anio` descending

### Requirement: Missing album returns resource not found
If the requested album does not exist, the endpoint SHALL respond with 404 and `errorCode=RESOURCE_NOT_FOUND`.

#### Scenario: Album not registered
- **WHEN** a client requests `/api/albums/9999/ganadores` but album 9999 does not exist
- **THEN** the response status is 404 and `errorCode=RESOURCE_NOT_FOUND` and `message` explains the album was not found

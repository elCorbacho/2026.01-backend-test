## Why

The current API exposes album and sticker management but lacks an endpoint to retrieve the catalog of winners associated with each album, which is needed for reporting and highlighting achievements on the frontend.

## What Changes

- Create a `GanadorAlbum` model with fields for album reference, artist, prize, and year.
- Add a Spring Data repository to query winners by album.
- Implement a controller and service layer with a GET `/api/albums/{albumId}/ganadores` endpoint that returns the list of winners in `ApiResponseDTO` wrappers.
- Provide DTOs and mappers so the new endpoint returns only the necessary winner details (nombre, premio, year).

## Capabilities

### New Capabilities
- `album-winners-api`: Adds the ability to query album winners via the REST API, including the new entity, repository, service, controller, and DTO mappings.

### Modified Capabilities
- Ninguna (sin cambios de especificación)

## Impact

- Nueva entidad `GanadorAlbum` y su repositorio Spring Data JPA.
- Servicio e infraestructura para mapear desde la entidad al DTO.
- Controlador REST dentro de `controllers/api/AlbumController` o un nuevo `GanadorAlbumController` para exponer `/api/albums/{albumId}/ganadores`.
- Documentación de respuesta y pruebas de integración (si existen) para cubrir el nuevo endpoint.

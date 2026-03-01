package ipss.web2.examen.dtos;

import java.util.List;

// DTO para exponer respuesta paginada de albums
public record AlbumPageResponseDTO(
        List<AlbumResponseDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

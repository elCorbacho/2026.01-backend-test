package ipss.web2.examen.dtos;

import java.util.List;

// DTO para exponer respuesta paginada de exoplanetas
public record ExoplanetaPageResponseDTO(
        List<ExoplanetaResponseDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

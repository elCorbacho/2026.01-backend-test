package ipss.web2.examen.dtos;

import java.util.List;

public record TipoAvePageResponseDTO(
        List<TipoAveResponseDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

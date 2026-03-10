package ipss.web2.examen.dtos;

import java.util.List;

public record PoblacionAvePageResponseDTO(
        List<PoblacionAveResponseDTO> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}

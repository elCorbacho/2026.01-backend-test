package ipss.web2.examen.dtos;

import java.time.LocalDateTime;

public record TipoInsectoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{
}

package ipss.web2.examen.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;


// DTO de respuesta para el catálogo de láminas
public record LaminaCatalogoResponseDTO(
    Long id,
    String nombre,
    String imagen,
    LocalDate fechaLanzamiento,
    String tipoLamina,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Boolean active
) {}

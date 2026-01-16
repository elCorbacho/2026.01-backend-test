package ipss.web2.examen.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

// DTO de solicitud para el cargue masivo de láminas
public record LaminaCargueMasivoRequestDTO(
    @NotNull(message = "El ID del álbum es obligatorio")
    Long albumId,
    
    @NotEmpty(message = "Debe proporcionar al menos una lámina")
    @Valid
    List<LaminaRequestDTO> laminas
) {}

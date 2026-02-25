package ipss.web2.examen.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancionRequestDTO {

    @NotBlank(message = "El título de la canción es obligatorio")
    @Size(min = 1, max = 150, message = "El título debe tener entre 1 y 150 caracteres")
    private String titulo;

    @NotBlank(message = "El artista es obligatorio")
    @Size(min = 1, max = 100, message = "El artista debe tener entre 1 y 100 caracteres")
    private String artista;

    @Min(value = 1, message = "La duración debe ser mayor a 0 segundos")
    @Max(value = 86400, message = "La duración no puede exceder 24 horas")
    private Integer duracion;

    @Size(max = 80, message = "El género no puede exceder 80 caracteres")
    private String genero;
}

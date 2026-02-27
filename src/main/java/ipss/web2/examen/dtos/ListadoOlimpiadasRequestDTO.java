package ipss.web2.examen.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de solicitud para crear o actualizar un listado de olimpiadas
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListadoOlimpiadasRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre debe tener entre 2 y 150 caracteres")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 120, message = "La ciudad debe tener entre 2 y 120 caracteres")
    private String ciudad;

    @NotBlank(message = "El país es obligatorio")
    @Size(min = 2, max = 120, message = "El país debe tener entre 2 y 120 caracteres")
    private String pais;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1896, message = "El año debe ser mayor o igual a 1896")
    @Max(value = 2100, message = "El año no puede ser mayor a 2100")
    private Integer anio;

    @NotBlank(message = "La temporada es obligatoria")
    @Size(min = 3, max = 20, message = "La temporada debe tener entre 3 y 20 caracteres")
    private String temporada;
}

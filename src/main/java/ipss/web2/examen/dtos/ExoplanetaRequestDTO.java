package ipss.web2.examen.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear un nuevo exoplaneta")
public class ExoplanetaRequestDTO {

    @NotBlank(message = "El nombre del exoplaneta es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre del exoplaneta", example = "Kepler-442b", required = true)
    private String nombre;

    @NotBlank(message = "El tipo del exoplaneta es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    @Schema(description = "Tipo o clasificación del exoplaneta", example = "Súper Tierra", required = true)
    private String tipo;

    @NotNull(message = "La distancia en años luz es obligatoria")
    @Min(value = 0, message = "La distancia debe ser mayor o igual a 0")
    @Schema(description = "Distancia desde la Tierra en años luz", example = "1206.0", required = true)
    private Double distanciaAnosLuz;

    @NotNull(message = "La masa relativa a Júpiter es obligatoria")
    @Min(value = 0, message = "La masa debe ser mayor o igual a 0")
    @Schema(description = "Masa del exoplaneta relativa a Júpiter", example = "2.34", required = true)
    private Double masaRelativaJupiter;

    @NotNull(message = "El año de descubrimiento es obligatorio")
    @Min(value = 1990, message = "El año de descubrimiento debe ser mayor o igual a 1990")
    @Max(value = 2100, message = "El año de descubrimiento debe ser menor o igual a 2100")
    @Schema(description = "Año en que fue descubierto el exoplaneta", example = "2015", required = true)
    private Integer descubiertoAnio;
}

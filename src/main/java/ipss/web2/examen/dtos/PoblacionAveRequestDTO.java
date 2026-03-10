package ipss.web2.examen.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoblacionAveRequestDTO {

    @NotNull(message = "El tipo de ave es obligatorio")
    private Long tipoAveId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor o igual a 1")
    @Max(value = 1000000, message = "La cantidad no puede superar 1000000")
    private Integer cantidad;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
}

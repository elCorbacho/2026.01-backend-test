package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// DTO de solicitud para presidentes de Rusia
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListadoPresidenteRusiaRequestDTO {

    @NotBlank(message = "El nombre del presidente es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @NotNull(message = "La fecha de inicio del periodo es obligatoria")
    private LocalDate periodoInicio;

    private LocalDate periodoFin;

    @Size(max = 120, message = "El partido no puede exceder 120 caracteres")
    private String partido;

    @Size(max = 500, message = "La descripcion no puede exceder 500 caracteres")
    private String descripcion;
}

package ipss.web2.examen.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para actualizar parcialmente presidentes de Rusia.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListadoPresidenteRusiaPatchRequestDTO {

    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate periodoInicio;

    @PastOrPresent(message = "La fecha de fin no puede ser futura")
    private LocalDate periodoFin;

    @Size(max = 120, message = "El partido no puede exceder 120 caracteres")
    private String partido;

    @Size(max = 500, message = "La descripcion no puede exceder 500 caracteres")
    private String descripcion;

    @AssertTrue(message = "Debe proporcionar al menos un campo para actualizar")
    public boolean hasAnyField() {
        return nombre != null
                || periodoInicio != null
                || periodoFin != null
                || partido != null
                || descripcion != null;
    }
}

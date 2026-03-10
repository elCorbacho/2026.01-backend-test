package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoAveRequestDTO {

    @NotBlank(message = "El nombre del tipo de ave es obligatorio")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String descripcion;
}

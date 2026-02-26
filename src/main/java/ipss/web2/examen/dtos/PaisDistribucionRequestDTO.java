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
public class PaisDistribucionRequestDTO {

    @NotBlank(message = "El nombre del país es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String nombre;

    @Size(max = 3, message = "El código ISO no puede exceder 3 caracteres")
    private String codigoIso;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
}

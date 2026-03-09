package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de solicitud para crear o actualizar un transportista.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportistaRequestDTO {

    @NotBlank(message = "El nombre del transportista es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombre;

    @NotBlank(message = "La empresa del transportista es obligatoria")
    @Size(max = 200, message = "La empresa no puede exceder 200 caracteres")
    private String empresa;

    @Size(max = 100, message = "El contacto no puede exceder 100 caracteres")
    private String contacto;
}

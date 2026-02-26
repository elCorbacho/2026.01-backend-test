package ipss.web2.examen.dtos;

import jakarta.validation.constraints.Email;
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
public class TiendaLaminaRequestDTO {

    @NotBlank(message = "El nombre de la tienda es obligatorio")
    @Size(max = 150, message = "El nombre de la tienda no puede exceder 150 caracteres")
    private String nombre;

    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 80, message = "La ciudad no puede exceder 80 caracteres")
    private String ciudad;

    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;

    @Size(max = 30, message = "El teléfono no puede exceder 30 caracteres")
    private String telefono;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 120, message = "El email no puede exceder 120 caracteres")
    private String email;
}


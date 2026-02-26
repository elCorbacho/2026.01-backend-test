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
public class EmpresaInsumosRequestDTO {

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150, message = "El nombre de la empresa no puede exceder 150 caracteres")
    private String nombre;

    @NotBlank(message = "El rubro es obligatorio")
    @Size(max = 120, message = "El rubro no puede exceder 120 caracteres")
    private String rubro;

    @Size(max = 120, message = "El contacto no puede exceder 120 caracteres")
    private String contacto;

    @Size(max = 30, message = "El teléfono no puede exceder 30 caracteres")
    private String telefono;

    @Email(message = "El email debe tener un formato válido")
    @Size(max = 120, message = "El email no puede exceder 120 caracteres")
    private String email;

    @Size(max = 200, message = "El sitio web no puede exceder 200 caracteres")
    private String sitioWeb;
}


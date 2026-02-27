package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de solicitud para crear una mina de Chile
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinaChileRequestDTO {

    @NotBlank(message = "El nombre de la mina es obligatorio")
    @Size(max = 150, message = "El nombre de la mina no puede exceder 150 caracteres")
    private String nombre;

    @NotBlank(message = "La región es obligatoria")
    @Size(max = 120, message = "La región no puede exceder 120 caracteres")
    private String region;

    @NotBlank(message = "El mineral principal es obligatorio")
    @Size(max = 120, message = "El mineral principal no puede exceder 120 caracteres")
    private String mineralPrincipal;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede exceder 50 caracteres")
    private String estado;
}


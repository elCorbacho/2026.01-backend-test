package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta que expone los datos de un transportista.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportistaResponseDTO {

    private Long id;
    private String nombre;
    private String empresa;
    private String contacto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

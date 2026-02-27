package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta simplificada para regiones de Chile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {
    private Long id;
    private String codigo;
    private String nombre;
}

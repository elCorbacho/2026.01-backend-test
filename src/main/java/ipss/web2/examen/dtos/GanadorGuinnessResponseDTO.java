package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO de respuesta para ganadores Guinness
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GanadorGuinnessResponseDTO {
    private String nombre;
    private String categoria;
    private String record;
    private Integer anio;
}

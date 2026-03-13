package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipoFutbolEspanaResponseDTO {

    private Long id;
    private String nombre;
    private String ciudad;
    private Integer fundacion;
    private String estadio;
}

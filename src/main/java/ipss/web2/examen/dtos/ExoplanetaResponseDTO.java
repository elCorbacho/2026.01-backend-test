package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExoplanetaResponseDTO
{
    private Long id;
    private String nombre;
    private String tipo;
    private Double distanciaAnosLuz;
    private Double masaRelativaJupiter;
    private Integer descubiertoAnio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

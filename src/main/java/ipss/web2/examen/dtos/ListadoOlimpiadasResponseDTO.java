package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO de respuesta para listado de olimpiadas
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListadoOlimpiadasResponseDTO {
    private Long id;
    private String nombre;
    private String ciudad;
    private String pais;
    private Integer anio;
    private String temporada;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

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
public class PaisDistribucionResponseDTO {
    private Long id;
    private String nombre;
    private String codigoIso;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

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
public class MarcaAutomovilResponseDTO {
    private Long id;
    private String nombre;
    private String paisOrigen;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

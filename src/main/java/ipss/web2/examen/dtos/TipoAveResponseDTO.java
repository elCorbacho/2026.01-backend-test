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
public class TipoAveResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

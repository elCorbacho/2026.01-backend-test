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
public class CancionResponseDTO {
    private Long id;
    private String titulo;
    private String artista;
    private Integer duracion;
    private String genero;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

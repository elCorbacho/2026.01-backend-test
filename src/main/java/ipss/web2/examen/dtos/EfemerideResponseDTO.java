package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EfemerideResponseDTO {
    private Long id;
    private String titulo;
    private LocalDate fecha;
    private String descripcion;
    private Long poblacion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

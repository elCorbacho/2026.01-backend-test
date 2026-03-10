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
public class PoblacionAveResponseDTO {

    private Long id;
    private Long tipoAveId;
    private String tipoAveNombre;
    private Integer cantidad;
    private LocalDate fecha;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

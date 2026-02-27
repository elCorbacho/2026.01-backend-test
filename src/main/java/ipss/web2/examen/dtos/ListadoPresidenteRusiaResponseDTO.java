package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// DTO de respuesta para presidentes de Rusia
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListadoPresidenteRusiaResponseDTO {
    private Long id;
    private String nombre;
    private LocalDate periodoInicio;
    private LocalDate periodoFin;
    private String partido;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

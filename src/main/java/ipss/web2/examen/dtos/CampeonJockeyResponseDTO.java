package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO de respuesta para campeones de jockey
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampeonJockeyResponseDTO {

    private Long id;
    private String nombreJockey;
    private String pais;
    private String titulo;
    private Integer anio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


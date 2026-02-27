package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO de respuesta para una mina de Chile
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MinaChileResponseDTO {

    private Long id;
    private String nombre;
    private String region;
    private String mineralPrincipal;
    private String estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


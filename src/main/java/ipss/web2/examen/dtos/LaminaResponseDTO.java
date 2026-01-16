package ipss.web2.examen.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

// DTO de respuesta para una lámina, incluyendo información del álbum asociado
public class LaminaResponseDTO {
    
    private Long id;
    
    private String nombre;
    
    private String imagen;
    
    private LocalDate fechaLanzamiento;
    
    private String tipoLamina;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean active;
    
    // Referencia al album (básico)
    private AlbumBasicResponseDTO album;
}

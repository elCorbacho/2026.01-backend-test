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
public class TiendaLaminaResponseDTO {

    private Long id;
    private String nombre;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String email;
    private LocalDate fechaApertura;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


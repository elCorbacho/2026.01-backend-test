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
public class EmpresaInsumosResponseDTO {

    private Long id;
    private String nombre;
    private String rubro;
    private String contacto;
    private String telefono;
    private String email;
    private String sitioWeb;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


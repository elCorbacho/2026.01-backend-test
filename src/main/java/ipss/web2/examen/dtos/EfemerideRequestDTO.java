package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EfemerideRequestDTO {

    @NotBlank
    private String titulo;

    @NotNull
    private LocalDate fecha;

    private String descripcion;

    private Long poblacion;
}

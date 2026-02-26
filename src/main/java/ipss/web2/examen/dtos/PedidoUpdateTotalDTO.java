package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUpdateTotalDTO {
    @NotNull(message = "El total es obligatorio")
    @PositiveOrZero(message = "El total debe ser 0 o mayor")
    private Double total;
}

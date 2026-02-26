package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clienteNombre;

    @NotNull(message = "El total es obligatorio")
    @PositiveOrZero(message = "El total debe ser 0 o mayor")
    private Double total;

    @NotEmpty(message = "Debe incluir al menos un ítem en el pedido")
    private List<@NotBlank(message = "El nombre del ítem no puede estar vacío") String> items;
}

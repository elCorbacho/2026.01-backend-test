package ipss.web2.examen.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


// DTO de solicitud para agregar una lámina al catálogo
public record LaminaCatalogoRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,

    String imagen,

    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    LocalDate fechaLanzamiento,

    @NotBlank(message = "El tipo de lámina es obligatorio")
    String tipoLamina
) {}

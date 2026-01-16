package ipss.web2.examen.dtos;

// DTO de respuesta para la carga de una lámina
public record LaminaCargaResponseDTO(
    Boolean esRepetida,
    Boolean estaEnCatalogo,
    Integer cantidadRepetidas,
    LaminaResponseDTO lamina
) {}

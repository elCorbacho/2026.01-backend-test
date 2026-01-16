package ipss.web2.examen.dtos;

// DTO de respuesta para el cargue masivo de láminas
public record LaminaCargueMasivoResponseDTO(
    Long laminaId,
    String nombre,
    Boolean esRepetida,
    Integer cantidadRepetidas,
    Boolean estaEnCatalogo,
    String estado
) {}

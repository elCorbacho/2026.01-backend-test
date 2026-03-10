package ipss.web2.examen.dtos;

// DTO de respuesta para el resumen de una marca bicicleta
public record MarcaBicicletaSummaryDTO(
    Long id,
    String nombre,
    Boolean active
) {}

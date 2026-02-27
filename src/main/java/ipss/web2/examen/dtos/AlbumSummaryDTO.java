package ipss.web2.examen.dtos;

// DTO de respuesta para el resumen de un álbum
public record AlbumSummaryDTO(
    Long id,
    String nombre,
    Integer year,
    Boolean active,
    Integer totalCatalogo,
    Integer totalLaminas,
    Integer laminasRepetidasTotal,
    Integer laminasFaltantesTotal
) {}

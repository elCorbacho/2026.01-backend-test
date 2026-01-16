package ipss.web2.examen.dtos;

import java.util.List;
import java.util.Map;


// DTO de respuesta para el estado de las láminas de un usuario
public record LaminasEstadoDTO(
    List<LaminaResponseDTO> laminasPoseidas,
    List<LaminaCatalogoResponseDTO> laminasFaltantes,
    Map<String, Integer> laminasRepetidas,
    Integer totalLaminas,
    Integer laminasFaltantesTotal,
    Integer laminasRepetidastotal
) {}

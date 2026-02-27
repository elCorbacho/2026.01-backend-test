package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.models.PresidenteChile;
import org.springframework.stereotype.Component;

// Mapper manual para PresidenteChile
@Component
public class PresidenteChileMapper {

    public PresidenteChileResponseDTO toDTO(PresidenteChile presidenteChile) {
        return PresidenteChileResponseDTO.builder()
                .id(presidenteChile.getId())
                .nombre(presidenteChile.getNombre())
                .periodoInicio(presidenteChile.getPeriodoInicio())
                .periodoFin(presidenteChile.getPeriodoFin())
                .partido(presidenteChile.getPartido())
                .descripcion(presidenteChile.getDescripcion())
                .createdAt(presidenteChile.getCreatedAt())
                .updatedAt(presidenteChile.getUpdatedAt())
                .build();
    }
}

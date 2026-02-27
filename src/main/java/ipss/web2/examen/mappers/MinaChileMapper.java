package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.MinaChileRequestDTO;
import ipss.web2.examen.dtos.MinaChileResponseDTO;
import ipss.web2.examen.models.MinaChile;
import org.springframework.stereotype.Component;

// Mapper manual para MinaChile
@Component
public class MinaChileMapper {

    public MinaChileResponseDTO toResponseDTO(MinaChile entity) {
        if (entity == null) {
            return null;
        }

        return MinaChileResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .region(entity.getRegion())
                .mineralPrincipal(entity.getMineralPrincipal())
                .estado(entity.getEstado())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public MinaChile toEntity(MinaChileRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return MinaChile.builder()
                .nombre(dto.getNombre())
                .region(dto.getRegion())
                .mineralPrincipal(dto.getMineralPrincipal())
                .estado(dto.getEstado())
                .active(true)
                .build();
    }
}


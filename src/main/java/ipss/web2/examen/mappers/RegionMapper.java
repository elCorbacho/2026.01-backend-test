package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.models.RegionChile;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionResponseDTO toResponseDTO(RegionChile region) {
        if (region == null) {
            return null;
        }
        return RegionResponseDTO.builder()
                .id(region.getId())
                .codigo(region.getCodigo())
                .nombre(region.getNombre())
                .build();
    }
}

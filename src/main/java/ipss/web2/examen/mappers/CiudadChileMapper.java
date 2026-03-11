package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.CiudadChileResponseDTO;
import ipss.web2.examen.models.CiudadChile;
import org.springframework.stereotype.Component;

@Component
public class CiudadChileMapper {

    public CiudadChileResponseDTO toResponseDTO(CiudadChile ciudadChile) {
        if (ciudadChile == null) {
            return null;
        }

        return CiudadChileResponseDTO.builder()
                .id(ciudadChile.getId())
                .nombre(ciudadChile.getNombre())
                .poblacion(ciudadChile.getPoblacion())
                .build();
    }
}

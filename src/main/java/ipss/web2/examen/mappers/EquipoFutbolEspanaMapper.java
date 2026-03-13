package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.EquipoFutbolEspanaResponseDTO;
import ipss.web2.examen.models.EquipoFutbolEspana;
import org.springframework.stereotype.Component;

@Component
public class EquipoFutbolEspanaMapper {

    public EquipoFutbolEspanaResponseDTO toResponseDTO(EquipoFutbolEspana entity) {
        if (entity == null) {
            return null;
        }

        return EquipoFutbolEspanaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .ciudad(entity.getCiudad())
                .fundacion(entity.getFundacion())
                .estadio(entity.getEstadio())
                .build();
    }
}

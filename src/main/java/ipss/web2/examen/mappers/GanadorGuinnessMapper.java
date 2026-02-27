package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.models.GanadorGuinness;
import org.springframework.stereotype.Component;

// Mapper manual para GanadorGuinness
@Component
public class GanadorGuinnessMapper {

    public GanadorGuinnessResponseDTO toDTO(GanadorGuinness ganadorGuinness) {
        return GanadorGuinnessResponseDTO.builder()
                .nombre(ganadorGuinness.getNombre())
                .categoria(ganadorGuinness.getCategoria())
                .record(ganadorGuinness.getRecord())
                .anio(ganadorGuinness.getAnio())
                .build();
    }
}

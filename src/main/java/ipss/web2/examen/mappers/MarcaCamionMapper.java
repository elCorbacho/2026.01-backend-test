package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.MarcaCamionResponseDTO;
import ipss.web2.examen.models.MarcaCamion;
import org.springframework.stereotype.Component;

@Component
public class MarcaCamionMapper {

    public MarcaCamionResponseDTO toResponseDTO(MarcaCamion marcaCamion) {
        if (marcaCamion == null) {
            return null;
        }
        return MarcaCamionResponseDTO.builder()
                .id(marcaCamion.getId())
                .nombre(marcaCamion.getNombre())
                .paisOrigen(marcaCamion.getPaisOrigen())
                .descripcion(marcaCamion.getDescripcion())
                .createdAt(marcaCamion.getCreatedAt())
                .updatedAt(marcaCamion.getUpdatedAt())
                .build();
    }
}

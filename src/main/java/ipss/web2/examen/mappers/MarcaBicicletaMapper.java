package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.MarcaBicicletaResponseDTO;
import ipss.web2.examen.dtos.MarcaBicicletaSummaryDTO;
import ipss.web2.examen.models.MarcaBicicleta;
import org.springframework.stereotype.Component;

@Component
public class MarcaBicicletaMapper {

    public MarcaBicicletaResponseDTO toResponseDTO(MarcaBicicleta marca) {
        if (marca == null) {
            return null;
        }
        return MarcaBicicletaResponseDTO.builder()
                .id(marca.getId())
                .nombre(marca.getNombre())
                .paisOrigen(marca.getPaisOrigen())
                .descripcion(marca.getDescripcion())
                .active(marca.getActive())
                .createdAt(marca.getCreatedAt())
                .updatedAt(marca.getUpdatedAt())
                .build();
    }

    public MarcaBicicletaSummaryDTO toSummaryDTO(MarcaBicicleta marca) {
        if (marca == null) {
            return null;
        }
        return new MarcaBicicletaSummaryDTO(
            marca.getId(),
            marca.getNombre(),
            marca.getActive()
        );
    }
}

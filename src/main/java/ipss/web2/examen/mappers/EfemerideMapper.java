package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.EfemerideRequestDTO;
import ipss.web2.examen.dtos.EfemerideResponseDTO;
import ipss.web2.examen.models.EfemerideChile;
import org.springframework.stereotype.Component;

@Component
public class EfemerideMapper {

    public EfemerideResponseDTO toResponseDTO(EfemerideChile e) {
        if (e == null) return null;
        return EfemerideResponseDTO.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .fecha(e.getFecha())
                .descripcion(e.getDescripcion())
                .poblacion(e.getPoblacion())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    public EfemerideChile toEntity(EfemerideRequestDTO req) {
        if (req == null) return null;
        return EfemerideChile.builder()
                .titulo(req.getTitulo())
                .fecha(req.getFecha())
                .descripcion(req.getDescripcion())
                .poblacion(req.getPoblacion())
                .active(true)
                .build();
    }
}

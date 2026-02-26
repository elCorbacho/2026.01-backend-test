package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.PaisDistribucionRequestDTO;
import ipss.web2.examen.dtos.PaisDistribucionResponseDTO;
import ipss.web2.examen.models.PaisDistribucion;
import org.springframework.stereotype.Component;

@Component
public class PaisDistribucionMapper {

    public PaisDistribucionResponseDTO toResponseDTO(PaisDistribucion pais) {
        return PaisDistribucionResponseDTO.builder()
                .id(pais.getId())
                .nombre(pais.getNombre())
                .codigoIso(pais.getCodigoIso())
                .descripcion(pais.getDescripcion())
                .createdAt(pais.getCreatedAt())
                .updatedAt(pais.getUpdatedAt())
                .build();
    }

    public PaisDistribucion toEntity(PaisDistribucionRequestDTO request) {
        return PaisDistribucion.builder()
                .nombre(request.getNombre())
                .codigoIso(request.getCodigoIso())
                .descripcion(request.getDescripcion())
                .active(true)
                .build();
    }

    public void updateEntity(PaisDistribucionRequestDTO request, PaisDistribucion pais) {
        pais.setNombre(request.getNombre());
        pais.setCodigoIso(request.getCodigoIso());
        pais.setDescripcion(request.getDescripcion());
    }
}

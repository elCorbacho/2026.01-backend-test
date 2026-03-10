package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.models.TipoAve;
import org.springframework.stereotype.Component;

@Component
public class TipoAveMapper {

    public TipoAveResponseDTO toResponseDTO(TipoAve entity) {
        if (entity == null) {
            return null;
        }

        return TipoAveResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public TipoAve toEntity(TipoAveRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return TipoAve.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .active(true)
                .build();
    }

    public void updateEntity(TipoAveRequestDTO dto, TipoAve entity) {
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
    }
}

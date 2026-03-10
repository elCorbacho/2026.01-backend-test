package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.dtos.PoblacionAveResponseDTO;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import org.springframework.stereotype.Component;

@Component
public class PoblacionAveMapper {

    public PoblacionAveResponseDTO toResponseDTO(PoblacionAve entity) {
        if (entity == null) {
            return null;
        }

        String tipoAveNombre = entity.getTipoAve() != null ? entity.getTipoAve().getNombre() : null;
        Long tipoAveId = entity.getTipoAve() != null ? entity.getTipoAve().getId() : null;

        return PoblacionAveResponseDTO.builder()
                .id(entity.getId())
                .tipoAveId(tipoAveId)
                .tipoAveNombre(tipoAveNombre)
                .cantidad(entity.getCantidad())
                .fecha(entity.getFecha())
                .active(entity.getActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public PoblacionAve toEntity(PoblacionAveRequestDTO dto, TipoAve tipoAve) {
        if (dto == null) {
            return null;
        }

        return PoblacionAve.builder()
                .tipoAve(tipoAve)
                .cantidad(dto.getCantidad())
                .fecha(dto.getFecha())
                .active(true)
                .build();
    }

    public void updateEntity(PoblacionAveRequestDTO dto, PoblacionAve entity, TipoAve tipoAve) {
        entity.setTipoAve(tipoAve);
        entity.setCantidad(dto.getCantidad());
        entity.setFecha(dto.getFecha());
    }
}

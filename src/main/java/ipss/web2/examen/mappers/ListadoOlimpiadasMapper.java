package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.ListadoOlimpiadasRequestDTO;
import ipss.web2.examen.dtos.ListadoOlimpiadasResponseDTO;
import ipss.web2.examen.models.ListadoOlimpiadas;
import org.springframework.stereotype.Component;

// Mapper manual para ListadoOlimpiadas
@Component
public class ListadoOlimpiadasMapper {

    public ListadoOlimpiadasResponseDTO toResponseDTO(ListadoOlimpiadas entity) {
        if (entity == null) {
            return null;
        }

        return ListadoOlimpiadasResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .ciudad(entity.getCiudad())
                .pais(entity.getPais())
                .anio(entity.getAnio())
                .temporada(entity.getTemporada())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ListadoOlimpiadas toEntity(ListadoOlimpiadasRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return ListadoOlimpiadas.builder()
                .nombre(dto.getNombre())
                .ciudad(dto.getCiudad())
                .pais(dto.getPais())
                .anio(dto.getAnio())
                .temporada(dto.getTemporada())
                .active(true)
                .build();
    }

    public void updateEntity(ListadoOlimpiadasRequestDTO dto, ListadoOlimpiadas entity) {
        entity.setNombre(dto.getNombre());
        entity.setCiudad(dto.getCiudad());
        entity.setPais(dto.getPais());
        entity.setAnio(dto.getAnio());
        entity.setTemporada(dto.getTemporada());
    }
}

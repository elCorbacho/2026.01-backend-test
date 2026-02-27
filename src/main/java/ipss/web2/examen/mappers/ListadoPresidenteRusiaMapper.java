package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaPatchRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import org.springframework.stereotype.Component;

// Mapper manual para ListadoPresidenteRusia
@Component
public class ListadoPresidenteRusiaMapper {

    public ListadoPresidenteRusiaResponseDTO toResponseDTO(ListadoPresidenteRusia entity) {
        if (entity == null) {
            return null;
        }

        return ListadoPresidenteRusiaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .periodoInicio(entity.getPeriodoInicio())
                .periodoFin(entity.getPeriodoFin())
                .partido(entity.getPartido())
                .descripcion(entity.getDescripcion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public ListadoPresidenteRusia toEntity(ListadoPresidenteRusiaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return ListadoPresidenteRusia.builder()
                .nombre(dto.getNombre())
                .periodoInicio(dto.getPeriodoInicio())
                .periodoFin(dto.getPeriodoFin())
                .partido(dto.getPartido())
                .descripcion(dto.getDescripcion())
                .active(true)
                .build();
    }

    public void applyPatch(ListadoPresidenteRusiaPatchRequestDTO patchDTO, ListadoPresidenteRusia entity) {
        if (patchDTO == null || entity == null) {
            return;
        }

        if (patchDTO.getNombre() != null) {
            entity.setNombre(patchDTO.getNombre());
        }
        if (patchDTO.getPeriodoInicio() != null) {
            entity.setPeriodoInicio(patchDTO.getPeriodoInicio());
        }
        if (patchDTO.getPeriodoFin() != null) {
            entity.setPeriodoFin(patchDTO.getPeriodoFin());
        }
        if (patchDTO.getPartido() != null) {
            entity.setPartido(patchDTO.getPartido());
        }
        if (patchDTO.getDescripcion() != null) {
            entity.setDescripcion(patchDTO.getDescripcion());
        }
    }
}

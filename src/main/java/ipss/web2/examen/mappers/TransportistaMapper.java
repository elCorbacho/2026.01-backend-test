package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.TransportistaRequestDTO;
import ipss.web2.examen.dtos.TransportistaResponseDTO;
import ipss.web2.examen.models.Transportista;
import org.springframework.stereotype.Component;

/**
 * Mapper manual para conversiones entre {@link Transportista} y sus DTOs.
 * No usa MapStruct; toda la lógica de mapeo es explícita.
 */
@Component
public class TransportistaMapper {

    /**
     * Convierte una entidad {@link Transportista} a {@link TransportistaResponseDTO}.
     *
     * @param transportista entidad origen (puede ser null)
     * @return DTO de respuesta, o null si la entidad es null
     */
    public TransportistaResponseDTO toResponseDTO(Transportista transportista) {
        if (transportista == null) {
            return null;
        }
        return TransportistaResponseDTO.builder()
                .id(transportista.getId())
                .nombre(transportista.getNombre())
                .empresa(transportista.getEmpresa())
                .contacto(transportista.getContacto())
                .createdAt(transportista.getCreatedAt())
                .updatedAt(transportista.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un {@link TransportistaRequestDTO} a una nueva entidad {@link Transportista}.
     *
     * @param dto DTO de solicitud
     * @return entidad lista para persistir
     */
    public Transportista toEntity(TransportistaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Transportista.builder()
                .nombre(dto.getNombre())
                .empresa(dto.getEmpresa())
                .contacto(dto.getContacto())
                .active(true)
                .build();
    }

    /**
     * Actualiza todos los campos de una entidad existente con los valores del DTO (PUT).
     *
     * @param dto    DTO con los nuevos valores
     * @param entity entidad a modificar in-place
     */
    public void updateEntity(TransportistaRequestDTO dto, Transportista entity) {
        if (dto == null || entity == null) {
            return;
        }
        entity.setNombre(dto.getNombre());
        entity.setEmpresa(dto.getEmpresa());
        entity.setContacto(dto.getContacto());
    }
}

package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.MarcaAutomovilResponseDTO;
import ipss.web2.examen.models.MarcaAutomovil;
import org.springframework.stereotype.Component;

@Component
public class MarcaAutomovilMapper {

    public MarcaAutomovilResponseDTO toResponseDTO(MarcaAutomovil marca) {
        if (marca == null) {
            return null;
        }
        return MarcaAutomovilResponseDTO.builder()
                .id(marca.getId())
                .nombre(marca.getNombre())
                .paisOrigen(marca.getPaisOrigen())
                .descripcion(marca.getDescripcion())
                .createdAt(marca.getCreatedAt())
                .updatedAt(marca.getUpdatedAt())
                .build();
    }
}

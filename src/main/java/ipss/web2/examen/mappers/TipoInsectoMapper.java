package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.TipoInsectoResponseDTO;
import ipss.web2.examen.models.TipoInsecto;
import org.springframework.stereotype.Component;

@Component
public class TipoInsectoMapper
{

    public TipoInsectoResponseDTO toResponseDTO(TipoInsecto entity)
    {
        if (entity == null) {
            return null;
        }

        return new TipoInsectoResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

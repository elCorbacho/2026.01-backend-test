package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.ExoplanetaRequestDTO;
import ipss.web2.examen.dtos.ExoplanetaResponseDTO;
import ipss.web2.examen.models.Exoplaneta;
import org.springframework.stereotype.Component;

@Component
public class ExoplanetaMapper
{
    /**
     * Convierte una entidad Exoplaneta a ExoplanetaResponseDTO.
     * Mapea todos los campos excepto active.
     *
     * @param entity la entidad Exoplaneta a convertir
     * @return ExoplanetaResponseDTO con los datos mapeados, o null si entity es null
     */
    public ExoplanetaResponseDTO toResponseDTO(Exoplaneta entity)
    {
        if (entity == null)
        {
            return null;
        }

        return ExoplanetaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .tipo(entity.getTipo())
                .distanciaAnosLuz(entity.getDistanciaAnosLuz())
                .masaRelativaJupiter(entity.getMasaRelativaJupiter())
                .descubiertoAnio(entity.getDescubiertoAnio())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un ExoplanetaRequestDTO a una nueva entidad Exoplaneta.
     * Establece active=true por defecto.
     * No establece id, createdAt, updatedAt (gestionados por JPA).
     *
     * @param requestDTO el DTO de solicitud con los datos del exoplaneta
     * @return nueva entidad Exoplaneta con los datos mapeados
     */
    public Exoplaneta toEntity(ExoplanetaRequestDTO requestDTO)
    {
        return Exoplaneta.builder()
                .nombre(requestDTO.getNombre())
                .tipo(requestDTO.getTipo())
                .distanciaAnosLuz(requestDTO.getDistanciaAnosLuz())
                .masaRelativaJupiter(requestDTO.getMasaRelativaJupiter())
                .descubiertoAnio(requestDTO.getDescubiertoAnio())
                .active(true)
                .build();
    }
}

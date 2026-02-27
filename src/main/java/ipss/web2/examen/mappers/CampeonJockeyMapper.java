package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.CampeonJockeyResponseDTO;
import ipss.web2.examen.models.CampeonJockey;
import org.springframework.stereotype.Component;

// Mapper manual para CampeonJockey
@Component
public class CampeonJockeyMapper {

    public CampeonJockeyResponseDTO toDTO(CampeonJockey campeonJockey) {
        if (campeonJockey == null) {
            return null;
        }

        return CampeonJockeyResponseDTO.builder()
                .id(campeonJockey.getId())
                .nombreJockey(campeonJockey.getNombreJockey())
                .pais(campeonJockey.getPais())
                .titulo(campeonJockey.getTitulo())
                .anio(campeonJockey.getAnio())
                .createdAt(campeonJockey.getCreatedAt())
                .updatedAt(campeonJockey.getUpdatedAt())
                .build();
    }
}


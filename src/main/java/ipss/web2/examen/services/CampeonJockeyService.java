package ipss.web2.examen.services;

import ipss.web2.examen.dtos.CampeonJockeyResponseDTO;
import ipss.web2.examen.mappers.CampeonJockeyMapper;
import ipss.web2.examen.models.CampeonJockey;
import ipss.web2.examen.repositories.CampeonJockeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class CampeonJockeyService {

    private final CampeonJockeyRepository campeonJockeyRepository;
    private final CampeonJockeyMapper campeonJockeyMapper;

    // Obtener listado de campeones de jockey activos
    @Transactional(readOnly = true)
    public List<CampeonJockeyResponseDTO> obtenerCampeonesActivos() {
        List<CampeonJockey> campeones = campeonJockeyRepository.findByActiveTrue();
        return campeones.stream()
                .map(campeonJockeyMapper::toDTO)
                .collect(Collectors.toList());
    }
}


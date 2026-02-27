package ipss.web2.examen.services;

import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.mappers.GanadorGuinnessMapper;
import ipss.web2.examen.models.GanadorGuinness;
import ipss.web2.examen.repositories.GanadorGuinnessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class GanadorGuinnessService {

    private final GanadorGuinnessRepository ganadorGuinnessRepository;
    private final GanadorGuinnessMapper ganadorGuinnessMapper;

    // Obtener listado de ganadores Guinness activos
    @Transactional(readOnly = true)
    public List<GanadorGuinnessResponseDTO> obtenerGanadoresGuinness() {
        List<GanadorGuinness> ganadores = ganadorGuinnessRepository.findByActiveTrue();
        return ganadores.stream()
                .map(ganadorGuinnessMapper::toDTO)
                .collect(Collectors.toList());
    }
}

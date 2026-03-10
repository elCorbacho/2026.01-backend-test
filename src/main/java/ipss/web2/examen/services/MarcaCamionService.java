package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaCamionResponseDTO;
import ipss.web2.examen.mappers.MarcaCamionMapper;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarcaCamionService {

    private final MarcaCamionRepository marcaRepository;
    private final MarcaCamionMapper marcaMapper;

    @Transactional(readOnly = true)
    public List<MarcaCamionResponseDTO> obtenerMarcasActivas() {
        return marcaRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(marcaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

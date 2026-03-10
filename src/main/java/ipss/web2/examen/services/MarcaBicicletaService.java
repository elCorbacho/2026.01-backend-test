package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaBicicletaResponseDTO;
import ipss.web2.examen.dtos.MarcaBicicletaSummaryDTO;
import ipss.web2.examen.mappers.MarcaBicicletaMapper;
import ipss.web2.examen.repositories.MarcaBicicletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarcaBicicletaService {

    private final MarcaBicicletaRepository marcaRepository;
    private final MarcaBicicletaMapper marcaMapper;

    @Transactional(readOnly = true)
    public List<MarcaBicicletaResponseDTO> obtenerMarcasActivas() {
        return marcaRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(marcaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MarcaBicicletaSummaryDTO> obtenerResumenMarcasActivas() {
        return marcaRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(marcaMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }
}

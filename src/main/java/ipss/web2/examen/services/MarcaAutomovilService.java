package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaAutomovilResponseDTO;
import ipss.web2.examen.mappers.MarcaAutomovilMapper;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MarcaAutomovilService {

    private final MarcaAutomovilRepository marcaRepository;
    private final MarcaAutomovilMapper marcaMapper;

    @Transactional(readOnly = true)
    public List<MarcaAutomovilResponseDTO> obtenerMarcasActivas() {
        return marcaRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(marcaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

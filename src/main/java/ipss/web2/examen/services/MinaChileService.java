package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MinaChileRequestDTO;
import ipss.web2.examen.dtos.MinaChileResponseDTO;
import ipss.web2.examen.mappers.MinaChileMapper;
import ipss.web2.examen.models.MinaChile;
import ipss.web2.examen.repositories.MinaChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class MinaChileService {

    private final MinaChileRepository minaChileRepository;
    private final MinaChileMapper minaChileMapper;

    // Obtener listado de minas de Chile activas
    @Transactional(readOnly = true)
    public List<MinaChileResponseDTO> obtenerMinasActivas() {
        List<MinaChile> minas = minaChileRepository.findByActiveTrue();
        return minas.stream()
                .map(minaChileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Crear nueva mina de Chile
    public MinaChileResponseDTO crearMina(MinaChileRequestDTO requestDTO) {
        MinaChile nueva = minaChileMapper.toEntity(requestDTO);
        MinaChile guardada = minaChileRepository.save(nueva);
        return minaChileMapper.toResponseDTO(guardada);
    }
}


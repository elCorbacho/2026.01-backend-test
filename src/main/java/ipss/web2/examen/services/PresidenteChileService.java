package ipss.web2.examen.services;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.PresidenteChileMapper;
import ipss.web2.examen.models.PresidenteChile;
import ipss.web2.examen.repositories.PresidenteChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class PresidenteChileService {

    private final PresidenteChileRepository presidenteChileRepository;
    private final PresidenteChileMapper presidenteChileMapper;

    // Obtener listado de presidentes de Chile activos
    @Transactional(readOnly = true)
    public List<PresidenteChileResponseDTO> obtenerPresidentesChile() {
        List<PresidenteChile> presidentes = presidenteChileRepository.findByActiveTrue();
        return presidentes.stream()
                .map(presidenteChileMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PresidenteChileResponseDTO obtenerPresidenteChilePorId(Long id) {
        PresidenteChile presidente = presidenteChileRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("PresidenteChile", "id", id));
        return presidenteChileMapper.toDTO(presidente);
    }
}


package ipss.web2.examen.services;

import ipss.web2.examen.dtos.CiudadChileResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.CiudadChileMapper;
import ipss.web2.examen.models.CiudadChile;
import ipss.web2.examen.repositories.CiudadChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CiudadChileService {

    private final CiudadChileRepository ciudadChileRepository;
    private final CiudadChileMapper ciudadChileMapper;

    @Transactional(readOnly = true)
    public List<CiudadChileResponseDTO> obtenerCiudadesChile() {
        return ciudadChileRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(ciudadChileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CiudadChileResponseDTO obtenerCiudadChilePorId(Long id) {
        CiudadChile ciudad = ciudadChileRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ciudad", "id", id, "CIUDAD_NOT_FOUND"));
        return ciudadChileMapper.toResponseDTO(ciudad);
    }
}

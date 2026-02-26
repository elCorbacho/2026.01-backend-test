package ipss.web2.examen.services;

import ipss.web2.examen.dtos.PaisDistribucionRequestDTO;
import ipss.web2.examen.dtos.PaisDistribucionResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.PaisDistribucionMapper;
import ipss.web2.examen.models.PaisDistribucion;
import ipss.web2.examen.repositories.PaisDistribucionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class PaisDistribucionService {

    private final PaisDistribucionRepository paisRepository;
    private final PaisDistribucionMapper paisMapper;

    public PaisDistribucionResponseDTO crearPais(PaisDistribucionRequestDTO requestDTO) {
        PaisDistribucion pais = paisMapper.toEntity(requestDTO);
        PaisDistribucion guardado = paisRepository.save(pais);
        return paisMapper.toResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    public PaisDistribucionResponseDTO obtenerPaisPorId(Long id) {
        PaisDistribucion pais = paisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaisDistribucion", "ID", id));
        return paisMapper.toResponseDTO(pais);
    }

    @Transactional(readOnly = true)
    public List<PaisDistribucionResponseDTO> obtenerTodosLosPaises() {
        return paisRepository.findByActiveTrue()
                .stream()
                .map(paisMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PaisDistribucionResponseDTO> obtenerPaises(Pageable pageable) {
        return paisRepository.findByActiveTrue(pageable)
                .map(paisMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<PaisDistribucionResponseDTO> buscarPorNombre(String nombre) {
        return paisRepository.findByNombreContainingIgnoreCaseAndActiveTrue(nombre)
                .stream()
                .map(paisMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PaisDistribucionResponseDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return paisRepository.findByNombreContainingIgnoreCaseAndActiveTrue(nombre, pageable)
                .map(paisMapper::toResponseDTO);
    }

    public PaisDistribucionResponseDTO actualizarPais(Long id, PaisDistribucionRequestDTO requestDTO) {
        PaisDistribucion pais = paisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaisDistribucion", "ID", id));
        paisMapper.updateEntity(requestDTO, pais);
        PaisDistribucion actualizado = paisRepository.save(pais);
        return paisMapper.toResponseDTO(actualizado);
    }

    public void eliminarPais(Long id) {
        PaisDistribucion pais = paisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaisDistribucion", "ID", id));
        pais.setActive(false);
        paisRepository.save(pais);
    }
}

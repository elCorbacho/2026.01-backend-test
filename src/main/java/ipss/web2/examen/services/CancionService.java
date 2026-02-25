package ipss.web2.examen.services;

import ipss.web2.examen.dtos.CancionRequestDTO;
import ipss.web2.examen.dtos.CancionResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.CancionMapper;
import ipss.web2.examen.models.Cancion;
import ipss.web2.examen.repositories.CancionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class CancionService {

    private final CancionRepository cancionRepository;
    private final CancionMapper cancionMapper;

    // Crear una nueva canción
    public CancionResponseDTO crearCancion(CancionRequestDTO requestDTO) {
        Cancion cancion = cancionMapper.toEntity(requestDTO);
        return cancionMapper.toResponseDTO(cancionRepository.save(cancion));
    }

    // Obtener canción por ID
    @Transactional(readOnly = true)
    public CancionResponseDTO obtenerCancionPorId(Long id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion", "ID", id));
        return cancionMapper.toResponseDTO(cancion);
    }

    // Listar todas las canciones activas
    @Transactional(readOnly = true)
    public List<CancionResponseDTO> obtenerTodasLasCanciones() {
        return cancionRepository.findByActiveTrue()
                .stream()
                .map(cancionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar canciones por artista
    @Transactional(readOnly = true)
    public List<CancionResponseDTO> buscarPorArtista(String artista) {
        return cancionRepository.findByArtistaContainingIgnoreCaseAndActiveTrue(artista)
                .stream()
                .map(cancionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Actualizar canción existente
    public CancionResponseDTO actualizarCancion(Long id, CancionRequestDTO requestDTO) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion", "ID", id));
        cancionMapper.updateEntity(requestDTO, cancion);
        return cancionMapper.toResponseDTO(cancionRepository.save(cancion));
    }

    // Eliminar (soft delete)
    public void eliminarCancion(Long id) {
        Cancion cancion = cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion", "ID", id));
        cancion.setActive(false);
        cancionRepository.save(cancion);
    }
}

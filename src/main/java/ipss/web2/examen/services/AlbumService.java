package ipss.web2.examen.services;

import ipss.web2.examen.dtos.AlbumRequestDTO;
import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.AlbumMapper;
import ipss.web2.examen.models.Album;
import ipss.web2.examen.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
    
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    
    // Crear un nuevo album
    public AlbumResponseDTO crearAlbum(AlbumRequestDTO requestDTO) {
        Album album = albumMapper.toEntity(requestDTO);
        Album albumGuardado = albumRepository.save(album);
        return albumMapper.toResponseDTO(albumGuardado);
    }
    
    // Obtener un album por ID
    @Transactional(readOnly = true)
    public AlbumResponseDTO obtenerAlbumPorId(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
        return albumMapper.toResponseDTO(album);
    }
    
    // Obtener todos los albums activos
    @Transactional(readOnly = true)
    public List<AlbumResponseDTO> obtenerTodosLosAlbums() {
        return albumRepository.findByActiveTrue()
                .stream()
                .map(albumMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
    
    // Actualizar un album existente
    public AlbumResponseDTO actualizarAlbum(Long id, AlbumRequestDTO requestDTO) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
        
        albumMapper.updateEntity(requestDTO, album);
        Album albumActualizado = albumRepository.save(album);
        return albumMapper.toResponseDTO(albumActualizado);
    }
    
    // Eliminar (desactivar) un album
    public void eliminarAlbum(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
        
        album.setActive(false);
        albumRepository.save(album);
    }
    
    // Obtener la entidad Album por ID (uso interno)
    @Transactional(readOnly = true)
    public Album obtenerAlbumEntityPorId(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
    }
}

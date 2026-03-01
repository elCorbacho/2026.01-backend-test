package ipss.web2.examen.services;

import ipss.web2.examen.dtos.GanadorAlbumDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.GanadorAlbumMapper;
import ipss.web2.examen.models.GanadorAlbum;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.GanadorAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class GanadorAlbumService {

    private final GanadorAlbumRepository ganadorAlbumRepository;
    private final AlbumRepository albumRepository;
    private final GanadorAlbumMapper ganadorAlbumMapper;

    @Transactional(readOnly = true)
    public List<GanadorAlbumDTO> obtenerGanadoresPorAlbum(Long albumId) {
        albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", albumId));

        List<GanadorAlbum> ganadores = ganadorAlbumRepository.findByAlbumIdAndActiveTrueOrderByAnioDesc(albumId);
        return ganadores.stream()
                .map(ganadorAlbumMapper::toDTO)
                .collect(Collectors.toList());
    }
}

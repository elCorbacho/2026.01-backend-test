package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.CancionRequestDTO;
import ipss.web2.examen.dtos.CancionResponseDTO;
import ipss.web2.examen.models.Cancion;
import org.springframework.stereotype.Component;

@Component
public class CancionMapper {

    public CancionResponseDTO toResponseDTO(Cancion cancion) {
        return CancionResponseDTO.builder()
                .id(cancion.getId())
                .titulo(cancion.getTitulo())
                .artista(cancion.getArtista())
                .duracion(cancion.getDuracion())
                .genero(cancion.getGenero())
                .createdAt(cancion.getCreatedAt())
                .updatedAt(cancion.getUpdatedAt())
                .build();
    }

    public Cancion toEntity(CancionRequestDTO requestDTO) {
        return Cancion.builder()
                .titulo(requestDTO.getTitulo())
                .artista(requestDTO.getArtista())
                .duracion(requestDTO.getDuracion())
                .genero(requestDTO.getGenero())
                .active(true)
                .build();
    }

    public void updateEntity(CancionRequestDTO requestDTO, Cancion cancion) {
        cancion.setTitulo(requestDTO.getTitulo());
        cancion.setArtista(requestDTO.getArtista());
        cancion.setDuracion(requestDTO.getDuracion());
        cancion.setGenero(requestDTO.getGenero());
    }
}

package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.GanadorAlbumDTO;
import ipss.web2.examen.models.GanadorAlbum;
import org.springframework.stereotype.Component;

@Component
public class GanadorAlbumMapper {

    public GanadorAlbumDTO toDTO(GanadorAlbum ganadorAlbum) {
        return GanadorAlbumDTO.builder()
                .artista(ganadorAlbum.getArtista())
                .premio(ganadorAlbum.getPremio())
                .anio(ganadorAlbum.getAnio())
                .build();
    }
}

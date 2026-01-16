package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.AlbumBasicResponseDTO;
import ipss.web2.examen.dtos.LaminaCatalogoRequestDTO;
import ipss.web2.examen.dtos.LaminaCatalogoResponseDTO;
import ipss.web2.examen.dtos.LaminaRequestDTO;
import ipss.web2.examen.dtos.LaminaResponseDTO;
import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.Lamina;
import ipss.web2.examen.models.LaminaCatalogo;
import org.springframework.stereotype.Component;


// Mapper para convertir entre Lamina, LaminaRequestDTO, LaminaResponseDTO,
// LaminaCatalogoRequestDTO, LaminaCatalogoResponseDTO
@Component
public class LaminaMapper {
    
    public LaminaResponseDTO toResponseDTO(Lamina lamina) {
        if (lamina == null) {
            return null;
        }
        
        LaminaResponseDTO dto = new LaminaResponseDTO();
        dto.setId(lamina.getId());
        dto.setNombre(lamina.getNombre());
        dto.setImagen(lamina.getImagen());
        dto.setFechaLanzamiento(lamina.getFechaLanzamiento());
        dto.setTipoLamina(lamina.getTipoLamina());
        dto.setCreatedAt(lamina.getCreatedAt());
        dto.setUpdatedAt(lamina.getUpdatedAt());
        dto.setActive(lamina.getActive());
        
        // Mapear el album de forma básica
        if (lamina.getAlbum() != null) {
            AlbumBasicResponseDTO albumBasic = new AlbumBasicResponseDTO(
                    lamina.getAlbum().getId(),
                    lamina.getAlbum().getNombre(),
                    lamina.getAlbum().getYear()
            );
            dto.setAlbum(albumBasic);
        }
        
        return dto;
    }
    
    public Lamina toEntity(LaminaRequestDTO dto, Album album) {
        if (dto == null) {
            return null;
        }
        
        Lamina lamina = new Lamina();
        lamina.setNombre(dto.getNombre());
        lamina.setImagen(dto.getImagen());
        lamina.setFechaLanzamiento(dto.getFechaLanzamiento());
        lamina.setTipoLamina(dto.getTipoLamina());
        lamina.setAlbum(album);
        lamina.setActive(true);
        
        return lamina;
    }
    
    public void updateEntity(LaminaRequestDTO dto, Lamina lamina, Album album) {
        if (dto == null) {
            return;
        }
        
        lamina.setNombre(dto.getNombre());
        lamina.setImagen(dto.getImagen());
        lamina.setFechaLanzamiento(dto.getFechaLanzamiento());
        lamina.setTipoLamina(dto.getTipoLamina());
        lamina.setAlbum(album);
    }
    
    public LaminaCatalogo toCatalogoEntity(LaminaCatalogoRequestDTO dto, Album album) {
        if (dto == null) {
            return null;
        }
        
        LaminaCatalogo catalogo = new LaminaCatalogo();
        catalogo.setNombre(dto.nombre());
        catalogo.setImagen(dto.imagen());
        catalogo.setFechaLanzamiento(dto.fechaLanzamiento());
        catalogo.setTipoLamina(dto.tipoLamina());
        catalogo.setAlbum(album);
        catalogo.setActive(true);
        
        return catalogo;
    }
    
    public LaminaCatalogoResponseDTO toCatalogoResponseDTO(LaminaCatalogo entity) {
        if (entity == null) {
            return null;
        }
        
        return new LaminaCatalogoResponseDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getImagen(),
            entity.getFechaLanzamiento(),
            entity.getTipoLamina(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.getActive()
        );
    }
}

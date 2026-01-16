package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.LaminaCatalogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


// Repositorio para la entidad Lámina de Catálogo
public interface LaminaCatalogoRepository extends JpaRepository<LaminaCatalogo, Long> {
    List<LaminaCatalogo> findByAlbumAndActiveTrue(Album album);
    Optional<LaminaCatalogo> findByAlbumAndNombreAndActiveTrue(Album album, String nombre);
    long countByAlbumAndActiveTrue(Album album);
}

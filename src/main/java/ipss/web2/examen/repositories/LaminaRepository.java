package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.Lamina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


// Repositorio para la entidad Lámina
@Repository
public interface LaminaRepository extends JpaRepository<Lamina, Long> {
    List<Lamina> findByAlbumIdAndActiveTrue(Long albumId);
    List<Lamina> findByActiveTrue();
    List<Lamina> findByAlbumAndActiveTrue(Album album);
    List<Lamina> findByAlbumAndNombreAndActiveTrue(Album album, String nombre);
    long countByAlbumAndActiveTrue(Album album);

    @Query("select count(distinct l.nombre) from Lamina l where l.album = :album and l.active = true")
    long countDistinctNombreByAlbumAndActiveTrue(@Param("album") Album album);
}

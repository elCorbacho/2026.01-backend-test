package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// Repositorio para la entidad Álbum
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByActiveTrue();
    List<Album> findByActive(Boolean active);
    List<Album> findByYearAndActive(Integer year, Boolean active);
    List<Album> findByYearAndActiveTrue(Integer year);
    java.util.Optional<Album> findByIdAndActiveTrue(Long id);
}

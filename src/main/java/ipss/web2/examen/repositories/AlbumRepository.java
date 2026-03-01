package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// Repositorio para la entidad Álbum
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByActiveTrue();
    List<Album> findByActive(Boolean active);
    List<Album> findByYearAndActive(Integer year, Boolean active);
    List<Album> findByYearAndActiveTrue(Integer year);
    Optional<Album> findByIdAndActiveTrue(Long id);

    Page<Album> findByActiveTrue(Pageable pageable);

    Page<Album> findByActive(Boolean active, Pageable pageable);

    Page<Album> findByYearAndActive(Integer year, Boolean active, Pageable pageable);

    Page<Album> findByYearAndActiveTrue(Integer year, Pageable pageable);
}

package ipss.web2.examen.repositories;

import ipss.web2.examen.models.GanadorPremioAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GanadorPremioAlbumRepository extends JpaRepository<GanadorPremioAlbum, Long> {
    List<GanadorPremioAlbum> findByActiveTrue();
}

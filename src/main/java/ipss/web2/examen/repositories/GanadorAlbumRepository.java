package ipss.web2.examen.repositories;

import ipss.web2.examen.models.GanadorAlbum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GanadorAlbumRepository extends JpaRepository<GanadorAlbum, Long> {
    List<GanadorAlbum> findByAlbumIdAndActiveTrueOrderByAnioDesc(Long albumId);
}

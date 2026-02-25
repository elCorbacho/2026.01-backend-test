package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Long> {
    List<Cancion> findByActiveTrue();
    List<Cancion> findByArtistaContainingIgnoreCaseAndActiveTrue(String artista);
}

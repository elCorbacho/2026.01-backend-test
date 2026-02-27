package ipss.web2.examen.repositories;

import ipss.web2.examen.models.PresidenteChile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para la entidad PresidenteChile
@Repository
public interface PresidenteChileRepository extends JpaRepository<PresidenteChile, Long> {
    List<PresidenteChile> findByActiveTrue();
}

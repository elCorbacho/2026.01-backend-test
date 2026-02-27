package ipss.web2.examen.repositories;

import ipss.web2.examen.models.MinaChile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinaChileRepository extends JpaRepository<MinaChile, Long> {

    // Buscar minas de Chile activas
    List<MinaChile> findByActiveTrue();
}


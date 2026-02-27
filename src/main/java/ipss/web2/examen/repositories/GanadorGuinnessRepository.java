package ipss.web2.examen.repositories;

import ipss.web2.examen.models.GanadorGuinness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GanadorGuinnessRepository extends JpaRepository<GanadorGuinness, Long> {
    // Buscar ganadores activos
    List<GanadorGuinness> findByActiveTrue();
}

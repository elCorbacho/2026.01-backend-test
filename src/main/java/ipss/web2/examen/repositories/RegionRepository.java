package ipss.web2.examen.repositories;

import ipss.web2.examen.models.RegionChile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionChile, Long> {
    List<RegionChile> findByActiveTrueOrderByCodigoAsc();
}

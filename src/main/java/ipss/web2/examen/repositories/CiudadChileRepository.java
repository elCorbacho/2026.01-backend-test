package ipss.web2.examen.repositories;

import ipss.web2.examen.models.CiudadChile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CiudadChileRepository extends JpaRepository<CiudadChile, Long> {

    List<CiudadChile> findByActiveTrueOrderByNombreAsc();

    Optional<CiudadChile> findByIdAndActiveTrue(Long id);
}

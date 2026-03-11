package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Exoplaneta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para la entidad Exoplaneta
@Repository
public interface ExoplanetaRepository extends JpaRepository<Exoplaneta, Long> {

    Optional<Exoplaneta> findByIdAndActiveTrue(Long id);

    Page<Exoplaneta> findByActiveTrueOrderByNombreAsc(Pageable pageable);
}

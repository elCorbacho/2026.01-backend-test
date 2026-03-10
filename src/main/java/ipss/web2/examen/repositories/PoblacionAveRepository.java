package ipss.web2.examen.repositories;

import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PoblacionAveRepository extends JpaRepository<PoblacionAve, Long> {

    List<PoblacionAve> findByTipoAveAndActiveTrue(TipoAve tipoAve);

    @EntityGraph(attributePaths = "tipoAve")
    Page<PoblacionAve> findByActiveTrue(Pageable pageable);

    @EntityGraph(attributePaths = "tipoAve")
    Page<PoblacionAve> findByTipoAveIdAndActiveTrue(Long tipoAveId, Pageable pageable);

    Optional<PoblacionAve> findByIdAndActiveTrue(Long id);
}

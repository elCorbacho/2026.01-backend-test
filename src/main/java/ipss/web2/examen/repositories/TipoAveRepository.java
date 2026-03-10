package ipss.web2.examen.repositories;

import ipss.web2.examen.models.TipoAve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoAveRepository extends JpaRepository<TipoAve, Long> {

    List<TipoAve> findByActiveTrue();

    Page<TipoAve> findByActiveTrue(Pageable pageable);

    Optional<TipoAve> findByIdAndActiveTrue(Long id);
}

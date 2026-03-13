package ipss.web2.examen.repositories;

import ipss.web2.examen.models.EfemerideChile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EfemerideChileRepository extends JpaRepository<EfemerideChile, Long> {
    List<EfemerideChile> findByActiveTrue();
    Optional<EfemerideChile> findByIdAndActiveTrue(Long id);
}

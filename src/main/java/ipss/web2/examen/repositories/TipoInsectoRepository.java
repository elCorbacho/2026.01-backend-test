package ipss.web2.examen.repositories;

import ipss.web2.examen.models.TipoInsecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoInsectoRepository extends JpaRepository<TipoInsecto, Long>
{

    List<TipoInsecto> findByActiveTrueOrderByNombreAsc();

    Optional<TipoInsecto> findByIdAndActiveTrue(Long id);
}

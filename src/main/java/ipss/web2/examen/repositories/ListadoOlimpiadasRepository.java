package ipss.web2.examen.repositories;

import ipss.web2.examen.models.ListadoOlimpiadas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Repositorio para listado de olimpiadas
public interface ListadoOlimpiadasRepository extends JpaRepository<ListadoOlimpiadas, Long> {

    List<ListadoOlimpiadas> findByActiveTrue();

    Optional<ListadoOlimpiadas> findByIdAndActiveTrue(Long id);
}

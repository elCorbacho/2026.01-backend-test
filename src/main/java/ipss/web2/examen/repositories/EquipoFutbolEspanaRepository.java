package ipss.web2.examen.repositories;

import ipss.web2.examen.models.EquipoFutbolEspana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoFutbolEspanaRepository extends JpaRepository<EquipoFutbolEspana, Long> {
    List<EquipoFutbolEspana> findByActiveTrue();
    Optional<EquipoFutbolEspana> findByIdAndActiveTrue(Long id);
}

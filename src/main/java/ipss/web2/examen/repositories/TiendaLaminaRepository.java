package ipss.web2.examen.repositories;

import ipss.web2.examen.models.TiendaLamina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiendaLaminaRepository extends JpaRepository<TiendaLamina, Long> {

    List<TiendaLamina> findByActiveTrue();

    List<TiendaLamina> findByCiudadContainingIgnoreCaseAndActiveTrue(String ciudad);
}


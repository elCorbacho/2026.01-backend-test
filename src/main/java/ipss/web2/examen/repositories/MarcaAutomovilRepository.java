package ipss.web2.examen.repositories;

import ipss.web2.examen.models.MarcaAutomovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaAutomovilRepository extends JpaRepository<MarcaAutomovil, Long> {
    List<MarcaAutomovil> findByActiveTrueOrderByNombreAsc();
}

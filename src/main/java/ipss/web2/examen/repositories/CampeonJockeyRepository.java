package ipss.web2.examen.repositories;

import ipss.web2.examen.models.CampeonJockey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampeonJockeyRepository extends JpaRepository<CampeonJockey, Long> {

    // Buscar campeones de jockey activos
    List<CampeonJockey> findByActiveTrue();
}


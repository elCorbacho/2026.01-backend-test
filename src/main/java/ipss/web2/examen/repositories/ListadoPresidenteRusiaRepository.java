package ipss.web2.examen.repositories;

import ipss.web2.examen.models.ListadoPresidenteRusia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para la entidad ListadoPresidenteRusia
@Repository
public interface ListadoPresidenteRusiaRepository extends JpaRepository<ListadoPresidenteRusia, Long> {
    List<ListadoPresidenteRusia> findByActiveTrue();
}

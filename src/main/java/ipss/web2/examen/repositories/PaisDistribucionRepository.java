package ipss.web2.examen.repositories;

import ipss.web2.examen.models.PaisDistribucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PaisDistribucionRepository extends JpaRepository<PaisDistribucion, Long> {
    Page<PaisDistribucion> findByActiveTrue(Pageable pageable);

    Page<PaisDistribucion> findByNombreContainingIgnoreCaseAndActiveTrue(String nombre, Pageable pageable);

    List<PaisDistribucion> findByActiveTrue();

    List<PaisDistribucion> findByNombreContainingIgnoreCaseAndActiveTrue(String nombre);
}

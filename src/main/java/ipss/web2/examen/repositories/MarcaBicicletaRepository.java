package ipss.web2.examen.repositories;

import ipss.web2.examen.models.MarcaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaBicicletaRepository extends JpaRepository<MarcaBicicleta, Long> {
    List<MarcaBicicleta> findByActiveTrueOrderByNombreAsc();
}

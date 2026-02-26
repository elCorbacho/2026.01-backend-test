package ipss.web2.examen.repositories;

import ipss.web2.examen.models.EmpresaInsumos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaInsumosRepository extends JpaRepository<EmpresaInsumos, Long> {

    List<EmpresaInsumos> findByActiveTrue();

    List<EmpresaInsumos> findByRubroContainingIgnoreCaseAndActiveTrue(String rubro);
}


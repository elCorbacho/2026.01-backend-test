package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para operaciones sobre la entidad {@link Transportista}.
 * Utiliza soft-delete: los registros inactivos no deben exponerse a través de la API.
 */
@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

    /** Devuelve todos los transportistas cuyo campo active = true. */
    List<Transportista> findByActiveTrue();

    /** Busca un transportista activo por su ID. */
    Optional<Transportista> findByIdAndActiveTrue(Long id);
}

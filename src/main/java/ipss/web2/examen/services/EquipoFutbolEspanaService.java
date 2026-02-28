package ipss.web2.examen.services;

import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipoFutbolEspanaService {
    private final EquipoFutbolEspanaRepository repository;

    public List<EquipoFutbolEspana> obtenerEquiposActivos() {
        return repository.findByActivoTrue();
    }
}

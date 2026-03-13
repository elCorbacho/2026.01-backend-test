package ipss.web2.examen.services;

import ipss.web2.examen.dtos.EquipoFutbolEspanaResponseDTO;
import ipss.web2.examen.mappers.EquipoFutbolEspanaMapper;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipoFutbolEspanaService {
    private final EquipoFutbolEspanaRepository repository;
    private final EquipoFutbolEspanaMapper mapper;

    @Transactional(readOnly = true)
    public List<EquipoFutbolEspanaResponseDTO> obtenerEquiposActivos() {
        return repository.findByActiveTrue()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

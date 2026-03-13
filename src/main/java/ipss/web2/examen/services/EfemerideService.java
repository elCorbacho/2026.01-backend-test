package ipss.web2.examen.services;

import ipss.web2.examen.dtos.EfemerideRequestDTO;
import ipss.web2.examen.dtos.EfemerideResponseDTO;
import ipss.web2.examen.mappers.EfemerideMapper;
import ipss.web2.examen.models.EfemerideChile;
import ipss.web2.examen.repositories.EfemerideChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EfemerideService {

    private final EfemerideChileRepository repository;
    private final EfemerideMapper mapper;

    @Transactional(readOnly = true)
    public List<EfemerideResponseDTO> listarEfemerides() {
        List<EfemerideChile> list = repository.findByActiveTrue();
        return list.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }

    public EfemerideResponseDTO crearEfemeride(EfemerideRequestDTO req) {
        EfemerideChile entity = mapper.toEntity(req);
        EfemerideChile saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }
}

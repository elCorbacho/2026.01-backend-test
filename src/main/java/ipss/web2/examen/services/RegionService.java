package ipss.web2.examen.services;

import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.mappers.RegionMapper;
import ipss.web2.examen.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Transactional(readOnly = true)
    public List<RegionResponseDTO> obtenerRegionesActivas() {
        return regionRepository.findByActiveTrueOrderByCodigoAsc()
                .stream()
                .map(regionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}

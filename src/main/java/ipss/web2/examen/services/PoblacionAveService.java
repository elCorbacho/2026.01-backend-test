package ipss.web2.examen.services;

import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.dtos.PoblacionAvePageResponseDTO;
import ipss.web2.examen.dtos.PoblacionAveResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.PoblacionAveMapper;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestiona operaciones de registros de poblacion de aves.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PoblacionAveService {

    private final PoblacionAveRepository poblacionAveRepository;
    private final TipoAveService tipoAveService;
    private final PoblacionAveMapper poblacionAveMapper;

    public PoblacionAveResponseDTO crearPoblacionAve(PoblacionAveRequestDTO requestDTO) {
        TipoAve tipoAve = tipoAveService.obtenerTipoAveEntityActivo(requestDTO.getTipoAveId());
        PoblacionAve entity = poblacionAveMapper.toEntity(requestDTO, tipoAve);
        PoblacionAve guardada = poblacionAveRepository.save(entity);
        return poblacionAveMapper.toResponseDTO(guardada);
    }

    @Transactional(readOnly = true)
    public PoblacionAveResponseDTO obtenerPoblacionAvePorId(Long id) {
        PoblacionAve poblacion = poblacionAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("PoblacionAve", "ID", id, "POBLACION_AVE_NOT_FOUND"));
        return poblacionAveMapper.toResponseDTO(poblacion);
    }

    /**
     * Lista poblaciones activas y permite filtrar por tipo de ave de forma opcional.
     */
    @Transactional(readOnly = true)
    public PoblacionAvePageResponseDTO obtenerPoblacionesPaginadas(Integer page, Integer size, Long tipoAveId) {
        Pageable pageable = construirPageable(page, size);
        Page<PoblacionAve> poblacionPage;

        if (tipoAveId == null) {
            poblacionPage = poblacionAveRepository.findByActiveTrue(pageable);
        } else {
            tipoAveService.obtenerTipoAveEntityActivo(tipoAveId);
            poblacionPage = poblacionAveRepository.findByTipoAveIdAndActiveTrue(tipoAveId, pageable);
        }

        List<PoblacionAveResponseDTO> content = poblacionPage.getContent().stream()
                .map(poblacionAveMapper::toResponseDTO)
                .collect(Collectors.toList());

        return new PoblacionAvePageResponseDTO(
                content,
                poblacionPage.getNumber(),
                poblacionPage.getSize(),
                poblacionPage.getTotalElements(),
                poblacionPage.getTotalPages()
        );
    }

    public PoblacionAveResponseDTO actualizarPoblacionAve(Long id, PoblacionAveRequestDTO requestDTO) {
        PoblacionAve poblacion = poblacionAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("PoblacionAve", "ID", id, "POBLACION_AVE_NOT_FOUND"));

        TipoAve tipoAve = tipoAveService.obtenerTipoAveEntityActivo(requestDTO.getTipoAveId());
        poblacionAveMapper.updateEntity(requestDTO, poblacion, tipoAve);

        PoblacionAve actualizada = poblacionAveRepository.save(poblacion);
        return poblacionAveMapper.toResponseDTO(actualizada);
    }

    public void eliminarPoblacionAve(Long id) {
        PoblacionAve poblacion = poblacionAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("PoblacionAve", "ID", id, "POBLACION_AVE_NOT_FOUND"));
        poblacion.setActive(false);
        poblacionAveRepository.save(poblacion);
    }

    /**
     * Construye paginacion con valores por defecto y validaciones de rango.
     */
    private Pageable construirPageable(Integer page, Integer size) {
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        if (pageNumber < 0) {
            throw new InvalidOperationException("El parametro 'page' debe ser mayor o igual a 0", "INVALID_PAGE");
        }

        if (pageSize < 1 || pageSize > 100) {
            throw new InvalidOperationException("El parametro 'size' debe estar entre 1 y 100", "INVALID_SIZE");
        }

        return PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
    }
}

package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAvePageResponseDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TipoAveMapper;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
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
 * Gestiona operaciones del catalogo de tipos de ave.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TipoAveService {

    private final TipoAveRepository tipoAveRepository;
    private final PoblacionAveRepository poblacionAveRepository;
    private final TipoAveMapper tipoAveMapper;

    public TipoAveResponseDTO crearTipoAve(TipoAveRequestDTO requestDTO) {
        TipoAve tipoAve = tipoAveMapper.toEntity(requestDTO);
        TipoAve guardado = tipoAveRepository.save(tipoAve);
        return tipoAveMapper.toResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    public TipoAveResponseDTO obtenerTipoAvePorId(Long id) {
        TipoAve tipoAve = tipoAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoAve", "ID", id, "TIPO_AVE_NOT_FOUND"));
        return tipoAveMapper.toResponseDTO(tipoAve);
    }

    @Transactional(readOnly = true)
    public TipoAvePageResponseDTO obtenerTiposAvePaginados(Integer page, Integer size) {
        Pageable pageable = construirPageable(page, size);
        Page<TipoAve> tipoAvePage = tipoAveRepository.findByActiveTrue(pageable);
        List<TipoAveResponseDTO> content = tipoAvePage.getContent().stream()
                .map(tipoAveMapper::toResponseDTO)
                .collect(Collectors.toList());

        return new TipoAvePageResponseDTO(
                content,
                tipoAvePage.getNumber(),
                tipoAvePage.getSize(),
                tipoAvePage.getTotalElements(),
                tipoAvePage.getTotalPages()
        );
    }

    public TipoAveResponseDTO actualizarTipoAve(Long id, TipoAveRequestDTO requestDTO) {
        TipoAve tipoAve = tipoAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoAve", "ID", id, "TIPO_AVE_NOT_FOUND"));

        tipoAveMapper.updateEntity(requestDTO, tipoAve);
        TipoAve actualizado = tipoAveRepository.save(tipoAve);
        return tipoAveMapper.toResponseDTO(actualizado);
    }

    /**
     * Realiza soft delete del tipo y de sus poblaciones activas relacionadas.
     */
    public void eliminarTipoAve(Long id) {
        TipoAve tipoAve = tipoAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoAve", "ID", id, "TIPO_AVE_NOT_FOUND"));

        List<PoblacionAve> poblacionesActivas = poblacionAveRepository.findByTipoAveAndActiveTrue(tipoAve);
        for (PoblacionAve poblacion : poblacionesActivas) {
            poblacion.setActive(false);
        }
        if (!poblacionesActivas.isEmpty()) {
            poblacionAveRepository.saveAll(poblacionesActivas);
        }

        tipoAve.setActive(false);
        tipoAveRepository.save(tipoAve);
    }

    @Transactional(readOnly = true)
    public TipoAve obtenerTipoAveEntityActivo(Long id) {
        return tipoAveRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoAve", "ID", id, "TIPO_AVE_NOT_FOUND"));
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

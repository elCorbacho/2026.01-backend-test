package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TipoInsectoResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TipoInsectoMapper;
import ipss.web2.examen.models.TipoInsecto;
import ipss.web2.examen.repositories.TipoInsectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TipoInsectoService
{

    private final TipoInsectoRepository tipoInsectoRepository;
    private final TipoInsectoMapper tipoInsectoMapper;

    @Transactional(readOnly = true)
    public List<TipoInsectoResponseDTO> obtenerTiposInsecto()
    {
        return tipoInsectoRepository.findByActiveTrueOrderByNombreAsc()
                .stream()
                .map(tipoInsectoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TipoInsectoResponseDTO obtenerTipoInsectoPorId(Long id)
    {
        validarId(id);
        TipoInsecto entity = obtenerTipoInsectoEntityActivo(id);
        return tipoInsectoMapper.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public TipoInsecto obtenerTipoInsectoEntityActivo(Long id)
    {
        return tipoInsectoRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoInsecto", "id", id, "TIPO_INSECTO_NOT_FOUND"));
    }

    private void validarId(Long id)
    {
        if (id == null || id < 1) {
            throw new InvalidOperationException("El identificador debe ser mayor o igual a 1", "INVALID_INSECTO_ID");
        }
    }
}

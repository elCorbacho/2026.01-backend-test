package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ListadoOlimpiadasRequestDTO;
import ipss.web2.examen.dtos.ListadoOlimpiadasResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ListadoOlimpiadasMapper;
import ipss.web2.examen.models.ListadoOlimpiadas;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class ListadoOlimpiadasService {

    private final ListadoOlimpiadasRepository listadoOlimpiadasRepository;
    private final ListadoOlimpiadasMapper listadoOlimpiadasMapper;

    // Crear nuevo listado de olimpiadas
    public ListadoOlimpiadasResponseDTO crearListadoOlimpiadas(ListadoOlimpiadasRequestDTO requestDTO) {
        ListadoOlimpiadas entity = listadoOlimpiadasMapper.toEntity(requestDTO);
        return listadoOlimpiadasMapper.toResponseDTO(listadoOlimpiadasRepository.save(entity));
    }

    // Listar listados de olimpiadas activos
    @Transactional(readOnly = true)
    public List<ListadoOlimpiadasResponseDTO> obtenerListadoOlimpiadas() {
        return listadoOlimpiadasRepository.findByActiveTrue()
                .stream()
                .map(listadoOlimpiadasMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener listado de olimpiadas por ID
    @Transactional(readOnly = true)
    public ListadoOlimpiadasResponseDTO obtenerListadoOlimpiadasPorId(Long id) {
        ListadoOlimpiadas listado = listadoOlimpiadasRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ListadoOlimpiadas", "ID", id));
        return listadoOlimpiadasMapper.toResponseDTO(listado);
    }

    // Actualizar listado de olimpiadas
    public ListadoOlimpiadasResponseDTO actualizarListadoOlimpiadas(Long id, ListadoOlimpiadasRequestDTO requestDTO) {
        ListadoOlimpiadas listado = listadoOlimpiadasRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ListadoOlimpiadas", "ID", id));
        listadoOlimpiadasMapper.updateEntity(requestDTO, listado);
        return listadoOlimpiadasMapper.toResponseDTO(listadoOlimpiadasRepository.save(listado));
    }

    // Eliminar listado de olimpiadas (soft delete)
    public void eliminarListadoOlimpiadas(Long id) {
        ListadoOlimpiadas listado = listadoOlimpiadasRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("ListadoOlimpiadas", "ID", id));
        listado.setActive(false);
        listadoOlimpiadasRepository.save(listado);
    }
}

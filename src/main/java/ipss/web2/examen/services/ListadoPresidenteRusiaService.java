package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.mappers.ListadoPresidenteRusiaMapper;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class ListadoPresidenteRusiaService {

    private final ListadoPresidenteRusiaRepository listadoPresidenteRusiaRepository;
    private final ListadoPresidenteRusiaMapper listadoPresidenteRusiaMapper;

    // Obtener listado de presidentes de Rusia activos
    @Transactional(readOnly = true)
    public List<ListadoPresidenteRusiaResponseDTO> obtenerPresidentesRusia() {
        List<ListadoPresidenteRusia> presidentes = listadoPresidenteRusiaRepository.findByActiveTrue();
        return presidentes.stream()
                .map(listadoPresidenteRusiaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Crear nuevo presidente de Rusia
    public ListadoPresidenteRusiaResponseDTO crearPresidenteRusia(ListadoPresidenteRusiaRequestDTO requestDTO) {
        ListadoPresidenteRusia nuevo = listadoPresidenteRusiaMapper.toEntity(requestDTO);
        ListadoPresidenteRusia guardado = listadoPresidenteRusiaRepository.save(nuevo);
        return listadoPresidenteRusiaMapper.toResponseDTO(guardado);
    }
}

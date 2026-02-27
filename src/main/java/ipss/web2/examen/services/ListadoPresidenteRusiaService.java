package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaPatchRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ListadoPresidenteRusiaMapper;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Transactional(readOnly = true)
    public ListadoPresidenteRusiaResponseDTO obtenerPresidenteRusiaPorId(Long id) {
        ListadoPresidenteRusia presidente = obtenerActivoOrThrow(id);
        return listadoPresidenteRusiaMapper.toResponseDTO(presidente);
    }

    // Crear nuevo presidente de Rusia
    public ListadoPresidenteRusiaResponseDTO crearPresidenteRusia(ListadoPresidenteRusiaRequestDTO requestDTO) {
        ListadoPresidenteRusia nuevo = listadoPresidenteRusiaMapper.toEntity(requestDTO);
        ListadoPresidenteRusia guardado = listadoPresidenteRusiaRepository.save(nuevo);
        return listadoPresidenteRusiaMapper.toResponseDTO(guardado);
    }

    public ListadoPresidenteRusiaResponseDTO actualizarPresidenteRusiaParcial(
            Long id,
            ListadoPresidenteRusiaPatchRequestDTO patchDTO) {

        if (patchDTO == null || !patchDTO.hasAnyField()) {
            throw new InvalidOperationException(
                    "Debe proporcionar al menos un campo para actualizar",
                    "PRESIDENT_PATCH_EMPTY");
        }

        ListadoPresidenteRusia existente = obtenerActivoOrThrow(id);
        validarFechas(patchDTO, existente);
        listadoPresidenteRusiaMapper.applyPatch(patchDTO, existente);
        ListadoPresidenteRusia actualizado = listadoPresidenteRusiaRepository.save(existente);
        return listadoPresidenteRusiaMapper.toResponseDTO(actualizado);
    }

    public void eliminarPresidenteRusia(Long id) {
        ListadoPresidenteRusia existente = obtenerActivoOrThrow(id);
        existente.setActive(false);
        listadoPresidenteRusiaRepository.save(existente);
    }

    private ListadoPresidenteRusia obtenerActivoOrThrow(Long id) {
        return listadoPresidenteRusiaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Presidente de Rusia",
                        "id",
                        id,
                        "PRESIDENT_NOT_FOUND"));
    }

    private void validarFechas(ListadoPresidenteRusiaPatchRequestDTO patchDTO, ListadoPresidenteRusia existente) {
        LocalDate inicio = patchDTO.getPeriodoInicio() != null ? patchDTO.getPeriodoInicio() : existente.getPeriodoInicio();
        LocalDate fin = patchDTO.getPeriodoFin() != null ? patchDTO.getPeriodoFin() : existente.getPeriodoFin();

        if (fin != null && inicio != null && fin.isBefore(inicio)) {
            throw new InvalidOperationException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio",
                    "PRESIDENT_INVALID_TERM_RANGE");
        }
    }
}

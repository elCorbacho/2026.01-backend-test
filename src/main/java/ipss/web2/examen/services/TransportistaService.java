package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TransportistaRequestDTO;
import ipss.web2.examen.dtos.TransportistaResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TransportistaMapper;
import ipss.web2.examen.models.Transportista;
import ipss.web2.examen.repositories.TransportistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para la gestión de {@link Transportista}.
 * Toda la lógica de negocio vive aquí; el controlador sólo delega.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;
    private final TransportistaMapper transportistaMapper;

    // ─── Queries (read-only) ──────────────────────────────────────────────────

    /**
     * Devuelve todos los transportistas activos.
     *
     * @return lista de DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<TransportistaResponseDTO> obtenerTodos() {
        return transportistaRepository.findByActiveTrue()
                .stream()
                .map(transportistaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve un transportista activo por su ID.
     *
     * @param id identificador del transportista
     * @return DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra o está inactivo
     */
    @Transactional(readOnly = true)
    public TransportistaResponseDTO obtenerPorId(Long id) {
        Transportista transportista = transportistaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportista", "ID", id));
        return transportistaMapper.toResponseDTO(transportista);
    }

    // ─── Commands ────────────────────────────────────────────────────────────

    /**
     * Crea un nuevo transportista.
     *
     * @param requestDTO datos del transportista a crear
     * @return DTO de respuesta del transportista creado
     */
    public TransportistaResponseDTO crear(TransportistaRequestDTO requestDTO) {
        Transportista transportista = transportistaMapper.toEntity(requestDTO);
        Transportista saved = transportistaRepository.save(transportista);
        return transportistaMapper.toResponseDTO(saved);
    }

    /**
     * Actualiza completamente un transportista existente (PUT).
     *
     * @param id         ID del transportista a actualizar
     * @param requestDTO nuevos datos
     * @return DTO de respuesta actualizado
     * @throws ResourceNotFoundException si el transportista no existe o está inactivo
     */
    public TransportistaResponseDTO actualizar(Long id, TransportistaRequestDTO requestDTO) {
        Transportista transportista = transportistaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportista", "ID", id));
        transportistaMapper.updateEntity(requestDTO, transportista);
        Transportista saved = transportistaRepository.save(transportista);
        return transportistaMapper.toResponseDTO(saved);
    }

    /**
     * Realiza un soft-delete del transportista (marca {@code active = false}).
     *
     * @param id ID del transportista a eliminar
     * @throws ResourceNotFoundException si el transportista no existe o ya está inactivo
     */
    public void eliminar(Long id) {
        Transportista transportista = transportistaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transportista", "ID", id));
        transportista.setActive(false);
        transportistaRepository.save(transportista);
    }
}

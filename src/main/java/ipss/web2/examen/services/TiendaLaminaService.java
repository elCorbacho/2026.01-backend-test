package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TiendaLaminaRequestDTO;
import ipss.web2.examen.dtos.TiendaLaminaResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TiendaLaminaMapper;
import ipss.web2.examen.models.TiendaLamina;
import ipss.web2.examen.repositories.TiendaLaminaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class TiendaLaminaService {

    private final TiendaLaminaRepository tiendaLaminaRepository;
    private final TiendaLaminaMapper tiendaLaminaMapper;

    public TiendaLaminaResponseDTO crearTienda(TiendaLaminaRequestDTO requestDTO) {
        TiendaLamina tienda = tiendaLaminaMapper.toEntity(requestDTO);
        return tiendaLaminaMapper.toResponseDTO(tiendaLaminaRepository.save(tienda));
    }

    @Transactional(readOnly = true)
    public TiendaLaminaResponseDTO obtenerTiendaPorId(Long id) {
        TiendaLamina tienda = tiendaLaminaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TiendaLamina", "ID", id));
        return tiendaLaminaMapper.toResponseDTO(tienda);
    }

    @Transactional(readOnly = true)
    public List<TiendaLaminaResponseDTO> obtenerTodasLasTiendas() {
        return tiendaLaminaRepository.findByActiveTrue()
                .stream()
                .map(tiendaLaminaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TiendaLaminaResponseDTO> buscarPorCiudad(String ciudad) {
        return tiendaLaminaRepository.findByCiudadContainingIgnoreCaseAndActiveTrue(ciudad)
                .stream()
                .map(tiendaLaminaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TiendaLaminaResponseDTO actualizarTienda(Long id, TiendaLaminaRequestDTO requestDTO) {
        TiendaLamina tienda = tiendaLaminaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TiendaLamina", "ID", id));
        tiendaLaminaMapper.updateEntity(requestDTO, tienda);
        return tiendaLaminaMapper.toResponseDTO(tiendaLaminaRepository.save(tienda));
    }

    public void eliminarTienda(Long id) {
        TiendaLamina tienda = tiendaLaminaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TiendaLamina", "ID", id));
        tienda.setActive(false);
        tiendaLaminaRepository.save(tienda);
    }
}


package ipss.web2.examen.services;

import ipss.web2.examen.dtos.EmpresaInsumosRequestDTO;
import ipss.web2.examen.dtos.EmpresaInsumosResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.EmpresaInsumosMapper;
import ipss.web2.examen.models.EmpresaInsumos;
import ipss.web2.examen.repositories.EmpresaInsumosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaInsumosService {

    private final EmpresaInsumosRepository empresaInsumosRepository;
    private final EmpresaInsumosMapper empresaInsumosMapper;

    public EmpresaInsumosResponseDTO crearEmpresa(EmpresaInsumosRequestDTO requestDTO) {
        EmpresaInsumos empresa = empresaInsumosMapper.toEntity(requestDTO);
        return empresaInsumosMapper.toResponseDTO(empresaInsumosRepository.save(empresa));
    }

    @Transactional(readOnly = true)
    public EmpresaInsumosResponseDTO obtenerEmpresaPorId(Long id) {
        EmpresaInsumos empresa = empresaInsumosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmpresaInsumos", "ID", id));
        return empresaInsumosMapper.toResponseDTO(empresa);
    }

    @Transactional(readOnly = true)
    public List<EmpresaInsumosResponseDTO> obtenerTodasLasEmpresas() {
        return empresaInsumosRepository.findByActiveTrue()
                .stream()
                .map(empresaInsumosMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmpresaInsumosResponseDTO> buscarPorRubro(String rubro) {
        return empresaInsumosRepository.findByRubroContainingIgnoreCaseAndActiveTrue(rubro)
                .stream()
                .map(empresaInsumosMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmpresaInsumosResponseDTO actualizarEmpresa(Long id, EmpresaInsumosRequestDTO requestDTO) {
        EmpresaInsumos empresa = empresaInsumosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmpresaInsumos", "ID", id));
        empresaInsumosMapper.updateEntity(requestDTO, empresa);
        return empresaInsumosMapper.toResponseDTO(empresaInsumosRepository.save(empresa));
    }

    public void eliminarEmpresa(Long id) {
        EmpresaInsumos empresa = empresaInsumosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EmpresaInsumos", "ID", id));
        empresa.setActive(false);
        empresaInsumosRepository.save(empresa);
    }
}


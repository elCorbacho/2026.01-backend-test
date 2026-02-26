package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.EmpresaInsumosRequestDTO;
import ipss.web2.examen.dtos.EmpresaInsumosResponseDTO;
import ipss.web2.examen.models.EmpresaInsumos;
import org.springframework.stereotype.Component;

@Component
public class EmpresaInsumosMapper {

    public EmpresaInsumosResponseDTO toResponseDTO(EmpresaInsumos empresa) {
        return EmpresaInsumosResponseDTO.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .rubro(empresa.getRubro())
                .contacto(empresa.getContacto())
                .telefono(empresa.getTelefono())
                .email(empresa.getEmail())
                .sitioWeb(empresa.getSitioWeb())
                .createdAt(empresa.getCreatedAt())
                .updatedAt(empresa.getUpdatedAt())
                .build();
    }

    public EmpresaInsumos toEntity(EmpresaInsumosRequestDTO requestDTO) {
        return EmpresaInsumos.builder()
                .nombre(requestDTO.getNombre())
                .rubro(requestDTO.getRubro())
                .contacto(requestDTO.getContacto())
                .telefono(requestDTO.getTelefono())
                .email(requestDTO.getEmail())
                .sitioWeb(requestDTO.getSitioWeb())
                .active(true)
                .build();
    }

    public void updateEntity(EmpresaInsumosRequestDTO requestDTO, EmpresaInsumos empresa) {
        empresa.setNombre(requestDTO.getNombre());
        empresa.setRubro(requestDTO.getRubro());
        empresa.setContacto(requestDTO.getContacto());
        empresa.setTelefono(requestDTO.getTelefono());
        empresa.setEmail(requestDTO.getEmail());
        empresa.setSitioWeb(requestDTO.getSitioWeb());
    }
}


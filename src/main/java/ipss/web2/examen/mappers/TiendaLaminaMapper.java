package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.TiendaLaminaRequestDTO;
import ipss.web2.examen.dtos.TiendaLaminaResponseDTO;
import ipss.web2.examen.models.TiendaLamina;
import org.springframework.stereotype.Component;

@Component
public class TiendaLaminaMapper {

    public TiendaLaminaResponseDTO toResponseDTO(TiendaLamina tienda) {
        return TiendaLaminaResponseDTO.builder()
                .id(tienda.getId())
                .nombre(tienda.getNombre())
                .ciudad(tienda.getCiudad())
                .direccion(tienda.getDireccion())
                .telefono(tienda.getTelefono())
                .email(tienda.getEmail())
                .fechaApertura(tienda.getFechaApertura())
                .createdAt(tienda.getCreatedAt())
                .updatedAt(tienda.getUpdatedAt())
                .build();
    }

    public TiendaLamina toEntity(TiendaLaminaRequestDTO requestDTO) {
        return TiendaLamina.builder()
                .nombre(requestDTO.getNombre())
                .ciudad(requestDTO.getCiudad())
                .direccion(requestDTO.getDireccion())
                .telefono(requestDTO.getTelefono())
                .email(requestDTO.getEmail())
                .active(true)
                .build();
    }

    public void updateEntity(TiendaLaminaRequestDTO requestDTO, TiendaLamina tienda) {
        tienda.setNombre(requestDTO.getNombre());
        tienda.setCiudad(requestDTO.getCiudad());
        tienda.setDireccion(requestDTO.getDireccion());
        tienda.setTelefono(requestDTO.getTelefono());
        tienda.setEmail(requestDTO.getEmail());
    }
}


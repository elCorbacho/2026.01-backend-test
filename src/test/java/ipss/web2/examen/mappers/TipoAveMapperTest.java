package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.models.TipoAve;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TipoAveMapperTest {

    private final TipoAveMapper mapper = new TipoAveMapper();

    @Test
    @DisplayName("Mapper debe convertir TipoAveRequestDTO a entidad")
    void toEntityShouldMapRequestFields() {
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder()
                .nombre("Condor")
                .descripcion("Ave andina")
                .build();

        TipoAve entity = mapper.toEntity(requestDTO);

        assertEquals("Condor", entity.getNombre());
        assertEquals("Ave andina", entity.getDescripcion());
        assertTrue(entity.getActive());
    }

    @Test
    @DisplayName("Mapper debe convertir entidad TipoAve a response DTO")
    void toResponseShouldMapEntityFields() {
        TipoAve entity = TipoAve.builder()
                .id(10L)
                .nombre("Flamenco")
                .descripcion("Ave de humedales")
                .active(true)
                .build();

        TipoAveResponseDTO responseDTO = mapper.toResponseDTO(entity);

        assertEquals(10L, responseDTO.getId());
        assertEquals("Flamenco", responseDTO.getNombre());
        assertEquals("Ave de humedales", responseDTO.getDescripcion());
        assertTrue(responseDTO.getActive());
    }
}

package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.dtos.PoblacionAveResponseDTO;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PoblacionAveMapperTest {

    private final PoblacionAveMapper mapper = new PoblacionAveMapper();

    @Test
    @DisplayName("Mapper debe convertir request a entidad de poblacion")
    void toEntityShouldMapRequest() {
        TipoAve tipoAve = TipoAve.builder().id(1L).nombre("Condor").active(true).build();
        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(1L)
                .cantidad(90)
                .fecha(LocalDate.of(2025, 1, 1))
                .build();

        PoblacionAve entity = mapper.toEntity(requestDTO, tipoAve);

        assertEquals(90, entity.getCantidad());
        assertEquals(LocalDate.of(2025, 1, 1), entity.getFecha());
        assertEquals(tipoAve, entity.getTipoAve());
        assertTrue(entity.getActive());
    }

    @Test
    @DisplayName("Mapper debe convertir entidad de poblacion a response")
    void toResponseShouldMapEntity() {
        TipoAve tipoAve = TipoAve.builder().id(2L).nombre("Flamenco").build();
        PoblacionAve entity = PoblacionAve.builder()
                .id(7L)
                .tipoAve(tipoAve)
                .cantidad(350)
                .fecha(LocalDate.of(2025, 2, 15))
                .active(true)
                .build();

        PoblacionAveResponseDTO responseDTO = mapper.toResponseDTO(entity);

        assertEquals(7L, responseDTO.getId());
        assertEquals(2L, responseDTO.getTipoAveId());
        assertEquals("Flamenco", responseDTO.getTipoAveNombre());
        assertEquals(350, responseDTO.getCantidad());
        assertEquals(LocalDate.of(2025, 2, 15), responseDTO.getFecha());
    }
}

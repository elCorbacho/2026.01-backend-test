package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.ListadoOlimpiadasResponseDTO;
import ipss.web2.examen.models.ListadoOlimpiadas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListadoOlimpiadasMapperTest {

    @Test
    @DisplayName("Mapper debe convertir entidad a DTO correctamente")
    void toResponseDtoShouldMapFields() {
        ListadoOlimpiadasMapper mapper = new ListadoOlimpiadasMapper();
        ListadoOlimpiadas entity = ListadoOlimpiadas.builder()
                .id(7L)
                .nombre("París 2024")
                .ciudad("París")
                .pais("Francia")
                .anio(2024)
                .temporada("Verano")
                .build();

        ListadoOlimpiadasResponseDTO dto = mapper.toResponseDTO(entity);

        assertEquals(7L, dto.getId());
        assertEquals("París 2024", dto.getNombre());
        assertEquals("París", dto.getCiudad());
        assertEquals("Francia", dto.getPais());
        assertEquals(2024, dto.getAnio());
        assertEquals("Verano", dto.getTemporada());
    }
}

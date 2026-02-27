package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.models.GanadorGuinness;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GanadorGuinnessMapperTest {

    @Test
    @DisplayName("Mapper debe convertir entidad a DTO correctamente")
    void toDtoShouldMapFields() {
        GanadorGuinnessMapper mapper = new GanadorGuinnessMapper();
        GanadorGuinness entidad = GanadorGuinness.builder()
                .nombre("Ashrita Furman")
                .categoria("Resistencia")
                .record("Mayor cantidad de records")
                .anio(2024)
                .build();

        GanadorGuinnessResponseDTO dto = mapper.toDTO(entidad);

        assertEquals("Ashrita Furman", dto.getNombre());
        assertEquals("Resistencia", dto.getCategoria());
        assertEquals("Mayor cantidad de records", dto.getRecord());
        assertEquals(2024, dto.getAnio());
    }
}

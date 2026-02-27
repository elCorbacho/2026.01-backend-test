package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.models.PresidenteChile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PresidenteChileMapperTest {

    @Test
    @DisplayName("Mapper debe convertir entidad a DTO correctamente")
    void toDtoShouldMapFields() {
        PresidenteChileMapper mapper = new PresidenteChileMapper();
        PresidenteChile entidad = PresidenteChile.builder()
                .id(10L)
                .nombre("Eduardo Frei")
                .periodoInicio(LocalDate.of(1994, 3, 11))
                .periodoFin(LocalDate.of(2000, 3, 11))
                .partido("Democracia Cristiana")
                .descripcion("Presidente durante la consolidación democrática")
                .build();

        PresidenteChileResponseDTO dto = mapper.toDTO(entidad);

        assertEquals(10L, dto.getId());
        assertEquals("Eduardo Frei", dto.getNombre());
        assertEquals(LocalDate.of(1994, 3, 11), dto.getPeriodoInicio());
        assertEquals(LocalDate.of(2000, 3, 11), dto.getPeriodoFin());
        assertEquals("Democracia Cristiana", dto.getPartido());
        assertEquals("Presidente durante la consolidación democrática", dto.getDescripcion());
    }
}

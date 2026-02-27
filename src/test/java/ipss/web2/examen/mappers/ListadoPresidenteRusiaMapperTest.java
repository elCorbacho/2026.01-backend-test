package ipss.web2.examen.mappers;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaPatchRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ListadoPresidenteRusiaMapperTest {

    @Test
    @DisplayName("Mapper debe convertir entidad a DTO correctamente")
    void toResponseDtoShouldMapFields() {
        ListadoPresidenteRusiaMapper mapper = new ListadoPresidenteRusiaMapper();
        ListadoPresidenteRusia entidad = ListadoPresidenteRusia.builder()
                .id(7L)
                .nombre("Boris Yeltsin")
                .periodoInicio(LocalDate.of(1991, 7, 10))
                .periodoFin(LocalDate.of(1999, 12, 31))
                .partido("Independiente")
                .descripcion("Primer presidente de Rusia")
                .build();

        ListadoPresidenteRusiaResponseDTO dto = mapper.toResponseDTO(entidad);

        assertEquals(7L, dto.getId());
        assertEquals("Boris Yeltsin", dto.getNombre());
        assertEquals(LocalDate.of(1991, 7, 10), dto.getPeriodoInicio());
        assertEquals(LocalDate.of(1999, 12, 31), dto.getPeriodoFin());
        assertEquals("Independiente", dto.getPartido());
        assertEquals("Primer presidente de Rusia", dto.getDescripcion());
    }

    @Test
    @DisplayName("Mapper aplica parches solo sobre campos enviados")
    void applyPatchShouldUpdateOnlyProvidedFields() {
        ListadoPresidenteRusiaMapper mapper = new ListadoPresidenteRusiaMapper();
        ListadoPresidenteRusia entidad = ListadoPresidenteRusia.builder()
                .nombre("Boris Yeltsin")
                .periodoInicio(LocalDate.of(1991, 7, 10))
                .periodoFin(LocalDate.of(1999, 12, 31))
                .partido("Independiente")
                .descripcion("Primer presidente")
                .build();

        ListadoPresidenteRusiaPatchRequestDTO patch = ListadoPresidenteRusiaPatchRequestDTO.builder()
                .nombre("B. Yeltsin")
                .descripcion("Primer presidente ruso")
                .build();

        mapper.applyPatch(patch, entidad);

        assertEquals("B. Yeltsin", entidad.getNombre());
        assertEquals("Primer presidente ruso", entidad.getDescripcion());
        assertEquals(LocalDate.of(1991, 7, 10), entidad.getPeriodoInicio());
        assertEquals(LocalDate.of(1999, 12, 31), entidad.getPeriodoFin());
        assertEquals("Independiente", entidad.getPartido());

        mapper.applyPatch(ListadoPresidenteRusiaPatchRequestDTO.builder()
                .periodoFin(null)
                .build(), entidad);

        assertEquals(LocalDate.of(1999, 12, 31), entidad.getPeriodoFin(),
                "Los parches deben ignorar valores nulos para evitar borrar campos");
    }
}

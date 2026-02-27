package ipss.web2.examen.services;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.mappers.PresidenteChileMapper;
import ipss.web2.examen.models.PresidenteChile;
import ipss.web2.examen.repositories.PresidenteChileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresidenteChileServiceTest {

    @Mock
    private PresidenteChileRepository presidenteChileRepository;

    private PresidenteChileService presidenteChileService;

    @BeforeEach
    void setUp() {
        presidenteChileService = new PresidenteChileService(
                presidenteChileRepository,
                new PresidenteChileMapper()
        );
    }

    @Test
    @DisplayName("Service debe retornar solo presidentes activos")
    void obtenerPresidentesChileDebeRetornarActivos() {
        when(presidenteChileRepository.findByActiveTrue())
                .thenReturn(List.of(
                        PresidenteChile.builder()
                                .nombre("Ricardo Lagos")
                                .periodoInicio(LocalDate.of(2000, 3, 11))
                                .periodoFin(LocalDate.of(2006, 3, 11))
                                .partido("PPD")
                                .descripcion("Impulso de reformas sociales")
                                .build()
                ));

        List<PresidenteChileResponseDTO> resultado = presidenteChileService.obtenerPresidentesChile();

        assertEquals(1, resultado.size());
        assertEquals("Ricardo Lagos", resultado.get(0).getNombre());
        assertEquals("PPD", resultado.get(0).getPartido());
        assertEquals(LocalDate.of(2000, 3, 11), resultado.get(0).getPeriodoInicio());
    }
}

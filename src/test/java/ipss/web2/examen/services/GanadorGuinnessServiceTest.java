package ipss.web2.examen.services;

import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.mappers.GanadorGuinnessMapper;
import ipss.web2.examen.models.GanadorGuinness;
import ipss.web2.examen.repositories.GanadorGuinnessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GanadorGuinnessServiceTest {

    @Mock
    private GanadorGuinnessRepository ganadorGuinnessRepository;

    private GanadorGuinnessService ganadorGuinnessService;

    @BeforeEach
    void setUp() {
        ganadorGuinnessService = new GanadorGuinnessService(
                ganadorGuinnessRepository,
                new GanadorGuinnessMapper()
        );
    }

    @Test
    @DisplayName("Service debe retornar solo ganadores activos")
    void obtenerGanadoresGuinnessDebeRetornarActivos() {
        when(ganadorGuinnessRepository.findByActiveTrue())
                .thenReturn(List.of(
                        GanadorGuinness.builder()
                                .nombre("Lucy")
                                .categoria("Rescate")
                                .record("Mayor cantidad de rescates")
                                .anio(2023)
                                .build()
                ));

        List<GanadorGuinnessResponseDTO> resultado = ganadorGuinnessService.obtenerGanadoresGuinness();

        assertEquals(1, resultado.size());
        assertEquals("Lucy", resultado.get(0).getNombre());
        assertEquals("Rescate", resultado.get(0).getCategoria());
        assertEquals("Mayor cantidad de rescates", resultado.get(0).getRecord());
        assertEquals(2023, resultado.get(0).getAnio());
    }
}

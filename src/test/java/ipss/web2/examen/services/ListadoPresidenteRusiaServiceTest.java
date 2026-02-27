package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.mappers.ListadoPresidenteRusiaMapper;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListadoPresidenteRusiaServiceTest {

    @Mock
    private ListadoPresidenteRusiaRepository listadoPresidenteRusiaRepository;

    private ListadoPresidenteRusiaService listadoPresidenteRusiaService;

    @BeforeEach
    void setUp() {
        listadoPresidenteRusiaService = new ListadoPresidenteRusiaService(
                listadoPresidenteRusiaRepository,
                new ListadoPresidenteRusiaMapper()
        );
    }

    @Test
    @DisplayName("Service debe retornar solo presidentes activos")
    void obtenerPresidentesRusiaDebeRetornarActivos() {
        when(listadoPresidenteRusiaRepository.findByActiveTrue())
                .thenReturn(List.of(
                        ListadoPresidenteRusia.builder()
                                .nombre("Dmitry Medvedev")
                                .periodoInicio(LocalDate.of(2008, 5, 7))
                                .periodoFin(LocalDate.of(2012, 5, 7))
                                .partido("Rusia Unida")
                                .descripcion("Presidente 2008-2012")
                                .build()
                ));

        List<ListadoPresidenteRusiaResponseDTO> resultado = listadoPresidenteRusiaService.obtenerPresidentesRusia();

        assertEquals(1, resultado.size());
        assertEquals("Dmitry Medvedev", resultado.get(0).getNombre());
        assertEquals("Rusia Unida", resultado.get(0).getPartido());
        assertEquals(LocalDate.of(2008, 5, 7), resultado.get(0).getPeriodoInicio());
    }

    @Test
    @DisplayName("Service debe crear un presidente de Rusia")
    void crearPresidenteRusiaDebePersistir() {
        ListadoPresidenteRusiaRequestDTO request = ListadoPresidenteRusiaRequestDTO.builder()
                .nombre("Vladimir Putin")
                .periodoInicio(LocalDate.of(2012, 5, 7))
                .partido("Rusia Unida")
                .descripcion("Presidente desde 2012")
                .build();

        ListadoPresidenteRusia guardado = ListadoPresidenteRusia.builder()
                .id(5L)
                .nombre("Vladimir Putin")
                .periodoInicio(LocalDate.of(2012, 5, 7))
                .partido("Rusia Unida")
                .descripcion("Presidente desde 2012")
                .active(true)
                .build();

        when(listadoPresidenteRusiaRepository.save(any(ListadoPresidenteRusia.class)))
                .thenReturn(guardado);

        ListadoPresidenteRusiaResponseDTO resultado = listadoPresidenteRusiaService.crearPresidenteRusia(request);

        assertEquals(5L, resultado.getId());
        assertEquals("Vladimir Putin", resultado.getNombre());
        assertEquals("Rusia Unida", resultado.getPartido());
        assertEquals(LocalDate.of(2012, 5, 7), resultado.getPeriodoInicio());
    }
}

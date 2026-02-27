package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ListadoPresidenteRusiaPatchRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ListadoPresidenteRusiaMapper;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("Service obtiene presidente activo por id")
    void obtenerPresidentePorIdDebeRetornarDto() {
        ListadoPresidenteRusia entity = ListadoPresidenteRusia.builder()
                .id(9L)
                .nombre("Boris Yeltsin")
                .periodoInicio(LocalDate.of(1991, 7, 10))
                .active(true)
                .build();

        when(listadoPresidenteRusiaRepository.findByIdAndActiveTrue(9L)).thenReturn(Optional.of(entity));

        ListadoPresidenteRusiaResponseDTO dto = listadoPresidenteRusiaService.obtenerPresidenteRusiaPorId(9L);

        assertEquals(9L, dto.getId());
        assertEquals("Boris Yeltsin", dto.getNombre());
    }

    @Test
    @DisplayName("Service arroja ResourceNotFoundException cuando no existe presidente activo")
    void obtenerPresidentePorIdDebeFallarCuandoNoExiste() {
        when(listadoPresidenteRusiaRepository.findByIdAndActiveTrue(20L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> listadoPresidenteRusiaService.obtenerPresidenteRusiaPorId(20L));
    }

    @Test
    @DisplayName("Service aplica patch y valida coherencia de fechas")
    void actualizarPresidenteRusiaParcialDebeActualizarCampos() {
        ListadoPresidenteRusia existente = ListadoPresidenteRusia.builder()
                .id(5L)
                .nombre("Vladimir Putin")
                .periodoInicio(LocalDate.of(2012, 5, 7))
                .periodoFin(null)
                .partido("Rusia Unida")
                .descripcion("En funciones")
                .active(true)
                .build();

        when(listadoPresidenteRusiaRepository.findByIdAndActiveTrue(5L)).thenReturn(Optional.of(existente));
        when(listadoPresidenteRusiaRepository.save(any(ListadoPresidenteRusia.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ListadoPresidenteRusiaPatchRequestDTO patch = ListadoPresidenteRusiaPatchRequestDTO.builder()
                .descripcion("Presidente en ejercicio")
                .partido("Independiente")
                .build();

        ListadoPresidenteRusiaResponseDTO actualizado = listadoPresidenteRusiaService
                .actualizarPresidenteRusiaParcial(5L, patch);

        assertEquals("Presidente en ejercicio", actualizado.getDescripcion());
        assertEquals("Independiente", actualizado.getPartido());
        verify(listadoPresidenteRusiaRepository).save(any(ListadoPresidenteRusia.class));
    }

    @Test
    @DisplayName("Service valida rango de fechas y arroja InvalidOperationException")
    void actualizarPresidenteRusiaParcialDebeValidarFechas() {
        ListadoPresidenteRusia existente = ListadoPresidenteRusia.builder()
                .id(8L)
                .nombre("Presidente de prueba")
                .periodoInicio(LocalDate.of(2000, 1, 1))
                .periodoFin(LocalDate.of(2004, 12, 31))
                .active(true)
                .build();

        when(listadoPresidenteRusiaRepository.findByIdAndActiveTrue(8L)).thenReturn(Optional.of(existente));

        ListadoPresidenteRusiaPatchRequestDTO patch = ListadoPresidenteRusiaPatchRequestDTO.builder()
                .periodoFin(LocalDate.of(1999, 12, 31))
                .build();

        assertThrows(InvalidOperationException.class,
                () -> listadoPresidenteRusiaService.actualizarPresidenteRusiaParcial(8L, patch));
        Mockito.verifyNoMoreInteractions(listadoPresidenteRusiaRepository);
    }

    @Test
    @DisplayName("Service elimina lógicamente presidentes activos")
    void eliminarPresidenteRusiaDebeMarcarInactivo() {
        ListadoPresidenteRusia existente = ListadoPresidenteRusia.builder()
                .id(11L)
                .nombre("Dmitry Medvedev")
                .periodoInicio(LocalDate.of(2008, 5, 7))
                .active(true)
                .build();

        when(listadoPresidenteRusiaRepository.findByIdAndActiveTrue(11L)).thenReturn(Optional.of(existente));

        listadoPresidenteRusiaService.eliminarPresidenteRusia(11L);

        assertFalse(existente.getActive());
        verify(listadoPresidenteRusiaRepository).save(eq(existente));
    }
}

package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAvePageResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.mappers.TipoAveMapper;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoAveServiceTest {

    @Mock
    private TipoAveRepository tipoAveRepository;

    @Mock
    private PoblacionAveRepository poblacionAveRepository;

    private TipoAveService tipoAveService;

    @BeforeEach
    void setUp() {
        tipoAveService = new TipoAveService(tipoAveRepository, poblacionAveRepository, new TipoAveMapper());
    }

    @Test
    @DisplayName("Service debe hacer soft delete de tipo y poblaciones relacionadas")
    void eliminarTipoAveDebeDesactivarDependencias() {
        TipoAve tipoAve = TipoAve.builder().id(1L).nombre("Condor").active(true).build();
        PoblacionAve poblacion = PoblacionAve.builder()
                .id(2L)
                .tipoAve(tipoAve)
                .cantidad(120)
                .fecha(LocalDate.of(2025, 1, 1))
                .active(true)
                .build();

        when(tipoAveRepository.findByIdAndActiveTrue(1L)).thenReturn(Optional.of(tipoAve));
        when(poblacionAveRepository.findByTipoAveAndActiveTrue(tipoAve)).thenReturn(List.of(poblacion));

        tipoAveService.eliminarTipoAve(1L);

        assertEquals(false, tipoAve.getActive());
        assertEquals(false, poblacion.getActive());
        verify(poblacionAveRepository).saveAll(List.of(poblacion));
        verify(tipoAveRepository).save(tipoAve);
    }

    @Test
    @DisplayName("Service debe validar rango de paginacion en tipo ave")
    void obtenerTiposAvePaginadosDebeValidarPageYSize() {
        InvalidOperationException pageException = assertThrows(InvalidOperationException.class,
                () -> tipoAveService.obtenerTiposAvePaginados(-1, 10));
        assertEquals("INVALID_PAGE", pageException.getErrorCode());

        InvalidOperationException sizeException = assertThrows(InvalidOperationException.class,
                () -> tipoAveService.obtenerTiposAvePaginados(0, 101));
        assertEquals("INVALID_SIZE", sizeException.getErrorCode());
    }

    @Test
    @DisplayName("Service debe actualizar tipo ave existente")
    void actualizarTipoAveDebePersistirCambios() {
        TipoAve tipoAve = TipoAve.builder().id(8L).nombre("Antiguo").descripcion("desc").active(true).build();
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder().nombre("Nuevo").descripcion("actualizada").build();

        when(tipoAveRepository.findByIdAndActiveTrue(8L)).thenReturn(Optional.of(tipoAve));
        when(tipoAveRepository.save(tipoAve)).thenReturn(tipoAve);

        tipoAveService.actualizarTipoAve(8L, requestDTO);

        assertEquals("Nuevo", tipoAve.getNombre());
        assertEquals("actualizada", tipoAve.getDescripcion());
        verify(tipoAveRepository).save(tipoAve);
    }

    @Test
    @DisplayName("Service debe construir respuesta paginada de tipos de ave")
    void obtenerTiposAvePaginadosDebeRetornarPageResponseDTO() {
        TipoAve tipoAve = TipoAve.builder().id(5L).nombre("Condor").descripcion("A").active(true).build();

        when(tipoAveRepository.findByActiveTrue(org.mockito.ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(tipoAve), PageRequest.of(0, 10), 1));

        TipoAvePageResponseDTO response = tipoAveService.obtenerTiposAvePaginados(0, 10);

        assertEquals(1, response.content().size());
        assertEquals(1L, response.totalElements());
        assertFalse(response.content().isEmpty());
    }
}

package ipss.web2.examen.services;

import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.mappers.PoblacionAveMapper;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PoblacionAveServiceTest {

    @Mock
    private PoblacionAveRepository poblacionAveRepository;

    @Mock
    private TipoAveService tipoAveService;

    private PoblacionAveService poblacionAveService;

    @BeforeEach
    void setUp() {
        poblacionAveService = new PoblacionAveService(poblacionAveRepository, tipoAveService, new PoblacionAveMapper());
    }

    @Test
    @DisplayName("Service debe crear poblacion ave con tipo activo")
    void crearPoblacionAveDebePersistirRegistro() {
        TipoAve tipoAve = TipoAve.builder().id(1L).nombre("Condor").active(true).build();
        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(1L)
                .cantidad(40)
                .fecha(LocalDate.of(2025, 3, 10))
                .build();

        PoblacionAve guardada = PoblacionAve.builder()
                .id(3L)
                .tipoAve(tipoAve)
                .cantidad(40)
                .fecha(LocalDate.of(2025, 3, 10))
                .active(true)
                .build();

        when(tipoAveService.obtenerTipoAveEntityActivo(1L)).thenReturn(tipoAve);
        when(poblacionAveRepository.save(org.mockito.ArgumentMatchers.any(PoblacionAve.class))).thenReturn(guardada);

        assertEquals(3L, poblacionAveService.crearPoblacionAve(requestDTO).getId());
        verify(poblacionAveRepository).save(org.mockito.ArgumentMatchers.any(PoblacionAve.class));
    }

    @Test
    @DisplayName("Service debe hacer soft delete de poblacion")
    void eliminarPoblacionAveDebeMarcarInactiva() {
        PoblacionAve entity = PoblacionAve.builder()
                .id(6L)
                .cantidad(20)
                .fecha(LocalDate.of(2025, 2, 1))
                .active(true)
                .build();
        when(poblacionAveRepository.findByIdAndActiveTrue(6L)).thenReturn(Optional.of(entity));

        poblacionAveService.eliminarPoblacionAve(6L);

        assertEquals(false, entity.getActive());
        verify(poblacionAveRepository).save(entity);
    }

    @Test
    @DisplayName("Service debe validar rango de paginacion en poblaciones")
    void obtenerPoblacionesPaginadasDebeValidarRango() {
        InvalidOperationException pageException = assertThrows(InvalidOperationException.class,
                () -> poblacionAveService.obtenerPoblacionesPaginadas(-1, 10, null));
        assertEquals("INVALID_PAGE", pageException.getErrorCode());

        InvalidOperationException sizeException = assertThrows(InvalidOperationException.class,
                () -> poblacionAveService.obtenerPoblacionesPaginadas(0, 0, null));
        assertEquals("INVALID_SIZE", sizeException.getErrorCode());
    }
}

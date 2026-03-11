package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TipoInsectoResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TipoInsectoMapper;
import ipss.web2.examen.models.TipoInsecto;
import ipss.web2.examen.repositories.TipoInsectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TipoInsectoServiceTest
{

    @Mock
    private TipoInsectoRepository tipoInsectoRepository;

    private TipoInsectoService tipoInsectoService;

    @BeforeEach
    void setUp()
    {
        tipoInsectoService = new TipoInsectoService(tipoInsectoRepository, new TipoInsectoMapper());
    }

    @Test
    @DisplayName("Service retorna listado de tipos de insecto activos")
    void obtenerTiposInsectoDebeRetornarSoloActivos()
    {
        when(tipoInsectoRepository.findByActiveTrueOrderByNombreAsc())
                .thenReturn(List.of(
                        TipoInsecto.builder().id(1L).nombre("Abeja").descripcion("A").active(true).build(),
                        TipoInsecto.builder().id(2L).nombre("Escarabajo").descripcion("B").active(true).build()
                ));

        List<TipoInsectoResponseDTO> resultado = tipoInsectoService.obtenerTiposInsecto();

        assertEquals(2, resultado.size());
        assertEquals("Abeja", resultado.get(0).nombre());
        assertEquals("Escarabajo", resultado.get(1).nombre());
    }

    @Test
    @DisplayName("Service retorna detalle de tipo de insecto activo")
    void obtenerTipoInsectoPorIdDebeRetornarRegistro()
    {
        when(tipoInsectoRepository.findByIdAndActiveTrue(1L))
                .thenReturn(Optional.of(TipoInsecto.builder()
                        .id(1L)
                        .nombre("Abeja")
                        .descripcion("Polinizador")
                        .active(true)
                        .build()));

        TipoInsectoResponseDTO resultado = tipoInsectoService.obtenerTipoInsectoPorId(1L);

        assertEquals(1L, resultado.id());
        assertEquals("Abeja", resultado.nombre());
    }

    @Test
    @DisplayName("Service lanza not found cuando id no existe o esta inactivo")
    void obtenerTipoInsectoPorIdDebeLanzarNotFound()
    {
        when(tipoInsectoRepository.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> tipoInsectoService.obtenerTipoInsectoPorId(999L)
        );

        assertEquals("TIPO_INSECTO_NOT_FOUND", exception.getErrorCode());
    }

    @Test
    @DisplayName("Service valida id menor a 1")
    void obtenerTipoInsectoPorIdDebeValidarRangoDeId()
    {
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                () -> tipoInsectoService.obtenerTipoInsectoPorId(0L)
        );

        assertEquals("INVALID_INSECTO_ID", exception.getErrorCode());
    }
}

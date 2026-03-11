package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ExoplanetaResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ExoplanetaMapper;
import ipss.web2.examen.models.Exoplaneta;
import ipss.web2.examen.repositories.ExoplanetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExoplanetaServiceTest
{
    @Mock
    private ExoplanetaRepository exoplanetaRepository;

    private ExoplanetaService exoplanetaService;

    @BeforeEach
    void setUp()
    {
        exoplanetaService = new ExoplanetaService(
                exoplanetaRepository,
                new ExoplanetaMapper()
        );
    }

    @Test
    @DisplayName("obtenerPorId debe retornar exoplaneta cuando existe y esta activo")
    void obtenerPorIdDebeRetornarExoplanetaCuandoExiste()
    {
        Exoplaneta exoplaneta = Exoplaneta.builder()
                .id(1L)
                .nombre("Kepler-452b")
                .tipo("Super-Tierra")
                .distanciaAnosLuz(1400.0)
                .masaRelativaJupiter(5.0)
                .descubiertoAnio(2015)
                .active(true)
                .build();

        when(exoplanetaRepository.findByIdAndActiveTrue(1L))
                .thenReturn(Optional.of(exoplaneta));

        ExoplanetaResponseDTO resultado = exoplanetaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Kepler-452b", resultado.getNombre());
        assertEquals("Super-Tierra", resultado.getTipo());
        assertEquals(1400.0, resultado.getDistanciaAnosLuz());
        assertEquals(2015, resultado.getDescubiertoAnio());
    }

    @Test
    @DisplayName("obtenerPorId debe lanzar ResourceNotFoundException cuando el exoplaneta no existe")
    void obtenerPorIdDebeLanzarExcepcionCuandoNoExiste()
    {
        when(exoplanetaRepository.findByIdAndActiveTrue(999L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> exoplanetaService.obtenerPorId(999L)
        );

        assertEquals("Exoplaneta", ex.getResourceName());
        assertEquals("id", ex.getFieldName());
        assertEquals(999L, ex.getFieldValue());
    }

    @Test
    @DisplayName("obtenerPorId debe lanzar ResourceNotFoundException cuando el exoplaneta esta inactivo")
    void obtenerPorIdDebeLanzarExcepcionCuandoEstaInactivo()
    {
        when(exoplanetaRepository.findByIdAndActiveTrue(2L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> exoplanetaService.obtenerPorId(2L)
        );

        assertEquals("Exoplaneta", ex.getResourceName());
        assertEquals("id", ex.getFieldName());
        assertEquals(2L, ex.getFieldValue());
    }
}

package ipss.web2.examen.services;

import ipss.web2.examen.dtos.CiudadChileResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.CiudadChileMapper;
import ipss.web2.examen.models.CiudadChile;
import ipss.web2.examen.repositories.CiudadChileRepository;
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
class CiudadChileServiceTest {

    @Mock
    private CiudadChileRepository ciudadChileRepository;

    private CiudadChileService ciudadChileService;

    @BeforeEach
    void setUp() {
        ciudadChileService = new CiudadChileService(ciudadChileRepository, new CiudadChileMapper());
    }

    @Test
    @DisplayName("Service retorna ciudades activas ordenadas")
    void obtenerCiudadesChileRetornaListado() {
        when(ciudadChileRepository.findByActiveTrueOrderByNombreAsc())
                .thenReturn(List.of(
                        CiudadChile.builder().id(1L).nombre("Antofagasta").poblacion(388517L).active(true).build(),
                        CiudadChile.builder().id(2L).nombre("Santiago").poblacion(6257516L).active(true).build()
                ));

        List<CiudadChileResponseDTO> resultado = ciudadChileService.obtenerCiudadesChile();

        assertEquals(2, resultado.size());
        assertEquals("Antofagasta", resultado.get(0).getNombre());
    }

    @Test
    @DisplayName("Service retorna ciudad por id activo")
    void obtenerCiudadChilePorIdRetornaCiudad() {
        when(ciudadChileRepository.findByIdAndActiveTrue(1L))
                .thenReturn(Optional.of(
                        CiudadChile.builder().id(1L).nombre("Santiago").poblacion(6257516L).active(true).build()
                ));

        CiudadChileResponseDTO resultado = ciudadChileService.obtenerCiudadChilePorId(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Santiago", resultado.getNombre());
        assertEquals(6257516L, resultado.getPoblacion());
    }

    @Test
    @DisplayName("Service lanza not found para id inexistente")
    void obtenerCiudadChilePorIdInexistente() {
        when(ciudadChileRepository.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> ciudadChileService.obtenerCiudadChilePorId(999L)
        );

        assertEquals("CIUDAD_NOT_FOUND", exception.getErrorCode());
    }
}

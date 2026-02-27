package ipss.web2.examen.services;

import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ListadoOlimpiadasMapper;
import ipss.web2.examen.models.ListadoOlimpiadas;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListadoOlimpiadasServiceTest {

    @Mock
    private ListadoOlimpiadasRepository listadoOlimpiadasRepository;

    private ListadoOlimpiadasService listadoOlimpiadasService;

    @BeforeEach
    void setUp() {
        listadoOlimpiadasService = new ListadoOlimpiadasService(
                listadoOlimpiadasRepository,
                new ListadoOlimpiadasMapper()
        );
    }

    @Test
    @DisplayName("Service debe marcar listado como inactivo al eliminar")
    void eliminarListadoDebeMarcarInactivo() {
        ListadoOlimpiadas entity = ListadoOlimpiadas.builder()
                .id(3L)
                .nombre("París 2024")
                .ciudad("París")
                .pais("Francia")
                .anio(2024)
                .temporada("Verano")
                .active(true)
                .build();

        when(listadoOlimpiadasRepository.findByIdAndActiveTrue(3L))
                .thenReturn(Optional.of(entity));

        listadoOlimpiadasService.eliminarListadoOlimpiadas(3L);

        verify(listadoOlimpiadasRepository).save(entity);
    }

    @Test
    @DisplayName("Service debe lanzar ResourceNotFoundException al buscar por ID inexistente")
    void obtenerListadoPorIdInexistenteDebeLanzarExcepcion() {
        when(listadoOlimpiadasRepository.findByIdAndActiveTrue(99L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> listadoOlimpiadasService.obtenerListadoOlimpiadasPorId(99L));
    }
}

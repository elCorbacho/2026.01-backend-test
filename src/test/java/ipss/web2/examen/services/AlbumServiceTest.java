package ipss.web2.examen.services;

import ipss.web2.examen.dtos.AlbumSummaryDTO;
import ipss.web2.examen.dtos.AlbumPageResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.mappers.AlbumMapper;
import ipss.web2.examen.models.Album;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.LaminaCatalogoRepository;
import ipss.web2.examen.repositories.LaminaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private LaminaRepository laminaRepository;

    @Mock
    private LaminaCatalogoRepository laminaCatalogoRepository;

    private AlbumService albumService;

    @BeforeEach
    void setUp() {
        albumService = new AlbumService(
                albumRepository,
                new AlbumMapper(),
                laminaRepository,
                laminaCatalogoRepository
        );
    }

    @Test
    @DisplayName("Service debe retornar pagina por defecto con albums activos")
    void obtenerAlbumsPaginadosPorDefectoDebeRetornarActivos() {
        Album album = Album.builder()
                .id(1L)
                .nombre("Historias")
                .year(2024)
                .descripcion("Album activo")
                .active(true)
                .build();

        when(albumRepository.findByActiveTrue(PageRequest.of(0, 10, org.springframework.data.domain.Sort.by("id").ascending())))
                .thenReturn(new PageImpl<>(List.of(album), PageRequest.of(0, 10), 1));

        AlbumPageResponseDTO resultado = albumService.obtenerAlbumsPaginados(null, null, null, null);

        assertEquals(1, resultado.content().size());
        assertEquals("Historias", resultado.content().get(0).getNombre());
        assertEquals(0, resultado.page());
        assertEquals(10, resultado.size());
        assertEquals(1L, resultado.totalElements());
    }

    @Test
    @DisplayName("Service debe combinar filtros year y active en la consulta paginada")
    void obtenerAlbumsPaginadosConYearYActiveDebeAplicarFiltros() {
        Album album = Album.builder()
                .id(2L)
                .nombre("Clasic")
                .year(2020)
                .descripcion("Album inactivo")
                .active(false)
                .build();

        when(albumRepository.findByYearAndActive(2020, false, PageRequest.of(1, 5, org.springframework.data.domain.Sort.by("id").ascending())))
                .thenReturn(new PageImpl<>(List.of(album), PageRequest.of(1, 5), 6));

        AlbumPageResponseDTO resultado = albumService.obtenerAlbumsPaginados(1, 5, 2020, false);

        assertEquals(1, resultado.content().size());
        assertEquals("Clasic", resultado.content().get(0).getNombre());
        assertEquals(1, resultado.page());
        assertEquals(5, resultado.size());
        assertEquals(6L, resultado.totalElements());
        assertEquals(2, resultado.totalPages());
    }

    @Test
    @DisplayName("Service debe devolver pagina vacia si el indice esta fuera de rango")
    void obtenerAlbumsPaginadosFueraDeRangoDebeRetornarContenidoVacio() {
        when(albumRepository.findByActiveTrue(PageRequest.of(9, 10, org.springframework.data.domain.Sort.by("id").ascending())))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(9, 10), 20));

        AlbumPageResponseDTO resultado = albumService.obtenerAlbumsPaginados(9, 10, null, null);

        assertTrue(resultado.content().isEmpty());
        assertEquals(9, resultado.page());
        assertEquals(10, resultado.size());
        assertEquals(20L, resultado.totalElements());
        assertEquals(2, resultado.totalPages());
    }

    @Test
    @DisplayName("Service debe rechazar page negativa")
    void obtenerAlbumsPaginadosConPageNegativaDebeFallar() {
        InvalidOperationException ex = assertThrows(InvalidOperationException.class,
                () -> albumService.obtenerAlbumsPaginados(-1, 10, null, null));
        assertEquals("INVALID_PAGE", ex.getErrorCode());
    }

    @Test
    @DisplayName("Service debe rechazar size fuera de rango")
    void obtenerAlbumsPaginadosConSizeFueraDeRangoDebeFallar() {
        InvalidOperationException ex = assertThrows(InvalidOperationException.class,
                () -> albumService.obtenerAlbumsPaginados(0, 101, null, null));
        assertEquals("INVALID_SIZE", ex.getErrorCode());
    }

    @Test
    @DisplayName("Service debe construir resumen con conteos de laminas")
    void obtenerResumenAlbumDebeCalcularTotales() {
        Album album = Album.builder()
                .id(3L)
                .nombre("Coleccion")
                .year(2022)
                .descripcion("Resumen")
                .active(true)
                .build();

        when(albumRepository.findByIdAndActiveTrue(3L))
                .thenReturn(Optional.of(album));
        when(laminaCatalogoRepository.countByAlbumAndActiveTrue(album))
                .thenReturn(10L);
        when(laminaRepository.countByAlbumAndActiveTrue(album))
                .thenReturn(7L);
        when(laminaRepository.countDistinctNombreByAlbumAndActiveTrue(album))
                .thenReturn(5L);

        AlbumSummaryDTO resumen = albumService.obtenerResumenAlbum(3L);

        assertEquals(3L, resumen.id());
        assertEquals(10, resumen.totalCatalogo());
        assertEquals(7, resumen.totalLaminas());
        assertEquals(2, resumen.laminasRepetidasTotal());
        assertEquals(5, resumen.laminasFaltantesTotal());
    }
}

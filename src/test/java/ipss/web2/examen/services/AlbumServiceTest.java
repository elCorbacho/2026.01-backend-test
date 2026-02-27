package ipss.web2.examen.services;

import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.dtos.AlbumSummaryDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Service debe retornar albums activos por defecto")
    void obtenerAlbumsFiltradosPorDefectoDebeRetornarActivos() {
        when(albumRepository.findByActiveTrue())
                .thenReturn(List.of(
                        Album.builder()
                                .id(1L)
                                .nombre("Historias")
                                .year(2024)
                                .descripcion("Album activo")
                                .active(true)
                                .build()
                ));

        List<AlbumResponseDTO> resultado = albumService.obtenerAlbumsFiltrados(null, null);

        assertEquals(1, resultado.size());
        assertEquals("Historias", resultado.get(0).getNombre());
        assertEquals(2024, resultado.get(0).getYear());
    }

    @Test
    @DisplayName("Service debe filtrar albums por year y active")
    void obtenerAlbumsFiltradosConYearYActiveDebeAplicarFiltros() {
        when(albumRepository.findByYearAndActive(2020, false))
                .thenReturn(List.of(
                        Album.builder()
                                .id(2L)
                                .nombre("Clasic")
                                .year(2020)
                                .descripcion("Album inactivo")
                                .active(false)
                                .build()
                ));

        List<AlbumResponseDTO> resultado = albumService.obtenerAlbumsFiltrados(2020, false);

        assertEquals(1, resultado.size());
        assertEquals("Clasic", resultado.get(0).getNombre());
        assertEquals(2020, resultado.get(0).getYear());
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

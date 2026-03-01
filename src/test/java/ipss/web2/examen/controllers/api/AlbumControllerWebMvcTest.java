package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.AlbumPageResponseDTO;
import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.dtos.AlbumSummaryDTO;
import ipss.web2.examen.dtos.GanadorAlbumDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.AlbumService;
import ipss.web2.examen.services.GanadorAlbumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlbumController.class)
@Import(GlobalExceptionHandler.class)
class AlbumControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private GanadorAlbumService ganadorAlbumService;

    @Test
    @DisplayName("GET /api/albums con filtros debe responder 200 con ApiResponseDTO")
    void obtenerAlbumsConFiltrosDebeResponderOk() throws Exception {
        when(albumService.obtenerAlbumsPaginados(0, 10, 2020, false))
                .thenReturn(new AlbumPageResponseDTO(
                        List.of(AlbumResponseDTO.builder()
                                .id(10L)
                                .nombre("Clasicos")
                                .year(2020)
                                .descripcion("Album inactivo")
                                .build()),
                        0,
                        10,
                        1,
                        1
                ));

        mockMvc.perform(get("/api/albums")
                        .param("page", "0")
                        .param("size", "10")
                        .param("year", "2020")
                        .param("active", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Álbumes recuperados exitosamente"))
                .andExpect(jsonPath("$.data.content[0].id").value(10))
                .andExpect(jsonPath("$.data.content[0].nombre").value("Clasicos"))
                .andExpect(jsonPath("$.data.content[0].year").value(2020))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(10))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.totalPages").value(1))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/albums con parametro invalido debe responder 400")
    void obtenerAlbumsConParametroInvalidoDebeResponderBadRequest() throws Exception {
        mockMvc.perform(get("/api/albums").param("active", "no-es-boolean"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INVALID_PARAMETER_TYPE"));
    }

    @Test
    @DisplayName("GET /api/albums ante error inesperado debe responder 500 saneado")
    void obtenerAlbumsConErrorInesperadoDebeResponderSanitizado() throws Exception {
        when(albumService.obtenerAlbumsPaginados(null, null, null, null))
                .thenThrow(new RuntimeException("SQLSTATE 42P01 detalle interno"));

        mockMvc.perform(get("/api/albums"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("Ocurrió un error interno. Intenta nuevamente más tarde."))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("SQLSTATE"))));
    }

    @Test
    @DisplayName("GET /api/albums/{id}/summary debe responder 200 con resumen")
    void obtenerResumenAlbumDebeResponderOk() throws Exception {
        when(albumService.obtenerResumenAlbum(5L))
                .thenReturn(new AlbumSummaryDTO(
                        5L,
                        "Coleccion 2025",
                        2025,
                        true,
                        50,
                        40,
                        5,
                        10
                ));

        mockMvc.perform(get("/api/albums/5/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resumen del álbum recuperado exitosamente"))
                .andExpect(jsonPath("$.data.id").value(5))
                .andExpect(jsonPath("$.data.totalCatalogo").value(50))
                .andExpect(jsonPath("$.data.totalLaminas").value(40))
                .andExpect(jsonPath("$.data.laminasRepetidasTotal").value(5))
                .andExpect(jsonPath("$.data.laminasFaltantesTotal").value(10))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/albums/{albumId}/ganadores debe responder 200 con ganadores")
    void obtenerGanadoresPorAlbumDebeResponderOk() throws Exception {
        when(ganadorAlbumService.obtenerGanadoresPorAlbum(42L))
                .thenReturn(List.of(
                        GanadorAlbumDTO.builder().artista("Artista Uno").premio("Oro").anio(2024).build(),
                        GanadorAlbumDTO.builder().artista("Artista Dos").premio("Plata").anio(2023).build()
                ));

        mockMvc.perform(get("/api/albums/42/ganadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ganadores del álbum recuperados exitosamente"))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].artista").value("Artista Uno"))
                .andExpect(jsonPath("$.data[0].premio").value("Oro"))
                .andExpect(jsonPath("$.data[0].anio").value(2024));
    }

    @Test
    @DisplayName("GET /api/albums/{albumId}/ganadores debe responder 404 cuando album no existe")
    void obtenerGanadoresPorAlbumDebeResponderNotFound() throws Exception {
        when(ganadorAlbumService.obtenerGanadoresPorAlbum(9999L))
                .thenThrow(new ResourceNotFoundException("Album", "ID", 9999L));

        mockMvc.perform(get("/api/albums/9999/ganadores"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"));
    }

    @Test
    @DisplayName("GET /api/albums/{albumId}/ganadores mantiene orden por anio descendente")
    void obtenerGanadoresPorAlbumDebeMantenerOrdenDescendente() throws Exception {
        when(ganadorAlbumService.obtenerGanadoresPorAlbum(42L))
                .thenReturn(List.of(
                        GanadorAlbumDTO.builder().artista("Artista 2025").premio("Platino").anio(2025).build(),
                        GanadorAlbumDTO.builder().artista("Artista 2022").premio("Oro").anio(2022).build(),
                        GanadorAlbumDTO.builder().artista("Artista 2019").premio("Plata").anio(2019).build()
                ));

        mockMvc.perform(get("/api/albums/42/ganadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].anio").value(2025))
                .andExpect(jsonPath("$.data[1].anio").value(2022))
                .andExpect(jsonPath("$.data[2].anio").value(2019));
    }
}

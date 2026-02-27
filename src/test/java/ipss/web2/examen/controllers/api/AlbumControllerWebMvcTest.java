package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.dtos.AlbumSummaryDTO;
import ipss.web2.examen.services.AlbumService;
import ipss.web2.examen.services.GanadorAlbumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlbumController.class)
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
        when(albumService.obtenerAlbumsFiltrados(2020, false))
                .thenReturn(List.of(AlbumResponseDTO.builder()
                        .id(10L)
                        .nombre("Clasicos")
                        .year(2020)
                        .descripcion("Album inactivo")
                        .build()));

        mockMvc.perform(get("/api/albums")
                        .param("year", "2020")
                        .param("active", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Álbumes recuperados exitosamente"))
                .andExpect(jsonPath("$.data[0].id").value(10))
                .andExpect(jsonPath("$.data[0].nombre").value("Clasicos"))
                .andExpect(jsonPath("$.data[0].year").value(2020))
                .andExpect(jsonPath("$.timestamp").exists());
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
}

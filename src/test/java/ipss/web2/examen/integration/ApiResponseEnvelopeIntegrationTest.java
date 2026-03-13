package ipss.web2.examen.integration;

import ipss.web2.examen.services.AlbumService;
import ipss.web2.examen.services.GanadorAlbumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiResponseEnvelopeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlbumService albumService;

    @MockitoBean
    private GanadorAlbumService ganadorAlbumService;

    @Test
    @DisplayName("GET /api/albums ante error inesperado debe responder 500 con envelope uniforme")
    void unexpectedErrorMustReturnStandardizedInternalErrorEnvelope() throws Exception {
        when(albumService.obtenerAlbumsPaginados(null, null, null, null))
                .thenThrow(new RuntimeException("SQLSTATE 42P01 detalle interno"));

        mockMvc.perform(get("/api/albums"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Ocurrió un error interno. Intenta nuevamente más tarde."))
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message", not(containsString("SQLSTATE"))));
    }
}


package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.services.PresidenteChileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PresidenteChileController.class)
@Import(GlobalExceptionHandler.class)
class PresidenteChileControllerWebMvcTest extends ApiResponseEnvelopeTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PresidenteChileService presidenteChileService;

    @Test
    @DisplayName("GET /api/presidentes-chile debe responder 200 con ApiResponseDTO")
    void obtenerPresidentesChileDebeResponderOk() throws Exception {
        when(presidenteChileService.obtenerPresidentesChile())
                .thenReturn(List.of(PresidenteChileResponseDTO.builder()
                        .id(1L)
                        .nombre("Patricio Aylwin")
                        .periodoInicio(LocalDate.of(1990, 3, 11))
                        .periodoFin(LocalDate.of(1994, 3, 11))
                        .partido("Democracia Cristiana")
                        .descripcion("Transición democrática")
                        .build()));

        assertSuccessEnvelope(mockMvc.perform(get("/api/presidentes-chile"))
                        .andExpect(status().isOk()), "Presidentes de Chile recuperados exitosamente")
                .andExpect(jsonPath("$.data[0].nombre").value("Patricio Aylwin"))
                .andExpect(jsonPath("$.data[0].partido").value("Democracia Cristiana"));
    }

    @Test
    @DisplayName("GET /api/presidentes-chile/{id} debe responder 200 con envelope uniforme")
    void obtenerPresidenteChilePorIdDebeResponderEnvelopeExito() throws Exception {
        when(presidenteChileService.obtenerPresidentesChile())
                .thenReturn(List.of(PresidenteChileResponseDTO.builder()
                        .id(1L)
                        .nombre("Patricio Aylwin")
                        .periodoInicio(LocalDate.of(1990, 3, 11))
                        .periodoFin(LocalDate.of(1994, 3, 11))
                        .partido("Democracia Cristiana")
                        .descripcion("Transición democrática")
                        .build()));

        assertSuccessEnvelope(mockMvc.perform(get("/api/presidentes-chile/1"))
                        .andExpect(status().isOk()), "Presidente de Chile recuperado exitosamente")
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Patricio Aylwin"));
    }

    @Test
    @DisplayName("GET /api/presidentes-chile debe responder 200 con lista vacía")
    void obtenerPresidentesChileListaVacia() throws Exception {
        when(presidenteChileService.obtenerPresidentesChile()).thenReturn(List.of());

        assertSuccessEnvelope(mockMvc.perform(get("/api/presidentes-chile"))
                        .andExpect(status().isOk()), "Presidentes de Chile recuperados exitosamente")
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/presidentes-chile debe responder 500 ante error inesperado")
    void obtenerPresidentesChileErrorInesperado() throws Exception {
        when(presidenteChileService.obtenerPresidentesChile())
                .thenThrow(new RuntimeException("Fallo inesperado"));

        mockMvc.perform(get("/api/presidentes-chile"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}


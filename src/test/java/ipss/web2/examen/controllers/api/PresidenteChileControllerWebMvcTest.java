package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.services.PresidenteChileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class PresidenteChileControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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

        mockMvc.perform(get("/api/presidentes-chile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Presidentes de Chile recuperados exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Patricio Aylwin"))
                .andExpect(jsonPath("$.data[0].partido").value("Democracia Cristiana"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("GET /api/presidentes-chile debe responder 200 con lista vacía")
    void obtenerPresidentesChileListaVacia() throws Exception {
        when(presidenteChileService.obtenerPresidentesChile()).thenReturn(List.of());

        mockMvc.perform(get("/api/presidentes-chile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
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

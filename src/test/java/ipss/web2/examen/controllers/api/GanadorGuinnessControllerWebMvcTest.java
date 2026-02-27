package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.services.GanadorGuinnessService;
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

@WebMvcTest(GanadorGuinnessController.class)
class GanadorGuinnessControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GanadorGuinnessService ganadorGuinnessService;

    @Test
    @DisplayName("GET /api/ganadores-guinness debe responder 200 con ApiResponseDTO")
    void obtenerGanadoresGuinnessDebeResponderOk() throws Exception {
        when(ganadorGuinnessService.obtenerGanadoresGuinness())
                .thenReturn(List.of(GanadorGuinnessResponseDTO.builder()
                        .nombre("Drew")
                        .categoria("Velocidad")
                        .record("Mayor velocidad registrada")
                        .anio(2022)
                        .build()));

        mockMvc.perform(get("/api/ganadores-guinness"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ganadores Guinness recuperados exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Drew"))
                .andExpect(jsonPath("$.data[0].categoria").value("Velocidad"))
                .andExpect(jsonPath("$.data[0].record").value("Mayor velocidad registrada"))
                .andExpect(jsonPath("$.data[0].anio").value(2022))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

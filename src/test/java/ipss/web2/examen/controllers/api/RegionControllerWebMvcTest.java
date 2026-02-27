package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.services.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegionController.class)
@Import(GlobalExceptionHandler.class)
class RegionControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegionService regionService;

    @Test
    @DisplayName("GET /api/regiones responde con lista de regiones")
    void obtenerRegionesRespondeOk() throws Exception {
        RegionResponseDTO region = RegionResponseDTO.builder()
                .id(1L)
                .codigo("V")
                .nombre("Región de Valparaíso")
                .build();

        when(regionService.obtenerRegionesActivas()).thenReturn(Collections.singletonList(region));

        mockMvc.perform(get("/api/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Regiones recuperadas exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Región de Valparaíso"));
    }

    @Test
    @DisplayName("GET /api/regiones responde con lista vacía")
    void obtenerRegionesListaVacia() throws Exception {
        when(regionService.obtenerRegionesActivas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/regiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }
}

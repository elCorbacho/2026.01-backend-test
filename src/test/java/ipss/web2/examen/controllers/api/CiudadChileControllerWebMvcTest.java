package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.CiudadChileResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.CiudadChileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CiudadChileController.class)
@Import(GlobalExceptionHandler.class)
class CiudadChileControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CiudadChileService ciudadChileService;

    @Test
    @DisplayName("GET /api/ciudades-chile responde con listado")
    void obtenerCiudadesChileRespondeOk() throws Exception {
        CiudadChileResponseDTO santiago = CiudadChileResponseDTO.builder()
                .id(1L)
                .nombre("Santiago")
                .poblacion(6257516L)
                .build();

        when(ciudadChileService.obtenerCiudadesChile()).thenReturn(List.of(santiago));

        mockMvc.perform(get("/api/ciudades-chile"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ciudades de Chile recuperadas exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Santiago"))
                .andExpect(jsonPath("$.data[0].poblacion").value(6257516L));
    }

    @Test
    @DisplayName("GET /api/ciudades-chile/{id} responde con ciudad existente")
    void obtenerCiudadChilePorIdRespondeOk() throws Exception {
        CiudadChileResponseDTO santiago = CiudadChileResponseDTO.builder()
                .id(1L)
                .nombre("Santiago")
                .poblacion(6257516L)
                .build();

        when(ciudadChileService.obtenerCiudadChilePorId(1L)).thenReturn(santiago);

        mockMvc.perform(get("/api/ciudades-chile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ciudad de Chile recuperada exitosamente"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("GET /api/ciudades-chile/{id} responde 404 cuando no existe")
    void obtenerCiudadChilePorIdNoExiste() throws Exception {
        when(ciudadChileService.obtenerCiudadChilePorId(999L))
                .thenThrow(new ResourceNotFoundException("Ciudad", "id", 999L, "CIUDAD_NOT_FOUND"));

        mockMvc.perform(get("/api/ciudades-chile/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("CIUDAD_NOT_FOUND"));
    }

    @Test
    @DisplayName("GET /api/ciudades-chile/{id} responde 400 para tipo de id invalido")
    void obtenerCiudadChilePorIdTipoInvalido() throws Exception {
        mockMvc.perform(get("/api/ciudades-chile/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INVALID_PARAMETER_TYPE"));
    }
}


package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ListadoOlimpiadasResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.ListadoOlimpiadasService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListadoOlimpiadasController.class)
@Import(GlobalExceptionHandler.class)
class ListadoOlimpiadasControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListadoOlimpiadasService listadoOlimpiadasService;

    @Test
    @DisplayName("POST /api/listado-olimpiadas debe responder 201 con ApiResponseDTO")
    void crearListadoOlimpiadasDebeResponderCreated() throws Exception {
        when(listadoOlimpiadasService.crearListadoOlimpiadas(org.mockito.ArgumentMatchers.any()))
                .thenReturn(ListadoOlimpiadasResponseDTO.builder()
                        .id(1L)
                        .nombre("París 2024")
                        .ciudad("París")
                        .pais("Francia")
                        .anio(2024)
                        .temporada("Verano")
                        .build());

        String body = "{" +
                "\"nombre\":\"París 2024\"," +
                "\"ciudad\":\"París\"," +
                "\"pais\":\"Francia\"," +
                "\"anio\":2024," +
                "\"temporada\":\"Verano\"" +
                "}";

        mockMvc.perform(post("/api/listado-olimpiadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Listado de olimpiadas creado correctamente"))
                .andExpect(jsonPath("$.data.nombre").value("París 2024"));
    }

    @Test
    @DisplayName("POST /api/listado-olimpiadas debe responder 400 con errores de validación")
    void crearListadoOlimpiadasDebeResponderErrorValidacion() throws Exception {
        String body = "{" +
                "\"nombre\":\"\"," +
                "\"ciudad\":\"\"," +
                "\"pais\":\"\"," +
                "\"anio\":0," +
                "\"temporada\":\"\"" +
                "}";

        mockMvc.perform(post("/api/listado-olimpiadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET /api/listado-olimpiadas responde con lista vacía")
    void obtenerListadoOlimpiadasListaVacia() throws Exception {
        when(listadoOlimpiadasService.obtenerListadoOlimpiadas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/listado-olimpiadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/listado-olimpiadas/{id} responde 404 cuando no existe")
    void obtenerListadoOlimpiadasPorIdInexistente() throws Exception {
        when(listadoOlimpiadasService.obtenerListadoOlimpiadasPorId(9L))
                .thenThrow(new ResourceNotFoundException("ListadoOlimpiadas", "ID", 9L));

        mockMvc.perform(get("/api/listado-olimpiadas/9"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"));
    }
}

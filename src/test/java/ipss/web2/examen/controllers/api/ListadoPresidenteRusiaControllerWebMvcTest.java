package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.services.ListadoPresidenteRusiaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListadoPresidenteRusiaController.class)
@Import(GlobalExceptionHandler.class)
class ListadoPresidenteRusiaControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ListadoPresidenteRusiaService listadoPresidenteRusiaService;

    @Test
    @DisplayName("GET /api/listado-presidente-rusia debe responder 200 con ApiResponseDTO")
    void obtenerPresidentesRusiaDebeResponderOk() throws Exception {
        when(listadoPresidenteRusiaService.obtenerPresidentesRusia())
                .thenReturn(List.of(ListadoPresidenteRusiaResponseDTO.builder()
                        .id(1L)
                        .nombre("Boris Yeltsin")
                        .periodoInicio(LocalDate.of(1991, 7, 10))
                        .periodoFin(LocalDate.of(1999, 12, 31))
                        .partido("Independiente")
                        .descripcion("Primer presidente de Rusia")
                        .build()));

        mockMvc.perform(get("/api/listado-presidente-rusia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Presidentes de Rusia recuperados exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Boris Yeltsin"))
                .andExpect(jsonPath("$.data[0].partido").value("Independiente"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/listado-presidente-rusia debe responder 201 con ApiResponseDTO")
    void crearPresidenteRusiaDebeResponderCreated() throws Exception {
        ListadoPresidenteRusiaRequestDTO request = ListadoPresidenteRusiaRequestDTO.builder()
                .nombre("Vladimir Putin")
                .periodoInicio(LocalDate.of(2012, 5, 7))
                .partido("Rusia Unida")
                .descripcion("Presidente desde 2012")
                .build();

        when(listadoPresidenteRusiaService.crearPresidenteRusia(any(ListadoPresidenteRusiaRequestDTO.class)))
                .thenReturn(ListadoPresidenteRusiaResponseDTO.builder()
                        .id(5L)
                        .nombre("Vladimir Putin")
                        .periodoInicio(LocalDate.of(2012, 5, 7))
                        .partido("Rusia Unida")
                        .descripcion("Presidente desde 2012")
                        .build());

        mockMvc.perform(post("/api/listado-presidente-rusia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Presidente de Rusia creado correctamente"))
                .andExpect(jsonPath("$.data.id").value(5))
                .andExpect(jsonPath("$.data.nombre").value("Vladimir Putin"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/listado-presidente-rusia debe responder 400 con error de validacion")
    void crearPresidenteRusiaDebeValidarRequest() throws Exception {
        mockMvc.perform(post("/api/listado-presidente-rusia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errors.nombre").exists())
                .andExpect(jsonPath("$.errors.periodoInicio").exists());
    }
}

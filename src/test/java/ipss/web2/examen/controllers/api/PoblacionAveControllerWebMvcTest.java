package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.dtos.PoblacionAveResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.PoblacionAveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PoblacionAveController.class)
@Import(GlobalExceptionHandler.class)
class PoblacionAveControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PoblacionAveService poblacionAveService;

    @Test
    @DisplayName("POST /api/poblaciones-ave responde 201 en creacion valida")
    void crearPoblacionAveDebeResponderCreated() throws Exception {
        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(1L)
                .cantidad(80)
                .fecha(LocalDate.of(2025, 3, 1))
                .build();

        when(poblacionAveService.crearPoblacionAve(any(PoblacionAveRequestDTO.class)))
                .thenReturn(PoblacionAveResponseDTO.builder().id(4L).tipoAveId(1L).cantidad(80).build());

        mockMvc.perform(post("/api/poblaciones-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(4));
    }

    @Test
    @DisplayName("POST /api/poblaciones-ave responde 400 en validacion")
    void crearPoblacionAveDebeValidarPayload() throws Exception {
        mockMvc.perform(post("/api/poblaciones-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("POST /api/poblaciones-ave responde 400 con cantidad fuera de rango")
    void crearPoblacionAveDebeValidarCantidadFueraDeRango() throws Exception {
        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(1L)
                .cantidad(0)
                .fecha(LocalDate.of(2025, 3, 1))
                .build();

        mockMvc.perform(post("/api/poblaciones-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET /api/poblaciones-ave/{id} responde 404 cuando no existe")
    void obtenerPoblacionAvePorIdDebeResponderNotFound() throws Exception {
        when(poblacionAveService.obtenerPoblacionAvePorId(7L))
                .thenThrow(new ResourceNotFoundException("PoblacionAve", "ID", 7L, "POBLACION_AVE_NOT_FOUND"));

        mockMvc.perform(get("/api/poblaciones-ave/{id}", 7))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("POBLACION_AVE_NOT_FOUND"));
    }

    @Test
    @DisplayName("GET /api/poblaciones-ave responde 400 con error de negocio")
    void obtenerPoblacionesAveConPaginacionInvalidaDebeResponderBadRequest() throws Exception {
        when(poblacionAveService.obtenerPoblacionesPaginadas(-1, 10, null))
                .thenThrow(new InvalidOperationException("El parametro 'page' debe ser mayor o igual a 0", "INVALID_PAGE"));

        mockMvc.perform(get("/api/poblaciones-ave")
                        .param("page", "-1")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INVALID_PAGE"));
    }
}


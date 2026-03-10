package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.TipoAveService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoAveController.class)
@Import(GlobalExceptionHandler.class)
class TipoAveControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipoAveService tipoAveService;

    @Test
    @DisplayName("POST /api/tipos-ave responde 201 en creacion valida")
    void crearTipoAveDebeResponderCreated() throws Exception {
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder()
                .nombre("Condor")
                .descripcion("Ave andina")
                .build();

        when(tipoAveService.crearTipoAve(any(TipoAveRequestDTO.class)))
                .thenReturn(TipoAveResponseDTO.builder().id(1L).nombre("Condor").build());

        mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("POST /api/tipos-ave responde 400 en validacion")
    void crearTipoAveDebeValidarPayload() throws Exception {
        mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("GET /api/tipos-ave/{id} responde 404 cuando no existe")
    void obtenerTipoAvePorIdDebeResponderNotFound() throws Exception {
        when(tipoAveService.obtenerTipoAvePorId(9L))
                .thenThrow(new ResourceNotFoundException("TipoAve", "ID", 9L, "TIPO_AVE_NOT_FOUND"));

        mockMvc.perform(get("/api/tipos-ave/{id}", 9))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TIPO_AVE_NOT_FOUND"));
    }
}

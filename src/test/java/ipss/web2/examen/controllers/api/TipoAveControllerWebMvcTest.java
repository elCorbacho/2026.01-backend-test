package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.InvalidOperationException;
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
class TipoAveControllerWebMvcTest extends ApiResponseEnvelopeTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipoAveService tipoAveService;

    @Test
        @DisplayName("POST /api/tipos-ave responde 201 con envelope uniforme en creacion valida")
    void crearTipoAveDebeResponderCreated() throws Exception {
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder()
                .nombre("Condor")
                .descripcion("Ave andina")
                .build();

        when(tipoAveService.crearTipoAve(any(TipoAveRequestDTO.class)))
                .thenReturn(TipoAveResponseDTO.builder().id(1L).nombre("Condor").build());

        assertSuccessEnvelope(mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()), "Tipo de ave creado correctamente")
                .andExpect(jsonPath("$.errorCode").doesNotExist())
                .andExpect(jsonPath("$.errors").doesNotExist())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    @DisplayName("POST /api/tipos-ave responde 400 en validacion")
    void crearTipoAveDebeValidarPayload() throws Exception {
        assertErrorEnvelope(mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest()), "VALIDATION_ERROR")
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("POST /api/tipos-ave responde 400 en error de negocio manejado")
    void crearTipoAveDebeMapearErrorDeNegocio() throws Exception {
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder()
                .nombre("Condor")
                .descripcion("Ave andina")
                .build();

        when(tipoAveService.crearTipoAve(any(TipoAveRequestDTO.class)))
                .thenThrow(new InvalidOperationException("Operacion invalida", "INVALID_OPERATION"));

        assertErrorEnvelope(mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest()), "INVALID_OPERATION")
                .andExpect(jsonPath("$.message").value("Operacion invalida"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("GET /api/tipos-ave/{id} responde 404 cuando no existe")
    void obtenerTipoAvePorIdDebeResponderNotFound() throws Exception {
        when(tipoAveService.obtenerTipoAvePorId(9L))
                .thenThrow(new ResourceNotFoundException("TipoAve", "ID", 9L, "TIPO_AVE_NOT_FOUND"));

        assertErrorEnvelope(mockMvc.perform(get("/api/tipos-ave/{id}", 9))
                .andExpect(status().isNotFound()), "TIPO_AVE_NOT_FOUND");
    }
}

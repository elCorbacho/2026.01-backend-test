package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TipoAveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TipoAveRepository tipoAveRepository;

    @Autowired
    private PoblacionAveRepository poblacionAveRepository;

    @BeforeEach
    void setUp() {
        poblacionAveRepository.deleteAll();
        tipoAveRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/tipos-ave crea tipo de ave y responde envelope")
    void crearTipoAveDebeResponderCreated() throws Exception {
        TipoAveRequestDTO requestDTO = TipoAveRequestDTO.builder()
                .nombre("Zorzal")
                .descripcion("Ave comun")
                .build();

        mockMvc.perform(post("/api/tipos-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Tipo de ave creado correctamente"))
                .andExpect(jsonPath("$.errorCode").doesNotExist())
                .andExpect(jsonPath("$.data.nombre").value("Zorzal"));

        assertThat(tipoAveRepository.count()).isEqualTo(1);
    }

        @Test
        @DisplayName("POST /api/tipos-ave responde 400 con payload invalido")
        void crearTipoAveDebeResponderBadRequestConPayloadInvalido() throws Exception {
                mockMvc.perform(post("/api/tipos-ave")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content("{}"))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.success").value(false))
                                .andExpect(jsonPath("$.message").exists())
                                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                                .andExpect(jsonPath("$.data").doesNotExist());
        }

    @Test
    @DisplayName("DELETE /api/tipos-ave/{id} aplica soft delete")
    void eliminarTipoAveDebeMarcarInactivo() throws Exception {
        TipoAve tipoAve = tipoAveRepository.save(TipoAve.builder()
                .nombre("Flamenco")
                .descripcion("Laguna")
                .active(true)
                .build());

        mockMvc.perform(delete("/api/tipos-ave/{id}", tipoAve.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        TipoAve persisted = tipoAveRepository.findById(tipoAve.getId()).orElseThrow();
        assertThat(persisted.getActive()).isFalse();
    }

    @Test
    @DisplayName("DELETE /api/tipos-ave/{id} desactiva dependencias y mantiene integridad en consultas")
    void eliminarTipoAveConDependenciasDebeReflejarComportamientoConsistente() throws Exception {
        TipoAve tipoAve = tipoAveRepository.save(TipoAve.builder()
                .nombre("Garza")
                .descripcion("Humedales")
                .active(true)
                .build());

        PoblacionAve poblacionAve = poblacionAveRepository.save(PoblacionAve.builder()
                .tipoAve(tipoAve)
                .cantidad(21)
                .fecha(LocalDate.of(2025, 3, 1))
                .active(true)
                .build());

        mockMvc.perform(delete("/api/tipos-ave/{id}", tipoAve.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(get("/api/poblaciones-ave")
                        .param("page", "0")
                        .param("size", "10")
                        .param("tipoAveId", String.valueOf(tipoAve.getId())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TIPO_AVE_NOT_FOUND"));

        TipoAve tipoPersistido = tipoAveRepository.findById(tipoAve.getId()).orElseThrow();
        PoblacionAve poblacionPersistida = poblacionAveRepository.findById(poblacionAve.getId()).orElseThrow();

        assertThat(tipoPersistido.getActive()).isFalse();
        assertThat(poblacionPersistida.getActive()).isFalse();
    }

    @Test
    @DisplayName("GET /api/tipos-ave lista solo tipos activos")
    void obtenerTiposAveDebeFiltrarActivos() throws Exception {
        tipoAveRepository.save(TipoAve.builder().nombre("Condor").descripcion("A").active(true).build());
        tipoAveRepository.save(TipoAve.builder().nombre("Inactivo").descripcion("I").active(false).build());

        mockMvc.perform(get("/api/tipos-ave"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content.length()").value(1))
                .andExpect(jsonPath("$.data.content[0].nombre").value("Condor"));
    }

        @Test
        @DisplayName("GET /v3/api-docs documenta POST /api/tipos-ave con respuestas 201 y 400")
        void openApiDebeDocumentarContratoPostTipoAve() throws Exception {
                mockMvc.perform(get("/v3/api-docs"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.paths['/api/tipos-ave'].post").exists())
                                .andExpect(jsonPath("$.paths['/api/tipos-ave'].post.responses['201']").exists())
                                .andExpect(jsonPath("$.paths['/api/tipos-ave'].post.responses['400']").exists());
        }
}

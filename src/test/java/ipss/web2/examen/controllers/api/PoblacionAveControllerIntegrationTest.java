package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
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
class PoblacionAveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PoblacionAveRepository poblacionAveRepository;

    @Autowired
    private TipoAveRepository tipoAveRepository;

    @BeforeEach
    void setUp() {
        poblacionAveRepository.deleteAll();
        tipoAveRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/poblaciones-ave crea poblacion asociada a tipo existente")
    void crearPoblacionAveDebeResponderCreated() throws Exception {
        TipoAve tipoAve = tipoAveRepository.save(TipoAve.builder()
                .nombre("Condor")
                .descripcion("Andes")
                .active(true)
                .build());

        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(tipoAve.getId())
                .cantidad(45)
                .fecha(LocalDate.of(2025, 2, 2))
                .build();

        mockMvc.perform(post("/api/poblaciones-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.tipoAveId").value(tipoAve.getId()))
                .andExpect(jsonPath("$.data.cantidad").value(45));

        assertThat(poblacionAveRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("POST /api/poblaciones-ave responde 404 con tipo inexistente")
    void crearPoblacionAveConTipoInexistenteDebeResponder404() throws Exception {
        PoblacionAveRequestDTO requestDTO = PoblacionAveRequestDTO.builder()
                .tipoAveId(999L)
                .cantidad(10)
                .fecha(LocalDate.of(2025, 1, 1))
                .build();

        mockMvc.perform(post("/api/poblaciones-ave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TIPO_AVE_NOT_FOUND"));
    }

    @Test
    @DisplayName("DELETE /api/poblaciones-ave/{id} aplica soft delete")
    void eliminarPoblacionAveDebeMarcarInactiva() throws Exception {
        TipoAve tipoAve = tipoAveRepository.save(TipoAve.builder().nombre("Flamenco").active(true).build());
        PoblacionAve poblacion = poblacionAveRepository.save(PoblacionAve.builder()
                .tipoAve(tipoAve)
                .cantidad(99)
                .fecha(LocalDate.of(2025, 1, 5))
                .active(true)
                .build());

        mockMvc.perform(delete("/api/poblaciones-ave/{id}", poblacion.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        PoblacionAve persisted = poblacionAveRepository.findById(poblacion.getId()).orElseThrow();
        assertThat(persisted.getActive()).isFalse();
    }

    @Test
    @DisplayName("GET /api/poblaciones-ave excluye registros con soft delete")
    void obtenerPoblacionesAveDebeExcluirRegistrosInactivos() throws Exception {
        TipoAve tipoAve = tipoAveRepository.save(TipoAve.builder().nombre("Gaviota").active(true).build());

        PoblacionAve activa = poblacionAveRepository.save(PoblacionAve.builder()
                .tipoAve(tipoAve)
                .cantidad(15)
                .fecha(LocalDate.of(2025, 1, 12))
                .active(true)
                .build());

        PoblacionAve inactiva = poblacionAveRepository.save(PoblacionAve.builder()
                .tipoAve(tipoAve)
                .cantidad(7)
                .fecha(LocalDate.of(2025, 1, 13))
                .active(true)
                .build());

        mockMvc.perform(delete("/api/poblaciones-ave/{id}", inactiva.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/poblaciones-ave")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalElements").value(1))
                .andExpect(jsonPath("$.data.content[0].id").value(activa.getId()));
    }
}

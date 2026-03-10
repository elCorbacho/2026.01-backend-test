package ipss.web2.examen.controllers.api;

import ipss.web2.examen.config.MarcaCamionDataInitializer;
import ipss.web2.examen.models.MarcaCamion;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MarcaCamionControllerIntegrationTest {

    private static final int EXPECTED_COUNT = 3;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MarcaCamionRepository marcaCamionRepository;

    @Autowired
    private MarcaCamionDataInitializer marcaCamionDataInitializer;

    @Test
    @DisplayName("GET /api/marcas-camiones devuelve marcas y no duplica seed")
    void listarMarcasActivas() throws Exception {
        mockMvc.perform(get("/api/marcas-camiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marcas camiones recuperadas"))
                .andExpect(jsonPath("$.data.length()").value(EXPECTED_COUNT))
                .andExpect(jsonPath("$.data[0].nombre").isNotEmpty());

        assertThat(marcaCamionRepository.count()).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    @DisplayName("GET /api/marcas-camiones devuelve 200 con data vacia cuando no hay activos")
    void listarMarcasActivasSinRegistros() throws Exception {
        marcaCamionRepository.deleteAll();

        mockMvc.perform(get("/api/marcas-camiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marcas camiones recuperadas"))
                .andExpect(jsonPath("$.data.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/marcas-camiones excluye registros inactivos")
    void listarMarcasActivasExcluyeInactivas() throws Exception {
        marcaCamionRepository.deleteAll();
        marcaCamionRepository.save(MarcaCamion.builder().nombre("Scania").paisOrigen("Suecia").descripcion("Activa").active(true).build());
        marcaCamionRepository.save(MarcaCamion.builder().nombre("Kamaz").paisOrigen("Rusia").descripcion("Inactiva").active(false).build());

        mockMvc.perform(get("/api/marcas-camiones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].nombre").value("Scania"));
    }

    @Test
    @DisplayName("Inicializador de marcas de camiones no duplica semillas al reejecutarse")
    void inicializadorNoDuplicaSemillas() throws Exception {
        marcaCamionRepository.deleteAll();

        marcaCamionDataInitializer.run();
        long totalPrimeraEjecucion = marcaCamionRepository.count();

        marcaCamionDataInitializer.run();
        long totalSegundaEjecucion = marcaCamionRepository.count();

        assertThat(totalPrimeraEjecucion).isEqualTo(EXPECTED_COUNT);
        assertThat(totalSegundaEjecucion).isEqualTo(EXPECTED_COUNT);
    }
}

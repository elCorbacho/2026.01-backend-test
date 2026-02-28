package ipss.web2.examen.controllers.api;

import ipss.web2.examen.repositories.MarcaAutomovilRepository;
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
class MarcaAutomovilControllerIntegrationTest {

    private static final int EXPECTED_COUNT = 30;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MarcaAutomovilRepository marcaAutomovilRepository;

    @Test
    @DisplayName("GET /api/marcas devuelve 30 marcas y no duplica data")
    void listarMarcasActivas() throws Exception {
        mockMvc.perform(get("/api/marcas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Marcas recuperadas"))
                .andExpect(jsonPath("$.data.length()").value(EXPECTED_COUNT))
                .andExpect(jsonPath("$.data[0].nombre").isNotEmpty());

        mockMvc.perform(get("/api/marcas")).andExpect(status().isOk());

        assertThat(marcaAutomovilRepository.count()).isEqualTo(EXPECTED_COUNT);
    }
}

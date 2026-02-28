package ipss.web2.examen.controllers.api;

import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.services.EquipoFutbolEspanaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EquipoFutbolEspanaController.class)
class EquipoFutbolEspanaControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipoFutbolEspanaService service;

    @Test
    void obtenerEquipos_devuelveListaEnApiResponseDTO() throws Exception {
        Mockito.when(service.obtenerEquiposActivos()).thenReturn(Arrays.asList(
            EquipoFutbolEspana.builder().nombre("Real Madrid").activo(true).build(),
            EquipoFutbolEspana.builder().nombre("FC Barcelona").activo(true).build()
        ));
        mockMvc.perform(get("/api/equipos-futbol-espana"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data[0].nombre").value("Real Madrid"))
            .andExpect(jsonPath("$.data[1].nombre").value("FC Barcelona"));
    }
}

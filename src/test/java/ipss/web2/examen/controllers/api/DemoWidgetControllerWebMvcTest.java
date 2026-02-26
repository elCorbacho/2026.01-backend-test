package ipss.web2.examen.controllers.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DemoWidgetController.class)
class DemoWidgetControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/demo-widget/sample debe responder 200 con envelope ApiResponseDTO")
    void sampleShouldReturnOkResponse() throws Exception {
        mockMvc.perform(get("/api/demo-widget/sample"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Widget de prueba"))
                .andExpect(jsonPath("$.data.id").value(99))
                .andExpect(jsonPath("$.data.nombre").value("Demo widget ligero"))
                .andExpect(jsonPath("$.data.tipo").value("test-simple"))
                .andExpect(jsonPath("$.data.active").value(true))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

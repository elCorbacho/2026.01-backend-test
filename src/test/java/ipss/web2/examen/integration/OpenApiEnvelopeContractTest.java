package ipss.web2.examen.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiEnvelopeContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /v3/api-docs expone endpoints representativos con respuestas estandarizadas")
    void openApiMustDocumentRepresentativeEndpointsWithStandardizedResponses() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/albums'].get.responses['200']").exists())
                .andExpect(jsonPath("$.paths['/api/albums'].get.responses['400']").exists())
                .andExpect(jsonPath("$.paths['/api/albums'].get.responses['500']").exists())
                .andExpect(jsonPath("$.paths['/api/albums/{id}'].get.responses['200']").exists())
                .andExpect(jsonPath("$.paths['/api/albums/{id}'].get.responses['404']").exists())
                .andExpect(jsonPath("$.paths['/api/tipos-ave'].post.responses['201']").exists())
                .andExpect(jsonPath("$.paths['/api/tipos-ave'].post.responses['400']").exists())
                .andExpect(jsonPath("$.paths['/api/presidentes-chile/{id}'].get.responses['200']").exists())
                .andExpect(jsonPath("$.paths['/api/presidentes-chile/{id}'].get.responses['404']").exists())
                .andExpect(jsonPath("$.components.schemas.ApiResponseDTO").exists());
    }

    @Test
    @DisplayName("Runtime y OpenAPI deben mantener campos base del envelope")
    void runtimeAndOpenApiMustKeepEnvelopeTopLevelConsistency() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.components.schemas.ApiResponseDTO.properties.success").exists())
                .andExpect(jsonPath("$.components.schemas.ApiResponseDTO.properties.message").exists())
                .andExpect(jsonPath("$.components.schemas.ApiResponseDTO.properties.timestamp").exists())
                .andExpect(jsonPath("$.components.schemas.ApiResponseDTO.properties.errorCode").exists());

        mockMvc.perform(get("/api/albums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.timestamp").exists());

        mockMvc.perform(get("/api/albums/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.errorCode").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

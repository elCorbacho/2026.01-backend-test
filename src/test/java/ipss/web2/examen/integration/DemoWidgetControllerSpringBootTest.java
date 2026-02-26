package ipss.web2.examen.integration;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.models.DemoWidget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoWidgetControllerSpringBootTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("@SpringBootTest: GET /api/demo-widget/sample responde envelope y payload esperado")
    void sampleEndpointShouldReturnExpectedPayload() {
        ResponseEntity<ApiResponseDTO<DemoWidget>> response = restTemplate.exchange(
                "/api/demo-widget/sample",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSuccess()).isTrue();
        assertThat(response.getBody().getMessage()).isEqualTo("Widget de prueba");
        assertThat(response.getBody().getData()).isNotNull();
        assertThat(response.getBody().getData().getNombre()).isEqualTo("Demo widget ligero");
        assertThat(response.getBody().getData().getTipo()).isEqualTo("test-simple");
    }
}

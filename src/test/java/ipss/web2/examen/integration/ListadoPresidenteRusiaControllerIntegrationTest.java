package ipss.web2.examen.integration;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ListadoPresidenteRusiaControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ListadoPresidenteRusiaRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("PATCH /api/listado-presidente-rusia/{id} actualiza descripcion y partido")
    void patchPresidenteActualizaDescripcion() {
        ListadoPresidenteRusia presidente = repository.save(ListadoPresidenteRusia.builder()
                .nombre("Boris Yeltsin")
                .periodoInicio(LocalDate.of(1991, 7, 10))
                .periodoFin(LocalDate.of(1999, 12, 31))
                .partido("Independiente")
                .descripcion("Presidente")
                .active(true)
                .build());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(
                "{\"descripcion\":\"Primer presidente ruso\",\"partido\":\"Reformista\"}",
                headers);

        ResponseEntity<ApiResponseDTO<ListadoPresidenteRusiaResponseDTO>> response = restTemplate.exchange(
                "/api/listado-presidente-rusia/" + presidente.getId(),
                HttpMethod.PATCH,
                request,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getData().getDescripcion()).isEqualTo("Primer presidente ruso");
        assertThat(repository.findById(presidente.getId())).isPresent()
                .get()
                .extracting(ListadoPresidenteRusia::getPartido)
                .isEqualTo("Reformista");
    }

    @Test
    @DisplayName("DELETE /api/listado-presidente-rusia/{id} marca registro como inactivo")
    void deletePresidenteMarcaInactivo() {
        ListadoPresidenteRusia presidente = repository.save(ListadoPresidenteRusia.builder()
                .nombre("Dmitry Medvedev")
                .periodoInicio(LocalDate.of(2008, 5, 7))
                .periodoFin(LocalDate.of(2012, 5, 7))
                .partido("Rusia Unida")
                .descripcion("Periodo 2008-2012")
                .active(true)
                .build());

        ResponseEntity<ApiResponseDTO<Object>> response = restTemplate.exchange(
                "/api/listado-presidente-rusia/" + presidente.getId(),
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(repository.findByIdAndActiveTrue(presidente.getId())).isEmpty();
    }
}

package ipss.web2.examen.integration;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.models.RegionChile;
import ipss.web2.examen.repositories.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegionControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RegionRepository regionRepository;

    @BeforeEach
    void setUp() {
        regionRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /api/regiones excluye regiones inactivas")
    void obtenerRegionesIgnoraInactivas() {
        RegionChile activa = RegionChile.builder().codigo("III").nombre("Región de Atacama").active(true).build();
        RegionChile inactiva = RegionChile.builder().codigo("II").nombre("Región de Antofagasta").active(false).build();
        regionRepository.saveAll(List.of(activa, inactiva));

        ResponseEntity<ApiResponseDTO<List<RegionResponseDTO>>> response = restTemplate.exchange(
                "/api/regiones",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        ApiResponseDTO<List<RegionResponseDTO>> body = response.getBody();
        assertThat(body.getSuccess()).isTrue();
        assertThat(body.getData()).hasSize(1);
        assertThat(body.getData().get(0).getCodigo()).isEqualTo("III");
        assertThat(body.getData().get(0).getNombre()).isEqualTo("Región de Atacama");
    }
}

package ipss.web2.examen.services;

import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.mappers.RegionMapper;
import ipss.web2.examen.models.RegionChile;
import ipss.web2.examen.repositories.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    private RegionService regionService;

    @BeforeEach
    void setUp() {
        regionService = new RegionService(regionRepository, new RegionMapper());
    }

    @Test
    @DisplayName("Service debe devolver regiones activas" )
    void obtenerRegionesActivasDevuelveSoloActivas() {
        RegionChile peninsula = RegionChile.builder().codigo("V").nombre("Región de Valparaíso").active(true).build();
        RegionChile norte = RegionChile.builder().codigo("I").nombre("Región de Tarapacá").active(true).build();

        when(regionRepository.findByActiveTrueOrderByCodigoAsc()).thenReturn(List.of(peninsula, norte));

        List<RegionResponseDTO> resultado = regionService.obtenerRegionesActivas();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getCodigo()).isEqualTo("V");
        assertThat(resultado.get(1).getCodigo()).isEqualTo("I");
        assertThat(resultado.get(1).getNombre()).isEqualTo("Región de Tarapacá");

        verify(regionRepository).findByActiveTrueOrderByCodigoAsc();
    }
}

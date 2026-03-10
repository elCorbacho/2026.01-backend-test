package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaCamionResponseDTO;
import ipss.web2.examen.mappers.MarcaCamionMapper;
import ipss.web2.examen.models.MarcaCamion;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarcaCamionServiceTest {

    @Mock
    private MarcaCamionRepository marcaRepository;

    @Mock
    private MarcaCamionMapper marcaMapper;

    private MarcaCamionService marcaService;

    @BeforeEach
    void setUp() {
        marcaService = new MarcaCamionService(marcaRepository, marcaMapper);
    }

    @Test
    @DisplayName("Service debe devolver marcas de camion activas ordenadas")
    void shouldReturnActiveTruckBrandsOrdered() {
        MarcaCamion primera = MarcaCamion.builder().nombre("Iveco").active(true).build();
        MarcaCamion segunda = MarcaCamion.builder().nombre("MAN").active(true).build();
        MarcaCamion tercera = MarcaCamion.builder().nombre("Volvo Trucks").active(true).build();
        when(marcaRepository.findByActiveTrueOrderByNombreAsc()).thenReturn(List.of(primera, segunda, tercera));

        MarcaCamionResponseDTO dtoPrimera = MarcaCamionResponseDTO.builder().nombre("Iveco").build();
        MarcaCamionResponseDTO dtoSegunda = MarcaCamionResponseDTO.builder().nombre("MAN").build();
        MarcaCamionResponseDTO dtoTercera = MarcaCamionResponseDTO.builder().nombre("Volvo Trucks").build();
        when(marcaMapper.toResponseDTO(same(primera))).thenReturn(dtoPrimera);
        when(marcaMapper.toResponseDTO(same(segunda))).thenReturn(dtoSegunda);
        when(marcaMapper.toResponseDTO(same(tercera))).thenReturn(dtoTercera);

        List<MarcaCamionResponseDTO> resultado = marcaService.obtenerMarcasActivas();

        assertThat(resultado).containsExactly(dtoPrimera, dtoSegunda, dtoTercera);
        verify(marcaRepository).findByActiveTrueOrderByNombreAsc();
    }

    @Test
    @DisplayName("Service debe devolver lista vacia cuando no hay marcas activas")
    void shouldReturnEmptyListWhenNoActiveTruckBrands() {
        when(marcaRepository.findByActiveTrueOrderByNombreAsc()).thenReturn(List.of());

        List<MarcaCamionResponseDTO> resultado = marcaService.obtenerMarcasActivas();

        assertThat(resultado).isEmpty();
        verify(marcaRepository).findByActiveTrueOrderByNombreAsc();
    }
}

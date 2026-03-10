package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaBicicletaResponseDTO;
import ipss.web2.examen.mappers.MarcaBicicletaMapper;
import ipss.web2.examen.models.MarcaBicicleta;
import ipss.web2.examen.repositories.MarcaBicicletaRepository;
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
class MarcaBicicletaServiceTest {

    @Mock
    private MarcaBicicletaRepository marcaRepository;

    @Mock
    private MarcaBicicletaMapper marcaMapper;

    private MarcaBicicletaService marcaService;

    @BeforeEach
    void setUp() {
        marcaService = new MarcaBicicletaService(marcaRepository, marcaMapper);
    }

    @Test
    @DisplayName("Service debe devolver 3 marcas activas ordenadas")
    void shouldReturnActiveBrandsOrdered() {
        MarcaBicicleta primera = MarcaBicicleta.builder().nombre("Shimano").active(true).build();
        MarcaBicicleta segunda = MarcaBicicleta.builder().nombre("Bianchi").active(true).build();
        MarcaBicicleta tercera = MarcaBicicleta.builder().nombre("Specialized").active(true).build();
        when(marcaRepository.findByActiveTrueOrderByNombreAsc()).thenReturn(List.of(primera, segunda, tercera));

        MarcaBicicletaResponseDTO dtoPrimera = MarcaBicicletaResponseDTO.builder().nombre("Shimano").build();
        MarcaBicicletaResponseDTO dtoSegunda = MarcaBicicletaResponseDTO.builder().nombre("Bianchi").build();
        MarcaBicicletaResponseDTO dtoTercera = MarcaBicicletaResponseDTO.builder().nombre("Specialized").build();
        when(marcaMapper.toResponseDTO(same(primera))).thenReturn(dtoPrimera);
        when(marcaMapper.toResponseDTO(same(segunda))).thenReturn(dtoSegunda);
        when(marcaMapper.toResponseDTO(same(tercera))).thenReturn(dtoTercera);

        List<MarcaBicicletaResponseDTO> resultado = marcaService.obtenerMarcasActivas();

        assertThat(resultado).containsExactly(dtoPrimera, dtoSegunda, dtoTercera);
        verify(marcaRepository).findByActiveTrueOrderByNombreAsc();
    }
}

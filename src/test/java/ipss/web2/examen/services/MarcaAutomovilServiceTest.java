package ipss.web2.examen.services;

import ipss.web2.examen.dtos.MarcaAutomovilResponseDTO;
import ipss.web2.examen.mappers.MarcaAutomovilMapper;
import ipss.web2.examen.models.MarcaAutomovil;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
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
class MarcaAutomovilServiceTest {

    @Mock
    private MarcaAutomovilRepository marcaRepository;

    @Mock
    private MarcaAutomovilMapper marcaMapper;

    private MarcaAutomovilService marcaService;

    @BeforeEach
    void setUp() {
        marcaService = new MarcaAutomovilService(marcaRepository, marcaMapper);
    }

    @Test
    @DisplayName("Service debe devolver marcas activas ordenadas")
    void shouldReturnActiveBrandsOrdered() {
        MarcaAutomovil primera = MarcaAutomovil.builder().nombre("Audi").active(true).build();
        MarcaAutomovil segunda = MarcaAutomovil.builder().nombre("Toyota").active(true).build();
        when(marcaRepository.findByActiveTrueOrderByNombreAsc()).thenReturn(List.of(primera, segunda));

        MarcaAutomovilResponseDTO dtoPrimera = MarcaAutomovilResponseDTO.builder().nombre("Audi").build();
        MarcaAutomovilResponseDTO dtoSegunda = MarcaAutomovilResponseDTO.builder().nombre("Toyota").build();
        when(marcaMapper.toResponseDTO(same(primera))).thenReturn(dtoPrimera);
        when(marcaMapper.toResponseDTO(same(segunda))).thenReturn(dtoSegunda);

        List<MarcaAutomovilResponseDTO> resultado = marcaService.obtenerMarcasActivas();

        assertThat(resultado).containsExactly(dtoPrimera, dtoSegunda);
        verify(marcaRepository).findByActiveTrueOrderByNombreAsc();
    }
}

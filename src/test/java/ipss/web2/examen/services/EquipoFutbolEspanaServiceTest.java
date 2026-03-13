package ipss.web2.examen.services;

import ipss.web2.examen.dtos.EquipoFutbolEspanaResponseDTO;
import ipss.web2.examen.mappers.EquipoFutbolEspanaMapper;
import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EquipoFutbolEspanaServiceTest {
    @Test
    void obtenerEquiposActivos_devuelveSoloActivos() {
        EquipoFutbolEspanaRepository repo = Mockito.mock(EquipoFutbolEspanaRepository.class);
        EquipoFutbolEspanaMapper mapper = new EquipoFutbolEspanaMapper();
        EquipoFutbolEspanaService service = new EquipoFutbolEspanaService(repo, mapper);
        List<EquipoFutbolEspana> mockList = Arrays.asList(
            EquipoFutbolEspana.builder().nombre("Real Madrid").active(true).build(),
            EquipoFutbolEspana.builder().nombre("FC Barcelona").active(true).build()
        );
        Mockito.when(repo.findByActiveTrue()).thenReturn(mockList);
        List<EquipoFutbolEspanaResponseDTO> result = service.obtenerEquiposActivos();
        assertEquals(2, result.size());
        assertEquals("Real Madrid", result.get(0).getNombre());
        assertEquals("FC Barcelona", result.get(1).getNombre());
    }
}

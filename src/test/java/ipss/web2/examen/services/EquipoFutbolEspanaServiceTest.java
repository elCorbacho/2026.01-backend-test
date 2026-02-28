package ipss.web2.examen.services;

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
        EquipoFutbolEspanaService service = new EquipoFutbolEspanaService(repo);
        List<EquipoFutbolEspana> mockList = Arrays.asList(
            EquipoFutbolEspana.builder().nombre("Real Madrid").activo(true).build(),
            EquipoFutbolEspana.builder().nombre("FC Barcelona").activo(true).build()
        );
        Mockito.when(repo.findByActivoTrue()).thenReturn(mockList);
        List<EquipoFutbolEspana> result = service.obtenerEquiposActivos();
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(EquipoFutbolEspana::getActivo));
    }
}

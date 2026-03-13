package ipss.web2.examen.config;

import ipss.web2.examen.repositories.CampeonJockeyRepository;
import ipss.web2.examen.repositories.CiudadChileRepository;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import ipss.web2.examen.repositories.ExoplanetaRepository;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
import ipss.web2.examen.repositories.MarcaBicicletaRepository;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import ipss.web2.examen.repositories.MinaChileRepository;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.PresidenteChileRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
import ipss.web2.examen.repositories.TipoInsectoRepository;
import ipss.web2.examen.repositories.TransportistaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class NativeSeedStandardizationIntegrationTest {

    private static final int TARGET = 10;

    @Autowired
    private TransportistaRepository transportistaRepository;

    @Autowired
    private CampeonJockeyRepository campeonJockeyRepository;

    @Autowired
    private MinaChileRepository minaChileRepository;

    @Autowired
    private ListadoOlimpiadasRepository listadoOlimpiadasRepository;

    @Autowired
    private MarcaAutomovilRepository marcaAutomovilRepository;

    @Autowired
    private TipoInsectoRepository tipoInsectoRepository;

    @Autowired
    private TipoAveRepository tipoAveRepository;

    @Autowired
    private PoblacionAveRepository poblacionAveRepository;

    @Autowired
    private PresidenteChileRepository presidenteChileRepository;

    @Autowired
    private ListadoPresidenteRusiaRepository listadoPresidenteRusiaRepository;

    @Autowired
    private MarcaCamionRepository marcaCamionRepository;

    @Autowired
    private MarcaBicicletaRepository marcaBicicletaRepository;

    @Autowired
    private ExoplanetaRepository exoplanetaRepository;

    @Autowired
    private EquipoFutbolEspanaRepository equipoFutbolEspanaRepository;

    @Autowired
    private CiudadChileRepository ciudadChileRepository;

    @Test
    void shouldStandardizeNativeSeedToTenActiveRecordsPerTargetEntity() {
        assertThat(transportistaRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(campeonJockeyRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(minaChileRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(listadoOlimpiadasRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(marcaAutomovilRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
        assertThat(tipoInsectoRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
        assertThat(tipoAveRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(poblacionAveRepository.findByActiveTrueOrderByFechaAsc()).hasSize(TARGET);
        assertThat(presidenteChileRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(listadoPresidenteRusiaRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(marcaCamionRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
        assertThat(marcaBicicletaRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
        assertThat(exoplanetaRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
        assertThat(equipoFutbolEspanaRepository.findByActiveTrue()).hasSize(TARGET);
        assertThat(ciudadChileRepository.findByActiveTrueOrderByNombreAsc()).hasSize(TARGET);
    }
}

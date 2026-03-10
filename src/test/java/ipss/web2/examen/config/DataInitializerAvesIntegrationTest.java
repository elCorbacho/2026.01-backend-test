package ipss.web2.examen.config;

import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataInitializerAvesIntegrationTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private TipoAveRepository tipoAveRepository;

    @Autowired
    private PoblacionAveRepository poblacionAveRepository;

    @BeforeEach
    void setUp() {
        poblacionAveRepository.deleteAll();
        tipoAveRepository.deleteAll();
    }

    @Test
    @DisplayName("DataInitializer debe cargar tipos de ave y poblaciones de ejemplo")
    void dataInitializerDebeSembrarTiposYPoblaciones() throws Exception {
        dataInitializer.run();

        assertThat(tipoAveRepository.findByActiveTrue()).hasSize(2);
        assertThat(poblacionAveRepository.count()).isEqualTo(2);

        PoblacionAve primera = poblacionAveRepository.findAll().get(0);
        assertThat(primera.getTipoAve()).isNotNull();
        assertThat(primera.getTipoAve().getId()).isNotNull();
    }

    @Test
    @DisplayName("DataInitializer debe ser idempotente al ejecutarse dos veces")
    void dataInitializerDebeSerIdempotenteEnDobleEjecucion() throws Exception {
        dataInitializer.run();
        long tipoCountInicial = tipoAveRepository.count();
        long poblacionCountInicial = poblacionAveRepository.count();

        dataInitializer.run();
        dataInitializer.run();

        assertThat(tipoAveRepository.count()).isEqualTo(tipoCountInicial);
        assertThat(poblacionAveRepository.count()).isEqualTo(poblacionCountInicial);
        assertThat(poblacionAveRepository.findAll())
                .allSatisfy(poblacion -> {
                    assertThat(poblacion.getTipoAve()).isNotNull();
                    assertThat(poblacion.getTipoAve().getId()).isNotNull();
                });
    }

    @Test
    @DisplayName("DataInitializer debe reparar estado parcial cuando faltan poblaciones")
    void dataInitializerDebeRepararEstadoParcialAves() throws Exception {
        TipoAve tipoManual = tipoAveRepository.save(TipoAve.builder()
                .nombre("Condor andino")
                .descripcion("Existente")
                .active(true)
                .build());

        dataInitializer.run();

        assertThat(tipoAveRepository.findByActiveTrue()).hasSize(2);
        assertThat(poblacionAveRepository.findByTipoAveAndActiveTrue(tipoManual)).hasSize(1);
        assertThat(poblacionAveRepository.count()).isEqualTo(2);
    }
}

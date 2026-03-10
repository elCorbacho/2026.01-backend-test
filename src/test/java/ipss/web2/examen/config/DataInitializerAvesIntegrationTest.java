package ipss.web2.examen.config;

import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
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

    @Test
    @DisplayName("DataInitializer debe cargar tipos de ave y poblaciones de ejemplo")
    void dataInitializerDebeSembrarTiposYPoblaciones() {
        assertThat(tipoAveRepository.count()).isGreaterThanOrEqualTo(2);
        assertThat(poblacionAveRepository.count()).isGreaterThanOrEqualTo(2);

        PoblacionAve primera = poblacionAveRepository.findAll().get(0);
        assertThat(primera.getTipoAve()).isNotNull();
        assertThat(primera.getTipoAve().getId()).isNotNull();
    }

    @Test
    @DisplayName("DataInitializer debe ser idempotente al ejecutarse dos veces")
    void dataInitializerDebeSerIdempotenteEnDobleEjecucion() throws Exception {
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
}

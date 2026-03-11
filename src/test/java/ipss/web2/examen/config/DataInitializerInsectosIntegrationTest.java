package ipss.web2.examen.config;

import ipss.web2.examen.repositories.TipoInsectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataInitializerInsectosIntegrationTest
{

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private TipoInsectoRepository tipoInsectoRepository;

    @BeforeEach
    void setUp()
    {
        tipoInsectoRepository.deleteAll();
    }

    @Test
    @DisplayName("DataInitializer carga al menos 10 tipos de insecto activos")
    void dataInitializerDebeSembrarTiposInsecto() throws Exception
    {
        dataInitializer.run();

        assertThat(tipoInsectoRepository.findByActiveTrueOrderByNombreAsc())
                .hasSizeGreaterThanOrEqualTo(10)
                .allSatisfy(tipo -> {
                    assertThat(tipo.getId()).isNotNull();
                    assertThat(tipo.getActive()).isTrue();
                });
    }

    @Test
    @DisplayName("DataInitializer mantiene idempotencia para tipos de insecto")
    void dataInitializerDebeMantenerIdempotenciaTiposInsecto() throws Exception
    {
        dataInitializer.run();
        long cantidadInicial = tipoInsectoRepository.count();

        dataInitializer.run();
        dataInitializer.run();

        assertThat(tipoInsectoRepository.count()).isEqualTo(cantidadInicial);
        assertThat(tipoInsectoRepository.findByActiveTrueOrderByNombreAsc()).hasSize((int) cantidadInicial);
    }
}

package ipss.web2.examen.config;

import ipss.web2.examen.models.Exoplaneta;
import ipss.web2.examen.repositories.ExoplanetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

// Carga inicial de exoplanetas
@Slf4j
@Component
@RequiredArgsConstructor
public class ExoplanetaDataInitializer implements CommandLineRunner {

    private final ExoplanetaRepository exoplanetaRepository;

    @Override
    public void run(String... args) {
        if (exoplanetaRepository.count() > 0) {
            return;
        }

        List<Exoplaneta> exoplanetas = List.of(
                Exoplaneta.builder()
                        .nombre("Kepler-442b")
                        .tipo("Súper Tierra")
                        .distanciaAnosLuz(1206.0)
                        .masaRelativaJupiter(2.34)
                        .descubiertoAnio(2015)
                        .active(true)
                        .build(),
                Exoplaneta.builder()
                        .nombre("HD 209458 b")
                        .tipo("Gas gigante")
                        .distanciaAnosLuz(159.0)
                        .masaRelativaJupiter(0.69)
                        .descubiertoAnio(1999)
                        .active(true)
                        .build(),
                Exoplaneta.builder()
                        .nombre("TRAPPIST-1e")
                        .tipo("Terrestre")
                        .distanciaAnosLuz(39.0)
                        .masaRelativaJupiter(0.62)
                        .descubiertoAnio(2017)
                        .active(true)
                        .build(),
                Exoplaneta.builder()
                        .nombre("GJ 1214 b")
                        .tipo("Neptuniano")
                        .distanciaAnosLuz(48.0)
                        .masaRelativaJupiter(6.55)
                        .descubiertoAnio(2009)
                        .active(true)
                        .build(),
                Exoplaneta.builder()
                        .nombre("51 Pegasi b")
                        .tipo("Gas gigante")
                        .distanciaAnosLuz(50.0)
                        .masaRelativaJupiter(0.47)
                        .descubiertoAnio(1995)
                        .active(true)
                        .build()
        );

        exoplanetaRepository.saveAll(exoplanetas);
        log.info("Exoplanetas inicializados: {} registros creados", exoplanetas.size());
    }
}

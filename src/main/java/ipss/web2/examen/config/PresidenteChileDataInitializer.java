package ipss.web2.examen.config;

import ipss.web2.examen.models.PresidenteChile;
import ipss.web2.examen.repositories.PresidenteChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

// Carga inicial opcional de presidentes de Chile
@Component
@RequiredArgsConstructor
public class PresidenteChileDataInitializer implements CommandLineRunner {

    private final PresidenteChileRepository presidenteChileRepository;

    @Override
    public void run(String... args) {
        if (presidenteChileRepository.count() > 0) {
            return;
        }

        List<PresidenteChile> presidentes = List.of(
                PresidenteChile.builder()
                        .nombre("Patricio Aylwin")
                        .periodoInicio(LocalDate.of(1990, 3, 11))
                        .periodoFin(LocalDate.of(1994, 3, 11))
                        .partido("Democracia Cristiana")
                        .descripcion("Primer presidente tras el retorno a la democracia en 1990")
                        .active(true)
                        .build(),
                PresidenteChile.builder()
                        .nombre("Michelle Bachelet")
                        .periodoInicio(LocalDate.of(2006, 3, 11))
                        .periodoFin(LocalDate.of(2010, 3, 11))
                        .partido("Partido Socialista")
                        .descripcion("Primera mujer presidenta de Chile")
                        .active(true)
                        .build()
        );

        presidenteChileRepository.saveAll(presidentes);
    }
}

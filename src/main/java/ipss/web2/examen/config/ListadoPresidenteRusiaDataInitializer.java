package ipss.web2.examen.config;

import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

// Carga inicial opcional de presidentes de Rusia
@Component
@RequiredArgsConstructor
public class ListadoPresidenteRusiaDataInitializer implements CommandLineRunner {

    private final ListadoPresidenteRusiaRepository listadoPresidenteRusiaRepository;

    @Override
    public void run(String... args) {
        if (listadoPresidenteRusiaRepository.count() > 0) {
            return;
        }

        List<ListadoPresidenteRusia> presidentes = List.of(
                ListadoPresidenteRusia.builder()
                        .nombre("Boris Yeltsin")
                        .periodoInicio(LocalDate.of(1991, 7, 10))
                        .periodoFin(LocalDate.of(1999, 12, 31))
                        .partido("Independiente")
                        .descripcion("Primer presidente de la Federacion de Rusia tras la disolucion de la URSS")
                        .active(true)
                        .build(),
                ListadoPresidenteRusia.builder()
                        .nombre("Dmitry Medvedev")
                        .periodoInicio(LocalDate.of(2008, 5, 7))
                        .periodoFin(LocalDate.of(2012, 5, 7))
                        .partido("Rusia Unida")
                        .descripcion("Presidente de Rusia durante el periodo 2008-2012")
                        .active(true)
                        .build(),
                ListadoPresidenteRusia.builder()
                        .nombre("Vladimir Putin")
                        .periodoInicio(LocalDate.of(2012, 5, 7))
                        .periodoFin(null)
                        .partido("Rusia Unida")
                        .descripcion("Presidente de Rusia desde 2012")
                        .active(true)
                        .build()
        );

        listadoPresidenteRusiaRepository.saveAll(presidentes);
    }
}

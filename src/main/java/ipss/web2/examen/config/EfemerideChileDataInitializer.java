package ipss.web2.examen.config;

import ipss.web2.examen.models.EfemerideChile;
import ipss.web2.examen.repositories.EfemerideChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EfemerideChileDataInitializer implements CommandLineRunner {

    private final EfemerideChileRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() > 0) return;

        List<EfemerideChile> seeds = List.of(
                EfemerideChile.builder().titulo("Independencia de Chile").fecha(LocalDate.of(1810,9,18)).descripcion("Proclamación de la Primera Junta Nacional de Gobierno").poblacion(3500000L).active(true).build(),
                EfemerideChile.builder().titulo("Batalla de Maipú").fecha(LocalDate.of(1818,4,5)).descripcion("Batalla decisiva por la independencia").poblacion(120000L).active(true).build(),
                EfemerideChile.builder().titulo("Fundación de Valparaíso").fecha(LocalDate.of(1536,1,1)).descripcion("Asentamiento histórico en la costa").poblacion(300000L).active(true).build(),
                EfemerideChile.builder().titulo("Nacimiento de Gabriela Mistral").fecha(LocalDate.of(1889,4,7)).descripcion("Poetisa y premio Nobel de Literatura").poblacion(0L).active(true).build(),
                EfemerideChile.builder().titulo("Terremoto de Valdivia").fecha(LocalDate.of(1960,5,22)).descripcion("Mayor terremoto registrado en la historia moderna").poblacion(200000L).active(true).build(),
                EfemerideChile.builder().titulo("Aniversario del Poder Judicial").fecha(LocalDate.of(1830,6,1)).descripcion("Creación de instituciones judiciales").poblacion(0L).active(true).build(),
                EfemerideChile.builder().titulo("Día de las Glorias Navales").fecha(LocalDate.of(1879,5,21)).descripcion("Conmemoración de la Armada de Chile").poblacion(0L).active(true).build(),
                EfemerideChile.builder().titulo("Fiestas Patrias (Dieciocho)").fecha(LocalDate.of(1810,9,19)).descripcion("Celebraciones nacionales").poblacion(3500000L).active(true).build(),
                EfemerideChile.builder().titulo("Primer Vuelo de LAN").fecha(LocalDate.of(1929,3,5)).descripcion("Inicio de operaciones aéreas nacionales").poblacion(0L).active(true).build(),
                EfemerideChile.builder().titulo("Creación del Parque Nacional Torres del Paine").fecha(LocalDate.of(1959,5,13)).descripcion("Protección de áreas naturales").poblacion(0L).active(true).build()
        );

        repository.saveAll(seeds);
    }
}

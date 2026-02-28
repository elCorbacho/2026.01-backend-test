package ipss.web2.examen.config;

import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EquipoFutbolEspanaDataInitializer {
    private final EquipoFutbolEspanaRepository repository;

    @Bean
    public CommandLineRunner initEquiposFutbolEspana() {
        return args -> {
            if (repository.count() == 0) {
                repository.save(EquipoFutbolEspana.builder().nombre("Real Madrid").ciudad("Madrid").fundacion(1902).estadio("Santiago Bernabéu").activo(true).build());
                repository.save(EquipoFutbolEspana.builder().nombre("FC Barcelona").ciudad("Barcelona").fundacion(1899).estadio("Camp Nou").activo(true).build());
                repository.save(EquipoFutbolEspana.builder().nombre("Atlético de Madrid").ciudad("Madrid").fundacion(1903).estadio("Cívitas Metropolitano").activo(true).build());
                repository.save(EquipoFutbolEspana.builder().nombre("Sevilla FC").ciudad("Sevilla").fundacion(1890).estadio("Ramón Sánchez-Pizjuán").activo(true).build());
                repository.save(EquipoFutbolEspana.builder().nombre("Valencia CF").ciudad("Valencia").fundacion(1919).estadio("Mestalla").activo(true).build());
            }
        };
    }
}

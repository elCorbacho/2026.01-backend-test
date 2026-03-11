package ipss.web2.examen.config;

import ipss.web2.examen.models.CiudadChile;
import ipss.web2.examen.repositories.CiudadChileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CiudadChileDataInitializer implements CommandLineRunner {

    private final CiudadChileRepository ciudadChileRepository;

    @Override
    public void run(String... args) {
        if (ciudadChileRepository.count() > 0) {
            return;
        }

        List<CiudadChile> ciudades = List.of(
                CiudadChile.builder().nombre("Santiago").poblacion(6257516L).active(true).build(),
                CiudadChile.builder().nombre("Puente Alto").poblacion(568106L).active(true).build(),
                CiudadChile.builder().nombre("Maipu").poblacion(521627L).active(true).build(),
                CiudadChile.builder().nombre("Antofagasta").poblacion(388517L).active(true).build(),
                CiudadChile.builder().nombre("Vina del Mar").poblacion(334248L).active(true).build(),
                CiudadChile.builder().nombre("Valparaiso").poblacion(296655L).active(true).build(),
                CiudadChile.builder().nombre("Temuco").poblacion(282415L).active(true).build(),
                CiudadChile.builder().nombre("Concepcion").poblacion(255792L).active(true).build(),
                CiudadChile.builder().nombre("La Serena").poblacion(249656L).active(true).build(),
                CiudadChile.builder().nombre("Iquique").poblacion(223463L).active(true).build()
        );

        ciudadChileRepository.saveAll(ciudades);
    }
}

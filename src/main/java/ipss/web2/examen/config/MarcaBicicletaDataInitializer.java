package ipss.web2.examen.config;

import ipss.web2.examen.models.MarcaBicicleta;
import ipss.web2.examen.repositories.MarcaBicicletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarcaBicicletaDataInitializer implements CommandLineRunner {

    private final MarcaBicicletaRepository marcaBicicletaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (marcaBicicletaRepository.count() == 0) {
            poblarMarcasBicicleta();
        }
    }

    private void poblarMarcasBicicleta() {
        System.out.println("🚴 Cargando marcas de bicicleta...");
        MarcaBicicleta marca1 = MarcaBicicleta.builder()
                .nombre("Shimano")
                .paisOrigen("Japon")
                .descripcion("Componentes líderes en innovación ciclística")
                .active(true)
                .build();
        MarcaBicicleta marca2 = MarcaBicicleta.builder()
                .nombre("Specialized")
                .paisOrigen("Estados Unidos")
                .descripcion("Marcas premium de bicicletas y accesorios")
                .active(true)
                .build();
        MarcaBicicleta marca3 = MarcaBicicleta.builder()
                .nombre("Bianchi")
                .paisOrigen("Italia")
                .descripcion("Icono italiano de ciclismo competitivo")
                .active(true)
                .build();
        marcaBicicletaRepository.save(marca1);
        marcaBicicletaRepository.save(marca2);
        marcaBicicletaRepository.save(marca3);
        System.out.println("   ✅ " + marcaBicicletaRepository.count() + " marcas bicicleta insertadas");
    }
}

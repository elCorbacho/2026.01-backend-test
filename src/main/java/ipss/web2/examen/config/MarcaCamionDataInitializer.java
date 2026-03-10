package ipss.web2.examen.config;

import ipss.web2.examen.models.MarcaCamion;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarcaCamionDataInitializer implements CommandLineRunner {

    private final MarcaCamionRepository marcaCamionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (marcaCamionRepository.count() == 0) {
            poblarMarcasCamion();
        }
    }

    private void poblarMarcasCamion() {
        System.out.println("🚛 Cargando marcas de camiones...");

        MarcaCamion marca1 = MarcaCamion.builder()
                .nombre("Iveco")
                .paisOrigen("Italia")
                .descripcion("Fabricante europeo con foco en transporte de carga")
                .active(true)
                .build();

        MarcaCamion marca2 = MarcaCamion.builder()
                .nombre("MAN")
                .paisOrigen("Alemania")
                .descripcion("Soluciones de camiones pesados para larga distancia")
                .active(true)
                .build();

        MarcaCamion marca3 = MarcaCamion.builder()
                .nombre("Volvo Trucks")
                .paisOrigen("Suecia")
                .descripcion("Marca global de camiones orientada a seguridad y eficiencia")
                .active(true)
                .build();

        marcaCamionRepository.save(marca1);
        marcaCamionRepository.save(marca2);
        marcaCamionRepository.save(marca3);

        System.out.println("   ✅ " + marcaCamionRepository.count() + " marcas de camiones insertadas");
    }
}

package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Cancion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CancionRepositoryDataJpaTest {

    @Autowired
    private CancionRepository cancionRepository;

    @Test
    @DisplayName("@DataJpaTest: findByActiveTrue y buscar por artista ignore-case deben filtrar correctamente")
    void shouldFilterByActiveAndArtistIgnoringCase() {
        Cancion activaQueen = Cancion.builder()
                .titulo("Bohemian Rhapsody")
                .artista("Queen")
                .duracion(354)
                .genero("Rock")
                .active(true)
                .build();

        Cancion activaQotsa = Cancion.builder()
                .titulo("No One Knows")
                .artista("Queens of the Stone Age")
                .duracion(255)
                .genero("Alternative")
                .active(true)
                .build();

        Cancion inactivaQueen = Cancion.builder()
                .titulo("Inactiva")
                .artista("Queen")
                .duracion(200)
                .genero("Rock")
                .active(false)
                .build();

        cancionRepository.saveAll(List.of(activaQueen, activaQotsa, inactivaQueen));

        List<Cancion> activas = cancionRepository.findByActiveTrue();
        List<Cancion> porArtista = cancionRepository.findByArtistaContainingIgnoreCaseAndActiveTrue("QUEEN");

        assertThat(activas).hasSize(2);
        assertThat(porArtista).hasSize(2);
        assertThat(porArtista).allMatch(c -> Boolean.TRUE.equals(c.getActive()));
    }
}

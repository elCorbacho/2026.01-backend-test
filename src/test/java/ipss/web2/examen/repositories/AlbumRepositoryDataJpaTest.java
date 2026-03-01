package ipss.web2.examen.repositories;

import ipss.web2.examen.models.Album;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AlbumRepositoryDataJpaTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @DisplayName("@DataJpaTest: consultas paginadas de album deben respetar year + active")
    void shouldFilterAlbumsByYearAndActiveWithPagination() {
        Album activo2024A = Album.builder()
                .nombre("Album A")
                .year(2024)
                .descripcion("Activo 2024")
                .active(true)
                .build();

        Album activo2024B = Album.builder()
                .nombre("Album B")
                .year(2024)
                .descripcion("Activo 2024")
                .active(true)
                .build();

        Album inactivo2024 = Album.builder()
                .nombre("Album C")
                .year(2024)
                .descripcion("Inactivo 2024")
                .active(false)
                .build();

        Album activo2023 = Album.builder()
                .nombre("Album D")
                .year(2023)
                .descripcion("Activo 2023")
                .active(true)
                .build();

        albumRepository.saveAll(List.of(activo2024A, activo2024B, inactivo2024, activo2023));

        Page<Album> page = albumRepository.findByYearAndActive(2024, true, PageRequest.of(0, 1));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getContent().get(0).getYear()).isEqualTo(2024);
        assertThat(page.getContent().get(0).getActive()).isTrue();
    }
}

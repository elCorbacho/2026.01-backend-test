package ipss.web2.examen.verification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductionSafetyGuardrailsTest {

    private static final Set<String> REQUIRED_ALBUM_INDEXES = Set.of(
            "idx_album_release_year",
            "idx_album_is_active",
            "idx_album_nombre"
    );

    @Test
    @DisplayName("Packaging check: pasa cuando no hay entradas devtools")
    void packagingCheckShouldPassWhenDevtoolsIsAbsent() {
        List<String> jarEntries = List.of(
                "BOOT-INF/classes/ipss/web2/examen/Application.class",
                "BOOT-INF/lib/spring-boot-starter-web-3.5.11.jar"
        );

        assertThat(containsDevtools(jarEntries)).isFalse();
    }

    @Test
    @DisplayName("Packaging check: falla cuando aparece una entrada devtools")
    void packagingCheckShouldFailWhenDevtoolsIsPresent() {
        List<String> jarEntries = List.of(
                "BOOT-INF/classes/ipss/web2/examen/Application.class",
                "BOOT-INF/lib/spring-boot-devtools-3.5.11.jar"
        );

        assertThat(containsDevtools(jarEntries)).isTrue();
    }

    @Test
    @DisplayName("Validate mode guardrail: script de indices incluye todos los requeridos")
    void schemaScriptShouldContainAllRequiredAlbumIndexes() throws IOException {
        Path scriptPath = Path.of("docs", "db", "album_indexes.sql");
        String script = Files.readString(scriptPath);

        Set<String> declaredIndexes = REQUIRED_ALBUM_INDEXES.stream()
                .filter(script::contains)
                .collect(Collectors.toSet());

        assertThat(declaredIndexes).containsExactlyInAnyOrderElementsOf(REQUIRED_ALBUM_INDEXES);
    }

    @Test
    @DisplayName("Validate mode guardrail: detecta drift cuando falta indice requerido")
    void schemaGuardrailShouldFailWhenRequiredIndexIsMissing() {
        Set<String> schemaIndexes = Set.of("idx_album_release_year", "idx_album_nombre");

        assertThatThrownBy(() -> assertRequiredIndexesPresent(schemaIndexes))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("idx_album_is_active");
    }

    @Test
    @DisplayName("Validate mode guardrail: pasa cuando schema tiene todos los indices requeridos")
    void schemaGuardrailShouldPassWhenAllRequiredIndexesArePresent() {
        Set<String> schemaIndexes = Set.of(
                "idx_album_release_year",
                "idx_album_is_active",
                "idx_album_nombre"
        );

        assertRequiredIndexesPresent(schemaIndexes);
    }

    private static boolean containsDevtools(List<String> jarEntries) {
        return jarEntries.stream()
                .map(String::toLowerCase)
                .anyMatch(entry -> entry.contains("devtools"));
    }

    private static void assertRequiredIndexesPresent(Set<String> schemaIndexes) {
        Set<String> missingIndexes = REQUIRED_ALBUM_INDEXES.stream()
                .filter(required -> !schemaIndexes.contains(required))
                .collect(Collectors.toSet());

        if (!missingIndexes.isEmpty()) {
            throw new IllegalStateException("Missing required album indexes: " + missingIndexes);
        }
    }
}

package ipss.web2.examen.config;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.DemoWidget;
import ipss.web2.examen.models.EmpresaInsumos;
import ipss.web2.examen.models.GanadorAlbum;
import ipss.web2.examen.models.GanadorGuinness;
import ipss.web2.examen.models.GanadorPremioAlbum;
import ipss.web2.examen.models.PaisDistribucion;
import ipss.web2.examen.models.TestModel;
import ipss.web2.examen.models.TiendaLamina;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.DemoWidgetRepository;
import ipss.web2.examen.repositories.EmpresaInsumosRepository;
import ipss.web2.examen.repositories.GanadorAlbumRepository;
import ipss.web2.examen.repositories.GanadorGuinnessRepository;
import ipss.web2.examen.repositories.GanadorPremioAlbumRepository;
import ipss.web2.examen.repositories.PaisDistribucionRepository;
import ipss.web2.examen.repositories.TestModelRepository;
import ipss.web2.examen.repositories.TiendaLaminaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DataInitializerPopulationIntegrationTest {

    private static final long EXPECTED_COUNT = 30L;

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private GanadorGuinnessRepository ganadorGuinnessRepository;

    @Autowired
    private GanadorAlbumRepository ganadorAlbumRepository;

    @Autowired
    private GanadorPremioAlbumRepository ganadorPremioAlbumRepository;

    @Autowired
    private DemoWidgetRepository demoWidgetRepository;

    @Autowired
    private PaisDistribucionRepository paisDistribucionRepository;

    @Autowired
    private TestModelRepository testModelRepository;

    @Autowired
    private EmpresaInsumosRepository empresaInsumosRepository;

    @Autowired
    private TiendaLaminaRepository tiendaLaminaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @DisplayName("DataInitializer inserta 30 registros en las 8 entidades objetivo")
    void shouldPopulateThirtyRecordsForEachTargetEntity() {
        assertThat(ganadorGuinnessRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(ganadorAlbumRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(ganadorPremioAlbumRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(demoWidgetRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(paisDistribucionRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(testModelRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(empresaInsumosRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(tiendaLaminaRepository.count()).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    @DisplayName("DataInitializer deja active=true en las 8 entidades pobladas")
    void shouldSetActiveTrueForAllSeededRecords() {
        assertThat(ganadorGuinnessRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(ganadorAlbumRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(ganadorPremioAlbumRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(demoWidgetRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(paisDistribucionRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(testModelRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(empresaInsumosRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
        assertThat(tiendaLaminaRepository.findAll()).allMatch(item -> Boolean.TRUE.equals(item.getActive()));
    }

    @Test
    @DisplayName("GanadorAlbum mantiene referencia FK valida hacia Album")
    void shouldKeepValidAlbumForeignKeys() {
        Set<Long> albumIds = albumRepository.findAll().stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        assertThat(albumIds).isNotEmpty();
        assertThat(ganadorAlbumRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(ganador -> ganador.getAlbum() != null)
                .allMatch(ganador -> ganador.getAlbum().getId() != null)
                .allMatch(ganador -> albumIds.contains(ganador.getAlbum().getId()));
    }

    @Test
    @DisplayName("DataInitializer es idempotente para las 8 entidades objetivo")
    void shouldBeIdempotentWhenRunningInitializerTwice() throws Exception {
        dataInitializer.run();

        assertThat(ganadorGuinnessRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(ganadorAlbumRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(ganadorPremioAlbumRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(demoWidgetRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(paisDistribucionRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(testModelRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(empresaInsumosRepository.count()).isEqualTo(EXPECTED_COUNT);
        assertThat(tiendaLaminaRepository.count()).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    @DisplayName("GanadorGuinness cumple campos obligatorios, rangos y diversidad")
    void shouldValidateGanadorGuinnessSeededData() {
        assertThat(ganadorGuinnessRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 150)
                .allMatch(item -> item.getCategoria() != null && !item.getCategoria().isBlank())
                .allMatch(item -> item.getCategoria().length() <= 120)
                .allMatch(item -> item.getRecord() != null && !item.getRecord().isBlank())
                .allMatch(item -> item.getRecord().length() <= 255)
                .allMatch(item -> item.getAnio() != null && item.getAnio() >= 1900 && item.getAnio() <= 2030)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null);

        Set<String> categorias = ganadorGuinnessRepository.findAll().stream()
                .map(GanadorGuinness::getCategoria)
                .collect(Collectors.toSet());
        Set<Integer> decadas = ganadorGuinnessRepository.findAll().stream()
                .map(item -> item.getAnio() / 10)
                .collect(Collectors.toSet());

        assertThat(categorias.size()).isGreaterThan(2);
        assertThat(decadas.size()).isGreaterThan(2);
    }

    @Test
    @DisplayName("GanadorAlbum cumple FK, campos, rango de anio y carga LAZY")
    void shouldValidateGanadorAlbumSeededData() {
        assertThat(ganadorAlbumRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getAlbum() != null)
                .allMatch(item -> item.getAlbum().getNombre() != null && !item.getAlbum().getNombre().isBlank())
                .allMatch(item -> item.getArtista() != null && !item.getArtista().isBlank())
                .allMatch(item -> item.getArtista().length() <= 120)
                .allMatch(item -> item.getPremio() != null && !item.getPremio().isBlank())
                .allMatch(item -> item.getPremio().length() <= 120)
                .allMatch(item -> item.getAnio() != null && item.getAnio() >= 1950 && item.getAnio() <= 2030)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null);
    }

    @Test
    @DisplayName("GanadorPremioAlbum cumple campos, opcionales y auditoria")
    void shouldValidateGanadorPremioAlbumSeededData() {
        assertThat(ganadorPremioAlbumRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getArtista() != null && !item.getArtista().isBlank())
                .allMatch(item -> item.getArtista().length() <= 120)
                .allMatch(item -> item.getAlbum() != null && !item.getAlbum().isBlank())
                .allMatch(item -> item.getAlbum().length() <= 150)
                .allMatch(item -> item.getPremio() != null && !item.getPremio().isBlank())
                .allMatch(item -> item.getPremio().length() <= 120)
                .allMatch(item -> item.getAnio() != null && item.getAnio() >= 1950 && item.getAnio() <= 2030)
                .allMatch(item -> item.getGenero() == null || item.getGenero().length() <= 80)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null);

        assertThat(ganadorPremioAlbumRepository.findAll())
                .anyMatch(item -> item.getGenero() == null)
                .anyMatch(item -> item.getGenero() != null);
    }

    @Test
    @DisplayName("DemoWidget, PaisDistribucion, TestModel, EmpresaInsumos y TiendaLamina cumplen constraints")
    void shouldValidateRemainingEntitiesSeededData() {
        assertThat(demoWidgetRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 120)
                .allMatch(item -> item.getTipo() == null || item.getTipo().length() <= 100)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null)
                .anyMatch(item -> item.getTipo() == null);

        assertThat(paisDistribucionRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 100)
                .allMatch(item -> item.getCodigoIso() == null || item.getCodigoIso().length() <= 3)
                .allMatch(item -> item.getDescripcion() == null || item.getDescripcion().length() <= 500)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null)
                .anyMatch(item -> item.getCodigoIso() == null)
                .anyMatch(item -> item.getDescripcion() == null);

        assertThat(testModelRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 100)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null);

        assertThat(empresaInsumosRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 150)
                .allMatch(item -> item.getRubro() != null && !item.getRubro().isBlank())
                .allMatch(item -> item.getRubro().length() <= 120)
                .allMatch(item -> item.getContacto() == null || item.getContacto().length() <= 120)
                .allMatch(item -> item.getTelefono() == null || item.getTelefono().length() <= 30)
                .allMatch(item -> item.getEmail() == null || item.getEmail().length() <= 120)
                .allMatch(item -> item.getSitioWeb() == null || item.getSitioWeb().length() <= 200)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null)
                .anyMatch(item -> item.getContacto() == null)
                .anyMatch(item -> item.getTelefono() == null)
                .anyMatch(item -> item.getEmail() == null)
                .anyMatch(item -> item.getSitioWeb() == null);

        assertThat(tiendaLaminaRepository.findAll())
                .hasSize((int) EXPECTED_COUNT)
                .allMatch(item -> item.getNombre() != null && !item.getNombre().isBlank())
                .allMatch(item -> item.getNombre().length() <= 150)
                .allMatch(item -> item.getCiudad() != null && !item.getCiudad().isBlank())
                .allMatch(item -> item.getCiudad().length() <= 80)
                .allMatch(item -> item.getDireccion() == null || item.getDireccion().length() <= 200)
                .allMatch(item -> item.getTelefono() == null || item.getTelefono().length() <= 30)
                .allMatch(item -> item.getEmail() == null || item.getEmail().length() <= 120)
                .allMatch(item -> item.getCreatedAt() != null)
                .allMatch(item -> item.getUpdatedAt() != null)
                .anyMatch(item -> item.getDireccion() == null)
                .anyMatch(item -> item.getTelefono() == null)
                .anyMatch(item -> item.getEmail() == null)
                .anyMatch(item -> item.getFechaApertura() == null)
                .anyMatch(item -> item.getFechaApertura() != null);
    }

    @Test
    @DisplayName("Permite caracteres especiales en GanadorGuinness y preserva valor")
    void shouldPreserveSpecialCharactersForGanadorGuinness() {
        GanadorGuinness nuevo = GanadorGuinness.builder()
                .nombre("Niño Récord Ñandú")
                .categoria("Cultura")
                .record("Récord con acentos y ñ")
                .anio(2020)
                .build();

        GanadorGuinness saved = ganadorGuinnessRepository.save(nuevo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getNombre()).isEqualTo("Niño Récord Ñandú");
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.getActive()).isTrue();
    }

    @Test
    @DisplayName("Permite genero nulo en GanadorPremioAlbum")
    void shouldAllowNullGeneroForGanadorPremioAlbum() {
        GanadorPremioAlbum nuevo = GanadorPremioAlbum.builder()
                .artista("Artista Prueba")
                .album("Album Prueba")
                .premio("Premio Prueba")
                .anio(2022)
                .genero(null)
                .build();

        GanadorPremioAlbum saved = ganadorPremioAlbumRepository.save(nuevo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getGenero()).isNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.getActive()).isTrue();
    }

    @Test
    @DisplayName("Permite tipo nulo en DemoWidget")
    void shouldAllowNullTipoForDemoWidget() {
        DemoWidget nuevo = DemoWidget.builder()
                .nombre("Widget Null Tipo")
                .tipo(null)
                .build();

        DemoWidget saved = demoWidgetRepository.save(nuevo);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTipo()).isNull();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
        assertThat(saved.getActive()).isTrue();
    }

    @Test
    @DisplayName("Permite codigo ISO de 2 y 3 caracteres en PaisDistribucion")
    void shouldPreserveIsoCodesWithTwoAndThreeCharacters() {
        PaisDistribucion dosChars = PaisDistribucion.builder()
                .nombre("Pais Dos")
                .codigoIso("CL")
                .descripcion(null)
                .build();
        PaisDistribucion tresChars = PaisDistribucion.builder()
                .nombre("Pais Tres")
                .codigoIso("CHL")
                .descripcion("Descripcion")
                .build();

        PaisDistribucion savedDos = paisDistribucionRepository.save(dosChars);
        PaisDistribucion savedTres = paisDistribucionRepository.save(tresChars);

        assertThat(savedDos.getCodigoIso()).isEqualTo("CL");
        assertThat(savedTres.getCodigoIso()).isEqualTo("CHL");
        assertThat(savedDos.getCreatedAt()).isNotNull();
        assertThat(savedTres.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Permite opcionales nulos en EmpresaInsumos y TiendaLamina")
    void shouldAllowNullOptionalFieldsForEmpresaAndTienda() {
        EmpresaInsumos empresa = EmpresaInsumos.builder()
                .nombre("Empresa Minima")
                .rubro("Impresion")
                .build();

        TiendaLamina tienda = TiendaLamina.builder()
                .nombre("Tienda Minima")
                .ciudad("Santiago")
                .build();

        EmpresaInsumos savedEmpresa = empresaInsumosRepository.save(empresa);
        TiendaLamina savedTienda = tiendaLaminaRepository.save(tienda);

        assertThat(savedEmpresa.getContacto()).isNull();
        assertThat(savedEmpresa.getTelefono()).isNull();
        assertThat(savedEmpresa.getEmail()).isNull();
        assertThat(savedEmpresa.getSitioWeb()).isNull();
        assertThat(savedEmpresa.getCreatedAt()).isNotNull();
        assertThat(savedEmpresa.getUpdatedAt()).isNotNull();
        assertThat(savedEmpresa.getActive()).isTrue();

        assertThat(savedTienda.getDireccion()).isNull();
        assertThat(savedTienda.getTelefono()).isNull();
        assertThat(savedTienda.getEmail()).isNull();
        assertThat(savedTienda.getFechaApertura()).isNull();
        assertThat(savedTienda.getCreatedAt()).isNotNull();
        assertThat(savedTienda.getUpdatedAt()).isNotNull();
        assertThat(savedTienda.getActive()).isTrue();
    }

    @Test
    @DisplayName("Permite fechas de apertura variadas y preserva LocalDate")
    void shouldPreserveDifferentOpenDatesForTiendaLamina() {
        TiendaLamina tiendaUno = TiendaLamina.builder()
                .nombre("Tienda Fecha 1")
                .ciudad("Temuco")
                .fechaApertura(java.time.LocalDate.of(2015, 1, 2))
                .build();
        TiendaLamina tiendaDos = TiendaLamina.builder()
                .nombre("Tienda Fecha 2")
                .ciudad("Valparaiso")
                .fechaApertura(java.time.LocalDate.of(2020, 12, 31))
                .build();

        TiendaLamina savedUno = tiendaLaminaRepository.save(tiendaUno);
        TiendaLamina savedDos = tiendaLaminaRepository.save(tiendaDos);

        assertThat(savedUno.getFechaApertura()).isEqualTo(java.time.LocalDate.of(2015, 1, 2));
        assertThat(savedDos.getFechaApertura()).isEqualTo(java.time.LocalDate.of(2020, 12, 31));
    }

    @Test
    @DisplayName("TestModel permite active por defecto y auditoria")
    void shouldSetDefaultActiveAndAuditForTestModel() {
        TestModel model = TestModel.builder()
                .nombre("Test Model Nuevo")
                .build();

        TestModel saved = testModelRepository.save(model);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getActive()).isTrue();
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }
}

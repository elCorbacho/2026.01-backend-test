package ipss.web2.examen.config;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.Cancion;
import ipss.web2.examen.models.DemoWidget;
import ipss.web2.examen.models.EmpresaInsumos;
import ipss.web2.examen.models.GanadorAlbum;
import ipss.web2.examen.models.GanadorGuinness;
import ipss.web2.examen.models.GanadorPremioAlbum;
import ipss.web2.examen.models.Lamina;
import ipss.web2.examen.models.LaminaCatalogo;
import ipss.web2.examen.models.ListadoOlimpiadas;
import ipss.web2.examen.models.MinaChile;
import ipss.web2.examen.models.MarcaAutomovil;
import ipss.web2.examen.models.PaisDistribucion;
import ipss.web2.examen.models.RegionChile;
import ipss.web2.examen.models.TestModel;
import ipss.web2.examen.models.TiendaLamina;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.CancionRepository;
import ipss.web2.examen.models.CampeonJockey;
import ipss.web2.examen.repositories.LaminaRepository;
import ipss.web2.examen.repositories.LaminaCatalogoRepository;
import ipss.web2.examen.repositories.RegionRepository;
import ipss.web2.examen.repositories.CampeonJockeyRepository;
import ipss.web2.examen.repositories.DemoWidgetRepository;
import ipss.web2.examen.repositories.EmpresaInsumosRepository;
import ipss.web2.examen.repositories.GanadorAlbumRepository;
import ipss.web2.examen.repositories.GanadorGuinnessRepository;
import ipss.web2.examen.repositories.GanadorPremioAlbumRepository;
import ipss.web2.examen.repositories.MinaChileRepository;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
import ipss.web2.examen.repositories.PaisDistribucionRepository;
import ipss.web2.examen.repositories.TestModelRepository;
import ipss.web2.examen.repositories.TiendaLaminaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AlbumRepository albumRepository;
    private final CancionRepository cancionRepository;
    private final LaminaRepository laminaRepository;
    private final LaminaCatalogoRepository laminaCatalogoRepository;
    private final RegionRepository regionRepository;
    private final CampeonJockeyRepository campeonJockeyRepository;
    private final MinaChileRepository minaChileRepository;
    private final ListadoOlimpiadasRepository listadoOlimpiadasRepository;
    private final GanadorGuinnessRepository ganadorGuinnessRepository;
    private final GanadorAlbumRepository ganadorAlbumRepository;
    private final GanadorPremioAlbumRepository ganadorPremioAlbumRepository;
    private final DemoWidgetRepository demoWidgetRepository;
    private final PaisDistribucionRepository paisDistribucionRepository;
    private final TestModelRepository testModelRepository;
    private final EmpresaInsumosRepository empresaInsumosRepository;
    private final TiendaLaminaRepository tiendaLaminaRepository;
    private final MarcaAutomovilRepository marcaAutomovilRepository;

    private static final int TARGET_SEED_COUNT = 30;
    private static final int TARGET_MARCA_AUTOMOVIL_COUNT = 2;
    private static final String[] GUINNESS_CATEGORIES = {
            "Musica", "Deportes", "Ciencia", "Tecnologia", "Naturaleza", "Cultura"
    };
    private static final String[] WIDGET_TYPES = {
            "catalogo", "ranking", "estadistica", "noticia", "resumen"
    };
    private static final String[] COUNTRY_NAMES = {
            "Argentina", "Bolivia", "Brasil", "Canada", "Chile", "Colombia", "Costa Rica", "Cuba",
            "Ecuador", "Egipto", "Espana", "Estados Unidos", "Francia", "Guatemala", "Honduras", "India",
            "Italia", "Japon", "Mexico", "Noruega", "Panama", "Paraguay", "Peru", "Portugal",
            "Reino Unido", "Republica Checa", "Suiza", "Uruguay", "Venezuela", "Vietnam"
    };
    private static final String[] COUNTRY_ISO_CODES = {
            "ARG", "BOL", "BRA", "CAN", "CHL", "COL", "CRI", "CUB", "ECU", "EGY",
            "ESP", "USA", "FRA", "GTM", "HND", "IND", "ITA", "JPN", "MEX", "NOR",
            "PAN", "PRY", "PER", "PRT", "GBR", "CZE", "CHE", "URY", "VEN", "VNM"
    };
    private static final String[] SUPPLIER_RUBROS = {
            "Impresion", "Distribucion", "Merchandising", "Logistica", "Editorial"
    };
    private static final String[] STORE_CITIES = {
            "Santiago", "Valparaiso", "Concepcion", "Antofagasta", "Temuco", "Puerto Montt"
    };
    private static final String[] PREMIO_ARTISTAS = {
            "Daft Punk", "Adele", "Coldplay", "Rosalia", "Radiohead",
            "The Weeknd", "Mon Laferte", "Dua Lipa", "Billie Eilish", "Kendrick Lamar"
    };
    private static final String[] PREMIO_ALBUMES = {
            "Discovery", "25", "Parachutes", "Motomami", "OK Computer",
            "After Hours", "Norma", "Future Nostalgia", "Happier Than Ever", "DAMN"
    };
    private static final String[] PREMIO_TYPES = {
            "Album del Ano", "Mejor Produccion", "Critica Internacional", "Popularidad Global"
    };
    private static final String[] PREMIO_GENEROS = {
            "Pop", "Rock", "Electro", "Urbano", "Indie"
    };
    private static final String[][] AUTOMOTIVE_BRANDS = {
            {"Toyota", "Japon", "Referente global en sedanes confiables"},
            {"Ford", "Estados Unidos", "Camionetas y pick-ups iconicas"}
    };

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            poblarRegionesChile();
        }

        if (campeonJockeyRepository.count() == 0) {
            poblarCampeonesJockey();
        }

        if (minaChileRepository.count() == 0) {
            poblarMinasChile();
        }

        if (listadoOlimpiadasRepository.count() == 0) {
            poblarListadoOlimpiadas();
        }

        if (albumRepository.count() == 0) {
            poblarAlbumesYCanciones();
        } else {
            System.out.println("⚠️ Base de datos ya contiene álbumes. Saltando seed de álbumes/canciones.");
        }

        if (ganadorGuinnessRepository.count() == 0) {
            poblarGanadorGuinness();
        }

        if (demoWidgetRepository.count() == 0) {
            poblarDemoWidget();
        }

        if (testModelRepository.count() == 0) {
            poblarTestModel();
        }

        if (paisDistribucionRepository.count() == 0) {
            poblarPaisDistribucion();
        }

        if (empresaInsumosRepository.count() == 0) {
            poblarEmpresaInsumos();
        }

        if (tiendaLaminaRepository.count() == 0) {
            poblarTiendaLamina();
        }

        if (ganadorPremioAlbumRepository.count() == 0) {
            poblarGanadorPremioAlbum();
        }

        if (ganadorAlbumRepository.count() == 0) {
            poblarGanadorAlbum();
        }

        if (marcaAutomovilRepository.count() == 0) {
            poblarMarcasAutomovil();
        }
    }

    private void poblarAlbumesYCanciones() {
        System.out.println("🚀 Iniciando población de base de datos con anime populares...");

        // Album 1
        Album album1 = new Album();
        album1.setNombre("Caballeros del Zodiaco");
        album1.setYear(1986);
        album1.setDescripcion("Manga clásico sobre guerreros que protegen a Atenea");
        album1.setActive(true);
        album1.setLaminas(new ArrayList<>());
        album1.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum1 = Objects.requireNonNull(albumRepository.save(album1));

        crearCatalogo(savedAlbum1, new String[][]{
                {"Seiya - Caballero de Pegaso", "https://images.unsplash.com/photo-1617637881555-eae2ba6ee46f?w=400", "PORTADA"},
                {"Armadura Dorada de Aries", "https://images.unsplash.com/photo-1578322154310-cc4ecca2f5d5?w=400", "CONTENIDO"},
                {"Batalla contra Hades", "https://images.unsplash.com/photo-1607084591413-25427a3d4d12?w=400", "CONTENIDO"}
        }, 1986);

        agregarLaminasDeEjemplo(savedAlbum1, new String[][]{
                {"Seiya - Caballero de Pegaso", "https://images.unsplash.com/photo-1617637881555-eae2ba6ee46f?w=400", "PORTADA"},
                {"Seiya - Caballero de Pegaso", "https://images.unsplash.com/photo-1617637881555-eae2ba6ee46f?w=400", "PORTADA"},
                {"Armadura Dorada de Aries", "https://images.unsplash.com/photo-1578322154310-cc4ecca2f5d5?w=400", "CONTENIDO"}
        }, 1986);

        syncLaminasFromCatalog(savedAlbum1);

        // Album 2
        Album album2 = new Album();
        album2.setNombre("Dragon Ball Z");
        album2.setYear(1989);
        album2.setDescripcion("Saga de acción y aventura que cambió el anime para siempre");
        album2.setActive(true);
        album2.setLaminas(new ArrayList<>());
        album2.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum2 = Objects.requireNonNull(albumRepository.save(album2));

        crearCatalogo(savedAlbum2, new String[][]{
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Vegeta - El Príncipe Saiyajin", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"},
                {"Batalla contra Freezer", "https://images.unsplash.com/photo-1626814026595-cac13b1db48d?w=400", "CONTENIDO"}
        }, 1989);

        agregarLaminasDeEjemplo(savedAlbum2, new String[][]{
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Vegeta - El Príncipe Saiyajin", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"},
                {"Batalla contra Freezer", "https://images.unsplash.com/photo-1626814026595-cac13b1db48d?w=400", "CONTENIDO"},
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"}
        }, 1989);

        syncLaminasFromCatalog(savedAlbum2);

        // Album 3
        Album album3 = new Album();
        album3.setNombre("Naruto");
        album3.setYear(2002);
        album3.setDescripcion("Historia épica de un ninja que busca ser reconocido");
        album3.setActive(true);
        album3.setLaminas(new ArrayList<>());
        album3.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum3 = Objects.requireNonNull(albumRepository.save(album3));

        crearCatalogo(savedAlbum3, new String[][]{
                {"Naruto Uzumaki - El Hokage", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "PORTADA"},
                {"Sasuke Uchiha - El Último de su Clan", "https://images.unsplash.com/photo-1619983081563-430f63602d4b?w=400", "CONTENIDO"},
                {"Sakura Haruno - Kunoichi Poderosa", "https://images.unsplash.com/photo-1625948515291-89613c66ba51?w=400", "CONTENIDO"}
        }, 2002);

        agregarLaminasDeEjemplo(savedAlbum3, new String[][]{
                {"Naruto Uzumaki - El Hokage", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "PORTADA"},
                {"Sasuke Uchiha - El Último de su Clan", "https://images.unsplash.com/photo-1619983081563-430f63602d4b?w=400", "CONTENIDO"},
                {"Sakura Haruno - Kunoichi Poderosa", "https://images.unsplash.com/photo-1625948515291-89613c66ba51?w=400", "CONTENIDO"}
        }, 2002);

        syncLaminasFromCatalog(savedAlbum3);

        // Album 4
        Album album4 = new Album();
        album4.setNombre("Demon Slayer");
        album4.setYear(2018);
        album4.setDescripcion("La batalla entre cazadores de demonios y fuerzas oscuras");
        album4.setActive(true);
        album4.setLaminas(new ArrayList<>());
        album4.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum4 = Objects.requireNonNull(albumRepository.save(album4));

        crearCatalogo(savedAlbum4, new String[][]{
                {"Tanjiro Kamado - Cazador de Demonios", "https://images.unsplash.com/photo-1611339555312-e607c25352ca?w=400", "PORTADA"},
                {"Nezuko - El Demonio Humano", "https://images.unsplash.com/photo-1618519764d82b19d648d1aac2e2b63500cf471a?w=400", "CONTENIDO"},
                {"Hashira - Los Pilares de Fuego", "https://images.unsplash.com/photo-1619983081563-430f63602d4b?w=400", "CONTENIDO"}
        }, 2018);

        agregarLaminasDeEjemplo(savedAlbum4, new String[][]{
                {"Tanjiro Kamado - Cazador de Demonios", "https://images.unsplash.com/photo-1611339555312-e607c25352ca?w=400", "PORTADA"}
        }, 2018);

        syncLaminasFromCatalog(savedAlbum4);

        // Album 5
        Album album5 = new Album();
        album5.setNombre("Berserk");
        album5.setYear(1997);
        album5.setDescripcion("Oscura epopeya de un guerrero en busca de venganza");
        album5.setActive(true);
        album5.setLaminas(new ArrayList<>());
        album5.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum5 = Objects.requireNonNull(albumRepository.save(album5));

        crearCatalogo(savedAlbum5, new String[][]{
                {"Guts - El Guerrero Negro", "https://images.unsplash.com/photo-1618519764d82b19d648d1aac2e2b63500cf471a?w=400", "PORTADA"},
                {"La Mano del Dios", "https://images.unsplash.com/photo-1607084591413-25427a3d4d12?w=400", "CONTENIDO"},
                {"Griffith - El Falcón Blanco", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"}
        }, 1997);

        syncLaminasFromCatalog(savedAlbum5);

        Object[][] canciones = {
                {"Pegasus Fantasy", "Make-Up", 210, "J-Pop"},
                {"Cha-La Head-Cha-La", "Hironobu Kageyama", 228, "J-Pop"},
                {"Blue Bird", "Ikimono-gakari", 265, "J-Pop"},
                {"Gurenge", "LiSA", 232, "Anime"},
                {"Tank!", "The Seatbelts", 174, "Jazz"},
                {"A Cruel Angel's Thesis", "Yoko Takahashi", 230, "J-Pop"},
                {"The Hero", "JAM Project", 256, "Anime"}
        };
        for (Object[] c : canciones) {
            Cancion nueva = Cancion.builder()
                    .titulo((String) c[0])
                    .artista((String) c[1])
                    .duracion((Integer) c[2])
                    .genero((String) c[3])
                    .active(true)
                    .build();
            cancionRepository.save(Objects.requireNonNull(nueva));
        }

        long albumCount = albumRepository.count();
        long catalogCount = laminaCatalogoRepository.count();
        long laminaCount = laminaRepository.count();
        long cancionesCount = cancionRepository.count();

        System.out.println("✅ Base de datos poblada exitosamente");
        System.out.println("   📚 " + albumCount + " Álbumes creados con catálogos");
        System.out.println("   🖼️  " + catalogCount + " Láminas en catálogo");
        System.out.println("   🎵 " + cancionesCount + " Canciones cargadas");
        System.out.println("   📋 Resumen de estado (estimado):");
        System.out.println("      - Álbumes: " + albumCount + ", Láminas en posesión: " + laminaCount);
    }

    private void poblarGanadorGuinness() {
        System.out.println("🏆 Cargando ganadores Guinness...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            String categoria = GUINNESS_CATEGORIES[i % GUINNESS_CATEGORIES.length];
            GanadorGuinness ganador = GanadorGuinness.builder()
                    .nombre("Ganador Guinness " + (i + 1))
                    .categoria(categoria)
                    .record("Marca destacada #" + (i + 1) + " en " + categoria)
                    .anio(1990 + (i % 35))
                    .active(true)
                    .build();
            ganadorGuinnessRepository.save(Objects.requireNonNull(ganador));
        }
        System.out.println("   ✅ " + ganadorGuinnessRepository.count() + " ganadores Guinness insertados");
    }

    private void poblarDemoWidget() {
        System.out.println("🧩 Cargando demo widgets...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            DemoWidget widget = DemoWidget.builder()
                    .nombre("Demo Widget " + (i + 1))
                    .tipo(i % 5 == 0 ? null : WIDGET_TYPES[i % WIDGET_TYPES.length])
                    .active(true)
                    .build();
            demoWidgetRepository.save(Objects.requireNonNull(widget));
        }
        System.out.println("   ✅ " + demoWidgetRepository.count() + " demo widgets insertados");
    }

    private void poblarTestModel() {
        System.out.println("🧪 Cargando registros de test model...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            TestModel registro = TestModel.builder()
                    .nombre("Registro de prueba " + (i + 1))
                    .active(true)
                    .build();
            testModelRepository.save(Objects.requireNonNull(registro));
        }
        System.out.println("   ✅ " + testModelRepository.count() + " registros test model insertados");
    }

    private void poblarPaisDistribucion() {
        System.out.println("🌐 Cargando paises de distribucion...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            PaisDistribucion pais = PaisDistribucion.builder()
                    .nombre(COUNTRY_NAMES[i])
                    .codigoIso(i % 6 == 0 ? null : COUNTRY_ISO_CODES[i])
                    .descripcion(i % 7 == 0 ? null : "Mercado prioritario para distribucion en " + COUNTRY_NAMES[i])
                    .active(true)
                    .build();
            paisDistribucionRepository.save(Objects.requireNonNull(pais));
        }
        System.out.println("   ✅ " + paisDistribucionRepository.count() + " paises de distribucion insertados");
    }

    private void poblarEmpresaInsumos() {
        System.out.println("🏢 Cargando empresas de insumos...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            EmpresaInsumos empresa = EmpresaInsumos.builder()
                    .nombre("Empresa Insumos " + (i + 1))
                    .rubro(SUPPLIER_RUBROS[i % SUPPLIER_RUBROS.length])
                    .contacto(i % 4 == 0 ? null : "Contacto " + (i + 1))
                    .telefono(i % 6 == 0 ? null : "+56 9 " + String.format("%04d%04d", i + 1000, i + 2000))
                    .email(i % 5 == 0 ? null : "contacto" + (i + 1) + "@insumos.cl")
                    .sitioWeb(i % 3 == 0 ? null : "https://insumos" + (i + 1) + ".cl")
                    .active(true)
                    .build();
            empresaInsumosRepository.save(Objects.requireNonNull(empresa));
        }
        System.out.println("   ✅ " + empresaInsumosRepository.count() + " empresas de insumos insertadas");
    }

    private void poblarTiendaLamina() {
        System.out.println("🏪 Cargando tiendas de laminas...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            TiendaLamina tienda = TiendaLamina.builder()
                    .nombre("Tienda Lamina " + (i + 1))
                    .ciudad(STORE_CITIES[i % STORE_CITIES.length])
                    .direccion(i % 4 == 0 ? null : "Avenida Coleccion " + (100 + i))
                    .telefono(i % 5 == 0 ? null : "+56 2 " + String.format("%04d%04d", i + 3000, i + 4000))
                    .email(i % 6 == 0 ? null : "tienda" + (i + 1) + "@laminas.cl")
                    .fechaApertura(i % 3 == 0 ? null : LocalDate.of(2010 + (i % 14), (i % 12) + 1, (i % 27) + 1))
                    .active(true)
                    .build();
            tiendaLaminaRepository.save(Objects.requireNonNull(tienda));
        }
        System.out.println("   ✅ " + tiendaLaminaRepository.count() + " tiendas de laminas insertadas");
    }

    private void poblarGanadorPremioAlbum() {
        System.out.println("🎶 Cargando ganadores de premios de album...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            GanadorPremioAlbum ganador = GanadorPremioAlbum.builder()
                    .artista(PREMIO_ARTISTAS[i % PREMIO_ARTISTAS.length])
                    .album(PREMIO_ALBUMES[i % PREMIO_ALBUMES.length] + " Edition " + ((i / PREMIO_ALBUMES.length) + 1))
                    .premio(PREMIO_TYPES[i % PREMIO_TYPES.length])
                    .anio(1995 + (i % 30))
                    .genero(i % 4 == 0 ? null : PREMIO_GENEROS[i % PREMIO_GENEROS.length])
                    .active(true)
                    .build();
            ganadorPremioAlbumRepository.save(Objects.requireNonNull(ganador));
        }
        System.out.println("   ✅ " + ganadorPremioAlbumRepository.count() + " ganadores premio album insertados");
    }

    private void poblarGanadorAlbum() {
        List<Album> albums = albumRepository.findAll();
        if (albums.isEmpty()) {
            System.out.println("⚠️ No existen albums para poblar ganador_album. Seed omitido para evitar FK invalidas.");
            return;
        }

        System.out.println("🥇 Cargando ganadores por album...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            Album album = albums.get(i % albums.size());
            GanadorAlbum ganador = GanadorAlbum.builder()
                    .album(album)
                    .artista(PREMIO_ARTISTAS[i % PREMIO_ARTISTAS.length])
                    .premio(PREMIO_TYPES[i % PREMIO_TYPES.length])
                    .anio(1995 + (i % 30))
                    .active(true)
                    .build();
            ganadorAlbumRepository.save(Objects.requireNonNull(ganador));
        }
        System.out.println("   ✅ " + ganadorAlbumRepository.count() + " ganadores por album insertados");
    }

    private void poblarMarcasAutomovil() {
        System.out.println("🚗 Cargando marcas de automóviles...");
        for (int i = 0; i < TARGET_MARCA_AUTOMOVIL_COUNT; i++) {
            String[] datos = AUTOMOTIVE_BRANDS[i % AUTOMOTIVE_BRANDS.length];
            MarcaAutomovil marca = MarcaAutomovil.builder()
                    .nombre(datos[0])
                    .paisOrigen(datos[1])
                    .descripcion(datos[2])
                    .active(true)
                    .build();
            marcaAutomovilRepository.save(Objects.requireNonNull(marca));
        }
        System.out.println("   ✅ " + marcaAutomovilRepository.count() + " marcas automotrices insertadas");
    }

    private void crearCatalogo(Album album, String[][] laminasData, int year) {
        for (String[] datos : laminasData) {
            LaminaCatalogo catalogo = new LaminaCatalogo();
            catalogo.setNombre(datos[0]);
            catalogo.setImagen(datos[1]);
            catalogo.setTipoLamina(datos[2]);
            catalogo.setFechaLanzamiento(LocalDate.of(year, 1, 1));
            catalogo.setAlbum(album);
            catalogo.setActive(true);

            LaminaCatalogo savedCatalogo = Objects.requireNonNull(laminaCatalogoRepository.save(catalogo));
            album.getLaminasCatalogo().add(savedCatalogo);
        }

        albumRepository.save(album);
    }

    private void agregarLaminasDeEjemplo(Album album, String[][] laminasData, int year) {
        for (String[] datos : laminasData) {
            Lamina lamina = new Lamina();
            lamina.setNombre(datos[0]);
            lamina.setImagen(datos[1]);
            lamina.setTipoLamina(datos[2]);
            lamina.setFechaLanzamiento(LocalDate.of(year, 1, 1));
            lamina.setAlbum(album);
            lamina.setActive(true);

            Lamina savedLamina = Objects.requireNonNull(laminaRepository.save(lamina));
            album.getLaminas().add(savedLamina);
        }

        albumRepository.save(album);
    }

    private void syncLaminasFromCatalog(Album album) {
        if (album.getLaminasCatalogo() == null) return;

        for (LaminaCatalogo catalogo : album.getLaminasCatalogo()) {
            if (laminaRepository.findByAlbumAndNombreAndActiveTrue(album, catalogo.getNombre()).isEmpty()) {
                Lamina lamina = new Lamina();
                lamina.setNombre(catalogo.getNombre());
                lamina.setImagen(catalogo.getImagen());
                lamina.setTipoLamina(catalogo.getTipoLamina());
                lamina.setFechaLanzamiento(catalogo.getFechaLanzamiento());
                lamina.setAlbum(album);
                lamina.setActive(true);

                Lamina saved = Objects.requireNonNull(laminaRepository.save(lamina));
                album.getLaminas().add(saved);
            }
        }

        albumRepository.save(album);
    }

    private void poblarRegionesChile() {
        System.out.println("🌎 Cargando regiones oficiales de Chile...");
        String[][] regiones = {
                {"I", "Región de Tarapacá"},
                {"II", "Región de Antofagasta"},
                {"III", "Región de Atacama"},
                {"IV", "Región de Coquimbo"},
                {"V", "Región de Valparaíso"},
                {"VI", "Región del Libertador General Bernardo O’Higgins"},
                {"VII", "Región del Maule"},
                {"VIII", "Región del Biobío"},
                {"IX", "Región de La Araucanía"},
                {"X", "Región de Los Lagos"},
                {"XI", "Región Aysén del General Carlos Ibáñez del Campo"},
                {"XII", "Región de Magallanes y de la Antártica Chilena"},
                {"XIII", "Región Metropolitana de Santiago"},
                {"XIV", "Región de Los Ríos"},
                {"XV", "Región de Arica y Parinacota"},
                {"XVI", "Región de Ñuble"}
        };

        for (String[] datos : regiones) {
            RegionChile region = RegionChile.builder()
                    .codigo(datos[0])
                    .nombre(datos[1])
                    .active(true)
                    .build();
            regionRepository.save(region);
        }
        System.out.println("   ✅ " + regionRepository.count() + " regiones insertadas");
    }

    private void poblarCampeonesJockey() {
        System.out.println("🏇 Cargando campeones históricos de jockey...");

        Object[][] campeones = {
                {"Frankie Dettori", "Reino Unido", "Ganador múltiple de la Dubai World Cup", 2019},
                {"Lanfranco Dettori", "Italia", "Triple Corona de carreras europeas", 2018},
                {"Mike Smith", "Estados Unidos", "Triple Corona estadounidense con Justify", 2018},
                {"Christophe Soumillon", "Bélgica", "Récord de victorias en Francia en una temporada", 2017}
        };

        for (Object[] c : campeones) {
            CampeonJockey campeon = CampeonJockey.builder()
                    .nombreJockey((String) c[0])
                    .pais((String) c[1])
                    .titulo((String) c[2])
                    .anio((Integer) c[3])
                    .active(true)
                    .build();
            campeonJockeyRepository.save(campeon);
        }

        System.out.println("   ✅ " + campeonJockeyRepository.count() + " campeones de jockey insertados");
    }

    private void poblarMinasChile() {
        System.out.println("⛏️ Cargando minas representativas de Chile...");

        Object[][] minas = {
                {"Chuquicamata", "Región de Antofagasta", "Cobre", "ACTIVA"},
                {"Escondida", "Región de Antofagasta", "Cobre", "ACTIVA"},
                {"El Teniente", "Región del Libertador General Bernardo O’Higgins", "Cobre", "ACTIVA"},
                {"La Disputada de Las Condes", "Región Metropolitana de Santiago", "Cobre", "CERRADA"}
        };

        for (Object[] m : minas) {
            MinaChile mina = MinaChile.builder()
                    .nombre((String) m[0])
                    .region((String) m[1])
                    .mineralPrincipal((String) m[2])
                    .estado((String) m[3])
                    .active(true)
                    .build();
            minaChileRepository.save(mina);
        }

        System.out.println("   ✅ " + minaChileRepository.count() + " minas de Chile insertadas");
    }

    private void poblarListadoOlimpiadas() {
        System.out.println("🏅 Cargando listado de olimpiadas...");

        Object[][] olimpiadas = {
                {"París 2024", "París", "Francia", 2024, "Verano"},
                {"Tokio 2020", "Tokio", "Japón", 2020, "Verano"},
                {"Río 2016", "Río de Janeiro", "Brasil", 2016, "Verano"},
                {"Londres 2012", "Londres", "Reino Unido", 2012, "Verano"},
                {"Beijing 2008", "Beijing", "China", 2008, "Verano"}
        };

        for (Object[] item : olimpiadas) {
            ListadoOlimpiadas listado = new ListadoOlimpiadas();
            listado.setNombre((String) item[0]);
            listado.setCiudad((String) item[1]);
            listado.setPais((String) item[2]);
            listado.setAnio((Integer) item[3]);
            listado.setTemporada((String) item[4]);
            listado.setActive(true);
            listadoOlimpiadasRepository.save(Objects.requireNonNull(listado));
        }

        System.out.println("   ✅ " + listadoOlimpiadasRepository.count() + " olimpiadas insertadas");
    }
}

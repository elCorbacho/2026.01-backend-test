package ipss.web2.examen.config;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.CampeonJockey;
import ipss.web2.examen.models.GanadorGuinness;
import ipss.web2.examen.models.Lamina;
import ipss.web2.examen.models.LaminaCatalogo;
import ipss.web2.examen.models.ListadoOlimpiadas;
import ipss.web2.examen.models.MarcaAutomovil;
import ipss.web2.examen.models.MinaChile;
import ipss.web2.examen.models.RegionChile;
import ipss.web2.examen.models.Transportista;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.CampeonJockeyRepository;
import ipss.web2.examen.repositories.GanadorGuinnessRepository;
import ipss.web2.examen.repositories.LaminaCatalogoRepository;
import ipss.web2.examen.repositories.LaminaRepository;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
import ipss.web2.examen.repositories.MinaChileRepository;
import ipss.web2.examen.repositories.RegionRepository;
import ipss.web2.examen.repositories.TransportistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Inicializador de datos de desarrollo para la aplicación.
 * Puebla la base de datos con datos de ejemplo al arrancar en perfil de desarrollo.
 * Todos los métodos de población comprueban primero si ya existen datos (idempotente).
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TransportistaRepository transportistaRepository;
    private final AlbumRepository albumRepository;
    private final LaminaRepository laminaRepository;
    private final LaminaCatalogoRepository laminaCatalogoRepository;
    private final RegionRepository regionRepository;
    private final CampeonJockeyRepository campeonJockeyRepository;
    private final MinaChileRepository minaChileRepository;
    private final ListadoOlimpiadasRepository listadoOlimpiadasRepository;
    private final GanadorGuinnessRepository winnerGuinnessRepository;
    private final MarcaAutomovilRepository marcaAutomovilRepository;

    private static final int TARGET_SEED_COUNT = 30;
    private static final int TARGET_MARCA_AUTOMOVIL_COUNT = 2;
    private static final String[] GUINNESS_CATEGORIES = {
            "Musica", "Deportes", "Ciencia", "Tecnologia", "Naturaleza", "Cultura"
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

        // Transportistas se inicializan de forma independiente (no acoplados a álbumes)
        if (transportistaRepository.count() == 0) {
            poblarTransportistas();
        }

        if (albumRepository.count() == 0) {
            poblarAlbumes();
        } else {
            System.out.println("⚠️ Base de datos ya contiene álbumes. Saltando seed inicial de álbumes.");
        }

        if (winnerGuinnessRepository.count() == 0) {
            poblarGanadorGuinness();
        }

        if (marcaAutomovilRepository.count() == 0) {
            poblarMarcasAutomovil();
        }
    }

    // ─── Transportistas ───────────────────────────────────────────────────────

    /**
     * Puebla la tabla de transportistas con datos iniciales para desarrollo.
     * Se ejecuta solo si la tabla está vacía.
     */
    private void poblarTransportistas() {
        System.out.println("⚙️ Cargando transportistas...");

        List<Transportista> transportistas = List.of(
                Transportista.builder()
                        .nombre("Juan Perez")
                        .empresa("Transporte Universal SA")
                        .contacto("+12345678")
                        .active(true)
                        .build(),
                Transportista.builder()
                        .nombre("Maria Gomez")
                        .empresa("Logística Rápida")
                        .contacto("+87654321")
                        .active(true)
                        .build(),
                Transportista.builder()
                        .nombre("Carlos Soto")
                        .empresa("Distribuciones Norte")
                        .contacto("+56912345678")
                        .active(true)
                        .build()
        );

        transportistaRepository.saveAll(transportistas);
        System.out.println("   ✅ " + transportistaRepository.count() + " transportistas insertados");
    }

    // ─── Álbumes ──────────────────────────────────────────────────────────────

    private void poblarAlbumes() {
        System.out.println("🚀 Iniciando poblacion de base de datos con anime populares...");

        // Album 1
        Album album1 = new Album();
        album1.setNombre("Caballeros del Zodiaco");
        album1.setYear(1986);
        album1.setDescripcion("Manga clasico sobre guerreros que protegen a Atenea");
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
        album2.setDescripcion("Saga de accion y aventura que cambio el anime para siempre");
        album2.setActive(true);
        album2.setLaminas(new ArrayList<>());
        album2.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum2 = Objects.requireNonNull(albumRepository.save(album2));

        crearCatalogo(savedAlbum2, new String[][]{
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Vegeta - El Principe Saiyajin", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"},
                {"Batalla contra Freezer", "https://images.unsplash.com/photo-1626814026595-cac13b1db48d?w=400", "CONTENIDO"}
        }, 1989);

        agregarLaminasDeEjemplo(savedAlbum2, new String[][]{
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Vegeta - El Principe Saiyajin", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"},
                {"Batalla contra Freezer", "https://images.unsplash.com/photo-1626814026595-cac13b1db48d?w=400", "CONTENIDO"},
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"},
                {"Goku - Super Saiyajin", "https://images.unsplash.com/photo-1632779686507-fe4db93d0f93?w=400", "PORTADA"}
        }, 1989);

        syncLaminasFromCatalog(savedAlbum2);

        // Album 3
        Album album3 = new Album();
        album3.setNombre("Naruto");
        album3.setYear(2002);
        album3.setDescripcion("Historia epica de un ninja que busca ser reconocido");
        album3.setActive(true);
        album3.setLaminas(new ArrayList<>());
        album3.setLaminasCatalogo(new ArrayList<>());
        Album savedAlbum3 = Objects.requireNonNull(albumRepository.save(album3));

        crearCatalogo(savedAlbum3, new String[][]{
                {"Naruto Uzumaki - El Hokage", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "PORTADA"},
                {"Sasuke Uchiha - El Ultimo de su Clan", "https://images.unsplash.com/photo-1619983081563-430f63602d4b?w=400", "CONTENIDO"},
                {"Sakura Haruno - Kunoichi Poderosa", "https://images.unsplash.com/photo-1625948515291-89613c66ba51?w=400", "CONTENIDO"}
        }, 2002);

        agregarLaminasDeEjemplo(savedAlbum3, new String[][]{
                {"Naruto Uzumaki - El Hokage", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "PORTADA"},
                {"Sasuke Uchiha - El Ultimo de su Clan", "https://images.unsplash.com/photo-1619983081563-430f63602d4b?w=400", "CONTENIDO"},
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
                {"Griffith - El Falcon Blanco", "https://images.unsplash.com/photo-1633356122544-f134324ef6db?w=400", "CONTENIDO"}
        }, 1997);

        syncLaminasFromCatalog(savedAlbum5);

        long albumCount = albumRepository.count();
        long catalogCount = laminaCatalogoRepository.count();
        long laminaCount = laminaRepository.count();

        System.out.println("✅ Base de datos poblada exitosamente");
        System.out.println("   📚 " + albumCount + " Albumes creados con catalogos");
        System.out.println("   🖼️  " + catalogCount + " Laminas en catalogo");
        System.out.println("   📋 Resumen de estado (estimado):");
        System.out.println("      - Albumes: " + albumCount + ", Laminas en posesion: " + laminaCount);
    }

    // ─── Ganadores Guinness ───────────────────────────────────────────────────

    private void poblarGanadorGuinness() {
        System.out.println("🏆 Cargando ganadores Guinness...");
        for (int i = 0; i < TARGET_SEED_COUNT; i++) {
            String categoria = GUINNESS_CATEGORIES[i % GUINNESS_CATEGORIES.length];
            GanadorGuinness winner = GanadorGuinness.builder()
                    .nombre("Ganador Guinness " + (i + 1))
                    .categoria(categoria)
                    .record("Marca destacada #" + (i + 1) + " en " + categoria)
                    .anio(1990 + (i % 35))
                    .active(true)
                    .build();
            winnerGuinnessRepository.save(Objects.requireNonNull(winner));
        }
        System.out.println("   ✅ " + winnerGuinnessRepository.count() + " ganadores Guinness insertados");
    }

    // ─── Marcas Automóvil ─────────────────────────────────────────────────────

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

    // ─── Helpers ──────────────────────────────────────────────────────────────

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

    // ─── Chile ────────────────────────────────────────────────────────────────

    private void poblarRegionesChile() {
        System.out.println("🌎 Cargando regiones oficiales de Chile...");
        String[][] regiones = {
                {"I", "Región de Tarapacá"},
                {"II", "Región de Antofagasta"},
                {"III", "Región de Atacama"},
                {"IV", "Región de Coquimbo"},
                {"V", "Región de Valparaíso"},
                {"VI", "Región del Libertador General Bernardo O'Higgins"},
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
                {"Chuquicamata", "Región de Antofagasta", "Cobre", "INACTIVA"},
                {"Escondida", "Región de Antofagasta", "Cobre", "INACTIVA"},
                {"El Teniente", "Región del Libertador General Bernardo O'Higgins", "Cobre", "INACTIVA"},
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

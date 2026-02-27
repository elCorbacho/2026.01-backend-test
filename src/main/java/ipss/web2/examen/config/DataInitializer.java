package ipss.web2.examen.config;

import ipss.web2.examen.models.Album;
import ipss.web2.examen.models.Cancion;
import ipss.web2.examen.models.Lamina;
import ipss.web2.examen.models.LaminaCatalogo;
import ipss.web2.examen.models.RegionChile;
import ipss.web2.examen.repositories.AlbumRepository;
import ipss.web2.examen.repositories.CancionRepository;
import ipss.web2.examen.repositories.LaminaRepository;
import ipss.web2.examen.repositories.LaminaCatalogoRepository;
import ipss.web2.examen.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AlbumRepository albumRepository;
    private final CancionRepository cancionRepository;
    private final LaminaRepository laminaRepository;
    private final LaminaCatalogoRepository laminaCatalogoRepository;
    private final RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            poblarRegionesChile();
        }

        if (albumRepository.count() > 0) {
            System.out.println("⚠️ Base de datos ya contiene datos. Saltando inicialización.");
            return;
        }

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

        // Canciones
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
}

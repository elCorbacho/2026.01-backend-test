package ipss.web2.examen.config;

import ipss.web2.examen.models.CampeonJockey;
import ipss.web2.examen.models.CiudadChile;
import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.models.Exoplaneta;
import ipss.web2.examen.models.ListadoOlimpiadas;
import ipss.web2.examen.models.ListadoPresidenteRusia;
import ipss.web2.examen.models.MarcaAutomovil;
import ipss.web2.examen.models.MarcaBicicleta;
import ipss.web2.examen.models.MarcaCamion;
import ipss.web2.examen.models.MinaChile;
import ipss.web2.examen.models.PoblacionAve;
import ipss.web2.examen.models.PresidenteChile;
import ipss.web2.examen.models.TipoAve;
import ipss.web2.examen.models.TipoInsecto;
import ipss.web2.examen.models.Transportista;
import ipss.web2.examen.repositories.CampeonJockeyRepository;
import ipss.web2.examen.repositories.CiudadChileRepository;
import ipss.web2.examen.repositories.EquipoFutbolEspanaRepository;
import ipss.web2.examen.repositories.ExoplanetaRepository;
import ipss.web2.examen.repositories.ListadoOlimpiadasRepository;
import ipss.web2.examen.repositories.ListadoPresidenteRusiaRepository;
import ipss.web2.examen.repositories.MarcaAutomovilRepository;
import ipss.web2.examen.repositories.MarcaBicicletaRepository;
import ipss.web2.examen.repositories.MarcaCamionRepository;
import ipss.web2.examen.repositories.MinaChileRepository;
import ipss.web2.examen.repositories.PoblacionAveRepository;
import ipss.web2.examen.repositories.PresidenteChileRepository;
import ipss.web2.examen.repositories.TipoAveRepository;
import ipss.web2.examen.repositories.TipoInsectoRepository;
import ipss.web2.examen.repositories.TransportistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@Component
@RequiredArgsConstructor
public class NativeSeedStandardizationInitializer {

    private static final int TARGET_ACTIVE_RECORDS = 10;

    private final CiudadChileRepository ciudadChileRepository;
    private final EquipoFutbolEspanaRepository equipoFutbolEspanaRepository;
    private final ExoplanetaRepository exoplanetaRepository;
    private final ListadoPresidenteRusiaRepository listadoPresidenteRusiaRepository;
    private final MarcaBicicletaRepository marcaBicicletaRepository;
    private final MarcaCamionRepository marcaCamionRepository;
    private final PresidenteChileRepository presidenteChileRepository;
    private final TransportistaRepository transportistaRepository;
    private final CampeonJockeyRepository campeonJockeyRepository;
    private final MinaChileRepository minaChileRepository;
    private final ListadoOlimpiadasRepository listadoOlimpiadasRepository;
    private final MarcaAutomovilRepository marcaAutomovilRepository;
    private final TipoInsectoRepository tipoInsectoRepository;
    private final TipoAveRepository tipoAveRepository;
    private final PoblacionAveRepository poblacionAveRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void standardizeNativeSeedPopulation() {
        log.info("Starting native seed standardization (target active records per entity: {})", TARGET_ACTIVE_RECORDS);

        normalizeTransportistas();
        normalizeCampeonesJockey();
        normalizeMinasChile();
        normalizeListadoOlimpiadas();
        normalizeMarcaAutomovil();
        normalizeTipoInsecto();
        normalizeTipoAve();
        normalizePoblacionAve();
        normalizePresidentesChile();
        normalizePresidentesRusia();
        normalizeMarcaCamion();
        normalizeMarcaBicicleta();
        normalizeExoplanetas();
        normalizeEquiposFutbolEspana();
        normalizeCiudadesChile();

        log.info("Native seed standardization completed");
    }

    private void normalizeTransportistas() {
        normalizeEntity(
                "transportista",
                transportistaRepository.findByActiveTrue(),
                index -> Transportista.builder()
                        .nombre("Transportista Seed " + index)
                        .empresa("Empresa Logistica " + index)
                        .contacto(String.format("+5699000%04d", index))
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()) && notBlank(entity.getEmpresa()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Transportista Seed Corregido");
                    }
                    if (!notBlank(entity.getEmpresa())) {
                        entity.setEmpresa("Empresa Logistica Corregida");
                    }
                    if (!notBlank(entity.getContacto())) {
                        entity.setContacto("+56990000000");
                    }
                },
                records -> transportistaRepository.saveAll(records)
        );
    }

    private void normalizeCampeonesJockey() {
        normalizeEntity(
                "campeon_jockey",
                campeonJockeyRepository.findByActiveTrue(),
                index -> CampeonJockey.builder()
                        .nombreJockey("Campeon Jockey " + index)
                        .pais("Chile")
                        .titulo("Titulo historico " + index)
                        .anio(2000 + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombreJockey()) && notBlank(entity.getTitulo()) && entity.getAnio() != null,
                entity -> {
                    if (!notBlank(entity.getNombreJockey())) {
                        entity.setNombreJockey("Campeon Jockey Corregido");
                    }
                    if (!notBlank(entity.getTitulo())) {
                        entity.setTitulo("Titulo corregido");
                    }
                    if (entity.getAnio() == null) {
                        entity.setAnio(2000);
                    }
                    if (!notBlank(entity.getPais())) {
                        entity.setPais("Chile");
                    }
                },
                records -> campeonJockeyRepository.saveAll(records)
        );
    }

    private void normalizeMinasChile() {
        normalizeEntity(
                "mina_chile",
                minaChileRepository.findByActiveTrue(),
                index -> MinaChile.builder()
                        .nombre("Mina Seed " + index)
                        .region("Region Seed " + index)
                        .mineralPrincipal("Cobre")
                        .estado("ACTIVA")
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre())
                        && notBlank(entity.getRegion())
                        && notBlank(entity.getMineralPrincipal())
                        && notBlank(entity.getEstado()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Mina Seed Corregida");
                    }
                    if (!notBlank(entity.getRegion())) {
                        entity.setRegion("Region corregida");
                    }
                    if (!notBlank(entity.getMineralPrincipal())) {
                        entity.setMineralPrincipal("Cobre");
                    }
                    if (!notBlank(entity.getEstado())) {
                        entity.setEstado("ACTIVA");
                    }
                },
                records -> minaChileRepository.saveAll(records)
        );
    }

    private void normalizeListadoOlimpiadas() {
        normalizeEntity(
                "listado_olimpiadas",
                listadoOlimpiadasRepository.findByActiveTrue(),
                index -> ListadoOlimpiadas.builder()
                        .nombre("Olimpiadas Seed " + index)
                        .ciudad("Ciudad Seed " + index)
                        .pais("Pais Seed " + index)
                        .anio(1980 + index)
                        .temporada(index % 2 == 0 ? "Verano" : "Invierno")
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre())
                        && notBlank(entity.getCiudad())
                        && notBlank(entity.getPais())
                        && entity.getAnio() != null
                        && notBlank(entity.getTemporada()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Olimpiadas Seed Corregida");
                    }
                    if (!notBlank(entity.getCiudad())) {
                        entity.setCiudad("Ciudad corregida");
                    }
                    if (!notBlank(entity.getPais())) {
                        entity.setPais("Pais corregido");
                    }
                    if (entity.getAnio() == null) {
                        entity.setAnio(2000);
                    }
                    if (!notBlank(entity.getTemporada())) {
                        entity.setTemporada("Verano");
                    }
                },
                records -> listadoOlimpiadasRepository.saveAll(records)
        );
    }

    private void normalizeMarcaAutomovil() {
        normalizeEntity(
                "marca_automovil",
                marcaAutomovilRepository.findByActiveTrueOrderByNombreAsc(),
                index -> MarcaAutomovil.builder()
                        .nombre("Marca Auto " + index)
                        .paisOrigen("Pais " + index)
                        .descripcion("Marca automotriz seed " + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Marca Auto Corregida");
                    }
                    if (!notBlank(entity.getPaisOrigen())) {
                        entity.setPaisOrigen("Pais corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> marcaAutomovilRepository.saveAll(records)
        );
    }

    private void normalizeTipoInsecto() {
        normalizeEntity(
                "tipo_insecto",
                tipoInsectoRepository.findByActiveTrueOrderByNombreAsc(),
                index -> TipoInsecto.builder()
                        .nombre("Tipo Insecto " + index)
                        .descripcion("Descripcion insecto " + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Tipo Insecto Corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> tipoInsectoRepository.saveAll(records)
        );
    }

    private void normalizeTipoAve() {
        normalizeEntity(
                "tipo_ave",
                tipoAveRepository.findByActiveTrue(),
                index -> TipoAve.builder()
                        .nombre("Tipo Ave " + index)
                        .descripcion("Descripcion tipo ave " + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Tipo Ave Corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> tipoAveRepository.saveAll(records)
        );
    }

    private void normalizePoblacionAve() {
        List<TipoAve> tiposActivos = tipoAveRepository.findByActiveTrue();
        if (tiposActivos.isEmpty()) {
            log.warn("Skipping poblacion_ave normalization because no active tipo_ave records were found");
            return;
        }

        List<PoblacionAve> activeRecords = poblacionAveRepository.findByActiveTrueOrderByFechaAsc();
        List<PoblacionAve> toPersist = new ArrayList<>();

        for (PoblacionAve current : activeRecords) {
            if (current.getTipoAve() == null) {
                current.setTipoAve(tiposActivos.get(0));
                toPersist.add(current);
            }
            if (current.getCantidad() == null || current.getCantidad() <= 0) {
                current.setCantidad(100);
                toPersist.add(current);
            }
            if (current.getFecha() == null) {
                current.setFecha(LocalDate.of(2025, 1, 1));
                toPersist.add(current);
            }
            current.setActive(true);
        }

        if (activeRecords.size() > TARGET_ACTIVE_RECORDS) {
            for (int i = TARGET_ACTIVE_RECORDS; i < activeRecords.size(); i++) {
                PoblacionAve extra = activeRecords.get(i);
                extra.setActive(false);
                toPersist.add(extra);
            }
        }

        if (!toPersist.isEmpty()) {
            poblacionAveRepository.saveAll(toPersist);
        }

        int currentActive = Math.min(activeRecords.size(), TARGET_ACTIVE_RECORDS);
        if (currentActive < TARGET_ACTIVE_RECORDS) {
            int missing = TARGET_ACTIVE_RECORDS - currentActive;
            List<PoblacionAve> toCreate = new ArrayList<>();
            for (int i = 0; i < missing; i++) {
                int recordIndex = currentActive + i + 1;
                TipoAve tipo = tiposActivos.get((recordIndex - 1) % tiposActivos.size());
                toCreate.add(PoblacionAve.builder()
                        .tipoAve(tipo)
                        .cantidad(100 + (recordIndex * 10))
                        .fecha(LocalDate.of(2025, 1, 1).plusDays(recordIndex))
                        .active(true)
                        .build());
            }
            poblacionAveRepository.saveAll(toCreate);
        }

        int finalCount = poblacionAveRepository.findByActiveTrueOrderByFechaAsc().size();
        log.info("Entity {} standardized: active={} target={}", "poblacion_ave", finalCount, TARGET_ACTIVE_RECORDS);
    }

    private void normalizePresidentesChile() {
        normalizeEntity(
                "presidente_chile",
                presidenteChileRepository.findByActiveTrue(),
                index -> {
                    int startYear = 1990 + ((index - 1) * 4);
                    return PresidenteChile.builder()
                            .nombre("Presidente Chile Seed " + index)
                            .periodoInicio(LocalDate.of(startYear, 3, 11))
                            .periodoFin(LocalDate.of(startYear + 4, 3, 10))
                            .partido("Partido Seed " + index)
                            .descripcion("Presidente de ejemplo " + index)
                            .active(true)
                            .build();
                },
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()) && entity.getPeriodoInicio() != null,
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Presidente Chile Corregido");
                    }
                    if (entity.getPeriodoInicio() == null) {
                        entity.setPeriodoInicio(LocalDate.of(1990, 3, 11));
                    }
                    if (!notBlank(entity.getPartido())) {
                        entity.setPartido("Partido corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> presidenteChileRepository.saveAll(records)
        );
    }

    private void normalizePresidentesRusia() {
        normalizeEntity(
                "listado_presidente_rusia",
                listadoPresidenteRusiaRepository.findByActiveTrue(),
                index -> {
                    int startYear = 1991 + ((index - 1) * 4);
                    return ListadoPresidenteRusia.builder()
                            .nombre("Presidente Rusia Seed " + index)
                            .periodoInicio(LocalDate.of(startYear, 5, 7))
                            .periodoFin(LocalDate.of(startYear + 4, 5, 6))
                            .partido("Partido Seed " + index)
                            .descripcion("Presidente de ejemplo " + index)
                            .active(true)
                            .build();
                },
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()) && entity.getPeriodoInicio() != null,
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Presidente Rusia Corregido");
                    }
                    if (entity.getPeriodoInicio() == null) {
                        entity.setPeriodoInicio(LocalDate.of(1991, 5, 7));
                    }
                    if (!notBlank(entity.getPartido())) {
                        entity.setPartido("Partido corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> listadoPresidenteRusiaRepository.saveAll(records)
        );
    }

    private void normalizeMarcaCamion() {
        normalizeEntity(
                "marca_camion",
                marcaCamionRepository.findByActiveTrueOrderByNombreAsc(),
                index -> MarcaCamion.builder()
                        .nombre("Marca Camion " + index)
                        .paisOrigen("Pais " + index)
                        .descripcion("Marca de camion seed " + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Marca Camion Corregida");
                    }
                    if (!notBlank(entity.getPaisOrigen())) {
                        entity.setPaisOrigen("Pais corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> marcaCamionRepository.saveAll(records)
        );
    }

    private void normalizeMarcaBicicleta() {
        normalizeEntity(
                "marca_bicicleta",
                marcaBicicletaRepository.findByActiveTrueOrderByNombreAsc(),
                index -> MarcaBicicleta.builder()
                        .nombre("Marca Bicicleta " + index)
                        .paisOrigen("Pais " + index)
                        .descripcion("Marca de bicicleta seed " + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Marca Bicicleta Corregida");
                    }
                    if (!notBlank(entity.getPaisOrigen())) {
                        entity.setPaisOrigen("Pais corregido");
                    }
                    if (!notBlank(entity.getDescripcion())) {
                        entity.setDescripcion("Descripcion corregida");
                    }
                },
                records -> marcaBicicletaRepository.saveAll(records)
        );
    }

    private void normalizeExoplanetas() {
        normalizeEntity(
                "exoplaneta",
                exoplanetaRepository.findByActiveTrueOrderByNombreAsc(),
                index -> Exoplaneta.builder()
                        .nombre("Exoplaneta Seed " + index)
                        .tipo(index % 2 == 0 ? "Gas gigante" : "Terrestre")
                        .distanciaAnosLuz(40.0 + index)
                        .masaRelativaJupiter(0.5 + (index * 0.1))
                        .descubiertoAnio(1990 + index)
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre())
                        && notBlank(entity.getTipo())
                        && entity.getDistanciaAnosLuz() != null
                        && entity.getMasaRelativaJupiter() != null
                        && entity.getDescubiertoAnio() != null,
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Exoplaneta Corregido");
                    }
                    if (!notBlank(entity.getTipo())) {
                        entity.setTipo("Terrestre");
                    }
                    if (entity.getDistanciaAnosLuz() == null || entity.getDistanciaAnosLuz() <= 0) {
                        entity.setDistanciaAnosLuz(50.0);
                    }
                    if (entity.getMasaRelativaJupiter() == null || entity.getMasaRelativaJupiter() <= 0) {
                        entity.setMasaRelativaJupiter(1.0);
                    }
                    if (entity.getDescubiertoAnio() == null) {
                        entity.setDescubiertoAnio(2000);
                    }
                },
                records -> exoplanetaRepository.saveAll(records)
        );
    }

    private void normalizeEquiposFutbolEspana() {
        normalizeEntity(
                "equipo_futbol_espana",
                equipoFutbolEspanaRepository.findByActivoTrue(),
                index -> EquipoFutbolEspana.builder()
                        .nombre("Equipo Seed " + index)
                        .ciudad("Ciudad Seed " + index)
                        .fundacion(1900 + index)
                        .estadio("Estadio Seed " + index)
                        .activo(true)
                        .build(),
                entity -> entity.setActivo(false),
                entity -> notBlank(entity.getNombre()),
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Equipo Corregido");
                    }
                    if (!notBlank(entity.getCiudad())) {
                        entity.setCiudad("Ciudad corregida");
                    }
                    if (entity.getFundacion() == null) {
                        entity.setFundacion(1900);
                    }
                    if (!notBlank(entity.getEstadio())) {
                        entity.setEstadio("Estadio corregido");
                    }
                },
                records -> equipoFutbolEspanaRepository.saveAll(records)
        );
    }

    private void normalizeCiudadesChile() {
        normalizeEntity(
                "ciudad_chile",
                ciudadChileRepository.findByActiveTrueOrderByNombreAsc(),
                index -> CiudadChile.builder()
                        .nombre("Ciudad Seed " + index)
                        .poblacion(100000L + (index * 1000L))
                        .active(true)
                        .build(),
                entity -> entity.setActive(false),
                entity -> notBlank(entity.getNombre()) && entity.getPoblacion() != null && entity.getPoblacion() > 0,
                entity -> {
                    if (!notBlank(entity.getNombre())) {
                        entity.setNombre("Ciudad Corregida");
                    }
                    if (entity.getPoblacion() == null || entity.getPoblacion() <= 0) {
                        entity.setPoblacion(100000L);
                    }
                },
                records -> ciudadChileRepository.saveAll(records)
        );
    }

    private <T> void normalizeEntity(
            String entityName,
            List<T> activeRecords,
            Function<Integer, T> missingRecordFactory,
            Consumer<T> deactivateRecord,
            Predicate<T> isValid,
            Consumer<T> fixRecord,
            Function<List<T>, List<T>> persistBatch
    ) {
        List<T> toPersist = new ArrayList<>();

        for (T current : activeRecords) {
            if (!isValid.test(current)) {
                fixRecord.accept(current);
                toPersist.add(current);
            }
        }

        if (activeRecords.size() > TARGET_ACTIVE_RECORDS) {
            for (int i = TARGET_ACTIVE_RECORDS; i < activeRecords.size(); i++) {
                T extra = activeRecords.get(i);
                deactivateRecord.accept(extra);
                toPersist.add(extra);
            }
        }

        if (!toPersist.isEmpty()) {
            persistBatch.apply(toPersist);
        }

        int currentActive = Math.min(activeRecords.size(), TARGET_ACTIVE_RECORDS);
        if (currentActive < TARGET_ACTIVE_RECORDS) {
            int missing = TARGET_ACTIVE_RECORDS - currentActive;
            List<T> toCreate = new ArrayList<>();
            for (int i = 0; i < missing; i++) {
                int recordIndex = currentActive + i + 1;
                T candidate = missingRecordFactory.apply(recordIndex);
                fixRecord.accept(candidate);
                toCreate.add(Objects.requireNonNull(candidate));
            }
            persistBatch.apply(toCreate);
        }

        log.info(
                "Entity {} standardized: active(before)={} target={} action={} ",
                entityName,
                activeRecords.size(),
                TARGET_ACTIVE_RECORDS,
                activeRecords.size() == TARGET_ACTIVE_RECORDS ? "verified" : "remediated"
        );
    }

    private boolean notBlank(String value) {
        return value != null && !value.isBlank();
    }
}

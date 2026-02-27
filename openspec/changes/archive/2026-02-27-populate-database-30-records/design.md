# Design: Poblar Base de Datos con 30 Registros por Entidad

## Technical Approach

Se extenderá el `DataInitializer` existente para poblar 8 entidades con 30 registros cada una. El patrón será idéntico al actual: verificar si la tabla está vacía (`repository.count() == 0`) antes de poblar. Las entidades con dependencias (GanadorAlbum requiere Album existente) se poblarán después deAlbum. Las entidades se poblararán en orden pararespectar las foreign keys.

**Orden de población:**
1. GanadorGuinness (sin dependencias)
2. DemoWidget (sin dependencias)
3. TestModel (sin dependencias)
4. PaisDistribucion (sin dependencias)
5. EmpresaInsumos (sin dependencias)
6. TiendaLamina (sin dependencias)
7. GanadorPremioAlbum (sin dependencias)
8. GanadorAlbum (requiere Album existente - 5 albums ya existen en DataInitializer)

Se usará el patrón Builder para todas las entidades, `Objects.requireNonNull()` al guardar, y `active = true` manualmente.

## Architecture Decisions

### Decision: Extender DataInitializer en lugar de crear un nuevo Component
**Choice**: Agregar métodos de poblamiento al `DataInitializer` existente.
**Alternatives considered**: Crear un nuevo `@Component` separado (p. ej. `AdditionalDataInitializer`).
**Rationale**: Mantiene la inicialización de datos centralizada en un solo lugar, sigue el patrón actual del proyecto, y permite visualizar toda la carga de datos en un solo punto del log de inicio.

### Decision: Datos realistas para pruebas
**Choice**: Usar datos reales/razonables para cada entidad (no datos dummy genéricos).
**Alternatives considered**: Usar datos tipo "Test 1", "Test 2", etc.
**Rationale**: Facilita la verificación manual y las pruebas de integración con datos que representan escenarios reales.

### Decision: Manejo de GanadorAlbum con Album existente
**Choice**: Poblar GanadorAlbum después de Album, obteniendo los albums existentes del repositorio.
**Alternatives considered**: Crear nuevos albums para GanadorAlbum.
**Rationale**: Album tiene 5 registros existentes del DataInitializer actual. Reutilizarlos evita duplicación y respeta la integridad referencial.

## Data Flow

```
Application Startup
       │
       ▼
DataInitializer.run()
       │
       ├─► poblarRegionesChile()      [ya existe]
       ├─► poblarCampeonesJockey()    [ya existe]
       ├─► poblarMinasChile()         [ya existe]
       ├─► poblarListadoOlimpiadas()  [ya existe]
       │
       ├─► poblarGanadorGuinness()     [NUEVO - 30 registros]
       ├─► poblarDemoWidget()          [NUEVO - 30 registros]
       ├─► poblarTestModel()           [NUEVO - 30 registros]
       ├─► poblarPaisDistribucion()   [NUEVO - 30 registros]
       ├─► poblarEmpresaInsumos()     [NUEVO - 30 registros]
       ├─► poblarTiendaLamina()       [NUEVO - 30 registros]
       ├─► poblarGanadorPremioAlbum() [NUEVO - 30 registros]
       └─► poblarGanadorAlbum()       [NUEVO - 30 registros - requiere Album]
```

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modify | Agregar 8 nuevos métodos de poblamiento y registrarlos en `run()` con verificación `count() == 0`. |

### Métodos a agregar en DataInitializer:

1. `poblarGanadorGuinness()` - 30 records: nombre, categoria, record, anio
2. `poblarDemoWidget()` - 30 records: nombre, tipo
3. `poblarTestModel()` - 30 records: nombre
4. `poblarPaisDistribucion()` - 30 records: nombre, codigoIso, descripcion
5. `poblarEmpresaInsumos()` - 30 records: nombre, rubro, contacto, telefono, email, sitioWeb
6. `poblarTiendaLamina()` - 30 records: nombre, ciudad, direccion, telefono, email, fechaApertura
7. `poblarGanadorPremioAlbum()` - 30 records: artista, album, premio, anio, genero
8. `poblarGanadorAlbum()` - 30 records: artista, premio, anio (relación con Album existente)

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Manual | Verificar que cada tabla contiene 30 registros después del inicio | Consultar cada repository `.count() == 30` en la consola |
| Manual | Verificar que GanadorAlbum tiene relación válida con Album | Consultar un GanadorAlbum y verificar que `getAlbum()` no es null |
| Manual | Verificar soft delete: todos los registros tienen `active = true` | Verificar en log de inicio o consultar BD |

## Migration / Rollout

No se requiere migración de base de datos. La población es idempotente: solo se ejecutará si `repository.count() == 0`. En entornos de producción donde ya existen datos, no se sobrescribirán.

Para ejecutar la población en un entorno existente:
1. Eliminar los registros de las tablas afectadas, o
2. Truncar las tablas (si la DB lo permite), o
3. Eliminar la base de datos y recrearla (para desarrollo)

## Open Questions

- [x] ¿Cuántos registros por entidad? **30 registros cada una** (240 registros totales)
- [x] ¿Qué datos usar? **Datos realistas** para cada tipo de entidad
- [x] ¿Orden de poblamiento? **GanadorAlbum al final** por su dependencia con Album
- [x] ¿Cómo verificar? **Logs de console** con conteo final por entidad

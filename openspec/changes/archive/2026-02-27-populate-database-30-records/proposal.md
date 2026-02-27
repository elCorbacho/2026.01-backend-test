# Proposal: Poblar Base de Datos con 30 Registros por Entidad

## Intent

Llenar las 8 entidades vacías del proyecto con 30 registros cada una utilizando DataInitializers de Spring Boot, siguiendo el patrón existente en `DataInitializer.java` para datos iniciales.

## Scope

### In Scope
- Poblar las siguientes entidades con 30 registros cada una:
  1. **GanadorGuinness** - campos: nombre, categoria, record, anio
  2. **GanadorAlbum** - campos: artista, premio, anio (relación con Album)
  3. **GanadorPremioAlbum** - estructura a verificar
  4. **DemoWidget** - campos: nombre, tipo
  5. **PaisDistribucion** - campos: nombre, codigoIso, descripcion
  6. **TestModel** - estructura a verificar
  7. **EmpresaInsumos** - campos: nombre, rubro, contacto, telefono, email, sitioWeb
  8. **TiendaLamina** - campos: nombre, ciudad, direccion, telefono, email, fechaApertura

### Out of Scope
- Crear nuevos endpoints o APIs para estas entidades.
- Modificar lógica de negocio existente.
- Migraciones de datos desde fuentes externas.

## Approach

1. **Verificar estructura de entidades**: Revisar las clases modelo existentes para confirmar campos y relaciones.
2. **Crear DataInitializer(s)**: 
   - Opción A: Un solo `DataInitializer.java` que llene todas las entidades.
   - Opción B: Un `DataInitializer` por entidad para mejor organización.
3. **Verificar datos existentes**: Antes de insertar, verificar si ya existen registros para evitar duplicados.
4. **Usar `@Transactional`** y manejo de errores apropiado.
5. **Mantener consistencia**: Usar los patrones de soft delete (`active = true`) y auditoría existente.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/config/DataInitializer.java` | Modify | Agregar lógica de poblamiento de entidades |
| `src/main/java/ipss/web2/examen/models/` | Read | Entidades a poblar |
| Base de datos | Write | 8 tablas con 30 registros cada una (240 registros totales) |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Datos duplicados al ejecutar múltiples veces | Medium | Verificar existencia antes de insertar o usar `COUNT` para validar |
| Conflictos con datos existentes | Low | Las entidades están vacías según la exploración |
| Errores de validación (constraints) | Low | Verificar constraints únicos antes de insertar |
| Fallo en relaciones (FK inválidas) | Medium | Para `GanadorAlbum`, verificar que Album exista antes de relacionar |

## Rollback Plan

1. **Reversión manual**: Eliminar los registros insertados directamente en la base de datos.
2. **Deshabilitar initializer**: Comentar o eliminar el código del DataInitializer para evitar re-inserción.
3. **Soft delete alternativo**: Si no se puede eliminar, ejecutar UPDATE para marcar `active = false`.

## Dependencies

- Spring Boot CommandLineRunner (ya existe en el proyecto).
- Entidades JPA existentes del proyecto.
- Repositories para cada entidad.

## Success Criteria

- [ ] Cada una de las 8 entidades tiene exactamente 30 registros.
- [ ] Todos los registros tienen `active = true`.
- [ ] Se mantiene la auditoría (`createdAt`, `updatedAt`).
- [ ] Para `GanadorAlbum`, las relaciones con `Album` son válidas.
- [ ] La aplicación inicia correctamente con los datos insertados.
- [ ] No hay errores de constraints únicos o foreign keys.

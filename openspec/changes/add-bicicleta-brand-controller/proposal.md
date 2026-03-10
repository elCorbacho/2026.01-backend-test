## Why

Las marcas de bicicleta son una categoría clave para ampliar el alcance del sistema de colecciones, permitiendo la gestión de catálogos y modelos específicos de bicicletas. Actualmente solo existen controllers para automóviles y láminas; falta cobertura para bicicletas, lo que limita la complejidad del sistema y su utilidad en nuevas verticales.

## What Changes

- Crear entidad `MarcaBicicleta` en el modelo JPA, con campos `id`, `nombre`, y `active` (soft delete).
- Implementar `MarcaBicicletaController` en `controllers/api`, con endpoint GET `/api/marcas-bicicleta` para listar marcas activas.
- Crear DTOs `MarcaBicicletaResponseDTO`, `MarcaBicicletaSummaryDTO` y mappers manuales.
- Crear `MarcaBicicletaRepository` con método `findByActiveTrue()`.
- Implementar `MarcaBicicletaService` con business logic y filtrado por estado activo.
- Agregar seed inicial en `DataInitializer` para poblar marcas conocidas de bicicleta.
- Documentar el endpoint en Swagger y agregar tests unitarios y de integración.

## Capabilities

### New Capabilities
- `bicicleta-brand-management`: Permite crear, listar y poblar marcas de bicicleta en la base de datos, soportando soft delete y expansión futura.

### Modified Capabilities

## Impact

- Se agregan nuevos archivos en `models/`, `controllers/api/`, `services/`, `repositories/`, `dtos/`, `mappers/`.
- Endpoint nuevo `/api/marcas-bicicleta` en API REST.
- Cambios en `DataInitializer` para seed de marcas.
- Tests nuevos para controlador, servicio y repository de bicicleta.
- Documentación Swagger ampliada con rutas y ejemplos.
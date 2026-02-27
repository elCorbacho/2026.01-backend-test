## Why

El sistema actual de gestión de álbumes y láminas no permite registrar ni consultar de forma estructurada a los campeones de jockey asociados a las competencias o a los álbumes/colecciones, lo que dificulta construir vistas de ranking, historiales y estadísticas deportivas. Este cambio busca introducir un modelo dedicado para campeones de jockey y una API de consulta unificada que exponga esta información de manera consistente y lista para consumo por frontends o integraciones externas.

## What Changes

- **Nuevo modelo `CampeonJockey`** en la capa de modelos, con información básica del jinete (nombre, país/opcional), el título o campeonato ganado, año de obtención y un indicador de estado activo.
- **Nueva tabla en base de datos** para persistir los campeones de jockey, siguiendo las convenciones de soft delete (`active`) y auditoría (`createdAt`, `updatedAt`).
- **Nuevo repositorio, servicio y controlador REST** dedicados a campeones de jockey, alineados con el patrón ya usado (Controller → Service → Repository → Model).
- **Nuevo endpoint GET** para listar campeones de jockey activos, retornando siempre `ResponseEntity<ApiResponseDTO<List<CampeonJockeyResponseDTO>>>`.
- **Poblado inicial de datos** a través de un inicializador (similar a `DataInitializer`) para registrar un conjunto base de campeones de jockey al iniciar la aplicación.
- **Documentación OpenAPI/Swagger actualizada** para incluir el recurso de campeones de jockey dentro del catálogo público de la API.

## Capabilities

### New Capabilities
- `campeones-jockey`: Gestión y exposición de campeones de jockey mediante un nuevo modelo JPA, servicio y controlador REST, incluyendo listado de campeones activos y datos iniciales precargados en la base de datos.

### Modified Capabilities
- `laminas`: Se amplía el contexto funcional de la plataforma para incluir información complementaria de campeones de jockey asociada al dominio de colecciones, sin modificar las reglas de negocio ni los contratos existentes de los endpoints de láminas.

## Impact

- **Modelos**: Se agrega una nueva entidad JPA `CampeonJockey` con sus anotaciones de auditoría y soft delete, manteniendo la coherencia con `Album`, `Lamina` y `LaminaCatalogo`.
- **Repositorios**: Nuevo `CampeonJockeyRepository` con métodos para recuperar únicamente registros activos.
- **Servicios**: Nuevo `CampeonJockeyService` (y su implementación) encargado de la lógica de negocio de lectura y eventual extensión a otros casos de uso (filtros por año, país, etc.).
- **Controladores**: Nuevo `CampeonJockeyController` bajo un espacio de nombres `/api/campeones-jockey` (o similar) que expone un endpoint GET de listado general y queda preparado para futuros endpoints (búsqueda por año, detalle, etc.).
- **Base de datos**: Nueva tabla persistente para campeones de jockey, con datos iniciales incluidos en el flujo de inicialización de datos del proyecto.
- **Documentación y colecciones de prueba**: Inclusión de los nuevos endpoints en las colecciones Postman/Bruno existentes y en la documentación Swagger, impactando a equipos que consumen la API pero sin introducir cambios rompientes sobre los recursos actuales.

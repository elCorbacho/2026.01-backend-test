## Why

El sistema actual no cuenta con un modelo ni una API específica para representar y exponer información de minas de Chile, lo que impide construir reportes, listados y futuros análisis sobre estos activos. Este cambio busca introducir un recurso dedicado para minas chilenas, accesible vía API, con operaciones de lectura y creación, y con datos iniciales poblados automáticamente.

## What Changes

- **Nuevo modelo `MinaChile`** en la capa de modelos, que represente minas ubicadas en Chile con atributos básicos (nombre, región, tipo de mineral principal, estado/actividad).
- **Nueva tabla en base de datos** para persistir información de minas, siguiendo las convenciones de auditoría (`createdAt`, `updatedAt`) y soft delete (`active`) del proyecto.
- **Nuevo repositorio, servicio y controlador REST** dedicados a minas de Chile, alineados con la arquitectura MVC existente.
- **Nuevos endpoints GET y POST** bajo un espacio de nombres tipo `/api/minas-chile` para listar minas y registrar nuevas minas.
- **Poblado inicial de datos** de ejemplo de minas chilenas al iniciar la aplicación cuando la tabla esté vacía.
- **Documentación OpenAPI actualizada** y colecciones de prueba extendidas para cubrir los nuevos endpoints.

## Capabilities

### New Capabilities
- `minas-chile`: Gestión y exposición de minas de Chile mediante un nuevo modelo JPA y una API REST que permita listar minas existentes y crear nuevas, con datos iniciales cargados en la base de datos.

### Modified Capabilities
- *(ninguna)* Por ahora no se modifican capacidades existentes; los recursos y endpoints actuales se mantienen sin cambios.

## Impact

- **Modelos**: Se agrega una nueva entidad `MinaChile` con campos específicos del dominio minero chileno, ajustada a las convenciones de auditoría y soft delete ya usadas en el proyecto.
- **Repositorios y servicios**: Se incorpora un nuevo repositorio JPA y un servicio de dominio para encapsular la lógica de lectura y creación de minas, reutilizando patrones de validación y transaccionalidad.
- **Controladores y API**: Se añade un controlador REST con endpoints GET y POST para minas de Chile, retornando siempre `ApiResponseDTO` y mensajes consistentes con el resto de la API.
- **Base de datos**: Se crea una nueva tabla para almacenar minas, con población inicial en el `DataInitializer` para facilitar pruebas y demostraciones sin operaciones manuales.
- **Ecosistema de herramientas**: Se actualiza Swagger/OpenAPI y, de ser necesario, las colecciones de Postman/Bruno para incluir y probar el nuevo recurso, sin introducir cambios rompientes sobre los contratos existentes.


## ADDED Requirements

### Requirement: Listar campeones de jockey activos
El sistema SHALL exponer un endpoint de solo lectura que permita obtener el listado completo de campeones de jockey activos registrados en la base de datos, retornando la información en un formato estructurado y consistente con el resto de la API.

#### Scenario: Listado exitoso de campeones
- **WHEN** el cliente realiza una petición GET al endpoint de campeones de jockey
- **THEN** el sistema responde con HTTP 200
- **AND** el cuerpo de la respuesta contiene un `ApiResponseDTO` con `success = true`
- **AND** el campo `data` contiene una lista de campeones de jockey activos (o una lista vacía si no hay registros)

### Requirement: Estructura de datos de campeones de jockey
El sistema SHALL representar cada campeón de jockey con un objeto que incluya al menos: identificador, nombre del jockey, país (opcional), título o campeonato ganado, año del título y metadatos de auditoría (fechas de creación y actualización).

#### Scenario: Estructura mínima del campeón
- **WHEN** el sistema retorna un campeón de jockey en el listado
- **THEN** cada elemento contiene los campos requeridos (`id`, `nombreJockey`, `pais` opcional, `titulo`, `anio`, `createdAt`, `updatedAt`)
- **AND** no se exponen campos internos irrelevantes para el cliente (por ejemplo, flags técnicos o claves foráneas innecesarias)

### Requirement: Solo campeones activos en el listado
El sistema SHALL excluir del listado de campeones de jockey todos aquellos registros marcados como inactivos (soft delete), de forma que el endpoint únicamente retorne campeones activos.

#### Scenario: Exclusión de campeones inactivos
- **WHEN** existen campeones de jockey marcados como inactivos en la base de datos
- **THEN** el endpoint de listado no incluye dichos registros en la colección retornada
- **AND** solo se muestran campeones con el flag de actividad en verdadero

### Requirement: Datos iniciales de campeones de jockey
El sistema SHALL poblar automáticamente un conjunto de campeones de jockey de ejemplo en la base de datos al iniciar la aplicación, siempre y cuando la tabla correspondiente se encuentre vacía.

#### Scenario: Poblado inicial en base de datos vacía
- **WHEN** la aplicación se inicia y la tabla de campeones de jockey no contiene registros
- **THEN** se insertan uno o más campeones de jockey predefinidos
- **AND** una posterior petición GET al endpoint de campeones retorna al menos esos registros iniciales


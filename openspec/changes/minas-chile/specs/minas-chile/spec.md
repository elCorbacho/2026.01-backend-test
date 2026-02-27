## ADDED Requirements

### Requirement: Listar minas de Chile activas
El sistema SHALL exponer un endpoint de solo lectura que permita obtener el listado completo de minas de Chile activas registradas en la base de datos, retornando la información en un formato estructurado y consistente con el resto de la API.

#### Scenario: Listado exitoso de minas
- **WHEN** el cliente realiza una petición GET al endpoint de minas de Chile
- **THEN** el sistema responde con HTTP 200
- **AND** el cuerpo de la respuesta contiene un `ApiResponseDTO` con `success = true`
- **AND** el campo `data` contiene una lista de minas de Chile activas (o una lista vacía si no hay registros)

### Requirement: Estructura de datos de una mina de Chile
El sistema SHALL representar cada mina de Chile con un objeto que incluya al menos: identificador, nombre de la mina, región, mineral principal, estado/actividad y metadatos de auditoría (fechas de creación y actualización).

#### Scenario: Estructura mínima de la mina
- **WHEN** el sistema retorna una mina de Chile en el listado o en una respuesta de creación
- **THEN** cada elemento contiene los campos requeridos (`id`, `nombre`, `region`, `mineralPrincipal`, `estado`, `createdAt`, `updatedAt`)
- **AND** no se exponen campos internos irrelevantes para el cliente

### Requirement: Registrar nuevas minas de Chile vía POST
El sistema SHALL permitir registrar nuevas minas de Chile mediante un endpoint POST que reciba un cuerpo de solicitud con los datos mínimos requeridos, valide la información y persista la mina como activa en la base de datos.

#### Scenario: Creación exitosa de una mina
- **WHEN** el cliente envía una petición POST al endpoint de minas de Chile con un cuerpo válido
- **THEN** el sistema responde con HTTP 201
- **AND** el cuerpo de la respuesta contiene un `ApiResponseDTO` con `success = true`
- **AND** el campo `data` contiene la mina creada, incluyendo su identificador generado

### Requirement: Validación básica en la creación de minas
El sistema SHALL validar que los campos requeridos para crear una mina de Chile (por ejemplo, nombre y región) no sean nulos ni vacíos, rechazando solicitudes inválidas con un error de validación.

#### Scenario: Error de validación en creación de mina
- **WHEN** el cliente envía una petición POST con campos requeridos vacíos o inválidos
- **THEN** el sistema responde con HTTP 400
- **AND** el cuerpo de la respuesta contiene un `ApiResponseDTO` con `success = false` y un `errorCode` apropiado

### Requirement: Solo minas activas en el listado
El sistema SHALL excluir del listado de minas de Chile todos aquellos registros marcados como inactivos (soft delete), de forma que el endpoint únicamente retorne minas activas.

#### Scenario: Exclusión de minas inactivas
- **WHEN** existen minas de Chile marcadas como inactivas en la base de datos
- **THEN** el endpoint de listado no incluye dichos registros en la colección retornada
- **AND** solo se muestran minas con el flag de actividad en verdadero

### Requirement: Datos iniciales de minas de Chile
El sistema SHALL poblar automáticamente un conjunto de minas de Chile de ejemplo en la base de datos al iniciar la aplicación, siempre y cuando la tabla correspondiente se encuentre vacía.

#### Scenario: Poblado inicial de minas
- **WHEN** la aplicación se inicia y la tabla de minas de Chile no contiene registros
- **THEN** se insertan una o más minas de Chile predefinidas
- **AND** una posterior petición GET al endpoint de minas retorna al menos esos registros iniciales


# Marca Automóvil Specification

## Purpose
Definir el comportamiento esperado del nuevo recurso REST que expone marcas de automóviles auditadas y la forma en que la base de datos se inicializa con 30 registros activos.

## Requirements

### Requirement: Listar marcas activas ordenadas
The system **MUST** exponer `GET /api/marcas` que retorne un `ApiResponseDTO<List<MarcaAutomovilResponseDTO>>` conteniendo únicamente las marcas con `active=true`, ordenadas alfabéticamente por `nombre`.

#### Scenario: Marcas activas disponibles
- GIVEN la base de datos contiene al menos 30 marcas de automóvil con `active=true` y el servicio `DataInitializer` no ha marcado ninguna como inactiva
- WHEN un consumidor hace `GET /api/marcas`
- THEN la respuesta tiene HTTP 200 y `success=true`
- AND el cuerpo `data` es una lista de 30 objetos con `id`, `nombre`, `paisOrigen`, `descripcion`, `createdAt` y `updatedAt`
- AND la lista está ordenada ascendentemente por `nombre`
- AND ninguna marca inactiva aparece en el resultado

#### Scenario: Sin marcas activas
- GIVEN la tabla de marcas está vacía (por ejemplo, la aplicación acaba de arrancar sin correr el seed aún)
- WHEN se invoca `GET /api/marcas`
- THEN la respuesta tiene HTTP 200, `data` es una lista vacía, y `success=true`
- AND no se lanza ninguna excepción ni mensaje de error

### Requirement: Sembrar 30 nombres conocidos en el arranque
`DataInitializer` **MUST** insertar exactamente 30 registros de marcas de automóvil (`TARGET_SEED_COUNT`) cuando `MarcaAutomovilRepository.count()` es cero, usando la lista predefinida (Toyota, Ford, Chevrolet, Honda, BMW, Mercedes-Benz, Audi, Volkswagen, Nissan, Hyundai, Kia, Subaru, Mazda, Tesla, Renault, Peugeot, Fiat, Citroën, Volvo, Jaguar, Land Rover, Porsche, Lamborghini, Ferrari, Aston Martin, Bentley, Rolls-Royce, Bugatti, Alfa Romeo, Mitsubishi, Suzuki) y marcándolos con `active=true`.

#### Scenario: Seed inicial completo
- GIVEN la tabla `marca_automovil` está vacía y la aplicación se inicia (o `DataInitializer` se ejecuta manualmente)
- WHEN `DataInitializer` recorre el array de marcas y guarda cada entidad
- THEN la tabla contiene exactamente 30 filas con `active=true`
- AND cada fila tiene `nombre` y `paisOrigen`/`descripcion` (si se proporcionan) acordes al listado conocido
- AND la próxima ejecución no duplica registros porque el guard `count() == 0` evita el seed

### Requirement: Inactivación preservada
El sistema **SHOULD** mantener el soft delete: si alguna marca se marca con `active=false`, **SHALL** excluirse de cualquier listado devuelto por `GET /api/marcas`.

#### Scenario: Marcas mezcladas activas e inactivas
- GIVEN la tabla tiene 30 marcas activas y 5 marcas inactivas
- WHEN se llama a `GET /api/marcas`
- THEN la respuesta sigue teniendo exactamente 30 elementos porque las 5 inactivas no se incluyen
- AND el orden sigue siendo alfabético con solo los activos

## Ready for Design
Esta especificación describe el comportamiento esperado; ya se puede avanzar a `/sdd-design add-car-brands` para definir la arquitectura del controlador, servicio y seed.

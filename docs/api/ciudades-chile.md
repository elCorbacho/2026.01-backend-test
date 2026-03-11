# API: Ciudades de Chile

## Endpoints

### GET /api/ciudades-chile

Recupera el listado de ciudades de Chile activas.

Respuesta exitosa:
- `success`: `true`
- `message`: `Ciudades de Chile recuperadas exitosamente`
- `data`: arreglo de objetos con `id`, `nombre`, `poblacion`

### GET /api/ciudades-chile/{id}

Recupera una ciudad de Chile activa por identificador.

Respuesta exitosa:
- `success`: `true`
- `message`: `Ciudad de Chile recuperada exitosamente`
- `data`: objeto con `id`, `nombre`, `poblacion`

Errores esperados:
- `404` con `errorCode=CIUDAD_NOT_FOUND` cuando no existe ciudad activa para el id
- `400` con `errorCode=INVALID_PARAMETER_TYPE` cuando el id no es numerico

## Datos iniciales H2

En entorno local, se cargan 10 ciudades iniciales con poblacion mediante el inicializador:
- `CiudadChileDataInitializer`

## 1. Modelo y repositorio

- [x] 1.1 Crear entidad JPA `MinaChile` en el paquete de modelos con campos (id, nombre, region, mineralPrincipal, estado, createdAt, updatedAt, active) y anotaciones de auditoría y soft delete.
- [x] 1.2 Crear repositorio `MinaChileRepository` extendiendo `JpaRepository` e incluir método `findByActiveTrue()` para recuperar solo minas activas.

## 2. DTOs y mapeo

- [x] 2.1 Crear `MinaChileRequestDTO` para el cuerpo del POST con los campos requeridos (nombre, region, mineralPrincipal, estado).
- [x] 2.2 Crear `MinaChileResponseDTO` para las respuestas, incluyendo los campos de auditoría.
- [x] 2.3 Implementar `MinaChileMapper` como componente en el paquete de mappers para convertir entre entidad y DTOs.

## 3. Servicio y lógica de negocio

- [x] 3.1 Crear servicio `MinaChileService` con métodos `obtenerMinasActivas()` y `crearMina(MinaChileRequestDTO)`, anotando métodos de solo lectura con `@Transactional(readOnly = true)`.
- [x] 3.2 Implementar validaciones básicas en la creación (campos requeridos no nulos/ni vacíos) aprovechando Jakarta Validation en los DTOs.

## 4. Controlador REST y API

- [x] 4.1 Crear `MinaChileController` en `controllers/api` con endpoint GET `/api/minas-chile` que retorne `ResponseEntity<ApiResponseDTO<List<MinaChileResponseDTO>>>`.
- [x] 4.2 Añadir endpoint POST `/api/minas-chile` que reciba `MinaChileRequestDTO` validado y retorne `ResponseEntity<ApiResponseDTO<MinaChileResponseDTO>>` con HTTP 201.
- [x] 4.3 Documentar los endpoints con anotaciones de SpringDoc (`@Tag`, `@Operation`) para que aparezcan en Swagger.

## 5. Datos iniciales y pruebas

- [x] 5.1 Ampliar `DataInitializer` (u otro inicializador de datos) para insertar un conjunto de minas de Chile cuando la tabla esté vacía al inicio de la aplicación.
- [ ] 5.2 Actualizar colecciones de Postman/Bruno (si aplica) para incluir casos de prueba de los endpoints GET y POST de minas de Chile.


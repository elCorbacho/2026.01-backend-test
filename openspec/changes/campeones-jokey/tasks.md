## 1. Modelo y repositorio

- [ ] 1.1 Crear entidad JPA `CampeonJockey` en el paquete de modelos con campos básicos (id, nombreJockey, pais, titulo, anio, createdAt, updatedAt, active) y anotaciones de auditoría y soft delete.
- [ ] 1.2 Crear repositorio `CampeonJockeyRepository` extendiendo `JpaRepository` e incluir método `findByActiveTrue()` para recuperar solo campeones activos.

## 2. DTOs y mapeo

- [ ] 2.1 Crear `CampeonJockeyResponseDTO` en el paquete de DTOs con los campos necesarios para exponer un campeón de jockey en la API.
- [ ] 2.2 Implementar `CampeonJockeyMapper` como componente en el paquete de mappers para convertir entre entidad y DTO.

## 3. Servicio y lógica de negocio

- [ ] 3.1 Crear interfaz `CampeonJockeyService` con método `obtenerCampeonesActivos()`.
- [ ] 3.2 Implementar `CampeonJockeyServiceImpl` usando `@Service`, anotando métodos de solo lectura con `@Transactional(readOnly = true)` y delegando en el repositorio y el mapper.

## 4. Controlador REST y API GET

- [ ] 4.1 Crear `CampeonJockeyController` en `controllers/api` con endpoint GET `/api/campeones-jockey` que retorne `ResponseEntity<ApiResponseDTO<List<CampeonJockeyResponseDTO>>>`.
- [ ] 4.2 Asegurar que el controlador siga las convenciones de nombres de métodos en español y uso de `ApiResponseDTO` y manejo de errores consistente con el resto de la API.

## 5. Datos iniciales y documentación

- [ ] 5.1 Ampliar `DataInitializer` (u otro inicializador de datos) para insertar un conjunto de campeones de jockey cuando la tabla esté vacía al inicio de la aplicación.
- [ ] 5.2 Actualizar la documentación OpenAPI/Swagger (anotaciones en el controlador) y colecciones de Postman/Bruno para incluir el nuevo endpoint de campeones de jockey.


# Tasks: Modelo, controlador y API GET con población inicial para equipos_futball_espana

## Phase 1: Foundation / Infrastructure
- [x] 1.1 Crear `src/main/java/ipss/web2/examen/models/EquipoFutbolEspana.java` con la entidad JPA
- [x] 1.2 Crear `src/main/java/ipss/web2/examen/repositories/EquipoFutbolEspanaRepository.java` con el repositorio Spring Data JPA
- [x] 1.3 Crear `src/main/java/ipss/web2/examen/config/EquipoFutbolEspanaDataInitializer.java` para poblar la tabla con equipos principales

## Phase 2: Core Implementation
- [x] 2.1 Crear `src/main/java/ipss/web2/examen/services/EquipoFutbolEspanaService.java` con la lógica de negocio
- [x] 2.2 Crear `src/main/java/ipss/web2/examen/controllers/api/EquipoFutbolEspanaController.java` con el endpoint GET

## Phase 3: Integration / Wiring
- [ ] 3.1 Verificar integración entre DataInitializer y repositorio
- [ ] 3.2 Verificar que el endpoint GET devuelva equipos activos correctamente

## Phase 4: Testing / Verification
- [ ] 4.1 Escribir pruebas unitarias para el servicio y repositorio
- [ ] 4.2 Escribir pruebas de integración para el controlador GET
- [ ] 4.3 Verificar escenarios del spec: consulta exitosa, sin equipos activos, arranque inicial, arranque con datos existentes, formato de respuesta

## Phase 5: Cleanup / Documentation
- [ ] 5.1 Actualizar documentación y comentarios en los archivos creados
- [ ] 5.2 Validar que no se expongan campos sensibles en la respuesta

---
applyTo: '**'
---

## Workflows de desarrollo

### Comandos principales (Windows)
- Build + tests: `mvnw.cmd clean install`
- Solo tests: `mvnw.cmd test`
- Ejecutar app: `mvnw.cmd spring-boot:run`
- Swagger UI: http://localhost:8080/swagger-ui.html

### Flujo de una feature (en orden)
1. Entidad en `models` (`active`, `createdAt`, `updatedAt`, `@EntityListeners`)
2. Repositorio con métodos derivados (`findByActiveTrue`)
3. DTOs con validación Jakarta (`@NotBlank`, `@Size`, `@Min`)
4. Mapper: `toEntity`, `toResponseDTO`, `updateEntity`
5. Servicio: lógica de dominio, sin devolver entidades JPA
6. Controlador: solo DTOs, siempre `ApiResponseDTO<T>`

### Checklist antes de un PR
- [ ] `@Valid` en todos los `@RequestBody`
- [ ] `@Transactional(readOnly = true)` en lecturas
- [ ] Soft delete implementado (no borrar filas físicamente)
- [ ] `ResourceNotFoundException` en lugar de `null`
- [ ] Sin lógica de negocio en controladores
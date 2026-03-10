## 1. Modelo y Repository

- [ ] 1.1 Crear entidad MarcaBicicleta en models/ con campos: id, nombre, active, y anotaciones JPA/Lombok.
- [ ] 1.2 Crear MarcaBicicletaRepository con método findByActiveTrue().

## 2. DTOs y Mapper

- [ ] 2.1 Crear MarcaBicicletaResponseDTO y MarcaBicicletaSummaryDTO en dtos/.
- [ ] 2.2 Implementar MarcaBicicletaMapper manual (@Component).

## 3. Service y lógica

- [ ] 3.1 Crear MarcaBicicletaService con inyección repository y métodos de negocio (listar marcas activas).

## 4. Controller y API

- [ ] 4.1 Crear MarcaBicicletaController en controllers/api/ con endpoint GET /api/marcas-bicicleta.
- [ ] 4.2 Documentar endpoint en Swagger.

## 5. Seed y DataInitializer

- [ ] 5.1 Agregar seed inicial de marcas de bicicleta en DataInitializer (solo si repo está vacío).

## 6. Testing

- [ ] 6.1 Crear pruebas unitarias y de integración para Modelo, Repository, Service y Controller.

## 7. Validación de convención

- [ ] 7.1 Confirmar soft delete, DTO y envelope ApiResponseDTO.
- [ ] 7.2 Revisar lint y convenciones de nombres.
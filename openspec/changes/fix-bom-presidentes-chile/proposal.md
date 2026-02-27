# Proposal: Fix BOM en archivos PresidenteChile

## Intent

Eliminar el BOM UTF-8 (\ufeff) presente en los archivos nuevos del recurso PresidenteChile para restaurar la compilación y permitir ejecutar tests y verificar cumplimiento de escenarios.

## Scope

### In Scope
- Remover BOM en clases `PresidenteChile*` (modelo, DTO, mapper, repositorio, servicio, controlador) y en `PresidenteChileDataInitializer`.
- Validar que los archivos queden en UTF-8 sin BOM.
- Re-ejecutar `mvn test` para confirmar compilación y ejecución de pruebas.

### Out of Scope
- Cambios funcionales en la lógica del recurso PresidenteChile.
- Ajustes de data seeding o respuesta API.
- Refactorización de pruebas existentes.

## Approach

Normalizar la codificación de los archivos afectados (re-escribir sin BOM) y ejecutar la suite de pruebas con `mvn test` para obtener evidencia de ejecución correcta.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/java/ipss/web2/examen/models/PresidenteChile.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/repositories/PresidenteChileRepository.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/services/PresidenteChileService.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/controllers/api/PresidenteChileController.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/dtos/PresidenteChileResponseDTO.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/mappers/PresidenteChileMapper.java` | Modified | Remover BOM UTF-8 |
| `src/main/java/ipss/web2/examen/config/PresidenteChileDataInitializer.java` | Modified | Remover BOM UTF-8 |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Persistencia del BOM por editores automáticos | Low | Reescritura con herramientas que no agregan BOM |
| Tests sigan fallando por otros motivos | Medium | Ejecutar `mvn test` y capturar nuevos errores |

## Rollback Plan

Revertir los archivos modificados a su versión anterior (con BOM) si se requiere replicar el estado previo, o restaurarlos desde control de versiones.

## Dependencies

- Disponibilidad de Maven wrapper para ejecutar `mvn test`.

## Success Criteria

- [ ] La compilación no falla por `illegal character: '\ufeff'`.
- [ ] `mvn test` completa con exit code 0.

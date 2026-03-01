# Verificacion de guardrails para `ddl-auto=validate`

Este flujo documenta evidencia deterministica para el modo de validacion de esquema sin depender de una base remota.

## Objetivo

- Confirmar escenario exitoso: todos los indices requeridos de album existen.
- Confirmar escenario de drift: la validacion detecta al menos un indice faltante.

## Comando de verificacion

En Windows:

```bash
.\mvnw.cmd -Dtest=ProductionSafetyGuardrailsTest#schemaGuardrailShouldPassWhenAllRequiredIndexesArePresent,ProductionSafetyGuardrailsTest#schemaGuardrailShouldFailWhenRequiredIndexIsMissing test
```

En Linux/macOS:

```bash
./mvnw -Dtest=ProductionSafetyGuardrailsTest#schemaGuardrailShouldPassWhenAllRequiredIndexesArePresent,ProductionSafetyGuardrailsTest#schemaGuardrailShouldFailWhenRequiredIndexIsMissing test
```

## Resultado esperado

- El test de ruta positiva pasa cuando los tres indices requeridos estan presentes.
- El test de ruta negativa pasa solo si detecta y reporta el indice faltante (simulacion de drift).
- El script de referencia `docs/db/album_indexes.sql` se mantiene como fuente de verdad para preaplicar indices antes del arranque en modo `validate`.

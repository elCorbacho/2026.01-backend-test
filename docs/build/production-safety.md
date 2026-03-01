# Verificación de seguridad de empaquetado en producción

Este procedimiento confirma que `spring-boot-devtools` no queda incluido en el jar de producción.

## 1) Construir artefacto

En Windows:

```bash
.\mvnw.cmd clean package
```

En Linux/macOS:

```bash
./mvnw clean package
```

## 2) Verificar contenido del jar

En Windows (PowerShell):

```powershell
jar tf target\*.jar | Select-String "devtools"
```

En Linux/macOS:

```bash
jar tf target/*.jar | grep devtools
```

## Resultado esperado

- La salida debe quedar vacía.
- Si aparece cualquier clase de `org/springframework/boot/devtools`, el empaquetado no es seguro para producción.

## Verificación automatizada (incluye ruta negativa)

Para validar de forma determinística el guardrail de empaquetado (safe + unsafe):

En Windows:

```bash
.\mvnw.cmd -Dtest=ProductionSafetyGuardrailsTest#packagingCheckShouldPassWhenDevtoolsIsAbsent,ProductionSafetyGuardrailsTest#packagingCheckShouldFailWhenDevtoolsIsPresent test
```

En Linux/macOS:

```bash
./mvnw -Dtest=ProductionSafetyGuardrailsTest#packagingCheckShouldPassWhenDevtoolsIsAbsent,ProductionSafetyGuardrailsTest#packagingCheckShouldFailWhenDevtoolsIsPresent test
```

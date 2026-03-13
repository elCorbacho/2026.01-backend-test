## Context

La carga nativa de datos semilla del proyecto incluye multiples entidades inicializadas por componentes de configuracion y scripts, pero sin una norma transversal de cardinalidad ni control de calidad uniforme. Esto provoca que algunas entidades tengan menos o mas registros de los esperados y que existan inconsistencias en campos obligatorios, formatos o relaciones referenciales.

El cambio requiere un diseno que alinee todos los elementos semilla bajo un contrato comun de calidad de datos, con foco en predictibilidad para desarrollo, pruebas funcionales y verificacion continua.

## Goals / Non-Goals

**Goals:**
- Establecer una regla de cardinalidad uniforme: cada entidad objetivo de poblacion nativa debe mantener exactamente 10 registros activos.
- Definir criterios de validacion de construccion de datos semilla (completitud, consistencia de formato y coherencia referencial).
- Estructurar un flujo de auditoria y correccion para detectar faltantes/excesos y completar o ajustar registros.
- Dejar trazabilidad de hallazgos y correcciones propuestas por entidad para facilitar mantenimiento.

**Non-Goals:**
- Cambiar reglas de negocio de los endpoints existentes.
- Redisenar el modelo de dominio o relaciones de entidades fuera del alcance de poblacion semilla.
- Introducir nuevas capacidades funcionales de producto para usuarios finales.

## Decisions

1. Definir alcance explicito de entidades semilla administradas por la estandarizacion.
- Rationale: Evita ambiguedad sobre que elementos deben cumplir la regla de 10 registros.
- Alternativa considerada: aplicar regla a toda tabla activa del esquema; se descarta por incluir tablas transaccionales no consideradas datos semilla.

2. Separar validacion en dos niveles: cardinalidad y calidad de construccion.
- Rationale: Permite detectar de forma precisa si el problema es cantidad o calidad del dato.
- Alternativa considerada: validacion unica agregada; se descarta porque oculta la causa raiz y dificulta remediacion.

3. Priorizar correccion por estrategia de completitud minima con preservacion de integridad.
- Rationale: Primero se garantiza la existencia de 10 registros validos por entidad; luego se atienden ajustes de consistencia fina sin romper referencias.
- Alternativa considerada: rehacer totalmente las semillas por entidad; se descarta por mayor riesgo de regresion funcional.

4. Incorporar criterio de idempotencia para la poblacion estandarizada.
- Rationale: Ejecutar multiples veces el proceso no debe duplicar ni degradar datos.
- Alternativa considerada: ejecucion de una sola vez; se descarta por baja robustez en entornos de prueba y reinicializacion.

## Risks / Trade-offs

- [Cobertura incompleta de entidades objetivo] -> Mitigacion: catalogo de entidades semilla bajo alcance y validacion explicita de cobertura antes de aplicar.
- [Correcciones que oculten problemas de origen en semillas] -> Mitigacion: registrar hallazgos por entidad con causa y accion propuesta.
- [Ajustes de cardinalidad que afecten relaciones existentes] -> Mitigacion: validar integridad referencial y reglas de unicidad antes de confirmar correcciones.
- [Incremento de esfuerzo de mantenimiento de semillas] -> Mitigacion: usar reglas de validacion reutilizables y criterios uniformes documentados.

## Migration Plan

1. Inventariar entidades incluidas en la poblacion nativa y definir baseline de cardinalidad/calidad.
2. Ejecutar auditoria de cardinalidad para detectar entidades con menos o mas de 10 registros activos.
3. Ejecutar auditoria de calidad de construccion en los 10 registros objetivo por entidad.
4. Definir y aplicar plan de remediacion (completar faltantes, depurar excesos, corregir inconsistencias).
5. Verificar cumplimiento final del estandar en todas las entidades objetivo.
6. Mantener una salida de validacion para futuras ejecuciones y control de regresiones.

Rollback strategy:
- Revertir a la fuente previa de semillas para las entidades afectadas y restaurar estado anterior cuando una remediacion comprometa integridad o comportamiento esperado.

## Open Questions

- Confirmar listado final de entidades consideradas como “elementos” dentro de la poblacion nativa bajo este cambio.
- Definir umbral de tolerancia para campos opcionales con datos vacios sin afectar la consideracion de “registro bien construido”.
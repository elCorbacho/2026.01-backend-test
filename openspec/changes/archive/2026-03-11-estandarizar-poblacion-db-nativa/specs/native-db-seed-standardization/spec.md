## ADDED Requirements

### Requirement: Cardinalidad estandar por entidad semilla
El sistema MUST verificar que cada entidad incluida en la poblacion nativa tenga exactamente 10 registros activos y MUST identificar de forma explicita cualquier entidad con faltantes o exceso.

#### Scenario: Entidad con cardinalidad correcta
- **WHEN** se audita una entidad semilla con exactamente 10 registros activos
- **THEN** la entidad se marca como conforme en cardinalidad

#### Scenario: Entidad con faltantes
- **WHEN** se audita una entidad semilla con menos de 10 registros activos
- **THEN** el sistema reporta la cantidad faltante y la entidad queda en estado no conforme

#### Scenario: Entidad con exceso
- **WHEN** se audita una entidad semilla con mas de 10 registros activos
- **THEN** el sistema reporta la cantidad excedente y la entidad queda en estado no conforme

### Requirement: Validacion de construccion de datos semilla
El sistema MUST validar que cada registro objetivo de las entidades semilla este bien construido, incluyendo campos obligatorios completos, formatos coherentes por dominio y consistencia en relaciones referenciales requeridas.

#### Scenario: Registro bien construido
- **WHEN** un registro semilla cumple campos obligatorios, formato y consistencia referencial
- **THEN** el registro se marca como valido para el estandar de calidad

#### Scenario: Registro con datos invalidos
- **WHEN** un registro semilla presenta campos obligatorios vacios, formato invalido o referencia inconsistente
- **THEN** el sistema reporta el tipo de inconsistencia y el registro se marca como no valido

### Requirement: Plan de remediacion por entidad
El sistema MUST generar una propuesta de remediacion por cada entidad no conforme, indicando acciones para poblar faltantes, corregir registros mal construidos y ajustar excesos hasta cumplir 10 registros validos.

#### Scenario: Propuesta para entidad con faltantes y errores de calidad
- **WHEN** una entidad presenta menos de 10 registros y ademas tiene registros no validos
- **THEN** la salida incluye acciones diferenciadas para completar faltantes y corregir calidad

#### Scenario: Propuesta para entidad con exceso
- **WHEN** una entidad presenta mas de 10 registros activos
- **THEN** la salida incluye acciones para normalizar el volumen a 10 sin romper consistencia de datos

### Requirement: Verificacion final de cumplimiento
El sistema MUST emitir una verificacion final que confirme por entidad el cumplimiento de 10 registros activos y validos, junto con el detalle de no conformidades remanentes si existen.

#### Scenario: Cumplimiento total
- **WHEN** todas las entidades objetivo alcanzan 10 registros activos y validos
- **THEN** la verificacion final se marca como cumplimiento completo

#### Scenario: Cumplimiento parcial
- **WHEN** al menos una entidad no alcanza el estandar definido
- **THEN** la verificacion final se marca como cumplimiento parcial con detalle de pendientes
## Inventario y alcance

Entidades objetivo del estandar (10 registros activos y validos):
- transportista
- campeon_jockey
- mina_chile
- listado_olimpiadas
- marca_automovil
- tipo_insecto
- tipo_ave
- poblacion_ave
- presidente_chile
- listado_presidente_rusia
- marca_camion
- marca_bicicleta
- exoplaneta
- equipo_futbol_espana
- ciudad_chile

## Criterios de calidad minima

Por entidad se exige:
- cardinalidad activa exacta igual a 10
- campos obligatorios no vacios o no nulos
- formato basico consistente por dominio (anio, fecha, poblacion y cantidades positivas)
- consistencia referencial para `poblacion_ave.tipo_ave_id`

## Baseline inicial y no conformidades

Baseline detectado previo a remediacion (aprox. segun semillas existentes):
- Conforme desde inicio: `ciudad_chile`, `tipo_insecto`
- Con faltantes (<10): `transportista`, `campeon_jockey`, `mina_chile`, `listado_olimpiadas`, `marca_automovil`, `tipo_ave`, `poblacion_ave`, `presidente_chile`, `listado_presidente_rusia`, `marca_camion`, `marca_bicicleta`, `exoplaneta`, `equipo_futbol_espana`
- Con exceso (>10): no detectado en entidades objetivo durante baseline de este cambio

## Plan de remediacion aplicado

Implementacion central en `NativeSeedStandardizationInitializer`:
- valida y corrige campos obligatorios invalidos
- completa faltantes con registros seed deterministicos hasta llegar a 10 activos
- normaliza excesos desactivando extras por soft delete (mantiene integridad)
- aplica idempotencia: ejecutar nuevamente mantiene 10 activos sin duplicar degradando calidad

## Verificacion final

Cobertura validada con test de integracion:
- `NativeSeedStandardizationIntegrationTest`
- verifica 10 activos por cada entidad objetivo

## Recomendaciones de mantenimiento

- Mantener nuevas semillas bajo el mismo estandar de calidad
- Extender el test de integracion cuando se agreguen nuevas entidades semilla al alcance
- Evitar inserciones manuales fuera del normalizador para no romper cardinalidad esperada

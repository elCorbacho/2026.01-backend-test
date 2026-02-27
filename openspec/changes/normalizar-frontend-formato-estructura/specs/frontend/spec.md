# Frontend Specification

## Purpose

Definir requisitos verificables para normalizar formato, estructura y organización del frontend estático en `src/main/resources/static`, asegurando mantenibilidad y evitando regresiones funcionales o visuales críticas.

## Requirements

### Requirement: Convención de nombres de archivos frontend

El sistema **MUST** aplicar una convención homogénea de nombres en archivos frontend locales usando `kebab-case`, sin espacios ni guiones bajos, para páginas HTML, hojas CSS y scripts JS.

#### Scenario: Renombrado de archivo no conforme

- **GIVEN** un archivo frontend local con nombre no conforme (por ejemplo, con `_`)
- **WHEN** se ejecuta la normalización
- **THEN** el archivo se renombra a `kebab-case`
- **AND** se actualizan las referencias entrantes al nuevo nombre

#### Scenario: Archivo ya conforme

- **GIVEN** un archivo frontend local ya en `kebab-case`
- **WHEN** se ejecuta la normalización
- **THEN** el archivo conserva su nombre original

### Requirement: Estructura estándar de assets estáticos

El sistema **MUST** ubicar los estilos locales en `src/main/resources/static/css/` y los scripts locales en `src/main/resources/static/js/`, evitando mantener CSS o JS de aplicación en la raíz de `static`.

#### Scenario: Reubicación de stylesheet local

- **GIVEN** un archivo CSS local ubicado en la raíz de `static`
- **WHEN** se aplica la normalización estructural
- **THEN** el archivo CSS se mueve a `static/css/`
- **AND** los HTML consumidores se actualizan para apuntar a la nueva ruta

#### Scenario: Recurso local inexistente en ruta esperada

- **GIVEN** un HTML que referencia un recurso local
- **WHEN** la ruta normalizada no contiene ese recurso
- **THEN** la normalización **MUST NOT** eliminar la referencia silenciosamente
- **AND** se registra la inconsistencia para corrección explícita

### Requirement: Normalización de rutas locales en HTML

El sistema **SHALL** usar una única convención de rutas para assets locales dentro de los HTML (absoluta desde `/` o relativa consistente), evitando mezcla dentro del mismo proyecto.

#### Scenario: Detección de rutas mixtas

- **GIVEN** un conjunto de páginas con rutas locales mixtas (`archivo.css` y `/archivo.css`)
- **WHEN** se ejecuta la normalización de rutas
- **THEN** todas las rutas locales convergen a una sola convención definida

#### Scenario: URLs externas no afectadas

- **GIVEN** una página con assets CDN externos (`https://...`)
- **WHEN** se normalizan rutas locales
- **THEN** las URLs externas se mantienen sin cambios

### Requirement: Extracción de CSS y JS inline voluminoso

El sistema **MUST** extraer bloques inline de CSS y JS de tamaño significativo a archivos externos versionables por módulo o página, manteniendo el comportamiento existente.

#### Scenario: Extracción de bloque style inline

- **GIVEN** una página con bloque `<style>` inline extenso
- **WHEN** se ejecuta la normalización
- **THEN** el CSS se mueve a un archivo externo en `static/css/`
- **AND** la página conserva renderizado equivalente

#### Scenario: Script inline dependiente de DOM

- **GIVEN** una página con `<script>` inline que depende del DOM cargado
- **WHEN** se externaliza el script
- **THEN** el orden de carga preserva la ejecución esperada
- **AND** no se generan errores en consola durante carga inicial

### Requirement: Integridad funcional y visual mínima post-normalización

El sistema **MUST** mantener la navegación, carga de recursos y comportamiento interactivo principal de las páginas priorizadas tras la reorganización.

#### Scenario: Validación de páginas críticas

- **GIVEN** las páginas priorizadas (`index.html`, `canciones.html`, `db-estructura.html`, `openspec-y-skills-teams.html`)
- **WHEN** se verifica la versión normalizada
- **THEN** cada página carga sin errores de recurso 404 locales
- **AND** los componentes interactivos principales responden como antes

#### Scenario: Regresión detectada

- **GIVEN** una página con regresión funcional o visual crítica tras normalización
- **WHEN** se ejecuta la validación de salida
- **THEN** la entrega se bloquea hasta corregir la regresión
- **AND** se documenta el ajuste aplicado

### Requirement: Documentación de convención frontend

El sistema **SHOULD** incluir una guía breve de convenciones de formato y estructura frontend para prevenir reincidencia de deuda técnica.

#### Scenario: Proyecto con nueva convención aplicada

- **GIVEN** que se completó la normalización inicial
- **WHEN** un colaborador agrega una nueva página o asset
- **THEN** dispone de una guía con convención de nombres, rutas y organización

#### Scenario: Evolución de estructura

- **GIVEN** que cambia la organización de assets en el futuro
- **WHEN** se actualiza la estructura oficial
- **THEN** la guía de convenciones se actualiza en el mismo cambio

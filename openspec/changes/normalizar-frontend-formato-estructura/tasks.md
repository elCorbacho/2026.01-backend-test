# Tasks: Normalizar formato, estructura y organización del frontend estático

## Phase 1: Foundation / Baseline de normalización

- [x] 1.1 Crear `src/main/resources/static/js/` para centralizar scripts locales de frontend.
- [x] 1.2 Inventariar en `docs/frontend-normalization-inventory.md` los HTML de `src/main/resources/static` con sus referencias CSS/JS locales y bloques inline detectados.
- [x] 1.3 Definir convención oficial en `docs/frontend-conventions.md` (kebab-case, rutas `/css/...` y `/js/...`, reglas anti-inline).
- [x] 1.4 Crear script de auditoría `scripts/frontend-audit.ps1` que reporte: nombres no conformes, CSS/JS fuera de carpeta estándar, rutas locales mixtas y cantidad de `<style>/<script inline>` por archivo.
- [x] 1.5 Ejecutar `scripts/frontend-audit.ps1` y guardar salida baseline en `docs/frontend-normalization-baseline.md`.

## Phase 2: Reestructura de assets y naming

- [x] 2.1 Mover `src/main/resources/static/pedidos.css` a `src/main/resources/static/css/pedidos.css`.
- [x] 2.2 Mover `src/main/resources/static/pedidos.js` a `src/main/resources/static/js/pedidos.js`.
- [x] 2.3 Actualizar `src/main/resources/static/pedidos.html` para referenciar `/css/pedidos.css` y `/js/pedidos.js`.
- [x] 2.4 Actualizar `src/main/resources/static/frontend-ui-ux-expert.html` para referenciar `/css/pedidos.css` con convención absoluta.
- [x] 2.5 Renombrar `src/main/resources/static/html_prueba.html` a `src/main/resources/static/html-prueba.html` (kebab-case).
- [x] 2.6 Buscar y actualizar referencias a `html_prueba.html` en `src/main/resources/static/*.html` y `readme.md` si aplica.

## Phase 3: Externalización de inline en páginas críticas

- [x] 3.1 Crear `src/main/resources/static/css/index.css` extrayendo el bloque `<style>` de `src/main/resources/static/index.html`.
- [x] 3.2 Crear `src/main/resources/static/js/index.js` extrayendo el bloque `<script inline>` de `src/main/resources/static/index.html`.
- [x] 3.3 Modificar `src/main/resources/static/index.html` para cargar `/css/index.css` y `/js/index.js`, eliminando `onclick` inline en `#backTop`.
- [x] 3.4 Crear `src/main/resources/static/css/canciones.css` y `src/main/resources/static/js/canciones.js` extrayendo inline de `src/main/resources/static/canciones.html`.
- [x] 3.5 Modificar `src/main/resources/static/canciones.html` para usar rutas absolutas y remover bloques inline extraídos.
- [x] 3.6 Crear `src/main/resources/static/css/db-estructura.css` y `src/main/resources/static/js/db-estructura.js` extrayendo inline de `src/main/resources/static/db-estructura.html`.
- [x] 3.7 Modificar `src/main/resources/static/db-estructura.html` para usar rutas absolutas y remover bloques inline extraídos.
- [x] 3.8 Crear `src/main/resources/static/css/openspec-y-skills-teams.css` y `src/main/resources/static/js/openspec-y-skills-teams.js` extrayendo inline de `src/main/resources/static/openspec-y-skills-teams.html`.
- [x] 3.9 Modificar `src/main/resources/static/openspec-y-skills-teams.html` para usar rutas absolutas y remover bloques inline extraídos.

## Phase 4: Verificación contra spec (funcional, rutas y regresión)

- [x] 4.1 Ejecutar `scripts/frontend-audit.ps1` y confirmar que no queden nombres con `_` ni CSS/JS locales fuera de `static/css` o `static/js` (Req: Convención + Estructura estándar).
- [x] 4.2 Validar que no existan rutas locales mixtas (relativa/absoluta) en `src/main/resources/static/*.html` (Req: Normalización de rutas).
- [ ] 4.3 Verificar manualmente en navegador `index.html`, `canciones.html`, `db-estructura.html`, `openspec-y-skills-teams.html` sin 404 de assets locales en DevTools (Scenario: Validación de páginas críticas).
- [ ] 4.4 Validar smoke funcional de interacciones principales en las páginas críticas (navegación, toggles, botones y scripts) para detectar regresión crítica.
- [x] 4.5 Registrar resultados de validación en `openspec/changes/normalizar-frontend-formato-estructura/verify-report.md` con evidencias por página.

## Phase 5: Cierre y documentación

- [x] 5.1 Completar `docs/frontend-conventions.md` con ejemplos “correcto/incorrecto” para nombres, rutas y uso de inline.
- [x] 5.2 Añadir sección en `readme.md` enlazando `docs/frontend-conventions.md` y reglas de contribución frontend.
- [x] 5.3 Ejecutar una pasada final de consistencia (links rotos, assets huérfanos) y documentar cleanup en `verify-report.md`.
- [x] 5.4 Preparar commit final del cambio con resumen de archivos creados/modificados/movidos y plan de rollback por lote.


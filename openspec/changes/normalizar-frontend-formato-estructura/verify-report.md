# Verify Report — normalizar-frontend-formato-estructura

## Fecha
2026-02-26

## Resumen
Se ejecutó la implementación técnica de normalización frontend en los lotes de foundation, reestructura y externalización de páginas críticas.

## Verificaciones automatizadas

### 1) Naming y estructura de assets
Ejecución:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File scripts/frontend-audit.ps1
```

Resultado:
- Nombres inválidos con `_`: **None**
- CSS fuera de `static/css`: **None**
- JS fuera de `static/js`: **None**

Evidencia: `docs/frontend-normalization-baseline.md`

### 2) Rutas locales
Resultado del audit:
- Archivos con mezcla de rutas relativas/absolutas locales: **None**

### 3) Externalización de páginas críticas
Verificado por archivo:
- `index.html` → `InlineStyleTags=0`, `InlineScriptTags=0`, usa `/css/index.css` y `/js/index.js`
- `canciones.html` → `InlineStyleTags=0`, `InlineScriptTags=0`, usa `/css/canciones.css` y `/js/canciones.js`
- `db-estructura.html` → `InlineStyleTags=0`, `InlineScriptTags=0`, usa `/css/db-estructura.css` y `/js/db-estructura.js`
- `openspec-y-skills-teams.html` → `InlineStyleTags=0`, `InlineScriptTags=0`, usa `/css/openspec-y-skills-teams.css` y `/js/openspec-y-skills-teams.js`

## Verificaciones manuales pendientes

- [ ] Abrir `index.html` y validar ausencia de 404 en assets locales desde DevTools.
- [ ] Abrir `canciones.html` y validar ausencia de 404 en assets locales desde DevTools.
- [ ] Abrir `db-estructura.html` y validar ausencia de 404 en assets locales desde DevTools.
- [ ] Abrir `openspec-y-skills-teams.html` y validar ausencia de 404 en assets locales desde DevTools.
- [ ] Ejecutar smoke funcional visual (navegación, botones, toggles, animaciones, scripts).

## Consistencia y cleanup

- Se creó `docs/frontend-conventions.md` con reglas y ejemplos.
- Se creó `docs/frontend-normalization-inventory.md`.
- Se agregó referencia de convenciones en `readme.md`.
- Se actualizó naming de `html_prueba.html` a `html-prueba.html`.

## Plan de commit/rollback por lote

### Commit recomendado A — Foundation + reestructura
- `scripts/frontend-audit.ps1`
- `docs/frontend-normalization-baseline.md`
- `docs/frontend-normalization-inventory.md`
- `docs/frontend-conventions.md`
- move de `pedidos.css/js`
- ajustes de rutas en `pedidos.html` y `frontend-ui-ux-expert.html`
- rename `html-prueba.html`

### Commit recomendado B — Externalización páginas críticas
- `index.html` + `css/index.css` + `js/index.js`
- `canciones.html` + `css/canciones.css` + `js/canciones.js`
- `db-estructura.html` + `css/db-estructura.css` + `js/db-estructura.js`
- `openspec-y-skills-teams.html` + `css/openspec-y-skills-teams.css` + `js/openspec-y-skills-teams.js`

### Rollback
- Revertir commit B si aparecen regresiones visuales/JS en páginas críticas.
- Revertir commit A si falla resolución de rutas locales o naming.

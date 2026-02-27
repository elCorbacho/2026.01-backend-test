# Inventario de normalización frontend

## Alcance auditado

Ruta: `src/main/resources/static`

## Páginas HTML detectadas

- `agentes.html`
- `canciones.html`
- `db-estructura-detallada.html`
- `db-estructura.html`
- `docker-como-funciona.html`
- `engram.html`
- `frontend-ui-ux-expert.html`
- `html-prueba.html`
- `index.html`
- `openspec-y-skills-teams.html`
- `overview.html`
- `pedidos.html`
- `sdd-como-funciona.html`
- `setup.html`

## Dependencias locales identificadas (post-normalización)

| Página | CSS local | JS local | Inline CSS | Inline JS |
|---|---|---|---:|---:|
| `index.html` | `/css/index.css` | `/js/index.js` | 0 | 0 |
| `canciones.html` | `/css/canciones.css` | `/js/canciones.js` | 0 | 0 |
| `db-estructura.html` | `/css/db-estructura.css` | `/js/db-estructura.js` | 0 | 0 |
| `openspec-y-skills-teams.html` | `/css/openspec-y-skills-teams.css` | `/js/openspec-y-skills-teams.js` | 0 | 0 |
| `pedidos.html` | `/css/pedidos.css` | `/js/pedidos.js` | 0 | 0 |
| `frontend-ui-ux-expert.html` | `/css/pedidos.css` | — | 1 | 0 |

> Nota: Otras páginas mantienen `<style>` inline por no estar en el lote de externalización priorizado.

## Cambios de estructura aplicados

- `pedidos.css` movido a `src/main/resources/static/css/pedidos.css`
- `pedidos.js` movido a `src/main/resources/static/js/pedidos.js`
- `html_prueba.html` renombrado a `html-prueba.html`
- Se creó `src/main/resources/static/js/` para centralizar scripts locales

## Evidencia

- Baseline actual: `docs/frontend-normalization-baseline.md`
- Script de auditoría: `scripts/frontend-audit.ps1`

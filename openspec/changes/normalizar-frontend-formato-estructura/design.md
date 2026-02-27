# Design: Normalizar formato, estructura y organización del frontend estático

## Technical Approach

Implementar una normalización incremental sobre `src/main/resources/static` sin cambios funcionales backend:
1. Estandarizar nombres de archivos frontend a `kebab-case`.
2. Centralizar assets locales en `static/css/` y `static/js/`.
3. Externalizar CSS/JS inline de páginas priorizadas y ajustar referencias.
4. Validar integridad funcional/visual mínima por página crítica.

Este diseño implementa directamente los requisitos del spec `frontend/spec.md` del cambio `normalizar-frontend-formato-estructura`.

## Architecture Decisions

### Decision: Convención única de rutas locales absolutas

**Choice**: Usar rutas locales absolutas desde raíz (`/css/...`, `/js/...`) en todos los HTML.
**Alternatives considered**: Rutas relativas por archivo (`css/...`, `../css/...`).
**Rationale**: Con Spring Boot static resources, rutas absolutas reducen errores al mover HTML y simplifican refactors masivos.

### Decision: Normalización incremental por lotes de páginas

**Choice**: Migrar primero páginas críticas (`index.html`, `canciones.html`, `db-estructura.html`, `openspec-y-skills-teams.html`) y luego el resto.
**Alternatives considered**: Big-bang sobre todos los HTML en un solo commit.
**Rationale**: Minimiza riesgo de regresiones y permite rollback acotado por lote.

### Decision: Mantener HTML en `static/` (sin mover a `static/pages/` por ahora)

**Choice**: No introducir subcarpeta `pages/` en esta iteración.
**Alternatives considered**: Reubicar todos los HTML a `static/pages/`.
**Rationale**: Reducir impacto en enlaces existentes y enfocar el cambio en normalización de assets y formato.

### Decision: Externalizar inline por archivo dedicado por página

**Choice**: Crear un CSS y JS propio por página priorizada (ej. `css/index.css`, `js/index.js`).
**Alternatives considered**: Un único CSS/JS monolítico compartido.
**Rationale**: Trazabilidad simple durante migración y menor acoplamiento inicial; permite modularización posterior.

## Data Flow

Flujo de resolución de recursos tras normalización:

    Browser
      │  GET /index.html
      ▼
    Spring Boot static handler
      │  sirve HTML desde src/main/resources/static
      ├──────────────► GET /css/index.css
      ├──────────────► GET /js/index.js
      └──────────────► GET CDN (bootstrap/fonts/icons)

Flujo de ejecución JS (página priorizada):

    HTML renderizado
      │
      ├─ carga CSS local + CDN
      ├─ carga JS local (defer o final del body)
      └─ inicializa listeners DOM/acciones UI (sin cambiar endpoints backend)

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modify | Remover bloques inline grandes, actualizar referencias a `/css/index.css` y `/js/index.js`, eliminar `onclick` inline. |
| `src/main/resources/static/canciones.html` | Modify | Externalizar CSS/JS inline y normalizar rutas locales. |
| `src/main/resources/static/db-estructura.html` | Modify | Externalizar CSS/JS inline y normalizar rutas locales. |
| `src/main/resources/static/openspec-y-skills-teams.html` | Modify | Externalizar CSS/JS inline y normalizar rutas locales. |
| `src/main/resources/static/frontend-ui-ux-expert.html` | Modify | Unificar referencia local de stylesheet a convención absoluta. |
| `src/main/resources/static/pedidos.html` | Modify | Ajustar referencias a `/css/pedidos.css` y `/js/pedidos.js`. |
| `src/main/resources/static/pedidos.css` | Move | Reubicar a `src/main/resources/static/css/pedidos.css`. |
| `src/main/resources/static/pedidos.js` | Move | Reubicar a `src/main/resources/static/js/pedidos.js`. |
| `src/main/resources/static/html_prueba.html` | Move/Rename | Renombrar a `html-prueba.html` y actualizar referencias si existen. |
| `src/main/resources/static/css/index.css` | Create | CSS externalizado de `index.html`. |
| `src/main/resources/static/js/index.js` | Create | JS externalizado de `index.html`. |
| `src/main/resources/static/css/canciones.css` | Create | CSS externalizado de `canciones.html`. |
| `src/main/resources/static/js/canciones.js` | Create | JS externalizado de `canciones.html`. |
| `src/main/resources/static/css/db-estructura.css` | Create | CSS externalizado de `db-estructura.html`. |
| `src/main/resources/static/js/db-estructura.js` | Create | JS externalizado de `db-estructura.html`. |
| `src/main/resources/static/css/openspec-y-skills-teams.css` | Create | CSS externalizado de `openspec-y-skills-teams.html`. |
| `src/main/resources/static/js/openspec-y-skills-teams.js` | Create | JS externalizado de `openspec-y-skills-teams.html`. |
| `docs/frontend-conventions.md` | Create | Convenciones de naming, rutas, estructura y reglas anti-inline. |

## Interfaces / Contracts

No hay cambios de contratos backend/API. Se define contrato interno de frontend estático:

```text
Naming:
- HTML/CSS/JS locales en kebab-case

Rutas locales:
- CSS: /css/<archivo>.css
- JS:  /js/<archivo>.js

Restricciones:
- No usar bloques inline grandes (<style>/<script>) en páginas nuevas
- Evitar atributos inline (style=, onclick=, oninput=, etc.)
```

Contrato de validación automática mínima (script de chequeo):

```text
- Debe detectar archivos con '_' en nombres frontend
- Debe detectar CSS/JS locales fuera de /css y /js
- Debe detectar uso de rutas locales mixtas (relativa + absoluta)
- Debe reportar <style>/<script inline> por archivo
```

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Static checks | Convención de nombres, ubicación de assets y rutas | Script PowerShell/grep en CI local con reglas de normalización. |
| Manual integration | Carga de páginas críticas y recursos locales | Abrir `index.html`, `canciones.html`, `db-estructura.html`, `openspec-y-skills-teams.html` y verificar ausencia de 404 en DevTools. |
| Functional smoke | Interacciones principales sin regresión | Validar navegación, toggles, botones, animaciones y llamadas `fetch` existentes. |
| Visual regression light | Cambios de presentación críticos | Comparación antes/después por captura de pantallas en secciones principales. |

## Migration / Rollout

Sin migración de datos.
Rollout por fases:
1. Crear carpetas/archivos destino y mover `pedidos.css/js`.
2. Externalizar páginas priorizadas una por una.
3. Normalizar nombres pendientes (ej. `html_prueba.html`).
4. Aplicar checklist de validación y merge.

Rollback: revertir commit del lote afectado y restaurar rutas previas.

## Open Questions

- [ ] ¿Se desea bloquear completamente `style=` y `on*=` inline en todas las páginas existentes o solo en páginas nuevas/modificadas?
- [ ] ¿La guía de convenciones debe exigirse vía CI (fallo de build) o solo como control manual inicial?

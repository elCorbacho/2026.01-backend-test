# Convenciones frontend (static)

## 1) Naming

Regla obligatoria para archivos locales frontend (`.html`, `.css`, `.js`):

- Usar `kebab-case`
- Sin espacios
- Sin guion bajo `_`

✅ Correcto:
- `html-prueba.html`
- `openspec-y-skills-teams.css`
- `db-estructura.js`

❌ Incorrecto:
- `html_prueba.html`
- `DbEstructura.js`
- `mi archivo.css`

## 2) Estructura de carpetas

- HTML: `src/main/resources/static/*.html`
- CSS local: `src/main/resources/static/css/*.css`
- JS local: `src/main/resources/static/js/*.js`

No dejar CSS/JS de aplicación en la raíz de `static`.

## 3) Convención de rutas locales

Usar rutas absolutas desde raíz web:

- CSS: `/css/<archivo>.css`
- JS: `/js/<archivo>.js`

✅ Correcto:
- `<link rel="stylesheet" href="/css/pedidos.css">`
- `<script src="/js/pedidos.js" defer></script>`

❌ Incorrecto:
- `<link rel="stylesheet" href="pedidos.css">`
- Mezclar `/css/...` y `css/...` en el mismo proyecto

## 4) Reglas anti-inline

Para páginas nuevas o modificadas:

- Evitar bloques `<style>` grandes
- Evitar bloques `<script>` grandes
- Evitar atributos inline (`style=`, `onclick=`, `oninput=`, etc.)

Si se requiere un estilo puntual temporal, documentar el motivo en el PR/cambio.

## 5) Validación recomendada

Ejecutar auditoría:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File scripts/frontend-audit.ps1
```

El reporte debe confirmar:
- sin nombres inválidos
- sin CSS/JS fuera de carpetas estándar
- sin mezcla de rutas locales relativa/absoluta

## 6) Alcance actual de externalización

Externalizado en este cambio:
- `index.html` → `/css/index.css`, `/js/index.js`
- `canciones.html` → `/css/canciones.css`, `/js/canciones.js`
- `db-estructura.html` → `/css/db-estructura.css`, `/js/db-estructura.js`
- `openspec-y-skills-teams.html` → `/css/openspec-y-skills-teams.css`, `/js/openspec-y-skills-teams.js`

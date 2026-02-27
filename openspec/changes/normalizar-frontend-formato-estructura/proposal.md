# Proposal: Normalizar formato, estructura y organización del frontend estático

## Intent

Actualmente el frontend en `src/main/resources/static` funciona, pero presenta deuda técnica de formato y normalización (assets mezclados, mucho CSS/JS inline y convenciones de nombres inconsistentes). Esta propuesta busca estandarizar estructura y estilo para mejorar mantenibilidad, legibilidad y escalabilidad.

## Scope

### In Scope
- Definir y aplicar convención de nombres para archivos frontend (kebab-case consistente).
- Reorganizar assets en carpetas normalizadas (`static/css`, `static/js`, `static/pages` opcional) y actualizar referencias.
- Extraer CSS y JS inline repetitivo/voluminoso a archivos dedicados por dominio o módulo.
- Unificar criterio de rutas de assets (relativas o absolutas) en todos los HTML.
- Documentar reglas mínimas de formato y estructura para futuras páginas.

### Out of Scope
- Rediseño visual completo de las páginas.
- Migración a framework SPA (React/Vue/Angular).
- Cambios funcionales en endpoints backend o lógica de negocio.

## Approach

Se aplicará una normalización incremental en tres fases:
1) Auditoría y mapa de dependencias frontend (HTML → CSS/JS/CDN).
2) Reestructura física de archivos y ajuste de rutas sin romper navegación.
3) Extracción de estilos/scripts inline y validación manual de regresión visual básica.

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/static/*.html` | Modified | Estandarización de referencias, reducción de inline style/script y nombres consistentes. |
| `src/main/resources/static/css/` | New/Modified | Centralización de hojas de estilo (hoy existe carpeta vacía). |
| `src/main/resources/static/js/` | New/Modified | Centralización de scripts cliente actualmente inline o en raíz. |
| `docs/` (opcional) | New | Guía corta de convención frontend del proyecto. |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Ruptura de rutas de assets tras mover archivos | Medium | Migración por lotes + checklist de validación por página. |
| Cambios visuales no deseados al extraer CSS inline | Medium | Comparación visual antes/después en páginas críticas. |
| Aumento de esfuerzo por heterogeneidad de páginas | Medium | Priorizar páginas principales (`index.html`, `canciones.html`, `db-estructura.html`). |

## Rollback Plan

Mantener commits atómicos por fase. Si falla una fase, revertir el commit específico y restaurar estructura/rutas previas de los archivos frontend impactados.

## Dependencies

- Definir convención oficial de nombres y rutas (acuerdo de equipo).
- (Opcional) herramienta de formateo/lint para HTML/CSS/JS en CI local.

## Success Criteria

- [ ] Todos los assets CSS están en `static/css` y JS en `static/js`.
- [ ] No existen rutas mixtas para recursos locales (se usa una sola convención).
- [ ] Las páginas principales no contienen bloques grandes de CSS/JS inline.
- [ ] Los nombres de archivos frontend siguen una convención homogénea.
- [ ] Navegación y comportamiento visual se mantienen sin regresiones funcionales.

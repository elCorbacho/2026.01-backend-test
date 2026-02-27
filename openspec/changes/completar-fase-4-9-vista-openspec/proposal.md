# Proposal: Completar Fase 4-9 de Vista OpenSpec y Skills Agent Teams

## Intent

El cambio anterior `agregar-vista-openspec-y-skills-teams` quedó incompleto en la Fase 4 (aproximadamente 75% completo). La vista existe pero carece de:
1. **Tabla comparativa** OpenSpec vs Skills Agent Teams (Purpose, Workflow, Output, Use Cases, Integration)
2. **Sección de integración** con ejemplo práctico de cómo ambas metodologías trabajan juntas
3. **Validación de responsiveness** en breakpoints críticos (mobile, tablet, desktop)
4. **Testing contra especificación** (16 requisitos)
5. **Build y verificación final**

Este cambio completa las Fases 4-9 para entregar una vista web funcional, responsive, y verificada.

## Scope

### In Scope
- **Fase 4**: Implementar tabla comparativa (4.1-4.10) y sección de integración (4.11-4.15)
  - Tabla responsive con 6 filas: Purpose, Workflow, Output, Use Cases, When to Choose, Integration
  - Sección integración con narrativa y ejemplo práctico (ASCII diagram o workflow visual)
- **Fase 5**: Validar responsiveness y tema claro/oscuro (5.1-5.10)
  - Test manual: mobile (375px), tablet (768px), desktop (1920px)
  - Verificar theme toggle y persistencia
  - Validar scroll progress, back-to-top, animaciones scroll-reveal
- **Fase 6**: Testing contra especificación (7.1-7.16, renumeradas como 6.x)
  - Verificar navegación, contenido, diseño visual, accesibilidad
  - Requisitos semánticos HTML (h1, h2, h3, links con texto significativo)
  - Keyboard navigation
- **Fase 8**: Build y deploy local (9.1-9.7, renumeradas como 8.x)
  - Ejecutar `mvn clean install` y verificar BUILD SUCCESS
  - Ejecutar `mvn spring-boot:run` localmente
  - Probar en navegador: http://localhost:8080/openspec-y-skills-teams.html

### Out of Scope
- Fase 7 (Cross-browser exhaustivo en Safari, Firefox mobile, etc.) — puede hacerse en sprint futuro
- Fase 9 (Final verification cosmética) — screenshots, Lighthouse > 90, etc. — puede diferirse
- Cambios en backend, APIs, o base de datos
- Cambios en otras vistas HTML (solo la vista OpenSpec se modifica)

## Approach

**Estrategia Fase 4-6+8 (omitiendo Fase 7 y 9):**

1. **Fase 4 - Tabla Comparativa**:
   - Implementar grid responsive (2 columns desktop, 1 column mobile) usando Bootstrap
   - 6 filas de comparación con styling alternado (`background: var(--surface-2)`)
   - Usar variables CSS para colors consistency (dark/light theme)

2. **Fase 4 - Integración**:
   - Narrativa en cards explaining cómo OpenSpec y Skills Teams se complementan
   - Visual workflow o ASCII diagram mostrando pasos de un ejemplo real
   - Insights sobre sinergias entre ambas metodologías

3. **Fase 5 - Responsiveness**:
   - Test manual en DevTools: 375px, 768px, 1920px
   - Verificar que tabla comparativa sea mobile-friendly (no overflow)
   - Theme toggle claro/oscuro funciona sin issues

4. **Fase 6 - Testing Spec**:
   - Validar contra 16 requisitos en `tasks.md` (7.1-7.16)
   - Verificar accesibilidad: semantic HTML, keyboard nav, contrast

5. **Fase 8 - Build**:
   - `mvn clean install` debe pasar sin errores
   - Servidor Spring Boot inicia sin logs de error
   - Páginas responden HTTP 200

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/static/openspec-y-skills-teams.html` | Modified | Reemplazar placeholders en líneas 532-560 con tabla comparativa e integración |
| `src/main/resources/static/index.html` | No change | Ya tiene link "Metodologías" en navbar desde cambio anterior ✅ |
| Build output | Generated | `target/classes/static/openspec-y-skills-teams.html` se genera automáticamente via Maven |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Tabla comparativa no es responsive en mobile | Medium | Test a 375px durante Fase 5; usar Bootstrap grid col-12/col-md-6 |
| Theme claro/oscuro no funciona en nueva sección | Low | Usar variables CSS `var(--primary)`, `var(--surface-1)`, etc. (ya están definidas) |
| Build falla por cambios en Maven/Spring | Low | Proyecto tiene commits recientes OK; run `mvn clean` si hay issues |
| Contenido de tabla es técnicamente incorrecto | Medium | Revisar propuesta original + validar contra proyecto en uso |
| Requisitos de Fase 6 (testing) descubren issues visuales | Medium | Iteración rápida: fix + retest cada issue |

## Rollback Plan

1. Revertir `src/main/resources/static/openspec-y-skills-teams.html` a último commit exitoso
2. Verificar `git status` — solo ese archivo debe mostrar cambios
3. `git checkout src/main/resources/static/openspec-y-skills-teams.html`
4. Run `mvn clean install` para verificar build sin el cambio
5. Si build anterior fallaba: `git reset --hard HEAD` (destructivo, solo si necesario)

**No es necesario revertir `index.html`** porque el link "Metodologías" ya fue agregado en cambio anterior.

## Dependencies

- Bootstrap 5.3.3 — ya presente en proyecto
- Google Fonts (Inter, JetBrains Mono) — ya cargadas en `index.html`
- Bootstrap Icons — ya presente
- Maven 3.8+ y Java 21 — ya configurados en proyecto

## Success Criteria

- [ ] Tabla comparativa implementada en `openspec-y-skills-teams.html` (6 filas, responsive)
- [ ] Sección integración implementada (narrativa + example, no placeholder)
- [ ] Página responsive: mobile (375px) no overflow, tablet (768px) mixed layout, desktop (1920px) full layout
- [ ] Theme toggle claro/oscuro funciona sin color issues
- [ ] Todos los requisitos de Fase 6 (testing spec) verificados manualmente ✅
- [ ] `mvn clean install` ejecuta sin errores → BUILD SUCCESS
- [ ] `mvn spring-boot:run` inicia servidor local sin logs de error
- [ ] http://localhost:8080/openspec-y-skills-teams.html responde HTTP 200 y page carga correctamente
- [ ] Navbar link "Metodologías" navega a la página sin issues
- [ ] No hay errores en console (F12 DevTools)
- [ ] Git status muestra solo `src/main/resources/static/openspec-y-skills-teams.html` modificado

---

## Related Changes

- **Cambio previo**: `agregar-vista-openspec-y-skills-teams` — Fases 1-3 completadas (foundation, navbar, content sections)
- **Cambio previo**: `integrate-app-structure-html-into-index` — Agregó sección arquitectura en index.html

---

## Notes

- Este cambio completa el scope original pero **omite** Phase 7 (cross-browser exhaustivo) y Phase 9 (cosmética final)
- Duración estimada: **12-15 horas** de trabajo
- Plan original estipulaba 94 tareas; este cambio cubre ~50-60 tareas críticas

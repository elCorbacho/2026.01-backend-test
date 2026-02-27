# Proposal: Agregar vista comparativa de OpenSpec y Skills Agent Teams

## Intent

Documentar y comparar dos metodologías clave del proyecto (Spec-Driven Development con OpenSpec y el sistema de Skills Agent Teams) mediante una vista educativa en el frontend. Esto ayuda a nuevos usuarios a entender cómo funcionan estas herramientas y cuáles son sus diferencias, mejorando la experiencia de onboarding del proyecto.

## Scope

### In Scope
- Crear nueva vista HTML (`openspec-y-skills-teams.html`) con:
  - Sección explicativa de OpenSpec (qué es, cómo funciona, beneficios)
  - Sección explicativa de Skills Agent Teams (qué es, cómo funciona, beneficios)
  - Tabla/comparativa lado a lado de ambas metodologías
  - Ejemplos prácticos de uso en el contexto del proyecto
- Agregar enlace en el navbar del `index.html` que apunte a esta nueva vista
- Respetar completamente el estilo visual, colores y tipografía del `index.html` (Bootstrap 5, design tokens, tema claro/oscuro)

### Out of Scope
- Modificar la lógica backend o endpoints API
- Cambiar el estilo o estructura de otras vistas HTML
- Integración con base de datos
- Sistema de autenticación o permisos para acceder a la vista

## Approach

1. **Análisis visual**: Estudiar el `index.html` para entender el sistema de design tokens, componentes Bootstrap y estructura CSS
2. **Creación de HTML**: Generar `openspec-y-skills-teams.html` con:
   - Header/navbar consistente
   - Secciones de contenido con cards Bootstrap personalizadas
   - Tabla comparativa con filas y estilos consistentes
   - Footer igual al del index
3. **Actualización del navbar**: Agregar link `Metodologías` o similar en el navbar del `index.html` que apunte a `/openspec-y-skills-teams.html`
4. **Testing visual**: Verificar que el tema claro/oscuro funcione correctamente en ambas vistas

## Affected Areas

| Area | Impact | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modified | Agregar enlace en navbar (`#vistas` section con link a nueva vista) |
| `src/main/resources/static/openspec-y-skills-teams.html` | New | Nueva vista con explicación y comparativa de OpenSpec vs Skills Agent Teams |

## Risks

| Risk | Likelihood | Mitigation |
|------|------------|------------|
| Inconsistencia visual con otras vistas | Medium | Copiar exactamente estructura CSS del `index.html`, usar design tokens idénticos |
| Tema oscuro no se sincroniza correctamente | Low | Probar en ambos temas (light/dark) usando `data-bs-theme` |
| Enlace mal posicionado en navbar | Low | Colocar entre "Vistas" y "Recursos" con espaciamiento consistente |

## Rollback Plan

1. Eliminar `src/main/resources/static/openspec-y-skills-teams.html`
2. Revertir cambios en `index.html` (remover el link del navbar)
3. Limpiar también `target/classes/static/openspec-y-skills-teams.html` si existe (generado por Maven)
4. No hay cambios en código Java o configuración, por lo que no hay necesidad de rebuild

## Dependencies

- Bootstrap 5.3.3 (ya presente en el proyecto)
- Google Fonts (Inter, JetBrains Mono) — ya cargadas en `index.html`
- Bootstrap Icons — ya presente en proyecto

## Success Criteria

- [ ] Nueva vista `openspec-y-skills-teams.html` existe en `src/main/resources/static/`
- [ ] Vista contiene secciones claras para OpenSpec, Skills Teams y comparativa
- [ ] Navbar del index tiene nuevo link navegable a la vista
- [ ] Estilo visual es 100% consistente con `index.html` (mismo color scheme, tipografía, bordes, espaciado)
- [ ] Tema oscuro/claro funciona correctamente en la nueva vista
- [ ] Vista es responsive (mobile-friendly) como el index

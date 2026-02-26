## Context

El sistema actual es API-first y no dispone de una vista HTML dedicada para inspeccionar rápidamente la estructura de la base de datos. Aunque el modelo existe en entidades JPA, falta una representación visual centralizada que complemente `index` y facilite revisión funcional/técnica.

## Goals / Non-Goals

**Goals:**
- Publicar una vista web nueva, separada de `index`, con detalle de tablas, columnas, claves y relaciones.
- Mantener la vista alineada con las entidades principales (`Album`, `Lamina`, `LaminaCatalogo`).
- Permitir navegación simple desde `index` hacia la nueva vista.
- No alterar contratos de API ni reglas de negocio existentes.

**Non-Goals:**
- No reemplazar Swagger/OpenAPI.
- No implementar edición de esquema desde la UI.
- No introducir introspección dinámica compleja contra INFORMATION_SCHEMA en esta fase.

## Decisions

1. **Vista estática estructurada basada en metadatos del dominio**
   - **Decisión:** Renderizar una página HTML (template) con la estructura de BD documentada por secciones (tablas, columnas, constraints y relaciones).
   - **Rationale:** Es simple, mantenible y suficiente para el objetivo de documentación rápida.
   - **Alternativas consideradas:**
     - Consulta dinámica a INFORMATION_SCHEMA: más automática pero agrega complejidad y dependencia del entorno de BD.
     - Exponer JSON y construir frontend aparte: mayor esfuerzo y no necesario para una vista complementaria inicial.

2. **Ruta web dedicada en controlador MVC**
   - **Decisión:** Crear endpoint GET web (no API REST) para servir la nueva vista.
   - **Rationale:** Separa claramente contenido de documentación UI de endpoints `/api/**`.
   - **Alternativas consideradas:**
     - Reusar `index` con tabs: aumenta acoplamiento y reduce claridad.

3. **Cobertura inicial de tablas núcleo**
   - **Decisión:** Incluir en la primera versión `album`, `lamina`, `lamina_catalogo` y sus relaciones FK/UNIQUE relevantes.
   - **Rationale:** Cubre el núcleo funcional actual con valor inmediato.
   - **Alternativas consideradas:**
     - Incluir todas las tablas técnicas desde inicio: menor foco y mayor ruido.

## Risks / Trade-offs

- **[Desalineación con cambios futuros de entidades]** → Mitigación: agregar tarea explícita de sincronización al modificar modelo de datos.
- **[Cobertura incompleta de constraints avanzadas]** → Mitigación: priorizar PK/FK/UNIQUE/auditoría en MVP y ampliar iterativamente.
- **[Duplicación de conocimiento (entidad vs vista)]** → Mitigación: documentar fuente de verdad y checklist de actualización en tareas.

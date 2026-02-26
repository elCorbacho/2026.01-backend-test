# README — Agentes, Skills y Memoria (Engram)

Este documento explica cómo están organizados y cómo usar los *agents/skills*, las instrucciones del repositorio y las herramientas de memoria (Engram) en este proyecto.

**Propósito:** centralizar las convenciones, reglas de sesión y ejemplos de uso para agentes automáticos y manuales que interactúan con este repositorio.

---

## Estructura de skills y archivos relevantes

- Carpeta de skills: `.github/skills/` — contiene descripciones y reglas para cada skill disponible.
- Instrucciones del repositorio: `.github/instructions/` — políticas y guías específicas (workflows, Spring Boot, MCP, memoria).
- Scripts y colecciones: `api-collections/` — colecciones Postman/Bruno para pruebas.

Consulta las instrucciones internas antes de cambiar comportamiento de un skill o del agente.

---

## Reglas de sesión y uso de la memoria (Engram)

Seguimos una política estricta para que el historial de trabajo sea recuperable y reproducible:

- Llamar a `mcp_engram_mem_session_start` al comenzar una sesión (proveer `directory`, `id`, `project`).
- Guardar observaciones importantes con `mcp_engram_mem_save` después de cambios significativos (bugfixes, decisiones, configuración).
- Buscar contexto previo con `mcp_engram_mem_search` antes de empezar una tarea que pueda solaparse con trabajo anterior.
- Al finalizar la sesión, llamar a `mcp_engram_mem_session_summary` con un resumen estructurado.
- Después de una compaction o reinicio de contexto, recuperar estado con `mcp_engram_mem_context`.

Ejemplo (pseudocódigo):

```text
# iniciar sesión
mcp_engram_mem_session_start({ directory: "<ruta>", id: "sess-YYYY-MM-DD", project: "2026.01-backend-test" })

# guardar observación importante
mcp_engram_mem_save({ title: "X", type: "decision", content: "**What**: ... **Why**: ... **Where**: ..." })

# al cerrar
mcp_engram_mem_session_summary({ content: "## Goal\n..." })
```

---

## Normas y convenciones para desarrollar con agentes

- Seguir el flujo Controllers → Services → Repositories → Entities — nunca exponer entidades JPA en respuestas.
- Usar DTOs y mappers (`dtos/`, `mappers/`) para transmisión de datos.
- Validaciones con Jakarta Validation y `@Valid` en `@RequestBody`.
- Todas las respuestas deben seguir el `ApiResponseDTO<T>` con `success`, `message`, `data`, `timestamp`.
- Transacciones: `@Transactional(readOnly=true)` en lecturas.
- Soft delete: usar campo `active`, no borrar físicamente.

Estas reglas ya están descritas en `.github/copilot-instructions.md` y en las instrucciones de workflow; revísalas antes de implementar.

---

## Cómo añadir/actualizar un skill

1. Añadir la documentación en `.github/skills/<skill-name>/SKILL.md` con propósito, inputs, outputs y restricciones.
2. Actualizar `.github/instructions/` si la nueva behavior cambia el flujo de trabajo.
3. Probar localmente y registrar observaciones clave con `mcp_engram_mem_save`.

---

## Checklist rápida antes de un PR (útil para agentes humanos)

- [ ] `@Valid` en todos los `@RequestBody` nuevos.
- [ ] No exponer entidades JPA en controladores.
- [ ] Soft delete implementado si se toca persistencia.
- [ ] Añadir `ResourceNotFoundException` en lugar de `null`.
- [ ] Añadir pruebas unitarias relevantes.

---

## Comandos útiles

```bash
# Build + tests (Windows)
.\mvnw.cmd clean install

# Solo tests
.\mvnw.cmd test

# Ejecutar app (Windows)
.\mvnw.cmd spring-boot:run

# Swagger UI
http://localhost:8080/swagger-ui.html
```

---

## Referencias internas

- Workflows y convenciones: [.github/instructions/workflow.instructions.md](.github/instructions/workflow.instructions.md)
- Instrucciones de memoria y uso de Engram: [.github/copilot-instructions.md](.github/copilot-instructions.md)
- Skills: [.github/skills/](.github/skills/)

---

Si quieres, puedo:

- 1) Añadir este archivo al README principal como sección enlazada.
- 2) Generar plantillas de `mcp_engram_mem_save`/`mcp_engram_mem_session_summary` prellenadas para facilitar su uso.

Dime cuál de los dos prefieres o si quieres ajustes en el contenido.

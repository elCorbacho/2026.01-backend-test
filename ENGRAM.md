# Engram — Memoria Persistente para Copilot / Claude

Engram es un servidor MCP (**Model Context Protocol**) que le da memoria persistente al asistente IA entre sesiones. Sin él, cada conversación empieza desde cero. Con él, el asistente recuerda decisiones, bugs arreglados, configuraciones y patrones de sesiones anteriores.

---

## Cómo funciona

```
Tu código                Engram
    │                      │
    │  ← nueva sesión ──→  │ mem_session_start()
    │                      │   registra sesión activa
    │  ← haces cambios ──→ │ mem_save()
    │                      │   guarda observación en BD local
    │  ← preguntas algo ─→ │ mem_search()
    │                      │   recupera contexto previo
    │  ← fin sesión ─────→ │ mem_session_summary()
    │                      │   archiva la sesión con resumen
```

Los datos se guardan en una **base de datos local en tu máquina** (SQLite). No van a ningún servidor externo.

---

## Configuración en este proyecto

**Archivo:** `.vscode/mcp.json`

```jsonc
"engram": {
    "command": "engram",
    "args": ["mcp"]
}
```

El servidor se activa automáticamente cuando VS Code inicia si los MCP tools están habilitados.

### Habilitar / deshabilitar
- `Ctrl+Shift+P` → **MCP: Enable/Disable Servers**
- O desde el panel de Chat → ícono de herramientas → activar `engram`

---

## Herramientas disponibles

| Herramienta | Cuándo se usa | Descripción |
|-------------|---------------|-------------|
| `mem_session_start` | Inicio de conversación | Registra sesión activa con proyecto y directorio |
| `mem_save` | Tras cada cambio importante | Guarda una observación estructurada |
| `mem_search` | Antes de empezar trabajo | Busca contexto de sesiones anteriores |
| `mem_context` | Tras un reset de contexto | Recupera el estado de la sesión actual |
| `mem_session_summary` | Al finalizar la sesión | Archiva la sesión con un resumen |
| `mem_session_end` | Al cerrar la sesión | Marca la sesión como terminada |
| `mem_get_observation` | Debug puntual | Lee una observación por su ID |
| `mem_timeline` | Auditoría | Ver cronología de observaciones |
| `mem_stats` | Estadísticas | Total de sesiones, proyectos, observaciones |
| `mem_delete` | Limpieza | Borra una observación por ID |
| `mem_update` | Corrección | Edita una observación existente |

---

## Formato correcto para guardar

Siempre usar `mem_save` con esta estructura:

```
title:   "Título corto y buscable"
type:    bugfix | decision | pattern | config | discovery
project: "2026.01-backend-test"

content:
  **What**: qué se hizo (1-2 líneas)
  **Why**:  por qué se hizo o qué problema resolvió
  **Where**: archivos/rutas afectados
  **Learned**: edge cases, gotchas, decisiones — omitir si no hay nada relevante
```

### Ejemplos reales de este proyecto

```
title: "Migración MySQL → H2 in-memory"
type: config
What: Reemplazada datasource MySQL RDS por H2 in-memory para dev local
Why: Eliminar dependencia de AWS RDS en desarrollo
Where: application.properties, pom.xml
Learned: `year` es palabra reservada en H2 2.x → necesita globally_quoted_identifiers=true
```

```
title: "Refactor API responses — factory methods"
type: pattern
What: Añadidos métodos estáticos ok(), created(), error() en ApiResponseDTO
Why: 11 endpoints repetían LocalDateTime.now() + builder completo manualmente
Where: dtos/ApiResponseDTO.java, 3 controllers
```

```
title: "findByAlbumId ignora soft delete"
type: bugfix
What: Renombrado a findByAlbumIdAndActiveTrue en LaminaRepository
Why: El método original devolvía láminas con active=false
Where: repositories/LaminaRepository.java, services/LaminaService.java
```

---

## Reglas del proyecto (lo que debe hacer el asistente)

Definidas en `.github/instructions/memory.instructions.md`:

1. **Al inicio de cada conversación** → llamar `mem_session_start`
2. **Después de cada cambio significativo** → llamar `mem_save` sin que el usuario lo pida
3. **Antes de trabajar en algo nuevo** → llamar `mem_search` para no repetir trabajo
4. **Tras un reset de contexto** → llamar `mem_context` para recuperar estado
5. **Al terminar la sesión** → llamar `mem_session_summary` (obligatorio)

> ⚠️ Si el asistente no llama `mem_session_start` al inicio, el guardado automático **no funciona**. Esto ocurre cuando el contexto se carga desde un resumen comprimido en lugar del flujo normal de conversación.

---

## Cómo buscar en la memoria

```
mem_search("H2 migration")          → encuentra configuraciones de BD
mem_search("bug laminas")           → encuentra todos los bugfixes de láminas
mem_search("factory methods")       → patrones de código usados
mem_search(project="2026.01-backend-test")  → todo del proyecto actual
```

---

## Problemas conocidos

| Problema | Causa | Solución |
|----------|-------|----------|
| El asistente no guarda solo | No llamó `mem_session_start` al inicio | Pedir explícitamente: *"guarda esto en engram"* |
| MCP tools deshabilitados | Se desactivaron en VS Code | `Ctrl+Shift+P` → MCP: Enable Servers |
| `mem_search` no encuentra nada | El servidor Engram no está corriendo | Verificar que `engram` aparece en el panel MCP |
| Contexto perdido tras compresión | Conversación muy larga fuerza resumen | Pedir: *"recupera contexto de engram"* |

---

## Ver memorias guardadas

No hay UI oficial, pero puedes:

1. Preguntar al asistente: *"busca en engram todo lo del proyecto"*
2. Preguntar: *"muéstrame las últimas memorias guardadas"*
3. Preguntar: *"dame estadísticas de engram"* (usa `mem_stats`)

---

## Estructura de archivos relacionados

```
.vscode/
└── mcp.json                          ← servidor engram registrado aquí

.github/
├── copilot-instructions.md           ← reglas de memoria para el asistente
└── instructions/
    └── memory.instructions.md        ← instrucciones detalladas de uso
```

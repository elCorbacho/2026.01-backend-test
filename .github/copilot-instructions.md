
# Proyecto: API REST Spring Boot 3.5 — Álbumes y Láminas


## Memory — Engram Persistent Memory


## Memory
You have access to Engram persistent memory via MCP tools (mem_save, mem_search, mem_session_summary, etc.).
- Save proactively after significant work — don't wait to be asked.
- After any compaction or context reset, call `mem_context` to recover session state before continuing.


## Memory
You have access to Engram persistent memory via MCP tools (mem_save, mem_search, mem_session_summary, etc.).
- Save proactively after significant work — don't wait to be asked.
- After any compaction or context reset, call `mem_context` to recover session state before continuing.

You have access to Engram persistent memory (mem_save, mem_search, mem_context).
Save proactively after significant work. After context resets, call mem_context to recover state.



You have access to Engram persistent memory via MCP tools:
mem_save, mem_search, mem_context, mem_session_summary,
mem_timeline, mem_get_observation, mem_stats, mem_session_start, mem_session_end.

### Rules

- Call `mem_session_start` at the beginning of every session.
- Save proactively with `mem_save` after significant work:
  bugfixes, architectural decisions, configurations, patterns discovered.
- Search with `mem_search` before starting work that may overlap previous sessions.
- Call `mem_session_summary` before ending the session. This is mandatory.
- After any context reset, call `mem_context` immediately to recover session state.

### Memory format for mem_save

- title: short descriptive title (e.g. "Fixed N+1 query in UserList")
- type: bugfix | decision | pattern | config | discovery
- content: What / Why / Where / Learned




## Arquitectura
- Controllers → Services → Repositories → Entities
- `dtos` + `mappers`: nunca exponer entidades JPA directamente
- `exceptions`: `GlobalExceptionHandler` centralizado
- Soft delete con campo `active` + auditoría (`createdAt`, `updatedAt`)

## Flujo de una feature (en orden)
1. Entidad en `models` (con `active`, `createdAt`, `updatedAt`)
2. Repositorio con métodos derivados (`findByActiveTrue`)
3. DTOs con validación Jakarta (`@NotBlank`, `@Size`, `@Min`)
4. Mapper con `toEntity`, `toResponseDTO`, `updateEntity`
5. Servicio con lógica de negocio (nunca devuelve entidades)
6. Controlador delgado, solo DTOs, siempre `ApiResponseDTO<T>`

## Reglas clave
- Todas las respuestas: `ApiResponseDTO<T>` con `success`, `message`, `data`, `timestamp`
- `@Valid` en todos los `@RequestBody`
- `@Transactional(readOnly = true)` en lecturas
- Lanzar `ResourceNotFoundException` (nunca retornar `null`)
- `InvalidOperationException` para reglas de negocio incumplidas
- Cero lógica de negocio en controladores

## Qué NO hacer
- No exponer entidades JPA en respuestas
- No acceder a repositorios desde controladores
- No borrar filas físicamente (usar soft delete)
- No romper el contrato de `ApiResponseDTO`

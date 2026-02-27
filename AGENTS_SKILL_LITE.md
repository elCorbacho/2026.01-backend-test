# Agents Skill Lite — Guía de Funcionamiento

## ¿Qué es Agents Skill Lite?

**Agents Skill Lite** es un sistema ligero de automatización que integra agentes especializados (sub-procesos) dentro del flujo de desarrollo de OpenSpec. Permite ejecutar tareas complejas de forma autónoma, delegando trabajo a agentes especializados sin perder control del contexto principal.

En este proyecto, los agentes skill lite automatizan tareas repetitivas del workflow SDD (Spec-Driven Development), permitiendo al usuario enfocarse en decisiones de alto nivel mientras los agentes manejan implementación, verificación y documentación.

---

## Arquitectura

### Componentes Principales

```
User Request
    ↓
Main Agent (Claude Code)
    ├─ Interpreta solicitud
    ├─ Determina qué skill ejecutar
    └─ Delega a Sub-agente especializado
        ↓
    Sub-Agente (Skill Lite)
        ├─ Realiza tarea específica
        ├─ Mantiene contexto del proyecto
        └─ Retorna resultados estructurados
        ↓
Main Agent
    ├─ Procesa resultados
    ├─ Toma decisiones basadas en output
    └─ Comunica al usuario
```

### Tipos de Sub-Agentes

| Agente | Propósito | Ejemplo de Tarea |
|--------|-----------|------------------|
| `sdd-explore` | Exploración y análisis | Investigar mejoras sin cambiar código |
| `sdd-propose` | Crear propuestas de cambio | Generar proposal.md estructurado |
| `sdd-design` | Diseño técnico | Crear design.md con arquitectura |
| `sdd-spec` | Especificaciones | Escribir spec.md con requisitos |
| `sdd-tasks` | Desglose de tareas | Generar tasks.md con checklist |
| `sdd-apply` | Implementación | Ejecutar tareas y escribir código |
| `sdd-verify` | Verificación | Validar que implementación cumple specs |
| `sdd-archive` | Cierre de cambio | Archivar cambio completado |
| `general-purpose` | Tareas complejas | Búsquedas avanzadas, análisis |
| `explore` | Exploración de código | Búsquedas rápidas en codebase |
| `plan` | Planificación | Diseñar estrategia de implementación |

---

## Cómo Funcionan los Agents Skill Lite

### 1. Invocación

**Desde la CLI:**
```bash
/sdd-propose
/sdd-design
/sdd-apply
```

**O con argumentos:**
```bash
/sdd-propose tema: "agregar autenticación"
/sdd-explore identificar problemas de performance
```

### 2. Flujo de Ejecución

**Paso 1: Reconocimiento de Skill**
```
Usuario: "/sdd-propose crear cambio para..."
Sistema: Reconoce que es un skill invocable
         ↓
         Busca en ~/.claude/skills/sdd-propose/
```

**Paso 2: Carga de Contexto**
- Lee `openspec/config.yaml` (configuración del proyecto)
- Lee `openspec/specs/` (especificaciones existentes)
- Lee `.git/config` y estructura del proyecto
- Carga memoria persistente (Engram) si existe

**Paso 3: Ejecución del Agente**
- El sub-agente ejecuta su lógica especializada
- Tiene acceso a herramientas de lectura/escritura
- Mantiene el estado del proyecto
- Comunica progreso parcial

**Paso 4: Retorno de Resultados**
```
Sub-agente completa tarea
    ↓
Retorna: {
  status: "success",
  artifacts: { file_path, content },
  summary: "resumen ejecutivo",
  next_step: "recomendación siguiente"
}
    ↓
Main Agent procesa y comunica al usuario
```

### 3. Persistencia de Artefactos

Los resultados pueden persistirse en tres modos:

| Modo | Ubicación | Uso |
|------|-----------|-----|
| `openspec` | `openspec/changes/{name}/` | Cambios estructurados del proyecto |
| `engram` | Memoria persistente (Engram) | Análisis, decisiones, aprendizajes |
| `none` | Solo retorno (no persiste) | Consultas rápidas, exploraciones |

---

## Workflow Típico con Agents Skill Lite

### Escenario: Implementar Nueva Funcionalidad

```
1. EXPLORE — sdd-explore
   Comando: /sdd-explore investigar requirements
   ├─ Analiza codebase existente
   ├─ Identifica patrones y convenciones
   └─ Propone áreas de mejora
        ↓
2. PROPOSE — sdd-propose
   Comando: /sdd-propose
   ├─ Lee resultado de exploración
   ├─ Crea proposal.md estructurado
   ├─ Define intent, scope, approach, risks
   └─ Guarda en openspec/changes/{name}/
        ↓
3. DESIGN — sdd-design
   Comando: /sdd-design
   ├─ Lee proposal.md
   ├─ Crea design.md con arquitectura
   ├─ Especifica trade-offs y decisiones
   └─ Propone estrategia de implementación
        ↓
4. SPEC — sdd-spec
   Comando: /sdd-spec
   ├─ Lee design.md
   ├─ Crea spec.md detallado
   ├─ Define requisitos y escenarios de test
   └─ Genera criterios de aceptación
        ↓
5. TASKS — sdd-tasks
   Comando: /sdd-tasks
   ├─ Lee spec.md
   ├─ Desglose en tasks.md (checklist accionable)
   ├─ Estima esfuerzo por tarea
   └─ Establece dependencias
        ↓
6. APPLY — sdd-apply
   Comando: /sdd-apply
   ├─ Lee tasks.md
   ├─ Implementa cada tarea
   ├─ Escribe código, crea/modifica archivos
   ├─ Ejecuta pruebas
   └─ Marca tareas como completadas
        ↓
7. VERIFY — sdd-verify
   Comando: /sdd-verify
   ├─ Lee spec.md, tasks.md, código implementado
   ├─ Valida que implementación cumple requisitos
   ├─ Ejecuta tests
   ├─ Verifica criterios de aceptación
   └─ Identifica gaps si los hay
        ↓
8. ARCHIVE — sdd-archive
   Comando: /sdd-archive
   ├─ Sincroniza delta specs a main specs
   ├─ Archiva cambio en openspec/
   ├─ Crea resumen final
   └─ Limpia artefactos temporales
```

---

## Ventajas de Agents Skill Lite

### Para el Usuario

✅ **Automatización Segura**
- Realiza tareas complejas sin intervención constante
- Mantiene control total vía aprobaciones

✅ **Consistencia**
- Sigue patrones y convenciones del proyecto automáticamente
- Documentación estructurada y predecible

✅ **Velocidad**
- Paraleliza tareas independientes
- Reduce ciclos de feedback

✅ **Rastreabilidad**
- Cada cambio está documentado en proposal/design/spec/tasks
- Historial completo en Git y Engram

### Para el Proyecto

✅ **Escalabilidad**
- Crece con el equipo sin perder estructura
- Nuevas features siguen mismo patrón

✅ **Conocimiento Corporativo**
- SDD documentation persiste (Engram + Git)
- Futuro equipo entiende decisiones pasadas

✅ **Calidad**
- Testing integrado en cada fase
- Verificación antes de aceptar cambios

---

## Configuración Local

### Archivos Clave

```
openspec/
├── config.yaml              ← Configuración global del proyecto
├── specs/                   ← Especificaciones principales
│   └── *.spec.md
└── changes/                 ← Cambios en progreso o completados
    └── {change-name}/
        ├── proposal.md      ← Intención y scope
        ├── design.md        ← Arquitectura técnica
        ├── spec.md          ← Requisitos detallados
        ├── tasks.md         ← Checklist de implementación
        └── archive/         ← Cambios archivados
```

### Inicializar SDD en Nuevo Proyecto

```bash
/sdd-init
```

Crea estructura openspec/ completa y config.yaml base.

---

## Limitaciones y Consideraciones

⚠️ **No Reemplaza Criterio Humano**
- Los agentes siguen instrucciones pero no entienden negocio
- Decisiones estratégicas requieren input del usuario

⚠️ **Dependencia de Contexto**
- Si config.yaml o specs están desactualizados, agentes producen output incorrecto
- Mantener documentación actualizada es crítico

⚠️ **Ejecución Secuencial vs Paralela**
- Algunos agentes dependen de output de otros
- El sistema mantiene dependencias automáticamente

⚠️ **Sin Acceso a APIs Externas**
- Los agentes solo trabajan con archivos locales y memoria persistente
- Integraciones externas requieren plugins especializados

---

## Ejemplo Práctico: Completar Vista OpenSpec

```bash
# 1. Explorar estado actual
/sdd-explore qué falta en la vista openspec

# 2. Crear propuesta de completitud
/sdd-propose completar fases 4-9

# 3. Diseñar solución
/sdd-design tabla comparativa y sección integración

# 4. Especificar requisitos
/sdd-spec responsiveness, testing, build

# 5. Desglosar en tareas
/sdd-tasks crear checklist de implementación

# 6. Implementar
/sdd-apply ejecutar todas las tareas

# 7. Verificar
/sdd-verify validar contra spec

# 8. Archivar
/sdd-archive finalizar cambio
```

---

## Memoria Persistente (Engram)

Los agentes guardan automáticamente:

- **Decisiones arquitectónicas** → `mem_save` (architecture)
- **Bugs y fixes** → `mem_save` (bugfix)
- **Patrones descubiertos** → `mem_save` (pattern)
- **Configuraciones** → `mem_save` (config)
- **Aprendizajes** → `mem_save` (learning)

Recuperar memoria:
```bash
# En cualquier momento
/engram:memory
```

---

## Conclusión

**Agents Skill Lite** es la infraestructura que permite que OpenSpec + SDD funcione de forma automatizada y escalable. Los agentes no toman decisiones, sino que *documentan y ejecutan* decisiones que el usuario ha tomado o orientado.

**Clave**: El usuario sigue siendo el "orquestador" — los agentes son herramientas especializadas que aceleran el trabajo sin reemplazar juicio.

---

**Última actualización**: 2026-02-26
**Versión**: 1.0

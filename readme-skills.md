## README — Organización de Skills del Proyecto

Este documento explica **cómo están organizados los skills** de este repositorio y **dónde tocar** cuando quieras crear, extender o depurar un skill.

Se complementa con `readme-agents.md`, que habla más de **cómo usar** agentes y memoria (Engram).

---

## Dónde viven los skills

- **Carpeta raíz de skills**: `.github/skills/`
- Cada subcarpeta directa dentro de `.github/skills/` representa **un skill**:
  - `.github/skills/java-spring-boot/`
  - `.github/skills/springboot-patterns/`
  - `.github/skills/code-reviewer/`
  - `.github/skills/frontend-design/`
  - `.github/skills/frontend-code-review/`
  - `.github/skills/postgresql-table-design/`
  - `.github/skills/openspec-*` (flujo Spec‑Driven Development)
  - `.github/skills/skill-creator/`
  - `.github/skills/find-skills/`
  - `.github/skills/nodejs-core/`, `copilot-sdk/`, etc.

Dentro de cada carpeta de skill hay tres tipos de contenido:

- **`SKILL.md`** (obligatorio): definición del skill (metadatos + guía de uso).
- **`references/`** (opcional): artículos internos, guías y checklists que el skill usa.
- **`scripts/` / `assets/`** (opcional): scripts auxiliares o archivos de configuración/esquemas.

---

## Estructura de un `SKILL.md`

Todos los `SKILL.md` siguen un patrón similar:

- **Cabecera YAML** con metadatos:
  - `name`: nombre interno del skill (`java-spring-boot`, `code-reviewer`, etc.).
  - `description`: qué resuelve este skill.
  - `sasmp_version`, `version`: versiones del formato/skill.
  - `bonded_agent`, `bond_type`: a qué agente principal se asocia.
  - `allowed-tools`: qué herramientas puede usar (por ejemplo: `Read`, `Write`, `Bash`, `Glob`, `Grep`).
  - `parameters` (si aplica): parámetros de entrada validados (tipos, enums, defaults).
- **Cuerpo en Markdown**:
  - Descripción extendida.
  - Cuándo usar el skill.
  - Temas cubiertos / patrones recomendados.
  - Ejemplos de uso, snippets y checklists.
  - Sección de troubleshooting (problemas comunes).

Ejemplo claro de esta estructura: `.github/skills/java-spring-boot/SKILL.md`.

---

## Tipos de skills que tiene este proyecto

- **Backend Java / Spring Boot**
  - `java-spring-boot`: guía completa para construir APIs Spring Boot de producción.
  - `springboot-patterns`: patrones y buenas prácticas específicos de esta arquitectura.
  - `postgresql-table-design`: diseño de tablas y modelado en PostgreSQL.

- **Frontend y UX**
  - `frontend-design`: lineamientos de UI/UX.
  - `frontend-code-review`: checklists y criterios para revisar frontend.

- **Revisión de código y calidad**
  - `code-reviewer` y `critical-code-reviewer`: skills centrados en revisión rigurosa de PRs.
  - `github-issue-creator`: ayuda a crear issues bien definidas.

- **Flujo Spec‑Driven Development (openspec)**
  - `openspec-explore`, `openspec-propose`, `openspec-apply-change`, `openspec-archive-change`:
    orquestan exploración, propuesta, especificación, aplicación y archivo de cambios.

- **Infraestructura de skills**
  - `skill-creator`: plantillas y herramientas para crear nuevos skills.
  - `find-skills`: localizar y sugerir skills existentes según necesidad.
  - `copilot-sdk`, `agents-v2-py`, `nodejs-core`: soporte y conocimientos transversales.

No es necesario conocer todos los skills en detalle; lo importante es saber **dónde buscar** cuando trabajas en una capa concreta (Spring Boot, frontend, DB, revisión de código, etc.).

---

## Cómo localizar rápidamente el skill que te interesa

1. **Identifica el dominio** de lo que estás haciendo:
   - API REST Spring → `java-spring-boot`, `springboot-patterns`.
   - Modelado de tablas → `postgresql-table-design`.
   - Revisión de PR → `code-reviewer`, `critical-code-reviewer`, `frontend-code-review`.
   - Diseño de flujos y cambios grandes → skills `openspec-*`.
2. Entra en `.github/skills/<skill>/SKILL.md` y revisa:
   - Sección *When to use this skill* / *Cuándo usarlo*.
   - Checklists de patrones y anti‑patrones.
   - Ejemplos de código y configuración.

Si quieres que un agente use un skill concreto de forma explícita, utiliza la sintaxis típica del entorno (por ejemplo, selección de skill en la UI o comandos `/skill ...` según el asistente que estés usando).

---

## Cómo añadir o modificar un skill

Cuando necesites **crear o actualizar** un skill:

1. **Crear/editar la carpeta**
   - Nuevo skill: crea `.github/skills/<nombre-skill>/`.
   - Skill existente: edita su `SKILL.md` y, si es necesario, `references/` o `scripts/`.
2. **Definir/actualizar `SKILL.md`**
   - Ajusta los metadatos de la cabecera YAML (`name`, `description`, `parameters`, etc.).
   - Añade secciones claras de *Overview*, *Cuándo usarlo*, *Patrones*, *Ejemplos* y *Troubleshooting*.
3. **Mantener referencias y scripts**
   - Documentos largos → `references/`.
   - Validadores o utilidades → `scripts/` (por ejemplo, validaciones de esquemas).
4. **Sincronizar con las instrucciones generales**
   - Si el nuevo skill cambia el flujo de trabajo, actualiza también:
     - `.github/instructions/`
     - `readme-agents.md` (para que los agentes sepan que existe).

---

## Relación con `readme-agents.md`

- `readme-agents.md` responde: **“cómo deben trabajar los agentes y la memoria (Engram) en este repo”**.
- `readme-skills.md` responde: **“dónde están y cómo se organizan los skills que esos agentes usan”**.

Cuando dudes:

- Para **políticas de sesión, memoria y workflows** → mira `readme-agents.md`.
- Para **profundizar en un dominio técnico concreto** (Spring, frontend, DB, revisión) → mira el `SKILL.md` correspondiente en `.github/skills/`.


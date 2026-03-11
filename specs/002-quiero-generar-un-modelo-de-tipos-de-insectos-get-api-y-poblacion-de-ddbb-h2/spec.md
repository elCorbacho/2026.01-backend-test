# Feature Specification: Catalogo de tipos de insectos

**Feature Branch**: `[002-quiero-generar-un-modelo-de-tipos-de-insectos-get-api-y-poblacion-de-ddbb-h2]`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "quiero generar un modelo de tipos de insectos get api y poblacion de ddbb h2"

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Consultar tipos de insectos (Priority: P1)

Como consumidor de la API, quiero obtener el listado de tipos de insectos para mostrar opciones disponibles en mi sistema.

**Why this priority**: Es el flujo principal de valor; sin consulta no existe utilidad para clientes API.

**Independent Test**: Se valida llamando al endpoint de consulta general y verificando que devuelve una coleccion de tipos de insectos activos con datos completos.

**Acceptance Scenarios**:

1. **Given** que existen tipos de insectos cargados, **When** el consumidor solicita el listado, **Then** recibe una respuesta exitosa con todos los registros activos.
2. **Given** que no existen tipos de insectos activos, **When** el consumidor solicita el listado, **Then** recibe una respuesta exitosa con coleccion vacia.

---

### User Story 2 - Consultar detalle de tipo de insecto (Priority: P2)

Como consumidor de la API, quiero consultar un tipo de insecto especifico para obtener su informacion detallada.

**Why this priority**: Complementa el flujo principal y permite uso puntual de datos por identificador.

**Independent Test**: Se valida llamando al endpoint de detalle con un identificador existente y otro inexistente para confirmar respuestas correctas.

**Acceptance Scenarios**:

1. **Given** que el tipo de insecto existe y esta activo, **When** el consumidor solicita el detalle por identificador, **Then** recibe una respuesta exitosa con los datos del registro.
2. **Given** que el identificador no existe o esta inactivo, **When** el consumidor solicita el detalle, **Then** recibe una respuesta de recurso no encontrado.

---

### User Story 3 - Disponibilidad de datos iniciales (Priority: P3)

Como desarrollador o tester, quiero que el sistema tenga una poblacion inicial de tipos de insectos para validar consultas desde el primer arranque.

**Why this priority**: Reduce tiempo de preparacion en ambientes locales y de prueba.

**Independent Test**: Se valida iniciando el sistema en entorno de prueba limpio y confirmando que existe un conjunto minimo de registros consultables.

**Acceptance Scenarios**:

1. **Given** una base de datos de pruebas vacia, **When** el sistema inicia, **Then** se cargan datos iniciales de tipos de insectos disponibles para consulta.

---

### Edge Cases

- Consulta de detalle con identificador no numerico o fuera de rango esperado.
- Consulta de detalle de registro marcado como inactivo.
- Arranque del sistema con datos iniciales ya existentes para evitar duplicados.
- Listado cuando todos los registros estan inactivos.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema DEBE mantener una entidad de tipo de insecto con identificador unico, nombre, descripcion corta y estado activo/inactivo.
- **FR-002**: El sistema DEBE exponer una operacion de consulta general de tipos de insectos que retorne solo registros activos.
- **FR-003**: El sistema DEBE exponer una operacion de consulta por identificador para recuperar un tipo de insecto activo.
- **FR-004**: El sistema DEBE responder con recurso no encontrado cuando se solicite un identificador inexistente o inactivo.
- **FR-005**: El sistema DEBE entregar respuestas con formato consistente para exito y error en las consultas.
- **FR-006**: El sistema DEBE poblar automaticamente un conjunto inicial de al menos 10 tipos de insectos durante el arranque en entorno de pruebas con base de datos en memoria.
- **FR-007**: El sistema NO DEBE duplicar registros de poblacion inicial si ya existen tipos de insectos cargados.
- **FR-008**: El sistema DEBE validar el identificador de entrada para evitar solicitudes invalidas y comunicar el error de forma clara.

### Key Entities *(include if feature involves data)*

- **Tipo de Insecto**: Representa una categoria de insecto consultable; atributos clave: identificador, nombre, descripcion y estado de actividad.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: El 100% de las consultas generales retorna una respuesta valida en menos de 2 segundos bajo carga normal de pruebas.
- **SC-002**: El 100% de las consultas por identificador existente retorna el registro correcto en el primer intento.
- **SC-003**: El 100% de las consultas por identificador inexistente o inactivo retorna un resultado de no encontrado con mensaje entendible.
- **SC-004**: En un arranque limpio de entorno de pruebas, los datos iniciales quedan disponibles para consulta en menos de 30 segundos.

## Assumptions

- La funcionalidad se limita a consultas y carga inicial de datos; no incluye operaciones de creacion, actualizacion o eliminacion.
- Los consumidores de la API son clientes internos del proyecto.
- El catalogo inicial de tipos de insectos puede ajustarse en contenido sin cambiar el objetivo funcional de la feature.

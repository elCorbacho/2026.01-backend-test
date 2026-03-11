# Feature Specification: Consulta de Ciudades de Chile

**Feature Branch**: `001-ciudades-chile-api`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "nuevo controlador modelo de ciudades de chile con metodo get general y get por id mas poblacion de ddbb h2 nativa con 10 registros"

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Consultar listado de ciudades (Priority: P1)

Como consumidor del sistema, quiero obtener el listado general de ciudades de Chile para mostrar un catálogo base de ciudades disponibles.

**Why this priority**: Sin esta capacidad no existe una vista general utilizable del recurso y no hay valor inicial del endpoint.

**Independent Test**: Puede probarse ejecutando la consulta general del recurso y verificando que retorna una colección con datos de ciudades y población.

**Acceptance Scenarios**:

1. **Given** que existen ciudades cargadas en el sistema, **When** el consumidor solicita el listado general, **Then** recibe una respuesta exitosa con la colección de ciudades.
2. **Given** que se solicita el listado general, **When** el sistema responde, **Then** cada elemento incluye identificador, nombre de ciudad y población.

---

### User Story 2 - Consultar ciudad por identificador (Priority: P2)

Como consumidor del sistema, quiero consultar una ciudad por su identificador para recuperar rápidamente el detalle de una ciudad específica.

**Why this priority**: Es el segundo flujo crítico para consumo de datos puntuales y permite navegación detallada desde el listado.

**Independent Test**: Puede probarse solicitando un identificador válido y uno inexistente, verificando respuesta correcta en ambos casos.

**Acceptance Scenarios**:

1. **Given** que existe una ciudad con un identificador válido, **When** el consumidor la consulta por id, **Then** recibe una respuesta exitosa con el detalle de esa ciudad.
2. **Given** que no existe una ciudad para el id solicitado, **When** el consumidor consulta por id, **Then** recibe una respuesta de recurso no encontrado.

---

### User Story 3 - Disponer de datos iniciales de referencia (Priority: P3)

Como consumidor del sistema en ambientes locales de desarrollo y prueba, quiero que el recurso tenga datos iniciales para validar el comportamiento sin carga manual.

**Why this priority**: Acelera pruebas funcionales y reduce pasos operativos en ambientes de desarrollo.

**Independent Test**: Puede validarse iniciando el sistema en limpio y comprobando que existen exactamente 10 ciudades disponibles para consulta.

**Acceptance Scenarios**:

1. **Given** un arranque en ambiente local limpio, **When** el recurso de ciudades queda disponible, **Then** existen 10 registros iniciales de ciudades.
2. **Given** que se consulta el listado general tras inicialización, **When** se evalúa el total de elementos, **Then** la cantidad coincide con los 10 registros precargados.

### Edge Cases

- ¿Qué ocurre si se consulta por un id con formato inválido (por ejemplo, texto en lugar de número)?
- ¿Qué ocurre si se consulta por un id numérico inexistente?
- ¿Qué ocurre si por un error de inicialización no se cargan los 10 registros esperados?
- ¿Qué ocurre si existen ciudades con nombres repetidos pero distinto identificador?

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: El sistema MUST exponer un recurso de ciudades de Chile consultable por consumidores autorizados.
- **FR-002**: El sistema MUST permitir obtener el listado general de ciudades disponibles.
- **FR-003**: El sistema MUST permitir obtener una ciudad específica mediante su identificador.
- **FR-004**: El sistema MUST devolver para cada ciudad al menos identificador, nombre y población.
- **FR-005**: El sistema MUST devolver resultado de recurso no encontrado al consultar un identificador inexistente.
- **FR-006**: El sistema MUST iniciar con exactamente 10 registros de ciudades en el entorno local de desarrollo y pruebas.
- **FR-007**: El sistema MUST mantener consistencia de formato de respuesta con el estándar de respuestas del proyecto.

### Key Entities *(include if feature involves data)*

- **Ciudad**: Representa una ciudad de Chile consultable en el API; atributos clave: identificador, nombre y población.

## Assumptions

- El catálogo inicial de 10 ciudades se considera dato de referencia para ambientes locales y de prueba.
- No se requiere en este alcance crear, actualizar o eliminar ciudades; solo lectura general y por id.
- La población se maneja como valor numérico entero no negativo.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: El 100% de las consultas al listado general retorna una respuesta exitosa cuando existen datos iniciales cargados.
- **SC-002**: El 100% de consultas por id existente retorna exactamente una ciudad correspondiente al identificador solicitado.
- **SC-003**: El 100% de consultas por id inexistente retorna resultado de recurso no encontrado de forma consistente.
- **SC-004**: En inicialización local limpia, el sistema dispone de 10 ciudades listas para ser consultadas sin intervención manual.

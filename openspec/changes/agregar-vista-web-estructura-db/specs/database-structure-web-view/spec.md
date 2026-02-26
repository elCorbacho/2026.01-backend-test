## ADDED Requirements

### Requirement: Publicar vista web de estructura de base de datos
El sistema SHALL exponer una vista web complementaria a `index` que describa la estructura de la base de datos del dominio principal.

#### Scenario: Acceso a la vista desde ruta dedicada
- **WHEN** un usuario navega a la ruta web configurada para estructura de BD
- **THEN** el sistema MUST responder con una página HTML renderizada con el detalle estructural

### Requirement: Mostrar tablas, columnas y tipos de datos
La vista SHALL listar cada tabla incluida en alcance con sus columnas y tipo de dato para lectura técnica rápida.

#### Scenario: Visualización de columnas por tabla
- **WHEN** la vista se carga correctamente
- **THEN** cada tabla mostrada MUST incluir nombre de columna y tipo de dato asociado

### Requirement: Mostrar relaciones y restricciones principales
La vista SHALL mostrar relaciones entre tablas y restricciones principales (PK, FK y UNIQUE) del dominio.

#### Scenario: Visualización de FK y restricción única
- **WHEN** se renderiza la sección de relaciones y constraints
- **THEN** la vista MUST indicar al menos las relaciones entre `album` y tablas hijas, y la unicidad `(album_id, nombre)` de `lamina_catalogo`

### Requirement: Mantener compatibilidad con APIs existentes
La incorporación de la vista web SHALL no modificar comportamiento ni contratos de endpoints REST existentes.

#### Scenario: Convivencia con endpoints API
- **WHEN** se despliega la nueva vista web
- **THEN** los endpoints bajo `/api/**` MUST continuar disponibles sin cambios de contrato por esta capacidad

## 1. Preparación de vista y routing web

- [x] 1.1 Definir ruta MVC dedicada para la vista de estructura de BD (separada de `/api/**` y complementaria a `index`).
- [x] 1.2 Crear/registrar controlador web para servir la nueva página HTML.

## 2. Construcción del contenido estructural

- [x] 2.1 Modelar el contenido de la vista con tablas objetivo (`album`, `lamina`, `lamina_catalogo`) y columnas/tipos.
- [x] 2.2 Incluir relaciones y constraints principales (PK, FK, UNIQUE `(album_id, nombre)`, auditoría y soft delete).

## 3. Interfaz y navegación

- [x] 3.1 Implementar template HTML de la nueva vista con secciones legibles para tablas, columnas y relaciones.
- [x] 3.2 Agregar enlace de navegación desde `index` hacia la nueva vista complementaria.

## 4. Verificación y documentación

- [x] 4.1 Verificar que la vista carga correctamente y que endpoints `/api/**` no cambian comportamiento.
- [x] 4.2 Actualizar documentación del proyecto indicando ruta y propósito de la nueva vista de estructura BD.

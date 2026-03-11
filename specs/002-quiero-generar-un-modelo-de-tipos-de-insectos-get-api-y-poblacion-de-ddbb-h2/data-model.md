# Data Model - Catalogo de tipos de insectos

## Entity: TipoInsecto

- Purpose: Representa una categoria de insecto disponible para consulta en la API.

## Fields

- `id` (Long): Identificador unico autogenerado.
- `nombre` (String, required): Nombre visible del tipo de insecto.
- `descripcion` (String, optional): Descripcion breve del tipo de insecto.
- `active` (Boolean, required, default `true`): Marca de soft delete y visibilidad en consultas.
- `createdAt` (DateTime, managed): Fecha de creacion auditada.
- `updatedAt` (DateTime, managed): Fecha de modificacion auditada.

## Validation Rules

- `nombre` obligatorio y no vacio.
- `nombre` con longitud maxima acotada por convencion de catalogos del proyecto.
- `descripcion` opcional con longitud maxima razonable para texto corto.
- `id` debe ser numerico y mayor a 0 para consulta por identificador.

## Query/Access Rules

- El listado solo incluye registros con `active = true`.
- El detalle por identificador solo retorna registro si `id` existe y `active = true`.
- Si no existe registro activo para un `id`, el sistema responde como no encontrado.

## Initial Data Rules

- Se carga un set inicial de al menos 10 tipos de insectos.
- La carga inicial es idempotente: no inserta duplicados cuando ya existen registros.

## State Transitions

- `Nuevo` -> `Activo`: al crearse registro semilla o de catalogo.
- `Activo` -> `Inactivo`: por soft delete futuro (fuera de alcance actual), sin eliminacion fisica.
- `Inactivo` -> `Activo`: posible reactivacion futura (fuera de alcance actual).

## DTO Views

- `TipoInsectoResponseDTO`: id, nombre, descripcion y estado.
- `TipoInsectoPageResponseDTO` (si se usa paginacion): lista de `TipoInsectoResponseDTO` + metadata de pagina.

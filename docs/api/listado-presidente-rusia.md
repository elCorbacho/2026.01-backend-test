# API: Presidentes de Rusia

La colección `listado_presidente_rusia` expone operaciones CRUD con soft delete. Todos los
endpoints responden con `ApiResponseDTO` y excluyen registros con `active = false`.

## GET /api/listado-presidente-rusia/{id}
- **Descripción**: devuelve un presidente activo por identificador.
- **Respuesta 200**:

```json
{
  "success": true,
  "message": "Presidente de Rusia recuperado exitosamente",
  "data": {
    "id": 5,
    "nombre": "Vladimir Putin",
    "periodoInicio": "2012-05-07",
    "periodoFin": null,
    "partido": "Rusia Unida",
    "descripcion": "Presidente de Rusia desde 2012",
    "createdAt": "2026-02-20T17:04:01.120713",
    "updatedAt": "2026-02-20T17:04:01.120713"
  }
}
```

- **Respuesta 404**: `errorCode = PRESIDENT_NOT_FOUND` cuando el registro no existe o fue eliminado.

## POST /api/listado-presidente-rusia
- **Payload requerido** (`ListadoPresidenteRusiaRequestDTO`):

```json
{
  "nombre": "Boris Yeltsin",
  "periodoInicio": "1991-07-10",
  "periodoFin": "1999-12-31",
  "partido": "Independiente",
  "descripcion": "Primer presidente de la Federación Rusa"
}
```

## PATCH /api/listado-presidente-rusia/{id}
- **Descripción**: aplica actualizaciones parciales; los campos omitidos permanecen intactos.
- **Payload (todos los campos opcionales)**:

```json
{
  "descripcion": "Primer presidente de la Federación Rusa",
  "partido": "Reformista",
  "periodoFin": "1999-12-31"
}
```

- **Validaciones**:
  - Debe enviar al menos un campo (de lo contrario `errorCode = VALIDATION_ERROR`).
  - `periodoFin` no puede ser anterior a `periodoInicio` resultante (`errorCode = PRESIDENT_INVALID_TERM_RANGE`).

## DELETE /api/listado-presidente-rusia/{id}
- **Descripción**: marca el registro como inactivo (soft delete). Responde 200 incluso cuando el cambio es idempotente.
- **Efecto**: el registro desaparece del listado general y de `GET /{id}`.

## Resumen de códigos de error
| Código | Significado |
|-------|-------------|
| `PRESIDENT_NOT_FOUND` | No existe un presidente activo con el ID solicitado. |
| `PRESIDENT_PATCH_EMPTY` | El cuerpo del PATCH no incluyó campos actualizables. |
| `PRESIDENT_INVALID_TERM_RANGE` | `periodoFin` es anterior a `periodoInicio`. |

## Colecciones API
- **Postman**: `api-collections/18.web2.examen-postman.json`
- **Bruno**: `api-collections/18.web2.examen-bruno.json`

Ambas colecciones contienen ejemplos list/GET/POST existentes y tres nuevos requests para GET por id, PATCH y DELETE.

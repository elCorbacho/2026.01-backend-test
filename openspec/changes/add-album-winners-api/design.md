## Context

El backend ya expone endpoints para álbumes y láminas, pero no tiene entidad ni API dedicada a los ganadores asociados a un álbum. Queremos reutilizar los patrones existentes (modelo + repositorio + servicio + controlador retornando `ApiResponseDTO`) y mantener la coherencia del stack (Java/Spring Boot, Lombok, Spring Data, DTOs manuales).

## Goals / Non-Goals

**Goals:**
- Introducir una entidad `GanadorAlbum` vinculada a un álbum que almacene artista, premio y año.
- Añadir un repositorio y servicio que expongan una consulta `findByAlbumId` para la colección de ganadores.
- Implementar un endpoint GET `/api/albumes/{albumId}/ganadores` que devuelva DTOs con nombre del artista, premio y año dentro de `ApiResponseDTO<List<GanadorAlbumDTO>>`.
- Crear un mapper manual que convierta entidad a DTO y aplicar validaciones básicas.

**Non-Goals:**
- Cambiar la lógica de soft delete o auditoría ya presente en otras entidades.
- Añadir endpoints de creación/actualización/eliminación para ganadores (solo lectura).

## Decisions

1. **Modelo independiente:** Se añadirá `GanadorAlbum` como entidad nueva con `@Entity`, `@ManyToOne` a `Album` y campos `artista`, `premio`, `anio`. Se ajusta la estrategia de auditoría/soft delete si hace falta replicando los campos `active`, `createdAt`, `updatedAt`.
2. **Repositorio específico:** Un `GanadorAlbumRepository` extenderá `JpaRepository<GanadorAlbum, Long>` y añadirá el método `List<GanadorAlbum> findByAlbumIdAndActiveTrue(Long albumId)` para enfocarse en registros activos.
3. **Servicio + DTO:** Se crea un servicio `GanadorAlbumService` con método `List<GanadorAlbumDTO> obtenerGanadoresPorAlbum(Long albumId)` que lanza `ResourceNotFoundException` si el álbum no existe y usa un mapper (`GanadorAlbumMapper`) para convertir a DTO con los campos de salida.
4. **Controlador REST:** Se expone un método GET en `AlbumController` o `GanadorAlbumController` que responde a `/api/albumes/{albumId}/ganadores` y retorna `ResponseEntity<ApiResponseDTO<List<GanadorAlbumDTO>>>`, respetando la envoltura `ApiResponseDTO` usada en el sistema.
5. **Respuesta estandarizada:** Se define `GanadorAlbumDTO` con `@Builder`/`@Value` (o `@Data`) que contiene `artista`, `premio`, `anio`. La respuesta incluye `success`, `message`, `data`, `errorCode`, `errors`, `timestamp` mediante `ApiResponseDTO`.

## Risks / Trade-offs

- [Risk] Si la tabla de ganadores crece mucho, la consulta actual podría paginarse o filtrar, lo cual aún no se implementa.
  → Mitigación: Documentar en los comentarios que futuros refinamientos pueden añadir paginación o filtros adicionales.
- [Trade-off] Reutilizar el controlador de álbumes evita crear otro módulo, pero mezcla responsabilidades.
  → Mitigación: Si crece la complejidad, separar el controlador en `GanadorAlbumController` pero por ahora se mantiene junto a álbumes.

## Migration Plan

1. Agregar la entidad `GanadorAlbum` y ejecutar migración (DDL) para crear la tabla con `album_id` FK.
2. Implementar repositorio, servicio, mapper y DTO.
3. Añadir el método GET en el controlador y documentar el endpoint en Swagger/OpenAPI.
4. Escribir pruebas de integración o unitarias si ya existen para otros endpoints, validando el flujo completo.
5. Verificar con `mvn clean package` y asegurar que los nuevos beans se detectan correctamente.

## Open Questions

1. ¿Necesitamos paginar la respuesta de ganadores en este cambio o lo dejamos para fases futuras?
2. ¿Se requiere un permiso/rol específico para consultar los ganadores o puede ser público?

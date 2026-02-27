## Context

El sistema expone actualmente recursos relacionados con álbumes, láminas y otros dominios auxiliares, todos siguiendo una arquitectura MVC en capas (Controller → Service → Repository → Model) con DTOs y mapeadores manuales, auditoría automática y soft delete. Para cubrir el nuevo dominio de minas en Chile, se requiere un recurso independiente que permita consultar y registrar minas con un modelo específico, sin afectar los recursos ya existentes.

## Goals / Non-Goals

**Goals:**
- Definir una entidad JPA `MinaChile` que represente minas ubicadas en Chile, con atributos relevantes (nombre, región, mineral principal, estado/actividad).
- Exponer un controlador REST con endpoints GET y POST en `/api/minas-chile` para listar minas y crear nuevas.
- Implementar un repositorio y un servicio de dominio que aíslen la lógica de acceso a datos y reglas básicas (solo minas activas en listados, validaciones mínimas).
- Poblar automáticamente la tabla de minas de Chile con un conjunto inicial de datos al iniciar la aplicación si la tabla está vacía.

**Non-Goals:**
- No se implementarán filtros avanzados, paginación o búsquedas complejas; el alcance inicial será un listado general y creación simple.
- No se abordarán aspectos de seguridad específicos para este recurso, más allá de la configuración global ya existente.
- No se crearán relaciones con otros modelos (álbumes, láminas, etc.) en esta primera iteración.

## Decisions

- **Entidad `MinaChile`:** Se ubicará en el paquete `models` y contendrá al menos: `id`, `nombre`, `region`, `mineralPrincipal`, `estado` (por ejemplo, ACTIVA/CERRADA como `String` simple), `createdAt`, `updatedAt` y `active`. Se reutilizarán las anotaciones de auditoría (`@CreatedDate`, `@LastModifiedDate`) y `@Builder.Default` para `active = true`.
- **Repositorio dedicado:** Se creará `MinaChileRepository` extendiendo `JpaRepository<MinaChile, Long>` con un método `findByActiveTrue()` para obtener únicamente minas activas.
- **DTOs y mapper:** Se añadirá `MinaChileRequestDTO` para el cuerpo del POST y `MinaChileResponseDTO` para las respuestas. Un `MinaChileMapper` manual en `mappers` convertirá entre entidad y DTOs, siguiendo el patrón usado en `LaminaMapper`.
- **Servicio de dominio:** Se implementará `MinaChileService` con métodos `obtenerMinasActivas()` y `crearMina(MinaChileRequestDTO)`, marcando el primero con `@Transactional(readOnly = true)` y el servicio completo con `@Transactional`. El servicio encapsulará la validación básica de entradas (por ejemplo, nombre no vacío, región no vacía).
- **Controlador REST:** Se añadirá `MinaChileController` en `controllers/api` con:
  - `GET /api/minas-chile` → `ResponseEntity<ApiResponseDTO<List<MinaChileResponseDTO>>>`
  - `POST /api/minas-chile` → `ResponseEntity<ApiResponseDTO<MinaChileResponseDTO>>`
  Ambos endpoints usarán DTOs, validación con Jakarta Validation en el request y el envoltorio `ApiResponseDTO`.
- **Inicialización de datos:** `DataInitializer` se ampliará para inyectar `MinaChileRepository` y poblar registros iniciales representativos de minas de Chile, solo cuando `minaChileRepository.count() == 0`.
- **OpenAPI/Swagger:** El controlador se documentará con anotaciones `@Tag` y `@Operation` de SpringDoc para que los endpoints aparezcan claramente identificados como parte del recurso de minas de Chile.

## Risks / Trade-offs

- **Modelo simplificado de minas:** Se utiliza una estructura simple (sin relaciones ni enumeraciones fuertes) para `estado` y `mineralPrincipal`, lo que facilita la implementación inicial pero puede requerir refactorización si el dominio crece en complejidad.
- **Datos de ejemplo estáticos:** La población inicial basada en un conjunto fijo de minas puede no cubrir todos los casos reales; como mitigación, se expone un endpoint POST que permite agregar nuevos registros y el diseño facilita futuras ampliaciones (filtros, paginación, etc.).
- **Crecimiento del número de entidades:** Agregar `MinaChile` incrementa el tamaño del modelo global, pero al mantenerse aislado y sin dependencias cruzadas no introduce acoplamientos fuertes con las entidades actuales.


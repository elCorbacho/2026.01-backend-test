# Fix1: Critical Issues - Detailed Instructions

## Overview
This document provides step-by-step instructions to fix **3 critical issues** and **4 high-priority issues** identified in the code review.

---

## CRITICAL ISSUE #1: PedidosController Response Envelope Inconsistency

### Problem
PedidosController returns raw DTOs instead of wrapping them in `ApiResponseDTO<T>`, breaking API contract consistency with all other 22 controllers.

### Affected File
`src/main/java/ipss/web2/examen/controllers/api/PedidosController.java`

### Current Code (Lines 26-78)
```java
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidosController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoResponseDTO resp = pedidoService.crearPedido(request);
        return ResponseEntity.status(201).body(resp);  // ❌ Raw DTO
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Long id) {
        PedidoResponseDTO p = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(p);  // ❌ Raw DTO
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(new ArrayList<>(store.values()));  // ❌ Raw List
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();  // ❌ No envelope
    }

    @PatchMapping("/{id}/total")
    public ResponseEntity<PedidoResponseDTO> actualizarTotal(@PathVariable Long id,
                                                             @RequestBody Map<String, Object> body) {
        // ... logic
        return ResponseEntity.ok(updated);  // ❌ Raw DTO
    }
}
```

### Fix Instructions

#### Step 1: Update All Endpoint Responses
Replace each return statement to use `ApiResponseDTO` wrapper:

```java
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidosController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PedidoResponseDTO>> crearPedido(
            @Valid @RequestBody PedidoRequestDTO request) {
        PedidoResponseDTO data = pedidoService.crearPedido(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO.created(data));  // ✅ Wrapped with created()
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PedidoResponseDTO>> obtenerPedido(
            @PathVariable Long id) {
        PedidoResponseDTO data = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.success(data));  // ✅ Wrapped
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PedidoResponseDTO>>> obtenerTodos() {
        List<PedidoResponseDTO> data = new ArrayList<>(store.values());
        return ResponseEntity.ok(ApiResponseDTO.success(data));  // ✅ Wrapped
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null));  // ✅ Wrapped, empty body
    }

    @PatchMapping("/{id}/total")
    public ResponseEntity<ApiResponseDTO<PedidoResponseDTO>> actualizarTotal(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        // ... logic
        return ResponseEntity.ok(ApiResponseDTO.success(updated));  // ✅ Wrapped
    }
}
```

#### Step 2: Verify ApiResponseDTO Helper Methods
Check that `ApiResponseDTO` has these factory methods (in `src/main/java/ipss/web2/examen/dtos/ApiResponseDTO.java`):

```java
public static <T> ApiResponseDTO<T> success(T data) {
    return new ApiResponseDTO<>(true, "Success", null, data, LocalDateTime.now());
}

public static <T> ApiResponseDTO<T> created(T data) {
    return new ApiResponseDTO<>(true, "Resource created", null, data, LocalDateTime.now());
}

public static <T> ApiResponseDTO<T> error(String message, String errorCode) {
    return new ApiResponseDTO<>(false, message, errorCode, null, LocalDateTime.now());
}
```

If these don't exist, add them to `ApiResponseDTO`.

#### Step 3: Test Locally
```bash
# Test POST
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"description":"Test"}'

# Verify response is wrapped:
# Expected: { "success": true, "message": "Resource created", "data": {...}, "timestamp": "..." }
```

#### Step 4: Update Swagger Documentation
Add annotations to each endpoint:

```java
@PostMapping
@Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido")
@ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
@ApiResponse(responseCode = "400", description = "Validación fallida")
public ResponseEntity<ApiResponseDTO<PedidoResponseDTO>> crearPedido(...) { ... }
```

---

## CRITICAL ISSUE #2: LaminaService Generic RuntimeException

### Problem
LaminaService throws `RuntimeException` instead of `InvalidOperationException`, breaking exception consistency and losing error codes.

### Affected File
`src/main/java/ipss/web2/examen/services/LaminaService.java`

### Current Code
```java
// Line 50
throw new RuntimeException("Este álbum ya tiene un catálogo de láminas definido");

// Line 71
throw new RuntimeException("Debe crear un catálogo de láminas primero");

// Line 80
throw new RuntimeException("Lámina NO existe en catálogo...");
```

### Fix Instructions

#### Step 1: Replace All RuntimeException Occurrences
```java
// Line 50 - BEFORE
throw new RuntimeException("Este álbum ya tiene un catálogo de láminas definido");

// Line 50 - AFTER
throw new InvalidOperationException(
    "Este álbum ya tiene un catálogo de láminas definido",
    "CATALOG_ALREADY_EXISTS"
);

// Line 71 - BEFORE
throw new RuntimeException("Debe crear un catálogo de láminas primero");

// Line 71 - AFTER
throw new InvalidOperationException(
    "Debe crear un catálogo de láminas primero",
    "CATALOG_NOT_CREATED"
);

// Line 80 - BEFORE
throw new RuntimeException("Lámina NO existe en catálogo...");

// Line 80 - AFTER
throw new InvalidOperationException(
    "Lámina NO existe en catálogo",
    "LAMINA_NOT_IN_CATALOG"
);
```

#### Step 2: Verify InvalidOperationException Is Imported
Add import if missing:
```java
import ipss.web2.examen.exceptions.InvalidOperationException;
```

#### Step 3: Verify GlobalExceptionHandler Catches It
Check `src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java` has handler:

```java
@ExceptionHandler(InvalidOperationException.class)
public ResponseEntity<ApiResponseDTO<Void>> handleInvalidOperation(
        InvalidOperationException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponseDTO.error(ex.getMessage(), ex.getErrorCode()));
}
```

#### Step 4: Test Locally
```bash
# Test endpoint that triggers the exception
curl -X POST http://localhost:8080/api/laminas/catalog \
  -H "Content-Type: application/json" \
  -d '{"albumId":1}'

# Verify response:
# Expected: { "success": false, "errorCode": "CATALOG_ALREADY_EXISTS", "message": "...", "timestamp": "..." }
# Status: 400 Bad Request
```

---

## CRITICAL ISSUE #3: H2 Console & Actuator Security

### Problem
H2 Console and Actuator endpoints exposed without authentication, allowing information disclosure and unauthorized database access.

### Affected File
`src/main/resources/application.properties`

### Current Code (Lines 10-13, 59)
```properties
# H2 Console exposed
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false

# Actuator env endpoint exposed
management.endpoints.web.exposure.include=health,info,metrics,env
```

### Fix Instructions

#### Step 1: Create Production Profile
Create new file: `src/main/resources/application-prod.properties`

```properties
# ===== PRODUCTION CONFIGURATION =====

# H2 Console disabled (switch to MySQL in production)
spring.h2.console.enabled=false

# Database (MySQL - uncomment in production)
spring.datasource.url=jdbc:mysql://db-ipss.cb2es8a2cxpo.us-east-2.rds.amazonaws.com:3306/web2_examen
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Restrict actuator endpoints (security)
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=when-authorized

# Logging
logging.level.root=INFO
logging.level.ipss.web2.examen=INFO
```

#### Step 2: Create Development Profile
Create new file: `src/main/resources/application-dev.properties`

```properties
# ===== DEVELOPMENT CONFIGURATION =====

# H2 Console enabled (dev only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false

# H2 Database
spring.datasource.url=jdbc:h2:mem:web2examen;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Actuator endpoints enabled (dev only)
management.endpoints.web.exposure.include=health,info,metrics,env
management.endpoint.health.show-details=always

# Logging
logging.level.root=INFO
logging.level.ipss.web2.examen=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

#### Step 3: Update Default application.properties
Keep it minimal, reference the profiles:

```properties
# ===== DEFAULT CONFIGURATION (DEVELOPMENT) =====
# Use: java -jar app.jar --spring.profiles.active=dev (dev)
# Use: java -jar app.jar --spring.profiles.active=prod (prod)

spring.application.name=web2-examen-api
server.port=8080

# Default to development if no profile specified
spring.profiles.default=dev

# Common settings
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

#### Step 4: Update pom.xml (Optional but Recommended)
Add Spring profiles plugin to enforce profile on build:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <profiles>
                    <profile>dev</profile>
                </profiles>
            </configuration>
        </plugin>
    </plugins>
</build>
```

#### Step 5: Test Locally
```bash
# Start in dev profile (H2 + actuator enabled)
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Verify H2 console is accessible
curl http://localhost:8080/h2-console  # Should work (200)

# Verify actuator /env is accessible
curl http://localhost:8080/actuator/env  # Should work (200)

# Simulate production mode (H2 disabled)
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"

# Verify H2 console is NOT accessible
curl http://localhost:8080/h2-console  # Should fail (404/403)

# Verify actuator limited to health
curl http://localhost:8080/actuator/health  # Should work (200)
curl http://localhost:8080/actuator/env     # Should fail (404)
```

---

## HIGH PRIORITY ISSUE #1: PedidosController PATCH Validation

### Problem
PATCH endpoint accepts unvalidated Map instead of validated DTO, allowing invalid data entry.

### Affected File
`src/main/java/ipss/web2/examen/controllers/api/PedidosController.java` (lines 58-77)

### Current Code
```java
@PatchMapping("/{id}/total")
public ResponseEntity<PedidoResponseDTO> actualizarTotal(@PathVariable Long id,
                                                         @RequestBody Map<String, Object> body) {
    Object totalObj = body.get("total");
    if (totalObj instanceof Number) {
        double total = ((Number) totalObj).doubleValue();  // No validation
```

### Fix Instructions

#### Step 1: Create PatchPedidoRequestDTO
Create file: `src/main/java/ipss/web2/examen/dtos/PatchPedidoRequestDTO.java`

```java
package ipss.web2.examen.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchPedidoRequestDTO {

    @NotNull(message = "Total no puede ser nulo")
    @Min(value = 0, message = "Total debe ser mayor o igual a 0")
    @Max(value = 999999, message = "Total no puede exceder 999999")
    private Double total;
}
```

#### Step 2: Update Controller Method
Replace the Map parameter with DTO:

```java
@PatchMapping("/{id}/total")
@Operation(summary = "Actualizar total del pedido")
@ApiResponse(responseCode = "200", description = "Total actualizado")
@ApiResponse(responseCode = "400", description = "Validación fallida")
public ResponseEntity<ApiResponseDTO<PedidoResponseDTO>> actualizarTotal(
        @PathVariable Long id,
        @Valid @RequestBody PatchPedidoRequestDTO request) {

    PedidoResponseDTO updated = pedidoService.actualizarTotal(id, request.getTotal());
    return ResponseEntity.ok(ApiResponseDTO.success(updated));
}
```

#### Step 3: Update Service Method
Ensure `PedidoService.actualizarTotal()` accepts Double:

```java
public PedidoResponseDTO actualizarTotal(Long id, Double total) {
    Pedido pedido = pedidoRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Pedido", "id", id));
    pedido.setTotal(total);
    Pedido updated = pedidoRepository.save(pedido);
    return pedidoMapper.toResponseDTO(updated);
}
```

#### Step 4: Test
```bash
# Valid request
curl -X PATCH http://localhost:8080/api/pedidos/1/total \
  -H "Content-Type: application/json" \
  -d '{"total": 100.50}'

# Invalid request (negative)
curl -X PATCH http://localhost:8080/api/pedidos/1/total \
  -H "Content-Type: application/json" \
  -d '{"total": -50}'  # Should return 400 with validation error

# Invalid request (too large)
curl -X PATCH http://localhost:8080/api/pedidos/1/total \
  -H "Content-Type: application/json" \
  -d '{"total": 9999999}'  # Should return 400 with validation error
```

---

## HIGH PRIORITY ISSUE #2: Soft Delete Inconsistency in AlbumService

### Problem
`obtenerAlbumPorId()` uses `findById()` which returns soft-deleted items, inconsistent with other methods.

### Affected File
`src/main/java/ipss/web2/examen/services/AlbumService.java`

### Current Code (Lines 46-48)
```java
@Transactional(readOnly = true)
public AlbumResponseDTO obtenerAlbumPorId(Long id) {
    Album album = albumRepository.findById(id)  // ❌ Ignores active=false
            .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
```

### Fix Instructions

#### Step 1: Replace Query Method
```java
// BEFORE
Album album = albumRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));

// AFTER
Album album = albumRepository.findByIdAndActiveTrue(id)
    .orElseThrow(() -> new ResourceNotFoundException("Album", "ID", id));
```

#### Step 2: Verify Repository Has Method
Check `src/main/java/ipss/web2/examen/repositories/AlbumRepository.java`:

```java
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByIdAndActiveTrue(Long id);
    List<Album> findByActiveTrue();
    Page<Album> findByActiveTrue(Pageable pageable);
}
```

If missing, add it.

#### Step 3: Search for Similar Issues in Other Services
Check these files for same pattern:
- `ExoplanetaService.java`
- `PoblacionAveService.java`
- `MarcaAutomovilService.java`

Replace all `findById(id)` with `findByIdAndActiveTrue(id)` in service methods.

#### Step 4: Test
```bash
# Create an album
curl -X POST http://localhost:8080/api/albums \
  -H "Content-Type: application/json" \
  -d '{"name": "Test Album", "year": 2024}'

# Get the ID from response, then soft-delete it
curl -X DELETE http://localhost:8080/api/albums/{id}

# Try to get the deleted album
curl http://localhost:8080/api/albums/{id}
# Should return 404, not 200
```

---

## HIGH PRIORITY ISSUE #3: System.out Logging in DataInitializer

### Problem
Uses `System.out.println()` instead of SLF4J, preventing log configuration and filtering.

### Affected File
`src/main/java/ipss/web2/examen/config/DataInitializer.java`

### Fix Instructions

#### Step 1: Add Lombok Logging Annotation
At the top of DataInitializer class:

```java
// BEFORE
public class DataInitializer implements ApplicationRunner {
    // ...
}

// AFTER
@Slf4j
public class DataInitializer implements ApplicationRunner {
    // ...
}
```

#### Step 2: Import Slf4j
Add import:
```java
import lombok.extern.slf4j.Slf4j;
```

#### Step 3: Replace All System.out Calls
Find and replace pattern:

```java
// BEFORE
System.out.println("⚠️ Base de datos ya contiene álbumes...");

// AFTER
log.info("Base de datos ya contiene álbumes");

// BEFORE
System.out.println("✅ Base de datos poblada exitosamente");

// AFTER
log.info("Base de datos poblada exitosamente");

// BEFORE
System.out.println("Inicializando datos...");

// AFTER
log.debug("Inicializando datos...");  // Use debug for verbose output
```

**Note:** Remove emoji characters (⚠️, ✅) - they may cause encoding issues in logs.

#### Step 4: Test
```bash
# Start app
./mvnw spring-boot:run

# Should see log lines like:
# 2026-03-13 10:30:45 - INFO  ipss.web2.examen.config.DataInitializer - Base de datos poblada exitosamente
```

---

## HIGH PRIORITY ISSUE #4: Actuator /env Endpoint Exposure

### Problem
`/actuator/env` exposes all environment variables, including potential secrets.

### Affected File
`src/main/resources/application.properties` (line 59)

### Current Code
```properties
management.endpoints.web.exposure.include=health,info,metrics,env
```

### Fix Instructions

This is already covered in **CRITICAL ISSUE #3** (H2 Console & Actuator Security).

Update both `application-dev.properties` and `application-prod.properties`:

**Development** (dev.properties):
```properties
management.endpoints.web.exposure.include=health,info,metrics,env
management.endpoint.health.show-details=always
```

**Production** (prod.properties):
```properties
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=when-authorized
```

---

## Implementation Checklist

- [ ] **CRITICAL #1**: Update PedidosController to use ApiResponseDTO wrapper (30 min)
  - [ ] Replace all return statements with ApiResponseDTO
  - [ ] Add Swagger annotations
  - [ ] Test POST, GET, DELETE, PATCH endpoints

- [ ] **CRITICAL #2**: Replace RuntimeException with InvalidOperationException in LaminaService (15 min)
  - [ ] Replace 3 occurrences in LaminaService
  - [ ] Verify GlobalExceptionHandler handles it
  - [ ] Test error response format

- [ ] **CRITICAL #3**: Secure H2 & Actuator with profiles (20 min)
  - [ ] Create application-prod.properties
  - [ ] Create application-dev.properties
  - [ ] Update default application.properties
  - [ ] Test both profiles locally

- [ ] **HIGH #1**: Add PATCH validation with PatchPedidoRequestDTO (20 min)
  - [ ] Create PatchPedidoRequestDTO class
  - [ ] Update controller method
  - [ ] Test validation on invalid inputs

- [ ] **HIGH #2**: Fix soft-delete in AlbumService (15 min)
  - [ ] Replace findById with findByIdAndActiveTrue
  - [ ] Check other services for same issue
  - [ ] Test deleted album returns 404

- [ ] **HIGH #3**: Replace System.out with SLF4J (10 min)
  - [ ] Add @Slf4j annotation
  - [ ] Replace all System.out.println calls
  - [ ] Test app startup logs

- [ ] **HIGH #4**: Restrict actuator /env
  - [ ] ✅ Already covered in CRITICAL #3

---

## Total Estimated Time
**~110 minutes** for all critical and high-priority fixes

## Execution Order Recommended
1. CRITICAL #1 (PedidosController) — Most visible to API clients
2. CRITICAL #2 (LaminaService) — Consistency fix
3. CRITICAL #3 (H2/Actuator) — Security fix
4. HIGH #1 (PATCH validation) — Data integrity
5. HIGH #2 (Soft delete) — Data consistency
6. HIGH #3 (Logging) — Operations/debugging

Once all fixes are complete:
- Run full test suite: `./mvnw test`
- Start app and manually test key endpoints
- Create PR with message: "fix: resolve critical and high-priority issues from code review"

---

## Questions or Issues?
If any fix doesn't apply or needs clarification, refer back to the original code review report for context.

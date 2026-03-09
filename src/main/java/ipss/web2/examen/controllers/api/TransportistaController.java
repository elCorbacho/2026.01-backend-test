package ipss.web2.examen.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.TransportistaRequestDTO;
import ipss.web2.examen.dtos.TransportistaResponseDTO;
import ipss.web2.examen.services.TransportistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de transportistas.
 * Expone operaciones CRUD bajo la ruta {@code /api/transportistas}.
 */
@RestController
@RequestMapping("/api/transportistas")
@RequiredArgsConstructor
@Tag(name = "Transportistas", description = "Gestión de transportistas")
public class TransportistaController {

    private final TransportistaService transportistaService;

    // ─── GET /api/transportistas ──────────────────────────────────────────────

    @GetMapping
    @Operation(summary = "Listar todos los transportistas activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de transportistas obtenida exitosamente")
    })
    public ResponseEntity<ApiResponseDTO<List<TransportistaResponseDTO>>> listarTransportistas() {
        List<TransportistaResponseDTO> transportistas = transportistaService.obtenerTodos();
        return ResponseEntity.ok(ApiResponseDTO.ok(transportistas, "Transportistas obtenidos exitosamente"));
    }

    // ─── GET /api/transportistas/{id} ────────────────────────────────────────

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un transportista por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transportista encontrado"),
            @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    })
    public ResponseEntity<ApiResponseDTO<TransportistaResponseDTO>> obtenerTransportistaPorId(
            @PathVariable Long id) {
        TransportistaResponseDTO transportista = transportistaService.obtenerPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(transportista, "Transportista obtenido exitosamente"));
    }

    // ─── POST /api/transportistas ─────────────────────────────────────────────

    @PostMapping
    @Operation(summary = "Crear un nuevo transportista")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transportista creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ApiResponseDTO<TransportistaResponseDTO>> crearTransportista(
            @Valid @RequestBody TransportistaRequestDTO requestDTO) {
        TransportistaResponseDTO creado = transportistaService.crear(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(creado, "Transportista creado exitosamente"));
    }

    // ─── PUT /api/transportistas/{id} ────────────────────────────────────────

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar completamente un transportista")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transportista actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Transportista no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<ApiResponseDTO<TransportistaResponseDTO>> actualizarTransportista(
            @PathVariable Long id,
            @Valid @RequestBody TransportistaRequestDTO requestDTO) {
        TransportistaResponseDTO actualizado = transportistaService.actualizar(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(actualizado, "Transportista actualizado exitosamente"));
    }

    // ─── DELETE /api/transportistas/{id} ─────────────────────────────────────

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar (soft-delete) un transportista")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transportista eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    })
    public ResponseEntity<ApiResponseDTO<Void>> eliminarTransportista(@PathVariable Long id) {
        transportistaService.eliminar(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Transportista eliminado exitosamente"));
    }
}

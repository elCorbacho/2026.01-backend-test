package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.services.PresidenteChileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador REST para presidentes de Chile - /api/presidentes-chile

@RestController
@RequestMapping("/api/presidentes-chile")
@RequiredArgsConstructor
@Tag(name = "Presidentes Chile", description = "Endpoints para consultar presidentes de Chile")
public class PresidenteChileController {

    private final PresidenteChileService presidenteChileService;

    // GET /api/presidentes-chile - Listar presidentes de Chile
    @GetMapping
        @Operation(summary = "Listar presidentes de Chile")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presidentes recuperados exitosamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
        })
    public ResponseEntity<ApiResponseDTO<List<PresidenteChileResponseDTO>>> obtenerPresidentesChile() {
        List<PresidenteChileResponseDTO> presidentes = presidenteChileService.obtenerPresidentesChile();
        return ResponseEntity.ok(ApiResponseDTO.ok(presidentes, "Presidentes de Chile recuperados exitosamente"));
    }

    // GET /api/presidentes-chile/{id} - Obtener presidente de Chile por ID
    @GetMapping("/{id}")
        @Operation(summary = "Obtener presidente de Chile por id")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Presidente recuperado exitosamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Presidente no encontrado",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
        })
    public ResponseEntity<ApiResponseDTO<PresidenteChileResponseDTO>> obtenerPresidenteChilePorId(@PathVariable Long id) {
        PresidenteChileResponseDTO presidente = presidenteChileService.obtenerPresidenteChilePorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(presidente, "Presidente de Chile recuperado exitosamente"));
    }
}


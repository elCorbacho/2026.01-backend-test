package ipss.web2.examen.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAvePageResponseDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.services.TipoAveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tipos-ave")
@RequiredArgsConstructor
@Tag(name = "Tipos de Ave", description = "Endpoints para gestionar tipos de ave")
public class TipoAveController {

    private final TipoAveService tipoAveService;

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de ave")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de ave creado correctamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> crearTipoAve(
            @Valid @RequestBody TipoAveRequestDTO requestDTO) {
        TipoAveResponseDTO response = tipoAveService.crearTipoAve(requestDTO);
        return createdResponse(response, "Tipo de ave creado correctamente");
    }

    @GetMapping("/{id}")
        @Operation(summary = "Obtener tipo de ave por id")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de ave recuperado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de ave no encontrado")
        })
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> obtenerTipoAvePorId(@PathVariable Long id) {
        TipoAveResponseDTO response = tipoAveService.obtenerTipoAvePorId(id);
        return okResponse(response, "Tipo de ave recuperado correctamente");
    }

    @GetMapping
        @Operation(summary = "Listar tipos de ave")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipos de ave recuperados exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros de paginación inválidos")
        })
    public ResponseEntity<ApiResponseDTO<TipoAvePageResponseDTO>> obtenerTiposAve(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        TipoAvePageResponseDTO response = tipoAveService.obtenerTiposAvePaginados(page, size);
        return okResponse(response, "Tipos de ave recuperados exitosamente");
    }

    @PutMapping("/{id}")
        @Operation(summary = "Actualizar tipo de ave")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de ave actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Tipo de ave no encontrado")
        })
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> actualizarTipoAve(
            @PathVariable Long id,
            @Valid @RequestBody TipoAveRequestDTO requestDTO) {
        TipoAveResponseDTO response = tipoAveService.actualizarTipoAve(id, requestDTO);
        return okResponse(response, "Tipo de ave actualizado correctamente");
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Eliminar tipo de ave (soft delete)")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de ave marcado como inactivo"),
            @ApiResponse(responseCode = "404", description = "Tipo de ave no encontrado")
        })
    public ResponseEntity<ApiResponseDTO<Void>> eliminarTipoAve(@PathVariable Long id) {
        tipoAveService.eliminarTipoAve(id);
        return okMessageResponse("Tipo de ave con ID: " + id + " ha sido marcado como inactivo");
    }

    private <T> ResponseEntity<ApiResponseDTO<T>> okResponse(T data, String message) {
        return ResponseEntity.ok(ApiResponseDTO.ok(data, message));
    }

    private <T> ResponseEntity<ApiResponseDTO<T>> createdResponse(T data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(data, message));
    }

    private ResponseEntity<ApiResponseDTO<Void>> okMessageResponse(String message) {
        return ResponseEntity.ok(ApiResponseDTO.ok(message));
    }
}

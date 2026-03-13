package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.AlbumRequestDTO;
import ipss.web2.examen.dtos.AlbumPageResponseDTO;
import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.dtos.AlbumSummaryDTO;
import ipss.web2.examen.dtos.GanadorAlbumDTO;
import ipss.web2.examen.services.AlbumService;
import ipss.web2.examen.services.GanadorAlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gestión de Álbumes - /api/albums
@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
@Tag(name = "Albumes", description = "Endpoints para gestionar albumes y sus ganadores")
public class AlbumController {
    
    private final AlbumService albumService;
    private final GanadorAlbumService ganadorAlbumService;
    
    // POST /api/albums - Crear nuevo álbum
    @PostMapping
    public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> crearAlbum(
            @Valid @RequestBody AlbumRequestDTO requestDTO) {
        AlbumResponseDTO response = albumService.crearAlbum(requestDTO);
        return createdResponse(response, "Álbum creado exitosamente");
    }
    
    // GET /api/albums/{id} - Obtener álbum por ID
    @GetMapping("/{id}")
        @Operation(summary = "Obtener album por id")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album recuperado exitosamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Album no encontrado",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
        })
    public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> obtenerAlbumPorId(
            @PathVariable Long id) {
        AlbumResponseDTO response = albumService.obtenerAlbumPorId(id);
        return okResponse(response, "Álbum recuperado exitosamente");
    }
    
    // GET /api/albums - Listar todos los álbumes
    @GetMapping
        @Operation(summary = "Listar albums paginados")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Albums recuperados exitosamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parametros de paginacion o filtros invalidos",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno inesperado",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
        })
    public ResponseEntity<ApiResponseDTO<AlbumPageResponseDTO>> obtenerTodosLosAlbumes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean active) {
        AlbumPageResponseDTO response = albumService.obtenerAlbumsPaginados(page, size, year, active);
        return okResponse(response, "Álbumes recuperados exitosamente");
    }

    // GET /api/albums/{id}/summary - Obtener resumen del álbum
    @GetMapping("/{id}/summary")
    public ResponseEntity<ApiResponseDTO<AlbumSummaryDTO>> obtenerResumenAlbum(
            @PathVariable Long id) {
        AlbumSummaryDTO summary = albumService.obtenerResumenAlbum(id);
        return okResponse(summary, "Resumen del álbum recuperado exitosamente");
    }
    
    // PUT /api/albums/{id} - Actualizar álbum
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> actualizarAlbum(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO requestDTO) {
        AlbumResponseDTO response = albumService.actualizarAlbum(id, requestDTO);
        return okResponse(response, "Álbum actualizado correctamente");
    }
    
    // DELETE /api/albums/{id} - Eliminar álbum (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarAlbum(@PathVariable Long id) {
        albumService.eliminarAlbum(id);
        return okMessageResponse("Álbum con ID: " + id + " ha sido marcado como inactivo");
    }

    @GetMapping("/{albumId}/ganadores")
    @Operation(
            summary = "Obtener ganadores por album",
            description = "Retorna los ganadores activos asociados a un album, ordenados de forma descendente por anio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ganadores recuperados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Album no encontrado")
    })
    public ResponseEntity<ApiResponseDTO<List<GanadorAlbumDTO>>> obtenerGanadoresPorAlbum(
            @PathVariable Long albumId) {
        List<GanadorAlbumDTO> ganadores = ganadorAlbumService.obtenerGanadoresPorAlbum(albumId);
        return okResponse(ganadores, "Ganadores del álbum recuperados exitosamente");
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

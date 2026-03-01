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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Álbum creado exitosamente"));
    }
    
    // GET /api/albums/{id} - Obtener álbum por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> obtenerAlbumPorId(
            @PathVariable Long id) {
        AlbumResponseDTO response = albumService.obtenerAlbumPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Álbum recuperado exitosamente"));
    }
    
    // GET /api/albums - Listar todos los álbumes
    @GetMapping
    public ResponseEntity<ApiResponseDTO<AlbumPageResponseDTO>> obtenerTodosLosAlbumes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Boolean active) {
        AlbumPageResponseDTO response = albumService.obtenerAlbumsPaginados(page, size, year, active);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Álbumes recuperados exitosamente"));
    }

    // GET /api/albums/{id}/summary - Obtener resumen del álbum
    @GetMapping("/{id}/summary")
    public ResponseEntity<ApiResponseDTO<AlbumSummaryDTO>> obtenerResumenAlbum(
            @PathVariable Long id) {
        AlbumSummaryDTO summary = albumService.obtenerResumenAlbum(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(summary, "Resumen del álbum recuperado exitosamente"));
    }
    
    // PUT /api/albums/{id} - Actualizar álbum
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> actualizarAlbum(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO requestDTO) {
        AlbumResponseDTO response = albumService.actualizarAlbum(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Álbum actualizado correctamente"));
    }
    
    // DELETE /api/albums/{id} - Eliminar álbum (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarAlbum(@PathVariable Long id) {
        albumService.eliminarAlbum(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Álbum con ID: " + id + " ha sido marcado como inactivo"));
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
        return ResponseEntity.ok(ApiResponseDTO.ok(ganadores, "Ganadores del álbum recuperados exitosamente"));
    }
}

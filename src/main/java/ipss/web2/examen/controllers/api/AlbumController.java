package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.AlbumRequestDTO;
import ipss.web2.examen.dtos.AlbumResponseDTO;
import ipss.web2.examen.services.AlbumService;
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
public class AlbumController {
    
    private final AlbumService albumService;
    
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
    public ResponseEntity<ApiResponseDTO<List<AlbumResponseDTO>>> obtenerTodosLosAlbumes() {
        List<AlbumResponseDTO> response = albumService.obtenerTodosLosAlbums();
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Álbumes recuperados exitosamente"));
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
}

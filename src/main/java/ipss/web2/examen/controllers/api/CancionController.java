package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.CancionRequestDTO;
import ipss.web2.examen.dtos.CancionResponseDTO;
import ipss.web2.examen.services.CancionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gestión de Canciones - /api/canciones
@RestController
@RequestMapping("/api/canciones")
@RequiredArgsConstructor
public class CancionController {

    private final CancionService cancionService;

    // POST /api/canciones - Crear nueva canción
    @PostMapping
    public ResponseEntity<ApiResponseDTO<CancionResponseDTO>> crearCancion(
            @Valid @RequestBody CancionRequestDTO requestDTO) {
        CancionResponseDTO response = cancionService.crearCancion(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Canción creada exitosamente"));
    }

    // GET /api/canciones/{id} - Obtener canción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CancionResponseDTO>> obtenerCancionPorId(
            @PathVariable Long id) {
        CancionResponseDTO response = cancionService.obtenerCancionPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Canción recuperada exitosamente"));
    }

    // GET /api/canciones - Listar todas las canciones activas
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CancionResponseDTO>>> obtenerTodasLasCanciones() {
        List<CancionResponseDTO> response = cancionService.obtenerTodasLasCanciones();
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Canciones recuperadas exitosamente"));
    }

    // GET /api/canciones/buscar?artista=xxx - Buscar por artista
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<CancionResponseDTO>>> buscarPorArtista(
            @RequestParam String artista) {
        List<CancionResponseDTO> response = cancionService.buscarPorArtista(artista);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Búsqueda completada"));
    }

    // PUT /api/canciones/{id} - Actualizar canción
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CancionResponseDTO>> actualizarCancion(
            @PathVariable Long id,
            @Valid @RequestBody CancionRequestDTO requestDTO) {
        CancionResponseDTO response = cancionService.actualizarCancion(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Canción actualizada correctamente"));
    }

    // DELETE /api/canciones/{id} - Eliminar canción (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarCancion(@PathVariable Long id) {
        cancionService.eliminarCancion(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Canción con ID: " + id + " ha sido marcada como inactiva"));
    }
}

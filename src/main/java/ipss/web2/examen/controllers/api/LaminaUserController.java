package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.LaminaCargaResponseDTO;
import ipss.web2.examen.dtos.LaminaCargueMasivoRequestDTO;
import ipss.web2.examen.dtos.LaminaCargueMasivoResponseDTO;
import ipss.web2.examen.dtos.LaminaRequestDTO;
import ipss.web2.examen.dtos.LaminaResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.services.LaminaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para CRUD de Láminas - /api/laminas (valida catálogo, detecta repetidas)
@RestController
@RequestMapping("/api/laminas")
@RequiredArgsConstructor
public class LaminaUserController {
    
    private final LaminaService laminaService;
    
    // POST /api/laminas - Agregar lámina validando catálogo y detectando repetidas
    @PostMapping
    public ResponseEntity<ApiResponseDTO<LaminaCargaResponseDTO>> agregarLamina(
            @Valid @RequestBody LaminaRequestDTO laminaDTO) {
        
        if (laminaDTO.getAlbumId() == null) {
            throw new InvalidOperationException("El album_id es obligatorio en POST individual /api/laminas", "VALIDATION_ERROR");
        }
        
        LaminaCargaResponseDTO response = laminaService.agregarLamina(laminaDTO.getAlbumId(), laminaDTO);
        
        String mensaje = response.esRepetida()
            ? "Lámina agregada (repetida: " + response.cantidadRepetidas() + " copias totales)"
            : "Lámina agregada";
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, mensaje));
    }
    
    // POST /api/laminas/masivo - Carga masiva de láminas con validación y reporte detallado
    @PostMapping("/masivo")
    public ResponseEntity<ApiResponseDTO<List<LaminaCargueMasivoResponseDTO>>> agregarLaminasMasivo(
            @Valid @RequestBody LaminaCargueMasivoRequestDTO cargueMasivo) {
        
        List<LaminaCargueMasivoResponseDTO> resultados = laminaService.agregarLaminasMasivo(cargueMasivo);
        
        long exitosas = resultados.stream().filter(r -> r.laminaId() != null).count();
        long fallidas = resultados.size() - exitosas;
        
        String mensaje = String.format("Carga masiva completada: %d exitosas, %d fallidas", exitosas, fallidas);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fallidas == 0
                        ? ApiResponseDTO.created(resultados, mensaje)
                        : ApiResponseDTO.<List<LaminaCargueMasivoResponseDTO>>builder()
                                .success(false)
                                .message(mensaje)
                                .data(resultados)
                                .timestamp(java.time.LocalDateTime.now())
                                .build());
    }
    
    // GET /api/laminas/{id} - Obtener lámina por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<LaminaResponseDTO>> obtenerLaminaPorId(@PathVariable Long id) {
        LaminaResponseDTO response = laminaService.obtenerLaminaPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Lámina obtenida correctamente"));
    }
    
    // GET /api/laminas - Obtener todas las láminas activas del sistema
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<LaminaResponseDTO>>> obtenerTodasLasLaminas() {
        List<LaminaResponseDTO> laminas = laminaService.obtenerTodasLasLaminas();
        return ResponseEntity.ok(ApiResponseDTO.ok(laminas,
                "Láminas obtenidas correctamente. Total: " + laminas.size()));
    }
    
    // GET /api/laminas/album/{albumId} - Obtener láminas poseídas de un álbum
    @GetMapping("/album/{albumId}")
    public ResponseEntity<ApiResponseDTO<List<LaminaResponseDTO>>> obtenerLaminasPorAlbum(
            @PathVariable Long albumId) {
        List<LaminaResponseDTO> laminas = laminaService.obtenerLaminasPorAlbum(albumId);
        return ResponseEntity.ok(ApiResponseDTO.ok(laminas,
                "Láminas del álbum obtenidas correctamente. Total: " + laminas.size()));
    }
    
    // PUT /api/laminas/{id} - Actualizar lámina
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<LaminaResponseDTO>> actualizarLamina(
            @PathVariable Long id,
            @Valid @RequestBody LaminaRequestDTO requestDTO) {
        LaminaResponseDTO response = laminaService.actualizarLamina(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Lámina actualizada correctamente"));
    }
    
    // DELETE /api/laminas/{id} - Eliminar lámina (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarLamina(@PathVariable Long id) {
        laminaService.eliminarLamina(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Lámina con ID: " + id + " ha sido marcada como inactiva"));
    }
}

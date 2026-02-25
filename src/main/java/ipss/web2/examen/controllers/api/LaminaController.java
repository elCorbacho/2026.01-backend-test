package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.LaminaCatalogoRequestDTO;
import ipss.web2.examen.dtos.LaminaCatalogoResponseDTO;
import ipss.web2.examen.dtos.LaminasEstadoDTO;
import ipss.web2.examen.services.LaminaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para Catálogo de Láminas - /api/albums/{albumId}/catalogo
@RestController
@RequestMapping("/api/albums/{albumId}/catalogo")
@RequiredArgsConstructor
public class LaminaController {
    
    private final LaminaService laminaService;

    // POST /api/albums/{albumId}/catalogo - Crear catálogo de láminas
    @PostMapping
    public ResponseEntity<ApiResponseDTO<List<LaminaCatalogoResponseDTO>>> crearCatalogo(
            @PathVariable Long albumId,
            @Valid @RequestBody List<LaminaCatalogoRequestDTO> catalogo) {
        
        List<LaminaCatalogoResponseDTO> response = laminaService.crearCatalogo(albumId, catalogo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response,
                        "Catálogo creado exitosamente con " + response.size() + " láminas"));
    }
    
    // GET /api/albums/{albumId}/catalogo - Obtener catálogo de láminas
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<LaminaCatalogoResponseDTO>>> obtenerCatalogo(
            @PathVariable Long albumId) {
        List<LaminaCatalogoResponseDTO> catalogo = laminaService.obtenerCatalogo(albumId);
        return ResponseEntity.ok(ApiResponseDTO.ok(catalogo,
                "Catálogo obtenido: " + catalogo.size() + " láminas disponibles"));
    }
    
    // GET /api/albums/{albumId}/catalogo/estado - Ver estado del álbum
    @GetMapping("/estado")
    public ResponseEntity<ApiResponseDTO<LaminasEstadoDTO>> obtenerEstado(@PathVariable Long albumId) {
        LaminasEstadoDTO estado = laminaService.obtenerEstado(albumId);
        return ResponseEntity.ok(ApiResponseDTO.ok(estado, "Estado del álbum obtenido"));
    }
}

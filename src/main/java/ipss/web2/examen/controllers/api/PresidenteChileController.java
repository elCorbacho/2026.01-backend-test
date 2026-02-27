package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.PresidenteChileResponseDTO;
import ipss.web2.examen.services.PresidenteChileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador REST para presidentes de Chile - /api/presidentes-chile
@SuppressWarnings("null")
@RestController
@RequestMapping("/api/presidentes-chile")
@RequiredArgsConstructor
public class PresidenteChileController {

    private final PresidenteChileService presidenteChileService;

    // GET /api/presidentes-chile - Listar presidentes de Chile
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PresidenteChileResponseDTO>>> obtenerPresidentesChile() throws Exception {
        try {
            List<PresidenteChileResponseDTO> presidentes = presidenteChileService.obtenerPresidentesChile();
            return ResponseEntity.ok(ApiResponseDTO.ok(presidentes, "Presidentes de Chile recuperados exitosamente"));
        } catch (RuntimeException ex) {
            throw new Exception(ex);
        }
    }
}

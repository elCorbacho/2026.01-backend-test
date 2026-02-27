package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.CampeonJockeyResponseDTO;
import ipss.web2.examen.services.CampeonJockeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

// Controlador REST para campeones de jockey - /api/campeones-jockey
@RestController
@RequestMapping("/api/campeones-jockey")
@RequiredArgsConstructor
@Tag(name = "Campeones de Jockey", description = "Endpoints para consultar campeones históricos de jockey")
public class CampeonJockeyController {

    private final CampeonJockeyService campeonJockeyService;

    // GET /api/campeones-jockey - Listar campeones de jockey activos
    @Operation(
            summary = "Listar campeones de jockey activos",
            description = "Obtiene el listado completo de campeones de jockey activos registrados en el sistema."
    )
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CampeonJockeyResponseDTO>>> obtenerCampeonesJockey() {
        List<CampeonJockeyResponseDTO> campeones = campeonJockeyService.obtenerCampeonesActivos();
        return ResponseEntity.ok(
                ApiResponseDTO.ok(campeones,
                        "Campeones de jockey recuperados exitosamente. Total: " + campeones.size())
        );
    }
}


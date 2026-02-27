package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.GanadorGuinnessResponseDTO;
import ipss.web2.examen.services.GanadorGuinnessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador REST para ganadores Guinness - /api/ganadores-guinness
@RestController
@RequestMapping("/api/ganadores-guinness")
@RequiredArgsConstructor
public class GanadorGuinnessController {

    private final GanadorGuinnessService ganadorGuinnessService;

    // GET /api/ganadores-guinness - Listar ganadores Guinness
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<GanadorGuinnessResponseDTO>>> obtenerGanadoresGuinness() {
        List<GanadorGuinnessResponseDTO> ganadores = ganadorGuinnessService.obtenerGanadoresGuinness();
        return ResponseEntity.ok(ApiResponseDTO.ok(ganadores, "Ganadores Guinness recuperados exitosamente"));
    }
}

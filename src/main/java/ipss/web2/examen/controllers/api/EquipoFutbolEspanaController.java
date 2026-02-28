package ipss.web2.examen.controllers.api;

import ipss.web2.examen.models.EquipoFutbolEspana;
import ipss.web2.examen.services.EquipoFutbolEspanaService;
import ipss.web2.examen.dtos.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos-futbol-espana")
@RequiredArgsConstructor
public class EquipoFutbolEspanaController {
    private final EquipoFutbolEspanaService service;

    // GET /api/equipos-futbol-espana
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<EquipoFutbolEspana>>> obtenerEquipos() {
        List<EquipoFutbolEspana> equipos = service.obtenerEquiposActivos();
        return ResponseEntity.ok(ApiResponseDTO.ok(equipos, "Equipos activos recuperados exitosamente"));
    }
}

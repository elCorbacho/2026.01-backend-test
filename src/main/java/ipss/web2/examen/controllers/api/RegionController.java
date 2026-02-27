package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.RegionResponseDTO;
import ipss.web2.examen.services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<RegionResponseDTO>>> obtenerRegiones() {
        List<RegionResponseDTO> regiones = regionService.obtenerRegionesActivas();
        return ResponseEntity.ok(ApiResponseDTO.ok(regiones, "Regiones recuperadas exitosamente"));
    }
}

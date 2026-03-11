package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.CiudadChileResponseDTO;
import ipss.web2.examen.services.CiudadChileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades-chile")
@RequiredArgsConstructor
public class CiudadChileController {

    private final CiudadChileService ciudadChileService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CiudadChileResponseDTO>>> obtenerCiudadesChile() {
        List<CiudadChileResponseDTO> ciudades = ciudadChileService.obtenerCiudadesChile();
        return ResponseEntity.ok(ApiResponseDTO.ok(ciudades, "Ciudades de Chile recuperadas exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<CiudadChileResponseDTO>> obtenerCiudadChilePorId(@PathVariable Long id) {
        CiudadChileResponseDTO ciudad = ciudadChileService.obtenerCiudadChilePorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(ciudad, "Ciudad de Chile recuperada exitosamente"));
    }
}

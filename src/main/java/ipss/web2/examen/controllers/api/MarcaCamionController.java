package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.MarcaCamionResponseDTO;
import ipss.web2.examen.services.MarcaCamionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas-camiones")
@RequiredArgsConstructor
public class MarcaCamionController {

    private final MarcaCamionService marcaCamionService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MarcaCamionResponseDTO>>> listarMarcasActivas() {
        List<MarcaCamionResponseDTO> marcasCamion = marcaCamionService.obtenerMarcasActivas();
        return ResponseEntity.ok(ApiResponseDTO.ok(marcasCamion, "Marcas camiones recuperadas"));
    }
}

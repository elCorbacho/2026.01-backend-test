package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.MarcaAutomovilResponseDTO;
import ipss.web2.examen.services.MarcaAutomovilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaAutomovilController {

    private final MarcaAutomovilService marcaService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MarcaAutomovilResponseDTO>>> listarMarcasActivas() {
        List<MarcaAutomovilResponseDTO> marcas = marcaService.obtenerMarcasActivas();
        return ResponseEntity.ok(ApiResponseDTO.ok(marcas, "Marcas recuperadas"));
    }
}

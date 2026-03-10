package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.MarcaBicicletaResponseDTO;
import ipss.web2.examen.services.MarcaBicicletaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas-bicicleta")
@RequiredArgsConstructor
public class MarcaBicicletaController {

    private final MarcaBicicletaService marcaService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MarcaBicicletaResponseDTO>>> listarMarcasActivas() {
        List<MarcaBicicletaResponseDTO> marcas = marcaService.obtenerMarcasActivas();
        return ResponseEntity.ok(ApiResponseDTO.ok(marcas, "Marcas bicicleta recuperadas"));
    }
}

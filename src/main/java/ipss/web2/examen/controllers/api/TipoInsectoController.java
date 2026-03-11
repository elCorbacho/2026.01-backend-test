package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.TipoInsectoResponseDTO;
import ipss.web2.examen.services.TipoInsectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-insecto")
@RequiredArgsConstructor
public class TipoInsectoController
{

    private final TipoInsectoService tipoInsectoService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TipoInsectoResponseDTO>>> obtenerTiposInsecto()
    {
        List<TipoInsectoResponseDTO> response = tipoInsectoService.obtenerTiposInsecto();
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tipos de insecto recuperados exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TipoInsectoResponseDTO>> obtenerTipoInsectoPorId(@PathVariable Long id)
    {
        TipoInsectoResponseDTO response = tipoInsectoService.obtenerTipoInsectoPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tipo de insecto recuperado exitosamente"));
    }
}

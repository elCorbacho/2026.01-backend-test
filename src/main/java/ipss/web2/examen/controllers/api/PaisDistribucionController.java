package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.PaisDistribucionRequestDTO;
import ipss.web2.examen.dtos.PaisDistribucionResponseDTO;
import ipss.web2.examen.services.PaisDistribucionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
public class PaisDistribucionController {

    private final PaisDistribucionService paisService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaisDistribucionResponseDTO>> crearPais(
            @Valid @RequestBody PaisDistribucionRequestDTO requestDTO) {
        PaisDistribucionResponseDTO response = paisService.crearPais(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "País creado exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PaisDistribucionResponseDTO>> obtenerPorId(@PathVariable Long id) {
        PaisDistribucionResponseDTO response = paisService.obtenerPaisPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "País recuperado exitosamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<PaisDistribucionResponseDTO>>> listarPaises() {
        List<PaisDistribucionResponseDTO> response = paisService.obtenerTodosLosPaises();
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Países recuperados exitosamente"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<PaisDistribucionResponseDTO>>> buscarPorNombre(@RequestParam String nombre) {
        List<PaisDistribucionResponseDTO> response = paisService.buscarPorNombre(nombre);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Búsqueda completada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PaisDistribucionResponseDTO>> actualizarPais(
            @PathVariable Long id,
            @Valid @RequestBody PaisDistribucionRequestDTO requestDTO) {
        PaisDistribucionResponseDTO response = paisService.actualizarPais(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "País actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarPais(@PathVariable Long id) {
        paisService.eliminarPais(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("País con ID: " + id + " ha sido marcado como inactivo"));
    }
}

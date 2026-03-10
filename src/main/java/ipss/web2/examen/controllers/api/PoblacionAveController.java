package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.PoblacionAveRequestDTO;
import ipss.web2.examen.dtos.PoblacionAveResponseDTO;
import ipss.web2.examen.services.PoblacionAveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("null")
@RestController
@RequestMapping("/api/poblaciones-ave")
@RequiredArgsConstructor
public class PoblacionAveController {

    private final PoblacionAveService poblacionAveService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PoblacionAveResponseDTO>> crearPoblacionAve(
            @Valid @RequestBody PoblacionAveRequestDTO requestDTO) {
        PoblacionAveResponseDTO response = poblacionAveService.crearPoblacionAve(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Poblacion de ave creada correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PoblacionAveResponseDTO>> obtenerPoblacionAvePorId(@PathVariable Long id) {
        PoblacionAveResponseDTO response = poblacionAveService.obtenerPoblacionAvePorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Poblacion de ave recuperada correctamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<PoblacionAveResponseDTO>>> obtenerPoblacionesAve(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Long tipoAveId) {
        Page<PoblacionAveResponseDTO> response = poblacionAveService.obtenerPoblacionesPaginadas(page, size, tipoAveId);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Poblaciones de ave recuperadas exitosamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<PoblacionAveResponseDTO>> actualizarPoblacionAve(
            @PathVariable Long id,
            @Valid @RequestBody PoblacionAveRequestDTO requestDTO) {
        PoblacionAveResponseDTO response = poblacionAveService.actualizarPoblacionAve(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Poblacion de ave actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarPoblacionAve(@PathVariable Long id) {
        poblacionAveService.eliminarPoblacionAve(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Poblacion de ave con ID: " + id + " ha sido marcada como inactiva"));
    }
}

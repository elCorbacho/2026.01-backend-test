package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.TipoAveRequestDTO;
import ipss.web2.examen.dtos.TipoAveResponseDTO;
import ipss.web2.examen.services.TipoAveService;
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
@RequestMapping("/api/tipos-ave")
@RequiredArgsConstructor
public class TipoAveController {

    private final TipoAveService tipoAveService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> crearTipoAve(
            @Valid @RequestBody TipoAveRequestDTO requestDTO) {
        TipoAveResponseDTO response = tipoAveService.crearTipoAve(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Tipo de ave creado correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> obtenerTipoAvePorId(@PathVariable Long id) {
        TipoAveResponseDTO response = tipoAveService.obtenerTipoAvePorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tipo de ave recuperado correctamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<Page<TipoAveResponseDTO>>> obtenerTiposAve(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        Page<TipoAveResponseDTO> response = tipoAveService.obtenerTiposAvePaginados(page, size);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tipos de ave recuperados exitosamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TipoAveResponseDTO>> actualizarTipoAve(
            @PathVariable Long id,
            @Valid @RequestBody TipoAveRequestDTO requestDTO) {
        TipoAveResponseDTO response = tipoAveService.actualizarTipoAve(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tipo de ave actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarTipoAve(@PathVariable Long id) {
        tipoAveService.eliminarTipoAve(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Tipo de ave con ID: " + id + " ha sido marcado como inactivo"));
    }
}

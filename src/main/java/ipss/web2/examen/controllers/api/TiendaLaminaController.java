package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.TiendaLaminaRequestDTO;
import ipss.web2.examen.dtos.TiendaLaminaResponseDTO;
import ipss.web2.examen.services.TiendaLaminaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de ejemplo para tiendas que venden láminas.
 * Ruta base: /api/tiendas-laminas
 */
@RestController
@RequestMapping("/api/tiendas-laminas")
@RequiredArgsConstructor
public class TiendaLaminaController {

    private final TiendaLaminaService tiendaLaminaService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<TiendaLaminaResponseDTO>> crearTienda(
            @Valid @RequestBody TiendaLaminaRequestDTO requestDTO) {
        TiendaLaminaResponseDTO response = tiendaLaminaService.crearTienda(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Tienda de láminas creada exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TiendaLaminaResponseDTO>> obtenerTiendaPorId(
            @PathVariable Long id) {
        TiendaLaminaResponseDTO response = tiendaLaminaService.obtenerTiendaPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tienda obtenida correctamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<TiendaLaminaResponseDTO>>> obtenerTodasLasTiendas() {
        List<TiendaLaminaResponseDTO> tiendas = tiendaLaminaService.obtenerTodasLasTiendas();
        return ResponseEntity.ok(ApiResponseDTO.ok(tiendas, "Tiendas activas obtenidas correctamente"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<TiendaLaminaResponseDTO>>> buscarPorCiudad(
            @RequestParam String ciudad) {
        List<TiendaLaminaResponseDTO> tiendas = tiendaLaminaService.buscarPorCiudad(ciudad);
        return ResponseEntity.ok(ApiResponseDTO.ok(tiendas, "Búsqueda por ciudad completada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<TiendaLaminaResponseDTO>> actualizarTienda(
            @PathVariable Long id,
            @Valid @RequestBody TiendaLaminaRequestDTO requestDTO) {
        TiendaLaminaResponseDTO response = tiendaLaminaService.actualizarTienda(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Tienda actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarTienda(@PathVariable Long id) {
        tiendaLaminaService.eliminarTienda(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Tienda con ID: " + id + " ha sido marcada como inactiva"));
    }
}


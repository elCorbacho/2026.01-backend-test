package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.ListadoOlimpiadasRequestDTO;
import ipss.web2.examen.dtos.ListadoOlimpiadasResponseDTO;
import ipss.web2.examen.services.ListadoOlimpiadasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para listado de olimpiadas - /api/listado-olimpiadas
@SuppressWarnings("null")
@RestController
@RequestMapping("/api/listado-olimpiadas")
@RequiredArgsConstructor
public class ListadoOlimpiadasController {

    private final ListadoOlimpiadasService listadoOlimpiadasService;

    // POST /api/listado-olimpiadas - Crear nuevo listado de olimpiadas
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ListadoOlimpiadasResponseDTO>> crearListadoOlimpiadas(
            @Valid @RequestBody ListadoOlimpiadasRequestDTO requestDTO) {
        ListadoOlimpiadasResponseDTO creada = listadoOlimpiadasService.crearListadoOlimpiadas(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(creada, "Listado de olimpiadas creado correctamente"));
    }

    // GET /api/listado-olimpiadas - Listar listados de olimpiadas activos
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ListadoOlimpiadasResponseDTO>>> obtenerListadoOlimpiadas() {
        List<ListadoOlimpiadasResponseDTO> listado = listadoOlimpiadasService.obtenerListadoOlimpiadas();
        return ResponseEntity.ok(ApiResponseDTO.ok(listado, "Listado de olimpiadas recuperado exitosamente"));
    }

    // GET /api/listado-olimpiadas/{id} - Obtener listado de olimpiadas por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ListadoOlimpiadasResponseDTO>> obtenerListadoOlimpiadasPorId(
            @PathVariable Long id) {
        ListadoOlimpiadasResponseDTO listado = listadoOlimpiadasService.obtenerListadoOlimpiadasPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(listado, "Listado de olimpiadas recuperado correctamente"));
    }

    // PUT /api/listado-olimpiadas/{id} - Actualizar listado de olimpiadas
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<ListadoOlimpiadasResponseDTO>> actualizarListadoOlimpiadas(
            @PathVariable Long id,
            @Valid @RequestBody ListadoOlimpiadasRequestDTO requestDTO) {
        ListadoOlimpiadasResponseDTO actualizado = listadoOlimpiadasService.actualizarListadoOlimpiadas(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(actualizado, "Listado de olimpiadas actualizado correctamente"));
    }

    // DELETE /api/listado-olimpiadas/{id} - Eliminar listado de olimpiadas (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarListadoOlimpiadas(@PathVariable Long id) {
        listadoOlimpiadasService.eliminarListadoOlimpiadas(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(
                "Listado de olimpiadas con ID: " + id + " ha sido marcado como inactivo"));
    }
}

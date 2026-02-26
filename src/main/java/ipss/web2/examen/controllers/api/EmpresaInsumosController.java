package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.EmpresaInsumosRequestDTO;
import ipss.web2.examen.dtos.EmpresaInsumosResponseDTO;
import ipss.web2.examen.services.EmpresaInsumosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST de ejemplo para empresas que venden insumos.
 * Ruta base: /api/empresas-insumos
 */
@RestController
@RequestMapping("/api/empresas-insumos")
@RequiredArgsConstructor
public class EmpresaInsumosController {

    private final EmpresaInsumosService empresaInsumosService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<EmpresaInsumosResponseDTO>> crearEmpresa(
            @Valid @RequestBody EmpresaInsumosRequestDTO requestDTO) {
        EmpresaInsumosResponseDTO response = empresaInsumosService.crearEmpresa(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(response, "Empresa de insumos creada exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EmpresaInsumosResponseDTO>> obtenerEmpresaPorId(
            @PathVariable Long id) {
        EmpresaInsumosResponseDTO response = empresaInsumosService.obtenerEmpresaPorId(id);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Empresa obtenida correctamente"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<EmpresaInsumosResponseDTO>>> obtenerTodasLasEmpresas() {
        List<EmpresaInsumosResponseDTO> empresas = empresaInsumosService.obtenerTodasLasEmpresas();
        return ResponseEntity.ok(ApiResponseDTO.ok(empresas, "Empresas activas obtenidas correctamente"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponseDTO<List<EmpresaInsumosResponseDTO>>> buscarPorRubro(
            @RequestParam String rubro) {
        List<EmpresaInsumosResponseDTO> empresas = empresaInsumosService.buscarPorRubro(rubro);
        return ResponseEntity.ok(ApiResponseDTO.ok(empresas, "Búsqueda por rubro completada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EmpresaInsumosResponseDTO>> actualizarEmpresa(
            @PathVariable Long id,
            @Valid @RequestBody EmpresaInsumosRequestDTO requestDTO) {
        EmpresaInsumosResponseDTO response = empresaInsumosService.actualizarEmpresa(id, requestDTO);
        return ResponseEntity.ok(ApiResponseDTO.ok(response, "Empresa actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> eliminarEmpresa(@PathVariable Long id) {
        empresaInsumosService.eliminarEmpresa(id);
        return ResponseEntity.ok(ApiResponseDTO.ok("Empresa con ID: " + id + " ha sido marcada como inactiva"));
    }
}


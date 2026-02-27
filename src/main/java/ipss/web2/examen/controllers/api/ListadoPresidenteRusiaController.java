package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaRequestDTO;
import ipss.web2.examen.dtos.ListadoPresidenteRusiaResponseDTO;
import ipss.web2.examen.services.ListadoPresidenteRusiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador REST para presidentes de Rusia - /api/listado-presidente-rusia
@SuppressWarnings("null")
@RestController
@RequestMapping("/api/listado-presidente-rusia")
@RequiredArgsConstructor
@Tag(name = "Presidentes de Rusia", description = "Endpoints para gestionar presidentes de Rusia")
public class ListadoPresidenteRusiaController {

    private final ListadoPresidenteRusiaService listadoPresidenteRusiaService;

    // GET /api/listado-presidente-rusia - Listar presidentes de Rusia
    @GetMapping
    @Operation(
            summary = "Listar presidentes de Rusia",
            description = "Obtiene el listado completo de presidentes de Rusia activos registrados en el sistema."
    )
    public ResponseEntity<ApiResponseDTO<List<ListadoPresidenteRusiaResponseDTO>>> obtenerPresidentesRusia() {
        List<ListadoPresidenteRusiaResponseDTO> presidentes = listadoPresidenteRusiaService.obtenerPresidentesRusia();
        return ResponseEntity.ok(ApiResponseDTO.ok(presidentes, "Presidentes de Rusia recuperados exitosamente"));
    }

    // POST /api/listado-presidente-rusia - Crear nuevo presidente de Rusia
    @PostMapping
    @Operation(
            summary = "Crear nuevo presidente de Rusia",
            description = "Registra un nuevo presidente de Rusia a partir de los datos enviados en el cuerpo de la solicitud."
    )
    public ResponseEntity<ApiResponseDTO<ListadoPresidenteRusiaResponseDTO>> crearPresidenteRusia(
            @Valid @RequestBody ListadoPresidenteRusiaRequestDTO requestDTO) {
        ListadoPresidenteRusiaResponseDTO creado = listadoPresidenteRusiaService.crearPresidenteRusia(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(creado, "Presidente de Rusia creado correctamente"));
    }
}

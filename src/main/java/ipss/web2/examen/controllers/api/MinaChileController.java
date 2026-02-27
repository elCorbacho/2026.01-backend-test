package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.MinaChileRequestDTO;
import ipss.web2.examen.dtos.MinaChileResponseDTO;
import ipss.web2.examen.services.MinaChileService;
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

// Controlador REST para minas de Chile - /api/minas-chile
@SuppressWarnings("null")
@RestController
@RequestMapping("/api/minas-chile")
@RequiredArgsConstructor
@Tag(name = "Minas de Chile", description = "Endpoints para gestionar minas ubicadas en Chile")
public class MinaChileController {

    private final MinaChileService minaChileService;

    // GET /api/minas-chile - Listar minas de Chile activas
    @GetMapping
    @Operation(
            summary = "Listar minas de Chile activas",
            description = "Obtiene el listado completo de minas de Chile activas registradas en el sistema."
    )
    public ResponseEntity<ApiResponseDTO<List<MinaChileResponseDTO>>> obtenerMinasChile() {
        List<MinaChileResponseDTO> minas = minaChileService.obtenerMinasActivas();
        return ResponseEntity.ok(
                ApiResponseDTO.ok(minas,
                        "Minas de Chile obtenidas correctamente. Total: " + minas.size())
        );
    }

    // POST /api/minas-chile - Crear nueva mina de Chile
    @PostMapping
    @Operation(
            summary = "Crear nueva mina de Chile",
            description = "Registra una nueva mina de Chile a partir de los datos enviados en el cuerpo de la solicitud."
    )
    public ResponseEntity<ApiResponseDTO<MinaChileResponseDTO>> crearMinaChile(
            @Valid @RequestBody MinaChileRequestDTO requestDTO) {
        MinaChileResponseDTO creada = minaChileService.crearMina(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.created(creada, "Mina de Chile creada correctamente"));
    }
}


package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.EfemerideRequestDTO;
import ipss.web2.examen.dtos.EfemerideResponseDTO;
import ipss.web2.examen.services.EfemerideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/efemerides-chile")
@RequiredArgsConstructor
@Tag(name = "Efemerides Chile", description = "Endpoints para efemerides de Chile")
public class EfemerideChileController {

    private final EfemerideService efemerideService;

    @GetMapping
    @Operation(summary = "Listar efemerides de Chile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Efemerides recuperadas",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    public ResponseEntity<ApiResponseDTO<List<EfemerideResponseDTO>>> listar() {
        List<EfemerideResponseDTO> items = efemerideService.listarEfemerides();
        return ResponseEntity.ok(ApiResponseDTO.ok(items, "Efemerides recuperadas"));
    }

    @PostMapping
    @Operation(summary = "Crear efemeride de Chile")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Efemeride creada",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    public ResponseEntity<ApiResponseDTO<EfemerideResponseDTO>> crear(@Valid @RequestBody EfemerideRequestDTO request) {
        EfemerideResponseDTO created = efemerideService.crearEfemeride(request);
        return ResponseEntity.status(201).body(ApiResponseDTO.created(created, "Efemeride creada"));
    }
}

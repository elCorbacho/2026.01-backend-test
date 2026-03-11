package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.dtos.ExoplanetaPageResponseDTO;
import ipss.web2.examen.dtos.ExoplanetaRequestDTO;
import ipss.web2.examen.dtos.ExoplanetaResponseDTO;
import ipss.web2.examen.services.ExoplanetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exoplanetas")
@RequiredArgsConstructor
@Tag(name = "Exoplanetas", description = "Endpoints para gestionar exoplanetas")
public class ExoplanetaController {
    
    private final ExoplanetaService exoplanetaService;


    @GetMapping
    @Operation(
        summary = "Obtener todos los exoplanetas",
        description = "Retorna una lista paginada de todos los exoplanetas activos ordenados por nombre"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Exoplanetas obtenidos exitosamente"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Parámetros de paginación inválidos"
    )
    public ApiResponseDTO<ExoplanetaPageResponseDTO> obtenerTodos(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size
    ) {
        ExoplanetaPageResponseDTO result = exoplanetaService.obtenerTodos(page, size);
        return ApiResponseDTO.ok(result, "Exoplanetas obtenidos exitosamente");
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener exoplaneta por ID",
        description = "Retorna un exoplaneta específico por su identificador"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Exoplaneta obtenido exitosamente"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Exoplaneta no encontrado"
    )
    public ApiResponseDTO<ExoplanetaResponseDTO> obtenerPorId(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "ID del exoplaneta",
            required = true,
            example = "1"
        )
        @org.springframework.web.bind.annotation.PathVariable Long id
    ) {
        ExoplanetaResponseDTO result = exoplanetaService.obtenerPorId(id);
        return ApiResponseDTO.ok(result, "Exoplaneta obtenido exitosamente");
    }

    @PostMapping
    @Operation(
        summary = "Crear nuevo exoplaneta",
        description = "Crea un nuevo registro de exoplaneta en el sistema"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Exoplaneta creado exitosamente"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos"
    )
    public ApiResponseDTO<ExoplanetaResponseDTO> crear(
        @Valid @RequestBody ExoplanetaRequestDTO requestDTO
    ) {
        ExoplanetaResponseDTO result = exoplanetaService.crear(requestDTO);
        return ApiResponseDTO.created(result, "Exoplaneta creado exitosamente");
    }

}

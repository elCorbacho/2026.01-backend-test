package ipss.web2.examen.services;

import ipss.web2.examen.dtos.ExoplanetaPageResponseDTO;
import ipss.web2.examen.dtos.ExoplanetaRequestDTO;
import ipss.web2.examen.dtos.ExoplanetaResponseDTO;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.ExoplanetaMapper;
import ipss.web2.examen.models.Exoplaneta;
import ipss.web2.examen.repositories.ExoplanetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones de exoplanetas.
 * Proporciona métodos para obtener exoplanetas con paginación y validación.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExoplanetaService
{
    private final ExoplanetaRepository exoplanetaRepository;
    private final ExoplanetaMapper exoplanetaMapper;

    /**
     * Obtiene todos los exoplanetas activos con paginación.
     * Los resultados se ordenan por nombre en orden ascendente.
     *
     * @param page número de página (0-indexed), por defecto 0
     * @param size tamaño de página, por defecto 10, debe estar entre 1 y 100
     * @return ExoplanetaPageResponseDTO con los exoplanetas paginados
     * @throws InvalidOperationException si page < 0 o size no está entre 1 y 100
     */
    public ExoplanetaPageResponseDTO obtenerTodos(Integer page, Integer size)
    {
        // Aplicar valores por defecto
        int pageNumber = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        // Validar parámetro page
        if (pageNumber < 0)
        {
            throw new InvalidOperationException(
                    "El parametro 'page' debe ser mayor o igual a 0",
                    "INVALID_PAGE_NUMBER"
            );
        }

        // Validar parámetro size
        if (pageSize < 1 || pageSize > 100)
        {
            throw new InvalidOperationException(
                    "El parametro 'size' debe estar entre 1 y 100",
                    "INVALID_PAGE_SIZE"
            );
        }

        // Crear Pageable con ordenamiento por nombre ascendente
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.ASC, "nombre")
        );

        // Consultar repositorio
        Page<Exoplaneta> exoplanetaPage = exoplanetaRepository.findByActiveTrueOrderByNombreAsc(pageable);

        // Mapear entidades a DTOs
        List<ExoplanetaResponseDTO> content = exoplanetaPage.getContent()
                .stream()
                .map(exoplanetaMapper::toResponseDTO)
                .collect(Collectors.toList());

        // Retornar respuesta paginada
        return new ExoplanetaPageResponseDTO(
                content,
                exoplanetaPage.getNumber(),
                exoplanetaPage.getSize(),
                exoplanetaPage.getTotalElements(),
                exoplanetaPage.getTotalPages()
        );
    }


    /**
     * Obtiene un exoplaneta por su identificador.
     *
     * @param id identificador del exoplaneta
     * @return ExoplanetaResponseDTO con los datos del exoplaneta
     * @throws ResourceNotFoundException si el exoplaneta no existe o está inactivo
     */
    public ExoplanetaResponseDTO obtenerPorId(Long id)
    {
        Exoplaneta exoplaneta = exoplanetaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exoplaneta", "id", id));

        return exoplanetaMapper.toResponseDTO(exoplaneta);
    }


    /**
     * Crea un nuevo exoplaneta.
     *
     * @param requestDTO datos del exoplaneta a crear
     * @return ExoplanetaResponseDTO con los datos del exoplaneta creado
     */
    @Transactional(readOnly = false)
    public ExoplanetaResponseDTO crear(ExoplanetaRequestDTO requestDTO)
    {
        // Convertir DTO a entidad (el mapper establece active=true)
        Exoplaneta exoplaneta = exoplanetaMapper.toEntity(requestDTO);

        // Persistir la entidad
        Exoplaneta exoplanetaGuardado = exoplanetaRepository.save(exoplaneta);

        // Mapear entidad guardada a DTO de respuesta
        return exoplanetaMapper.toResponseDTO(exoplanetaGuardado);
    }


}

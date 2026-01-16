package ipss.web2.examen.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**DTO genérico para envolver todas las respuestas de la API @param <T> Tipo de dato contenido en la respuesta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {
    
    // Indica si la operación fue exitosa
    private Boolean success;
    
    // Mensaje descriptivo de la operación
    private String message;
    
    //Datos de la respuesta (puede ser null en operaciones de eliminación)
    private T data;
    
    //Códigos de error en caso de fallo
    private String errorCode;
    
    // Detalles adicionales del error
    private Map<String, Object> errors;
    
    // Marca temporal de la respuesta
    private LocalDateTime timestamp;
}

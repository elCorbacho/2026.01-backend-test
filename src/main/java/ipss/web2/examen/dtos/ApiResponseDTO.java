package ipss.web2.examen.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO genérico para envolver todas las respuestas de la API.
 * @param <T> Tipo de dato contenido en la respuesta
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {

    private Boolean success;
    private String message;
    private T data;
    private String errorCode;
    private Map<String, Object> errors;
    private LocalDateTime timestamp;

    // ─── Factory methods ──────────────────────────────────────────────────────

    /** Respuesta 200 OK con datos */
    public static <T> ApiResponseDTO<T> ok(T data, String message) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /** Respuesta 201 Created con datos */
    public static <T> ApiResponseDTO<T> created(T data, String message) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /** Respuesta exitosa sin datos (p.ej. DELETE) */
    public static <T> ApiResponseDTO<T> ok(String message) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /** Respuesta de error */
    public static <T> ApiResponseDTO<T> error(String message, String errorCode) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /** Respuesta de error con detalles de campos */
    public static <T> ApiResponseDTO<T> error(String message, String errorCode, Map<String, Object> errors) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

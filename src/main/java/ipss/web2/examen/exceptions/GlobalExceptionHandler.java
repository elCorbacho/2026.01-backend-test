package ipss.web2.examen.exceptions;

import ipss.web2.examen.dtos.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Manejador global de excepciones para la aplicación
@SuppressWarnings("null")
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /// Maneja excepciones de recurso no encontrado (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message(ex.getMessage())
            .errorCode("RESOURCE_NOT_FOUND")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    // Maneja excepciones de operación inválida (400/422)
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidOperationException(
            InvalidOperationException ex, WebRequest request) {
        
        log.warn("Operación inválida: {}", ex.getMessage());
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message(ex.getMessage())
            .errorCode(ex.getErrorCode() != null ? ex.getErrorCode() : "INVALID_OPERATION")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    // Maneja errores de validación de datos (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        Map<String, Object> errorDetails = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorDetails.put(fieldName, errorMessage);
        });
        
        log.warn("Error de validación: {}", errorDetails);
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message("Error de validación en los datos enviados")
            .errorCode("VALIDATION_ERROR")
            .errors(errorDetails)
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    // Maneja errores de conversión de tipos de parámetros (400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String fieldName = ex.getName();
        String expectedType = ex.getRequiredType() != null ? 
            ex.getRequiredType().getSimpleName() : "unknown";
        String receivedValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(fieldName, String.format(
            "Tipo inválido. Esperado: %s, Recibido: %s", expectedType, receivedValue));
        
        log.warn("Error de tipo de parámetro: campo={}, tipo esperado={}, valor={}", 
            fieldName, expectedType, receivedValue);
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message("Parámetro inválido: " + fieldName)
            .errorCode("INVALID_PARAMETER_TYPE")
            .errors(errorDetails)
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    // Maneja violaciones de restricciones de base de datos (409)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        
        log.error("Error de integridad de datos", ex);
        
        String message = "Error al procesar los datos. Verifica que no hay duplicados o referencias inválidas.";
        if (ex.getMessage() != null && ex.getMessage().contains("UNIQUE constraint failed")) {
            message = "Ya existe un registro con estos datos (violación de unicidad)";
        }
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message(message)
            .errorCode("DATA_INTEGRITY_VIOLATION")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    // Maneja excepciones de endpoint no encontrado (404)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleNoHandlerFound(
            NoHandlerFoundException ex, WebRequest request) {
        
        log.warn("Endpoint no encontrado: {} {}", ex.getHttpMethod(), ex.getRequestURL());
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("metodo", ex.getHttpMethod());
        errorDetails.put("ruta", ex.getRequestURL());
        errorDetails.put("tipo", "Endpoint no existe");
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message("Endpoint inválido: " + ex.getHttpMethod() + " " + ex.getRequestURL())
            .errorCode("ENDPOINT_NOT_FOUND")
            .errors(errorDetails)
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    // Maneja excepciones de endpoint personalizado no encontrado (404)
    @ExceptionHandler(EndpointNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEndpointNotFoundException(
            EndpointNotFoundException ex, WebRequest request) {
        
        log.warn("Endpoint personalizado no encontrado: {}", ex.getMessage());
        
        Map<String, Object> errorDetails = new HashMap<>();
        if (ex.getMethod() != null) {
            errorDetails.put("metodo", ex.getMethod());
        }
        if (ex.getPath() != null) {
            errorDetails.put("ruta", ex.getPath());
        }
        errorDetails.put("tipo", "Endpoint inválido o no soportado");
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message(ex.getMessage())
            .errorCode("INVALID_ENDPOINT")
            .errors(errorDetails)
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    // Maneja excepciones de tiempo de ejecución genéricas (400/404)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        
        log.error("RuntimeException capturada: {}", ex.getMessage(), ex);
        
        // Determinar si es un error de lámina no en catálogo
        String message = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = "RUNTIME_ERROR";
        
        if (message != null && message.contains("NO existe en el catálogo")) {
            errorCode = "LAMINA_NOT_IN_CATALOG";
            status = HttpStatus.BAD_REQUEST;
        } else if (message != null && message.contains("no encontrado")) {
            errorCode = "RESOURCE_NOT_FOUND";
            status = HttpStatus.NOT_FOUND;
        } else if (message != null && message.contains("obligatorio")) {
            errorCode = "VALIDATION_ERROR";
            status = HttpStatus.BAD_REQUEST;
        }
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message(message != null ? message : "Error en tiempo de ejecución")
            .errorCode(errorCode)
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(status).body(response);
    }
    
    // Maneja excepciones genéricas no capturadas (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGlobalException(
            Exception ex, WebRequest request) {
        
        log.error("Error interno del servidor no manejado", ex);
        
        ApiResponseDTO<Object> response = ApiResponseDTO.builder()
            .success(false)
            .message("Error interno del servidor: " + ex.getClass().getSimpleName())
            .errorCode("INTERNAL_SERVER_ERROR")
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

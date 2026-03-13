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
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Manejador global de excepciones para la aplicación

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String GENERIC_INTERNAL_ERROR_MESSAGE = "Ocurrió un error interno. Intenta nuevamente más tarde.";
    private static final String INTERNAL_ERROR_CODE = "INTERNAL_SERVER_ERROR";
    private static final String VALIDATION_ERROR_CODE = "VALIDATION_ERROR";
    private static final String INVALID_OPERATION_ERROR_CODE = "INVALID_OPERATION";
    private static final String RESOURCE_NOT_FOUND_ERROR_CODE = "RESOURCE_NOT_FOUND";
    private static final String INVALID_PARAMETER_TYPE_ERROR_CODE = "INVALID_PARAMETER_TYPE";
    private static final String DATA_INTEGRITY_ERROR_CODE = "DATA_INTEGRITY_VIOLATION";
    private static final String ENDPOINT_NOT_FOUND_ERROR_CODE = "ENDPOINT_NOT_FOUND";
    
    // Maneja excepciones de recurso no encontrado (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : RESOURCE_NOT_FOUND_ERROR_CODE;

        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), errorCode);
    }
    
    // Maneja excepciones de operación inválida (400/422)
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleInvalidOperationException(
            InvalidOperationException ex, WebRequest request) {
        
        log.warn("Operación inválida: {}", ex.getMessage());
        
        String errorCode = ex.getErrorCode() != null ? ex.getErrorCode() : INVALID_OPERATION_ERROR_CODE;

        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), errorCode);
    }
    
    // Maneja errores de validación de datos (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, Object> errorDetails = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorName;
            if (error instanceof FieldError fieldError) {
                errorName = fieldError.getField();
            } else {
                errorName = error.getObjectName();
            }

            String errorMessage = error.getDefaultMessage();
            errorDetails.put(errorName, errorMessage);
        });

        log.warn("Error de validación: {}", errorDetails);

        return buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Error de validación en los datos enviados",
            VALIDATION_ERROR_CODE,
            null
        );
    }
    
    // Maneja errores de conversión de tipos de parámetros (400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String fieldName = ex.getName();
        Class<?> requiredType = ex.getRequiredType();
        Object rawValue = ex.getValue();
        String expectedType = requiredType != null ? requiredType.getSimpleName() : "unknown";
        String receivedValue = Objects.toString(rawValue, "null");
        
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put(fieldName, String.format(
            "Tipo inválido. Esperado: %s, Recibido: %s", expectedType, receivedValue));
        
        log.warn("Error de tipo de parámetro: campo={}, tipo esperado={}, valor={}", 
            fieldName, expectedType, receivedValue);
        
        return buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            "Parámetro inválido: " + fieldName,
            INVALID_PARAMETER_TYPE_ERROR_CODE,
            errorDetails
        );
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
        
        return buildErrorResponse(HttpStatus.CONFLICT, message, DATA_INTEGRITY_ERROR_CODE);
    }
    
    // Maneja excepciones de endpoint no encontrado (404)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleNoHandlerFound(
            NoHandlerFoundException ex, WebRequest request) {

        log.warn("Endpoint no encontrado: {} {}", ex.getHttpMethod(), ex.getRequestURL());

        return buildErrorResponse(
            HttpStatus.NOT_FOUND,
            "El endpoint solicitado no existe",
            ENDPOINT_NOT_FOUND_ERROR_CODE
        );
    }

    // Maneja recursos estaticos o rutas no encontradas reportadas por Spring MVC (404)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleNoResourceFoundException(
            NoResourceFoundException ex, WebRequest request) {

        log.warn("Recurso no encontrado por handler estatico: {}", ex.getResourcePath());

        return buildErrorResponse(
            HttpStatus.NOT_FOUND,
            "El recurso solicitado no fue encontrado",
            ENDPOINT_NOT_FOUND_ERROR_CODE
        );
    }
    
    // Maneja excepciones de endpoint personalizado no encontrado (404)
    @ExceptionHandler(EndpointNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEndpointNotFoundException(
            EndpointNotFoundException ex, WebRequest request) {

        log.warn("Endpoint personalizado no encontrado: {}", ex.getMessage());

        return buildErrorResponse(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            ENDPOINT_NOT_FOUND_ERROR_CODE
        );
    }
    
    // Maneja excepciones de tiempo de ejecución genéricas no cubiertas por handlers específicos (400)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        logUnhandledExceptionSafely("RuntimeException no manejada", ex, request);

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, GENERIC_INTERNAL_ERROR_MESSAGE, INTERNAL_ERROR_CODE);
    }
    
    // Maneja excepciones genéricas no capturadas (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGlobalException(
            Exception ex, WebRequest request) {
        logUnhandledExceptionSafely("Excepción no manejada", ex, request);

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, GENERIC_INTERNAL_ERROR_MESSAGE, INTERNAL_ERROR_CODE);
    }

    private ResponseEntity<ApiResponseDTO<Object>> buildErrorResponse(HttpStatus status, String message, String errorCode) {
        return ResponseEntity
            .status(status)
            .body(ApiResponseDTO.error(message, errorCode));
    }

    private ResponseEntity<ApiResponseDTO<Object>> buildErrorResponse(
            HttpStatus status,
            String message,
            String errorCode,
            Map<String, Object> errors) {
        return ResponseEntity
            .status(status)
            .body(ApiResponseDTO.error(message, errorCode, errors));
    }

    private void logUnhandledExceptionSafely(String logPrefix, Exception ex, WebRequest request) {
        try {
            writeUnhandledExceptionLog(logPrefix, ex, request);
        } catch (RuntimeException loggingFailure) {
            // Evita que un fallo en logging cambie la respuesta saneada al cliente.
        }
    }

    protected void writeUnhandledExceptionLog(String logPrefix, Exception ex, WebRequest request) {
        String requestContext = request != null ? request.getDescription(false) : "N/A";
        String handlerType = ex.getClass().getSimpleName();
        log.error("{}. handlerType={}, requestContext={}, message={}",
                logPrefix, handlerType, requestContext, ex.getMessage(), ex);
    }
}


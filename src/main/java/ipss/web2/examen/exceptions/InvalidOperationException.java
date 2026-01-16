package ipss.web2.examen.exceptions;

// Excepción personalizada para manejar operaciones inválidas
public class InvalidOperationException extends RuntimeException {
    
    private String errorCode;
    
    public InvalidOperationException(String message) {
        super(message);
    }
    
    public InvalidOperationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}

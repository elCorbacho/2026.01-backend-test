package ipss.web2.examen.exceptions;

import ipss.web2.examen.dtos.ApiResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("RuntimeException: si falla logging igual retorna respuesta saneada")
    void handleRuntimeExceptionShouldReturnSanitizedResponseWhenLoggingFails() {
        GlobalExceptionHandler handler = new LoggingFailingHandler();
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/albums");

        ResponseEntity<ApiResponseDTO<Object>> response =
                handler.handleRuntimeException(new RuntimeException("detalle interno"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSuccess()).isFalse();
        assertThat(response.getBody().getErrorCode()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().getMessage())
                .isEqualTo("Ocurrió un error interno. Intenta nuevamente más tarde.");
    }

    @Test
    @DisplayName("Exception: si falla logging igual retorna respuesta saneada")
    void handleGlobalExceptionShouldReturnSanitizedResponseWhenLoggingFails() {
        GlobalExceptionHandler handler = new LoggingFailingHandler();
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/api/albums");

        ResponseEntity<ApiResponseDTO<Object>> response =
                handler.handleGlobalException(new Exception("detalle interno"), request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSuccess()).isFalse();
        assertThat(response.getBody().getErrorCode()).isEqualTo("INTERNAL_SERVER_ERROR");
        assertThat(response.getBody().getMessage())
                .isEqualTo("Ocurrió un error interno. Intenta nuevamente más tarde.");
    }

    private static final class LoggingFailingHandler extends GlobalExceptionHandler {
        @Override
        protected void writeUnhandledExceptionLog(String logPrefix, Exception ex, WebRequest request) {
            throw new IllegalStateException("logger unavailable");
        }
    }
}

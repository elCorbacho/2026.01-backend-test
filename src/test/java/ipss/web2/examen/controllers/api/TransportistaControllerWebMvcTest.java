package ipss.web2.examen.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ipss.web2.examen.dtos.TransportistaRequestDTO;
import ipss.web2.examen.dtos.TransportistaResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.TransportistaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de capa web para {@link TransportistaController}.
 * Cubre: happy path, validación de request, 404 not found, y soft-delete.
 */
@WebMvcTest(TransportistaController.class)
@Import(GlobalExceptionHandler.class)
class TransportistaControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransportistaService transportistaService;

    // ─── GET /api/transportistas ──────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/transportistas debe responder 200 con lista de transportistas")
    void listarTransportistasDebeResponderOk() throws Exception {
        when(transportistaService.obtenerTodos()).thenReturn(List.of(
                TransportistaResponseDTO.builder()
                        .id(1L)
                        .nombre("Juan Perez")
                        .empresa("Transporte Universal SA")
                        .contacto("+12345678")
                        .build()
        ));

        mockMvc.perform(get("/api/transportistas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transportistas obtenidos exitosamente"))
                .andExpect(jsonPath("$.data[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.data[0].empresa").value("Transporte Universal SA"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    // ─── GET /api/transportistas/{id} ────────────────────────────────────────

    @Test
    @DisplayName("GET /api/transportistas/{id} debe responder 200 cuando el transportista existe")
    void obtenerTransportistaPorIdDebeResponderOk() throws Exception {
        when(transportistaService.obtenerPorId(1L)).thenReturn(
                TransportistaResponseDTO.builder()
                        .id(1L)
                        .nombre("Maria Gomez")
                        .empresa("Logística Rápida")
                        .contacto("+87654321")
                        .build()
        );

        mockMvc.perform(get("/api/transportistas/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nombre").value("Maria Gomez"))
                .andExpect(jsonPath("$.data.empresa").value("Logística Rápida"));
    }

    @Test
    @DisplayName("GET /api/transportistas/{id} debe responder 404 cuando el transportista no existe")
    void obtenerTransportistaPorIdDebeResponder404() throws Exception {
        when(transportistaService.obtenerPorId(99L))
                .thenThrow(new ResourceNotFoundException("Transportista", "ID", 99L));

        mockMvc.perform(get("/api/transportistas/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    // ─── POST /api/transportistas ─────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/transportistas debe responder 201 con el transportista creado")
    void crearTransportistaDebeResponder201() throws Exception {
        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("Pedro López")
                .empresa("Fast Delivery")
                .contacto("+56911223344")
                .build();

        when(transportistaService.crear(any(TransportistaRequestDTO.class))).thenReturn(
                TransportistaResponseDTO.builder()
                        .id(10L)
                        .nombre("Pedro López")
                        .empresa("Fast Delivery")
                        .contacto("+56911223344")
                        .build()
        );

        mockMvc.perform(post("/api/transportistas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transportista creado exitosamente"))
                .andExpect(jsonPath("$.data.id").value(10))
                .andExpect(jsonPath("$.data.nombre").value("Pedro López"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("POST /api/transportistas debe responder 400 cuando faltan campos obligatorios")
    void crearTransportistaDebeResponder400CuandoBodyInvalido() throws Exception {
        mockMvc.perform(post("/api/transportistas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"));

        verify(transportistaService, never()).crear(any());
    }

    // ─── PUT /api/transportistas/{id} ────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/transportistas/{id} debe responder 200 con el transportista actualizado")
    void actualizarTransportistaDebeResponder200() throws Exception {
        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("Nombre Actualizado")
                .empresa("Empresa Nueva")
                .contacto("+111")
                .build();

        when(transportistaService.actualizar(eq(3L), any(TransportistaRequestDTO.class))).thenReturn(
                TransportistaResponseDTO.builder()
                        .id(3L)
                        .nombre("Nombre Actualizado")
                        .empresa("Empresa Nueva")
                        .contacto("+111")
                        .build()
        );

        mockMvc.perform(put("/api/transportistas/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transportista actualizado exitosamente"))
                .andExpect(jsonPath("$.data.nombre").value("Nombre Actualizado"));
    }

    @Test
    @DisplayName("PUT /api/transportistas/{id} debe responder 404 cuando el transportista no existe")
    void actualizarTransportistaDebeResponder404() throws Exception {
        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("X").empresa("Y").build();

        when(transportistaService.actualizar(eq(999L), any()))
                .thenThrow(new ResourceNotFoundException("Transportista", "ID", 999L));

        mockMvc.perform(put("/api/transportistas/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }

    // ─── DELETE /api/transportistas/{id} ─────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/transportistas/{id} debe responder 200 tras soft-delete exitoso")
    void eliminarTransportistaDebeResponder200() throws Exception {
        doNothing().when(transportistaService).eliminar(5L);

        mockMvc.perform(delete("/api/transportistas/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Transportista eliminado exitosamente"));

        verify(transportistaService).eliminar(5L);
    }

    @Test
    @DisplayName("DELETE /api/transportistas/{id} debe responder 404 cuando no existe")
    void eliminarTransportistaDebeResponder404() throws Exception {
        doThrow(new ResourceNotFoundException("Transportista", "ID", 404L))
                .when(transportistaService).eliminar(404L);

        mockMvc.perform(delete("/api/transportistas/{id}", 404))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false));
    }
}


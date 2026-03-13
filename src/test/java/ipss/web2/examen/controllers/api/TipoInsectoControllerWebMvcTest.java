package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.TipoInsectoResponseDTO;
import ipss.web2.examen.exceptions.GlobalExceptionHandler;
import ipss.web2.examen.exceptions.InvalidOperationException;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.services.TipoInsectoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TipoInsectoController.class)
@Import(GlobalExceptionHandler.class)
class TipoInsectoControllerWebMvcTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TipoInsectoService tipoInsectoService;

    @Test
    @DisplayName("GET /api/tipos-insecto responde con listado activo")
    void obtenerTiposInsectoDebeResponderOk() throws Exception
    {
        TipoInsectoResponseDTO abeja = new TipoInsectoResponseDTO(
                1L,
                "Abeja",
                "Insecto polinizador",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(tipoInsectoService.obtenerTiposInsecto()).thenReturn(List.of(abeja));

        mockMvc.perform(get("/api/tipos-insecto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Tipos de insecto recuperados exitosamente"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].nombre").value("Abeja"));
    }

    @Test
    @DisplayName("GET /api/tipos-insecto responde lista vacia cuando no hay activos")
    void obtenerTiposInsectoDebeResponderColeccionVacia() throws Exception
    {
        when(tipoInsectoService.obtenerTiposInsecto()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/tipos-insecto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("GET /api/tipos-insecto/{id} responde 200 cuando existe")
    void obtenerTipoInsectoPorIdDebeResponderOk() throws Exception
    {
        TipoInsectoResponseDTO abeja = new TipoInsectoResponseDTO(
                1L,
                "Abeja",
                "Insecto polinizador",
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(tipoInsectoService.obtenerTipoInsectoPorId(1L)).thenReturn(abeja);

        mockMvc.perform(get("/api/tipos-insecto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Tipo de insecto recuperado exitosamente"))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/tipos-insecto/{id} responde 404 cuando no existe")
    void obtenerTipoInsectoPorIdDebeResponderNotFound() throws Exception
    {
        when(tipoInsectoService.obtenerTipoInsectoPorId(999L))
                .thenThrow(new ResourceNotFoundException("TipoInsecto", "id", 999L, "TIPO_INSECTO_NOT_FOUND"));

        mockMvc.perform(get("/api/tipos-insecto/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("TIPO_INSECTO_NOT_FOUND"));
    }

    @Test
    @DisplayName("GET /api/tipos-insecto/{id} responde 400 para id con tipo invalido")
    void obtenerTipoInsectoPorIdDebeResponderBadRequestPorTipoInvalido() throws Exception
    {
        mockMvc.perform(get("/api/tipos-insecto/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INVALID_PARAMETER_TYPE"));
    }

    @Test
    @DisplayName("GET /api/tipos-insecto/{id} responde 400 para id menor a 1")
    void obtenerTipoInsectoPorIdDebeResponderBadRequestPorRangoInvalido() throws Exception
    {
        when(tipoInsectoService.obtenerTipoInsectoPorId(0L))
                .thenThrow(new InvalidOperationException("El identificador debe ser mayor o igual a 1", "INVALID_INSECTO_ID"));

        mockMvc.perform(get("/api/tipos-insecto/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("INVALID_INSECTO_ID"));
    }
}


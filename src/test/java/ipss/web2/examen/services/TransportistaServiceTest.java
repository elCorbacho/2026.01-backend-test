package ipss.web2.examen.services;

import ipss.web2.examen.dtos.TransportistaRequestDTO;
import ipss.web2.examen.dtos.TransportistaResponseDTO;
import ipss.web2.examen.exceptions.ResourceNotFoundException;
import ipss.web2.examen.mappers.TransportistaMapper;
import ipss.web2.examen.models.Transportista;
import ipss.web2.examen.repositories.TransportistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios del servicio {@link TransportistaService}.
 * El repositorio está mockeado; el mapper es real para validar integración DTO↔entidad.
 */
@ExtendWith(MockitoExtension.class)
class TransportistaServiceTest {

    @Mock
    private TransportistaRepository transportistaRepository;

    private TransportistaService transportistaService;

    @BeforeEach
    void setUp() {
        // Mapper real (sin mock) para detectar problemas de mapeo
        transportistaService = new TransportistaService(transportistaRepository, new TransportistaMapper());
    }

    // ─── obtenerTodos ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerTodos debe devolver lista de DTOs de los transportistas activos")
    void obtenerTodosDebeRetornarListaDeTransportistasActivos() {
        Transportista t1 = Transportista.builder().id(1L).nombre("Juan").empresa("Emp A").contacto("+1").active(true).build();
        Transportista t2 = Transportista.builder().id(2L).nombre("Maria").empresa("Emp B").contacto("+2").active(true).build();
        when(transportistaRepository.findByActiveTrue()).thenReturn(List.of(t1, t2));

        List<TransportistaResponseDTO> resultado = transportistaService.obtenerTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Juan");
        assertThat(resultado.get(1).getNombre()).isEqualTo("Maria");
        verify(transportistaRepository).findByActiveTrue();
    }

    @Test
    @DisplayName("obtenerTodos debe retornar lista vacía cuando no hay transportistas activos")
    void obtenerTodosDebeRetornarListaVaciaWhenNoHayActivos() {
        when(transportistaRepository.findByActiveTrue()).thenReturn(List.of());

        List<TransportistaResponseDTO> resultado = transportistaService.obtenerTodos();

        assertThat(resultado).isEmpty();
    }

    // ─── obtenerPorId ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("obtenerPorId debe devolver DTO cuando el transportista existe y está activo")
    void obtenerPorIdDebeRetornarDTOCuandoExiste() {
        Transportista t = Transportista.builder().id(5L).nombre("Carlos").empresa("LogiX").contacto("+3").active(true).build();
        when(transportistaRepository.findByIdAndActiveTrue(5L)).thenReturn(Optional.of(t));

        TransportistaResponseDTO resultado = transportistaService.obtenerPorId(5L);

        assertThat(resultado.getId()).isEqualTo(5L);
        assertThat(resultado.getNombre()).isEqualTo("Carlos");
        assertThat(resultado.getEmpresa()).isEqualTo("LogiX");
    }

    @Test
    @DisplayName("obtenerPorId debe lanzar ResourceNotFoundException cuando el ID no existe")
    void obtenerPorIdDebeLanzarExcepcionCuandoNoExiste() {
        when(transportistaRepository.findByIdAndActiveTrue(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transportistaService.obtenerPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Transportista")
                .hasMessageContaining("99");
    }

    // ─── crear ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("crear debe persistir un nuevo transportista y devolver su DTO")
    void crearDebeGuardarTransportistaYRetornarDTO() {
        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("Pedro López")
                .empresa("Fast Delivery")
                .contacto("+56911223344")
                .build();

        Transportista saved = Transportista.builder()
                .id(10L)
                .nombre("Pedro López")
                .empresa("Fast Delivery")
                .contacto("+56911223344")
                .active(true)
                .build();
        when(transportistaRepository.save(any(Transportista.class))).thenReturn(saved);

        TransportistaResponseDTO resultado = transportistaService.crear(request);

        assertThat(resultado.getId()).isEqualTo(10L);
        assertThat(resultado.getNombre()).isEqualTo("Pedro López");
        assertThat(resultado.getEmpresa()).isEqualTo("Fast Delivery");
        verify(transportistaRepository).save(any(Transportista.class));
    }

    // ─── actualizar ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("actualizar debe modificar campos del transportista existente")
    void actualizarDebeModificarTransportistaExistente() {
        Transportista existing = Transportista.builder().id(3L).nombre("Viejo").empresa("Old Co").active(true).build();
        when(transportistaRepository.findByIdAndActiveTrue(3L)).thenReturn(Optional.of(existing));
        when(transportistaRepository.save(any(Transportista.class))).thenAnswer(inv -> inv.getArgument(0));

        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("Nuevo Nombre")
                .empresa("New Co")
                .contacto("+999")
                .build();

        TransportistaResponseDTO resultado = transportistaService.actualizar(3L, request);

        assertThat(resultado.getNombre()).isEqualTo("Nuevo Nombre");
        assertThat(resultado.getEmpresa()).isEqualTo("New Co");
        assertThat(resultado.getContacto()).isEqualTo("+999");
        verify(transportistaRepository).save(existing);
    }

    @Test
    @DisplayName("actualizar debe lanzar ResourceNotFoundException cuando el ID no existe")
    void actualizarDebeLanzarExcepcionCuandoNoExiste() {
        when(transportistaRepository.findByIdAndActiveTrue(999L)).thenReturn(Optional.empty());

        TransportistaRequestDTO request = TransportistaRequestDTO.builder()
                .nombre("X").empresa("Y").build();

        assertThatThrownBy(() -> transportistaService.actualizar(999L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    // ─── eliminar ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("eliminar debe hacer soft-delete (active=false) del transportista")
    void eliminarDebeHacerSoftDelete() {
        Transportista existing = Transportista.builder().id(7L).nombre("Ana").empresa("Emp Z").active(true).build();
        when(transportistaRepository.findByIdAndActiveTrue(7L)).thenReturn(Optional.of(existing));
        when(transportistaRepository.save(any(Transportista.class))).thenAnswer(inv -> inv.getArgument(0));

        transportistaService.eliminar(7L);

        assertThat(existing.getActive()).isFalse();
        verify(transportistaRepository).save(existing);
    }

    @Test
    @DisplayName("eliminar debe lanzar ResourceNotFoundException cuando el transportista no existe")
    void eliminarDebeLanzarExcepcionCuandoNoExiste() {
        when(transportistaRepository.findByIdAndActiveTrue(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transportistaService.eliminar(404L))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(transportistaRepository, never()).save(any());
    }
}

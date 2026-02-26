package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.PedidoRequestDTO;
import ipss.web2.examen.dtos.PedidoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/pedidos")
@Validated
public class PedidosController {

    private final Map<Long, PedidoResponseDTO> store = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        Long id = idGen.getAndIncrement();
        PedidoResponseDTO resp = PedidoResponseDTO.builder()
                .id(id)
                .clienteNombre(request.getClienteNombre())
                .total(request.getTotal())
                .items(new ArrayList<>(request.getItems()))
                .createdAt(LocalDateTime.now())
                .build();

        store.put(id, resp);
        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Long id) {
        PedidoResponseDTO p = store.get(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(new ArrayList<>(store.values()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (store.remove(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/total")
    public ResponseEntity<PedidoResponseDTO> actualizarTotal(@PathVariable Long id,
                                                             @RequestBody Map<String, Object> body) {
        PedidoResponseDTO p = store.get(id);
        if (p == null) return ResponseEntity.notFound().build();
        Object totalObj = body.get("total");
        if (totalObj instanceof Number) {
            double total = ((Number) totalObj).doubleValue();
            PedidoResponseDTO updated = PedidoResponseDTO.builder()
                    .id(p.getId())
                    .clienteNombre(p.getClienteNombre())
                    .items(p.getItems())
                    .total(total)
                    .createdAt(p.getCreatedAt())
                    .build();
            store.put(id, updated);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().build();
    }
}

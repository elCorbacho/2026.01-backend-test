package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.models.TestModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

// Controlador de prueba para endpoints de test
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/sample")
    public ResponseEntity<ApiResponseDTO<TestModel>> sample() {
        TestModel t = TestModel.builder()
                .id(1L)
                .nombre("Entidad de prueba")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(ApiResponseDTO.ok(t, "Ejemplo de entidad de prueba"));
    }
}

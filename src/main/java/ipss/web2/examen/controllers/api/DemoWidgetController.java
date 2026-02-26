package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.models.DemoWidget;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/demo-widget")
public class DemoWidgetController {

    @GetMapping("/sample")
    public ResponseEntity<ApiResponseDTO<DemoWidget>> sample() {
        DemoWidget widget = DemoWidget.builder()
                .id(99L)
                .nombre("Demo widget ligero")
                .tipo("test-simple")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(ApiResponseDTO.ok(widget, "Widget de prueba"));
    }
}

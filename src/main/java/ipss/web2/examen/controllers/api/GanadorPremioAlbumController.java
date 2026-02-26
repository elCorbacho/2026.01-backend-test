package ipss.web2.examen.controllers.api;

import ipss.web2.examen.dtos.ApiResponseDTO;
import ipss.web2.examen.models.GanadorPremioAlbum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

// Controlador de prueba para ganadores del premio al album
@RestController
@RequestMapping("/api/ganadores-premio-album")
public class GanadorPremioAlbumController {

    @GetMapping("/sample")
    public ResponseEntity<ApiResponseDTO<GanadorPremioAlbum>> sample() {
        GanadorPremioAlbum ganador = GanadorPremioAlbum.builder()
                .id(1L)
                .artista("Taylor Swift")
                .album("Midnights")
                .premio("Album of the Year")
                .anio(2024)
                .genero("Pop")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(ApiResponseDTO.ok(ganador, "Ejemplo de ganador del premio al album"));
    }
}

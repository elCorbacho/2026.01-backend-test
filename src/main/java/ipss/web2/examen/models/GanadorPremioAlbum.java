package ipss.web2.examen.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Modelo de prueba para representar un ganador del premio al album
@Entity
@Table(name = "ganador_premio_album")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class GanadorPremioAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "artista", nullable = false, length = 120)
    private String artista;

    @Column(name = "album", nullable = false, length = 150)
    private String album;

    @Column(name = "premio", nullable = false, length = 120)
    private String premio;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "genero", length = 80)
    private String genero;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean active = true;
}

package ipss.web2.examen.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// Entidad para campeones de jockey
@Entity
@Table(name = "campeon_jockey")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@EntityListeners(AuditingEntityListener.class)
public class CampeonJockey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre_jockey", nullable = false, length = 150)
    private String nombreJockey;

    @Column(name = "pais", length = 120)
    private String pais;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "anio", nullable = false)
    private Integer anio;

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


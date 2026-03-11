package ipss.web2.examen.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


// Modelo de entidad para Exoplaneta
@Entity
@Table(name = "exoplaneta", indexes = {
        @Index(name = "idx_exoplaneta_nombre", columnList = "nombre"),
        @Index(name = "idx_exoplaneta_is_active", columnList = "is_active"),
        @Index(name = "idx_exoplaneta_descubierto_anio", columnList = "descubierto_anio")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class Exoplaneta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "distancia_anos_luz", nullable = false)
    private Double distanciaAnosLuz;

    @Column(name = "masa_relativa_jupiter", nullable = false)
    private Double masaRelativaJupiter;

    @Column(name = "descubierto_anio", nullable = false)
    private Integer descubiertoAnio;

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

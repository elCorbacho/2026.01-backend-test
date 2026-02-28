package ipss.web2.examen.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "equipo_futbol_espana")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoFutbolEspana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "ciudad", length = 100)
    private String ciudad;

    @Column(name = "fundacion")
    private Integer fundacion;

    @Column(name = "estadio", length = 100)
    private String estadio;

    @Column(name = "activo")
    private Boolean activo = true;
}

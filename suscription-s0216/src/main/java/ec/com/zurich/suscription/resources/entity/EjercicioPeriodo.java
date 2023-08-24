package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EJERCICIOPERIODOS", schema = "SA")
public class EjercicioPeriodo {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DESCRIPCION", length = 50)
    private String descripcion;

    @Column(name = "FECHAINICIO")
    private LocalDateTime fechaInicio;

    @Column(name = "FECHAFIN")
    private LocalDateTime fechaFin;

    @Column(name = "EJERCICIOID", length = 16)
    private String ejercicioId;

    @Column(name = "ESTADOID", length = 16)
    private String estadoId;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

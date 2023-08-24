package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MONEDA", schema = "SA")
public class Moneda {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 40, nullable = false)
    private String nombre;

    @Column(name = "SIMBOLO", length = 5, nullable = false)
    private String simbolo;

    @Column(name = "NUMERODECIMALES", nullable = false)
    private Integer numeroDecimales;

    @Column(name = "CALIFICACION", length = 1, nullable = false)
    private String calificacion;

    @Column(name = "CODIGOCONTABLE", length = 40)
    private String codigoContable;

    @Column(name = "LIMITETOLERANCIA", nullable = false)
    private Double limiteTolerancia;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}
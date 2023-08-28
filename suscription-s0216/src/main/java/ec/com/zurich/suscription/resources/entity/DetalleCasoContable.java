package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "XDETALLECASOCONTABLE", schema = "SA")
@IdClass(DetalleCasoContableId.class)
public class DetalleCasoContable implements Serializable {

    @Id
    @Column(name = "CASOCONTABLEID", length = 16, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String casoContableId;

    @Id
    @Column(name = "RUBRO", length = 24, nullable = false)
    private String rubro;

    @Column(name = "SIGNO", length = 1, nullable = false)
    private String signo;

    @Column(name = "ESQUEMACUENTA", length = 128, nullable = false)
    private String esquemaCuenta;

    @Column(name = "ORDENAJUSTE", precision = 1, nullable = false)
    private BigDecimal ordenAjuste;

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

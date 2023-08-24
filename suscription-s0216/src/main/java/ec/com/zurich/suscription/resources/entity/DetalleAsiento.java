package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "DETALLEASIENTO", schema = "SA")
public class DetalleAsiento {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "TIPOMOVIMIENTO", length = 1)
    private String tipoMovimiento;

    @Column(name = "DETALLE", length = 4000)
    private String detalle;

    @Column(name = "VALORLOCAL", precision = 19, scale = 4)
    private BigDecimal valorLocal;

    @Column(name = "VALORORIGINAL", precision = 19, scale = 4)
    private BigDecimal valorOriginal;

    @Column(name = "VALOREXTERIOR", precision = 19, scale = 4)
    private BigDecimal valorExterior;

    @Column(name = "CLASE", length = 50)
    private String clase;

    @Column(name = "CLASEID", length = 16)
    private String claseId;

    @Column(name = "CUENTAID", length = 16)
    private String cuentaId;

    @Column(name = "PLANCUENTASID", length = 16)
    private String planCuentasId;

    @Column(name = "ASIENTOID", length = 16)
    private String asientoId;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "EJERCICIOID", length = 16)
    private String ejercicioId;

    @Column(name = "TIPORAMOID", length = 20)
    private String tipoRamoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASIENTOID", insertable = false, updatable = false)
    private Asiento asiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANCUENTASID", insertable = false, updatable = false)
    private PlanCuentas planCuentas;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

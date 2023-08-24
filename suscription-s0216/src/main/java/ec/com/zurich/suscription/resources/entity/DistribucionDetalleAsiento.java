package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DISTRIBUCIONDETALLEASIENTO", schema = "SA")
public class DistribucionDetalleAsiento {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DETALLEASIENTOID", length = 16)
    private String detalleAsientoId;

    @Column(name = "SUCURSALID", length = 16)
    private String sucursalId;

    @Column(name = "UNIDADNEGOCIOID", length = 16)
    private String unidadNegocioId;

    @Column(name = "VALORORIGEN", precision = 19, scale = 4)
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL", precision = 19, scale = 4)
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTERIOR", precision = 19, scale = 4)
    private BigDecimal valorExterior;

    @Column(name = "SALDOORIGEN", precision = 19, scale = 4)
    private BigDecimal saldoOrigen;

    @Column(name = "PORCENTAJE", precision = 8, scale = 4)
    private BigDecimal porcentaje;

    @Column(name = "CRITERIODISTRIBUCIONID", length = 16)
    private String criterioDistribucionId;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "EJERCICIOID", length = 16)
    private String ejercicioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DETALLEASIENTOID", insertable = false, updatable = false)
    private DetalleAsiento detalleAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUCURSALID", insertable = false, updatable = false)
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIDADNEGOCIOID", insertable = false, updatable = false)
    private UnidadNegocio unidadNegocio;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

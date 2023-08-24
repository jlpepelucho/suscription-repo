package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ASIENTO")
public class Asiento {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMEROASIENTO", nullable = false)
    private BigDecimal numeroAsiento;

    @Column(name = "FECHAASIENTO", nullable = false)
    private LocalDateTime fechaAsiento;

    @Column(name = "FECHAMAYORIZACION")
    private LocalDateTime fechaMayorizacion;

    @Column(name = "VALORDEBE", precision = 19, scale = 4, nullable = false)
    private BigDecimal valorDebe;

    @Column(name = "VALORHABER", precision = 19, scale = 4, nullable = false)
    private BigDecimal valorHaber;

    @Column(name = "DETALLE", length = 4000, nullable = false)
    private String detalle;

    @Column(name = "CLASE", length = 50, nullable = false)
    private String clase;

    @Column(name = "CLASEID", length = 16, nullable = false)
    private String claseId;

    @Column(name = "EJERCICIOPERIODOSID", length = 16, nullable = false)
    private String ejercicioPeriodosId;

    @Column(name = "TIPOASIENTOID", length = 16, nullable = false)
    private String tipoAsientoId;

    @Column(name = "SUCURSALID", length = 16, nullable = false)
    private String sucursalId;

    @Column(name = "ESTADOID", length = 16, nullable = false)
    private String estadoId;

    @Column(name = "MONEDAID", length = 16, nullable = false)
    private String monedaId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NUMEROTRANSACCION", length = 40)
    private String numeroTransaccion;

    @Column(name = "NUMEROREFERENCIA", length = 100)
    private String numeroReferencia;

    @Column(name = "BENEFICIARIOID", length = 16)
    private String beneficiarioId;

    @Column(name = "EJERCICIOID", length = 16)
    private String ejercicioId;

    @Column(name = "NUMEROEGRESO", length = 100)
    private String numeroEgreso;

    @Column(name = "AUTORIZACIONID", length = 16)
    private String autorizacionId;

    @Column(name = "TIPORAMOID", length = 16)
    private String tipoRamoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EJERCICIOPERIODOSID", insertable = false, updatable = false)
    private EjercicioPeriodo ejercicioPeriodo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

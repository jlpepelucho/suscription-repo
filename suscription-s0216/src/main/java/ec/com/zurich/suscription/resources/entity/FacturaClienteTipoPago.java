package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FACTURACLIENTETIPOPAGO", schema = "SA")
public class FacturaClienteTipoPago {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALOR", nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "VALORINTERES", precision = 19, scale = 4)
    private BigDecimal valorInteres;

    @Column(name = "NUMEROPAGOS", precision = 10, scale = 0)
    private BigDecimal numeroPagos;

    @Column(name = "FECHAPRIMERPAGO")
    private LocalDateTime fechaPrimerPago;

    @Column(name = "PERIODOPAGOS", precision = 10, scale = 0)
    private BigDecimal periodoPagos;

    @Column(name = "FACTURACLIENTEID", nullable = false)
    private String facturaClienteId;

    @Column(name = "TIPOPAGOID", nullable = false)
    private String tipoPagoId;

    @Column(name = "PORCENTAJEPAGO", precision = 9, scale = 6)
    private BigDecimal porcentajePago;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACTURACLIENTEID",  insertable = false, updatable = false)
    private FacturaCliente facturaCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOPAGOID",  insertable = false, updatable = false)
    private TipoPago tipoPago;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

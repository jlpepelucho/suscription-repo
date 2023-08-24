package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOVIMIENTODOCUMENTOCARTERA")
public class MovimientoDocumentoCartera {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALORORIGEN")
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL")
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTERIOR")
    private BigDecimal valorExterior;

    @Column(name = "SALDOORIGENDOC")
    private BigDecimal saldoOrigenDoc;

    @Column(name = "SALDOLOCALDOC")
    private BigDecimal saldoLocalDoc;

    @Column(name = "SALDOEXTERIORDOC")
    private BigDecimal saldoExteriorDoc;

    @Column(name = "TRANSACCIONADMINISTRATIVAID")
    private String transaccionAdministrativaId;

    @Column(name = "DOCUMENTOID")
    private String documentoId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "VALORORIGENDOC")
    private BigDecimal valorOrigenDoc;

    @Column(name = "VALORLOCALDOC")
    private BigDecimal valorLocalDoc;

    @Column(name = "VALOREXTERIORDOC")
    private BigDecimal valorExteriorDoc;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "RUBROCONTABLE")
    private String rubroContable;

    @Column(name = "ASIENTOID")
    private String asientoId;

    @Column(name = "FECHACONTABILIZACION")
    private LocalDateTime fechaContabilizacion;

    @Column(name = "ESDEPOSITOPORDEBITO")
    private Character esDepositoPorDebito;

    @Column(name = "VALORCHEQUE")
    private BigDecimal valorCheque;

    @Column(name = "VALORCHEQUEHISTORICO")
    private BigDecimal valorChequeHistorico;

    @Column(name = "ESINGRESOCHEQUE")
    private Character esIngresoCheque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACCIONADMINISTRATIVAID", insertable = false, updatable = false)
    private TransaccionAdministrativa transaccionAdministrativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTOID",  insertable = false, updatable = false)
    private Documento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTOID",  insertable = false, updatable = false)
    private NotasDebitoCredito notasDebitoCredito;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

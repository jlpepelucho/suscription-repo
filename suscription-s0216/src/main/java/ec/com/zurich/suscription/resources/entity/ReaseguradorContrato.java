package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REASEGURADORCONTRATO", schema = "SA")
public class ReaseguradorContrato {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "LIMITEPORSINIESTRO", precision = 19, scale = 4, nullable = false)
    private BigDecimal limitePorSiniestro;

    @Column(name = "PORCCOMISION", precision = 9, scale = 6)
    private BigDecimal porcentajeComision;

    @Column(name = "PORCCOMISIONBASICA", precision = 9, scale = 6)
    private BigDecimal porcentajeComisionBasica;

    @Column(name = "PORCCOMISIONESADICIONALES", precision = 9, scale = 6)
    private BigDecimal porcentajeComisionesAdicionales;

    @Column(name = "PORCCOMISIONUTILIDADES", precision = 9, scale = 6)
    private BigDecimal porcentajeComisionUtilidades;

    @Column(name = "PORCGASTOSREASEGURADORES", precision = 9, scale = 6)
    private BigDecimal porcentajeGastosReaseguradores;

    @Column(name = "PORCIMPUESTOCONTRATO", precision = 9, scale = 6)
    private BigDecimal porcentajeImpuestoContrato;

    @Column(name = "PORCIMPUESTOSCONTRIBUCIONES", precision = 9, scale = 6)
    private BigDecimal porcentajeImpuestosContribuciones;

    @Column(name = "PORCINCREMENTOCOMISION", precision = 9, scale = 6)
    private BigDecimal porcentajeIncrementoComision;

    @Column(name = "PORCINTERESES", precision = 9, scale = 6)
    private BigDecimal porcentajeIntereses;

    @Column(name = "PORCPARTICIPACION", precision = 9, scale = 6)
    private BigDecimal porcentajeParticipacion;

    @Column(name = "PORCREMESASEXTERIOR", precision = 9, scale = 6)
    private BigDecimal porcentajeRemesasExterior;

    @Column(name = "PORCRESERVA", precision = 9, scale = 6)
    private BigDecimal porcentajeReserva;

    @Column(name = "PORCSINIESTRALIDADINFERIOR", precision = 9, scale = 6)
    private BigDecimal porcentajeSiniestralidadInferior;

    @Column(name = "NETOAPAGAR", precision = 19, scale = 4)
    private BigDecimal netoAPagar;

    @Column(name = "TASANETAAPAGAR", precision = 9, scale = 6)
    private BigDecimal tasaNetaAPagar;

    @Column(name = "PUNTOSCOMISIONBROKER", precision = 10, scale = 0)
    private Integer puntosComisionBroker;

    @Column(name = "FORMACALCULOCOMISION", length = 5)
    private String formaCalculoComision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRATOREASEGUROID", insertable = false, updatable = false)
    private ContratoReaseguro contratoReaseguro;

    @Column(name = "CONTRATOREASEGUROID", length = 16, nullable = false)
    private String contratoReaseguroId;

    @Column(name = "BROKERREASEGUROID", length = 16, nullable = false)
    private String brokerReaseguroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BROKERREASEGUROID", insertable = false, updatable = false)
    private BrokerReaseguro brokerReaseguro;

    @Column(name = "PORCENTAJEVALORCONTRATO", precision = 9, scale = 6)
    private BigDecimal porcentajeValorContrato;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "PORCPARTICIPACIONPRIMA", precision = 9, scale = 6, columnDefinition = "DEFAULT 0")
    private BigDecimal porcentajeParticipacionPrima;



    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

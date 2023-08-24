package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PLANTILLAREASEGURADORCONTRATO")
public class PlantillaReaseguradorContrato {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "LIMITEPORSINIESTRO", nullable = false)
    private BigDecimal limitePorSiniestro;

    @Column(name = "PORCCOMISION", nullable = false)
    private BigDecimal porcentajeComision;

    @Column(name = "PORCCOMISIONBASICA", nullable = false)
    private BigDecimal porcentajeComisionBasica;

    @Column(name = "PORCCOMISIONESADICIONALES", nullable = false)
    private BigDecimal porcentajeComisionesAdicionales;

    @Column(name = "PORCCOMISIONUTILIDADES", nullable = false)
    private BigDecimal porcentajeComisionUtilidades;

    @Column(name = "PORCGASTOSREASEGURADORES", nullable = false)
    private BigDecimal porcentajeGastosReaseguradores;

    @Column(name = "PORCIMPUESTOCONTRATO", nullable = false)
    private BigDecimal porcentajeImpuestoContrato;

    @Column(name = "PORCIMPUESTOSCONTRIBUCIONES", nullable = false)
    private BigDecimal porcentajeImpuestosContribuciones;

    @Column(name = "PORCINCREMENTOCOMISION", nullable = false)
    private BigDecimal porcentajeIncrementoComision;

    @Column(name = "PORCINTERESES", nullable = false)
    private BigDecimal porcentajeIntereses;

    @Column(name = "PORCPARTICIPACION", nullable = false)
    private BigDecimal porcentajeParticipacion;

    @Column(name = "PORCREMESASEXTERIOR", nullable = false)
    private BigDecimal porcentajeRemesasExterior;

    @Column(name = "PORCRESERVA", nullable = false)
    private BigDecimal porcentajeReserva;

    @Column(name = "PORCSINIESTRALIDADINFERIOR", nullable = false)
    private BigDecimal porcentajeSiniestralidadInferior;

    @Column(name = "NETOAPAGAR", nullable = false)
    private BigDecimal netoAPagar;

    @Column(name = "TASANETAAPAGAR", nullable = false)
    private BigDecimal tasaNetaAPagar;

    @Column(name = "PUNTOSCOMISIONBROKER", nullable = false)
    private BigDecimal puntosComisionBroker;

    @Column(name = "FORMACALCULOCOMISION", nullable = false)
    private String formaCalculoComision;

    @Column(name = "PLANTILLACONTRATOREASEGUROID", nullable = false)
    private String plantillaContratoReaseguroId;

    @Column(name = "BROKERREASEGUROID", nullable = false)
    private String brokerReaseguroId;

    @Column(name = "PORCENTAJEVALORCONTRATO", nullable = false)
    private BigDecimal porcentajeValorContrato;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "PORCPARTICIPACIONPRIMA", nullable = false)
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

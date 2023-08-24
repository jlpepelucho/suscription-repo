package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Entity
@Table(name = "DOCUMENTO", schema = "SA")
public class Documento {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO", length = 20, nullable = false)
    private String numero;

    @Column(name = "DESCRIPCION", length = 4000, nullable = false)
    private String descripcion;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "MONEDAID", length = 16, nullable = false)
    private String monedaId;

    @Column(name = "SUCURSALID", length = 16, nullable = false)
    private String sucursalId;

    @Column(name = "ENTIDADID", length = 16, nullable = false)
    private String entidadId;

    @Column(name = "TIPODOCUMENTOID", length = 16, nullable = false)
    private String tipoDocumentoId;

    @Column(name = "ESTADOID", length = 16, nullable = false)
    private String estadoId;

    @Column(name = "RECORDATORIOID", length = 16)
    private String recordatorioId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "VALORORDENPAGO", precision = 19, scale = 4)
    private BigDecimal valorOrdenPago;

    @Column(name = "VALORORIGEN", precision = 19, scale = 4)
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL", precision = 19, scale = 4)
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTRANJERA", precision = 19, scale = 4)
    private BigDecimal valorExtranjera;

    @Column(name = "SALDOORIGEN", precision = 19, scale = 4)
    private BigDecimal saldoOrigen;

    @Column(name = "SALDOLOCAL", precision = 19, scale = 4)
    private BigDecimal saldoLocal;

    @Column(name = "SALDOEXTRANJERA", precision = 19, scale = 4)
    private BigDecimal saldoExtranjera;

    @Column(name = "FECHARECEPCION")
    private LocalDateTime fechaRecepcion;

    @Column(name = "FECHAVENCIMIENTO", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(name = "FECHACONTABILIZACION")
    private LocalDateTime fechaContabilizacion;

    @Column(name = "ASIENTOID", length = 16)
    private String asientoId;

    @Column(name = "AUTORIZACIONID", length = 16)
    private String autorizacionId;

    @Column(name = "DISCRIMINADOR", length = 3)
    private String discriminador;

    @Column(name = "RUBROCONTABLE", length = 40)
    private String rubroContable;

    @Column(name = "LIBERACOMISIONAGENTE", length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String liberaComisionAgente;

    @Column(name = "USUARIOCARGA", length = 16)
    private String usuarioCarga;

    @Column(name = "CAMBIO", length = 200)
    private String cambio;

    @Column(name = "AUTORIZACIONSRIID", length = 16)
    private String autorizacionSRIId;

    @Column(name = "PUNTOVENTAID", length = 16)
    private String puntoVentaId;

    @Column(name = "ESANTERIOR")
    private Boolean esAnterior;

    @Column(name = "NUMTRAMITE", precision = 19, scale = 4)
    private BigDecimal numTramite;

    @Column(name = "FECHADEBITOS")
    private LocalDateTime fechaDebitos;

    @Column(name = "DEBITOENVIADO", length = 1)
    private String debitoEnviado;

    @Column(name = "DEBITOREALIZADO", length = 1)
    private String debitoRealizado;

    @Column(name = "MOTIVOIFI", length = 100)
    private String motivoIFI;

    @Column(name = "FECHACARGA")
    private LocalDateTime fechaCarga;

    @Column(name = "MOTIVOGENERAL", length = 100)
    private String motivoGeneral;

    @Column(name = "RESPUESTASRI", length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String respuestaSRI;

    @Column(name = "AUTORIZACIONSRI", length = 50)
    private String autorizacionSRI;

    @Column(name = "NUMEROAUTORIZACION", length = 50)
    private String numeroAutorizacion;

    @Column(name = "REASEGURADORID", length = 16)
    private String reaseguradorId;

    @Column(name = "NUMERORESP", length = 20)
    private String numeroResp;

    @Column(name = "DESCRIPCIONRESP", length = 4000)
    private String descripcionResp;

    @Column(name = "ESELECTRONICO", length = 1)
    private String esElectronico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPODOCUMENTOID", updatable = false, insertable = false)
    private TipoDocumento tipoDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONEDAID", updatable = false, insertable = false)
    private Moneda moneda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID", updatable = false, insertable = false)
    private Entidad entidad;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

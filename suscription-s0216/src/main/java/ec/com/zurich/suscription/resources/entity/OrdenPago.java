package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ORDENPAGO", schema = "SA")
public class OrdenPago {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "CONCEPTO", length = 2000, nullable = false)
    private String concepto;

    @Column(name = "BENEFICIARIOID", length = 16)
    private String beneficiarioId;

    @Column(name = "SOLICITANTEID", length = 16)
    private String solicitanteId;

    @Column(name = "FECHAELABORACION", nullable = false)
    private LocalDateTime fechaElaboracion;

    @Column(name = "FECHAAPROBACION")
    private LocalDateTime fechaAprobacion;

    @Column(name = "FECHAPAGO")
    private LocalDateTime fechaPago;

    @Column(name = "FECHACOBRO")
    private LocalDateTime fechaCobro;

    @Column(name = "VALOR", precision = 19, scale = 4, nullable = false)
    private BigDecimal valor;

    @Column(name = "VALORRETENCIONES", precision = 19, scale = 4, nullable = false)
    private BigDecimal valorRetenciones;

    @Column(name = "VALORAPAGAR", precision = 19, scale = 4)
    private BigDecimal valorAPagar;

    @Column(name = "SALDO", precision = 19, scale = 4)
    private BigDecimal saldo;

    @Column(name = "NUMEROORDEN", precision = 10)
    private BigDecimal numeroOrden;

    @Column(name = "TIPOORDENID", length = 16, nullable = false)
    private String tipoOrdenId;

    @Column(name = "TIPOPAGO", nullable = false)
    private Integer tipoPago;

    @Column(name = "MONEDAID", length = 19, nullable = false)
    private String monedaId;

    @Column(name = "ESTADOID", length = 16, nullable = false)
    private String estadoId;

    @Column(name = "SUCURSALID", length = 16, nullable = false)
    private String sucursalId;

    @Column(name = "DEPARTAMENTOID", length = 16, nullable = false)
    private String departamentoId;

    @Column(name = "CUPOSXSEMANAID", length = 16)
    private String cuposXSemanaId;

    @Column(name = "AUTORIZACIONID", length = 16)
    private String autorizacionId;

    @Column(name = "NOMBREENTIDAD", length = 2048)
    private String nombreEntidad;

    @Column(name = "USUARIOAPRUEBAID", length = 16)
    private String usuarioApruebaId;

    @Column(name = "ENTIDADID", length = 16, nullable = false)
    private String entidadId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "VALORIMPUESTOS", precision = 19, scale = 4)
    private BigDecimal valorImpuestos;

    @Column(name = "NUMEROCESION", length = 50)
    private String numeroCesion;

    @Column(name = "FECHAINICIO")
    LocalDateTime fechaInicio;

    @Column(name = "FECHAFIN")
    LocalDateTime fechaFin;

    @Column(name = "AUTORIZACIONDEDUCIBLEID", length = 16)
    private String autorizacionDeducibleId;

    @Column(name = "ESTAIMPRESARETENCION", length = 1)
    private String estaImpresaRetencion;

    @Column(name = "ESTAIMPRESACONTRIBUCION", length = 1)
    private String estaImpresaContribucion;

    @Column(name = "AUTORIZACIONINFRASEGUROID", length = 16)
    private String autorizacionInfraSeguroId;

    @Column(name = "AUTORIZACIONANULAROPID", length = 16)
    private String autorizacionAnularOpId;

    @Column(name = "CONVENIOPAGOID", length = 16)
    private String convenioPagoId;

    @Column(name = "ESTRANSFERENCIA", length = 1, columnDefinition = "CHAR DEFAULT '0'")
    private String esTransferencia;

    @Column(name = "PAGORECURRENTE", length = 1, columnDefinition = "CHAR DEFAULT '0'")
    private String pagoRecurrente;

    @Column(name = "AUTORIZACIONEFECTIVIZACIONID", length = 16)
    private String autorizacionEfectivizacionId;

    @Column(name = "FECHACORTE")
    private LocalDateTime fechaCorte;

    @Column(name = "AS2_APROBACION")
    private Integer as2Aprobacion;

    @Column(name = "AS2_APROBADORES", length = 4000)
    private String as2Aprobadores;

    @Column(name = "ZINTEGRATOR_REQUEST_ID")
    private Integer zIntegratorRequestId;

    @Column(name = "NUMEROLOTE", length = 16)
    private String numeroLote;

    @Column(name = "FECHATRANSFERENCIA")
    private LocalDateTime fechaTransferencia;

    @Column(name = "CARTADEBITOID", length = 16)
    private String cartaDebitoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTAMENTOID", insertable = false,updatable = false)
    private Departamento departamento;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}


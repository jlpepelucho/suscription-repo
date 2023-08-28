package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ENDOSO",schema = "SA")
public class Endoso {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO", precision = 38, nullable = false)
    private BigDecimal numero;

    @Column(name = "FECHAANULACION")
    private LocalDate fechaAnulacion;

    @Column(name = "FECHAELABORACION", nullable = false)
    private LocalDateTime fechaElaboracion;

    @Column(name = "PORCCOMISIONAGENTE", precision = 9, scale = 6)
    private BigDecimal porcentajeComisionAgente;

    @Column(name = "VALORASEGURADO", precision = 19, scale = 4, nullable = false)
    private BigDecimal valorAsegurado;

    @Column(name = "VALORCOMISION", precision = 19, scale = 4)
    private BigDecimal valorComision;

    @Column(name = "VALORPRIMANETA", precision = 19, scale = 4)
    private BigDecimal valorPrimaNeta;

    @Column(name = "VIGENCIADESDE", nullable = false)
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", nullable = false)
    private LocalDateTime vigenciaHasta;

    @Column(name = "TIPOENDOSOID", length = 16, nullable = false)
    private String tipoEndosoId;

    @Column(name = "ENDOSOREF", length = 16)
    private String endosoRef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOENDOSOID", insertable = false, updatable = false)
    private TipoEndoso tipoEndoso;

    @Column(name = "SUCURSALID", length = 16, nullable = false)
    private String sucursalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUCURSALID",  nullable = false, insertable = false, updatable = false)
    private Sucursal sucursal;

    @Column(name = "TIPOITEMID", length = 16, nullable = false)
    private String tipoItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOITEMID",  nullable = false, insertable = false, updatable = false)
    private TipoItem tipoItem;

    @Column(name = "OBSERVACIONES", length = 4000)
    private String observaciones;

    @Column(name = "CLIENTEID", length = 16, nullable = false)
    private String clienteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENTEID",  nullable = false, insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "AUTORIZACIONID", length = 16)
    private String autorizacionId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ESDATOOTROSISTEMA", nullable = false)
    private Boolean esDatoOtroSistema;

    @Column(name = "UNIDADNEGOCIOID", length = 16, nullable = false)
    private String unidadNegocioId;

    @Column(name = "TIPOCANCELACION", length = 2)
    private String tipoCancelacion;

    @Column(name = "DERECHOSEMISION")
    private Boolean derechosEmision;

    @Column(name = "POLIZAID", length = 16, nullable = false)
    private String polizaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POLIZAID", insertable = false, updatable = false)
    private Poliza poliza;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIDADNEGOCIOID", insertable = false, updatable = false)
    private UnidadNegocio unidadNegocio;

    @Column(name = "ESAJUSTEVIGENCIA")
    private Boolean esAjusteVigencia;

    @Column(name = "PUNTOVENTAID", length = 16)
    private String puntoVentaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUNTOVENTAID", insertable = false, updatable = false)
    private PuntoVenta puntoVenta;

    @Column(name = "VALORRECARGOSC", precision = 10, scale = 2, columnDefinition = "NUMBER(10,2) DEFAULT -1")
    private BigDecimal valorRecargosC;

    @Column(name = "ESPRIMAANTICIPADA")
    private Boolean esPrimaAnticipada;

    @Column(name = "ESDEPOLIZAMADRE")
    private Boolean esDePolizaMadre;
    @Column(name = "EXCENTOIVA")
    private Boolean excentoiva;

    @Column(name = "ESAUTOMATICO")
    private Boolean esautomatico;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONCID", length = 16)
    private String autorizacioncid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONBID", length = 16)
    private String autorizacionbid;

    @Column(name = "PRIMAMINVERIFICADA")
    private Boolean primaminverificada;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONZFID", length = 16)
    private String autorizacionzfid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONPRID", length = 16)
    private String autorizacionprid;

    @Column(name = "ESVERIFICADO")
    private Boolean esverificado;

    @Column(name = "FECFINCREDITO")
    private Timestamp fecfincredito;

    @Size(max = 16)
    @Column(name = "ENTIDADAGENTEID", length = 16)
    private String entidadagenteid;

    @Size(max = 16)
    @Column(name = "TIPOSEGUROID", length = 16)
    private String tiposeguroid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONPRIMAMINID", length = 16)
    private String autorizacionprimaminid;

    @Column(name = "LIMITEINDEMNIZACION", precision = 19, scale = 4)
    private BigDecimal limiteindemnizacion;

    @Size(max = 200)
    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONCAMBIOCOMID", length = 16)
    private String autorizacioncambiocomid;

    @Column(name = "DIASAVISOSINIESTRO")
    private BigDecimal diasAvisoSiniestro;

    @Column(name = "DIASCONVENIOPAGO")
    private BigDecimal diasConvenioPago;

    @Size(max = 12)
    @Column(name = "AUTORIZACIONDIASCONVENIOPAGO", length = 12)
    private String autorizaciondiasconveniopago;

    @Size(max = 4000)
    @Column(name = "NOTAS", length = 4000)
    private String notas;

    @Column(name = "ESFACTURADOOTROSISTEMA")
    private Boolean esfacturadootrosistema;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONAID", length = 16)
    private String autorizacionaid;

    @Size(max = 12)
    @Column(name = "NUMEROCERTIFICADO", length = 12)
    private String numerocertificado;

    @Size(max = 16)
    @Column(name = "FIRMASUCURSALID")
    private String firmasucursalid;

    @Size(max = 1)
    @Column(name = "IMPRIMEORIGINAL", length = 1)
    private String imprimeoriginal;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONFIRMAID", length = 16)
    private String autorizacionfirmaid;

    @Size(max = 200)
    @Column(name = "TITULARTARJETA", length = 200)
    private String titulartarjeta;

    @Size(max = 16)
    @Column(name = "TARJETAID", length = 16)
    private String tarjetaid;

    @Size(max = 16)
    @Column(name = "TIPOPAGOID", length = 16)
    private String tipopagoid;

    @Size(max = 16)
    @Column(name = "NOMBRETARJETACREDITOID", length = 16)
    private String nombretarjetacreditoid;

    @Column(name = "NUMEROTARJETA")
    private BigDecimal numerotarjeta;

    @Size(max = 20)
    @Column(name = "NUMEROAUTORIZACION", length = 20)
    private String numeroautorizacion;

    @Column(name = "FECHAVENCIMIENTO")
    private LocalDate fechavencimiento;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONLIMITEEMBARQUEID", length = 16)
    private String autorizacionlimiteembarqueid;

    @Size(max = 12)
    @Column(name = "NUMEROTARIFA", length = 12)
    private String numerotarifa;

    @Column(name = "ANULAR")
    private Boolean anular;

    @Column(name = "COMISIONBROKER", precision = 10, scale = 2)
    private BigDecimal comisionbroker;

    @Column(name = "COMISIONMANTENIMIENTO", precision = 10, scale = 2)
    private BigDecimal comisionmantenimiento;

    @Size(max = 16)
    @Column(name = "FORMAPAGO", length = 16)
    private String formapago;

    @Size(max = 16)
    @Column(name = "CONVENIOPAGOID", length = 16)
    private String conveniopagoid;

    @Size(max = 16)
    @Column(name = "CAJAID", length = 16)
    private String cajaid;

    @Column(name = "PREIMPRESOVERIFICADO")
    private Boolean preimpresoverificado;

    @Size(max = 180)
    @Column(name = "SISTEMAEMISORID", length = 180)
    private String sistemaemisorid;

    @Size(max = 240)
    @Column(name = "SISTEMAEMISOR", length = 240)
    private String sistemaemisor;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONMONTOFIANID", length = 16)
    private String autorizacionmontofianid;

    @Column(name = "FECHACOTIZACION")
    private LocalDate fechacotizacion;

    @Size(max = 16)
    @Column(name = "PRODUCTOAPEID", length = 16)
    private String productoapeid;

    @Size(max = 16)
    @Column(name = "PUNTOVENTAAPEID", length = 16)
    private String puntoventaapeid;

    @Column(name = "ASOCIACIONCB")
    private Boolean asociacioncb;

    @Size(max = 16)
    @Column(name = "AUTORIZAFIANZASID", length = 16)
    private String autorizafianzasid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONPERSONAID", length = 16)
    private String autorizacionpersonaid;

    @Column(name = "ESCANCELACION")
    private Boolean escancelacion;

    @Column(name = "NUMEROTRAMITE", precision = 10, scale = 2)
    private BigDecimal numerotramite;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONVIGENCIAID", length = 16)
    private String autorizacionvigenciaid;

    @Column(name = "TASAMINIMA", precision = 10, scale = 3)
    private BigDecimal tasaminima;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONBROKERPOLID", length = 16)
    private String autorizacionbrokerpolid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONNUEVOSOATID", length = 16)
    private String autorizacionnuevosoatid;

    @Size(max = 5)
    @Column(name = "MOTIVOCANCELACION", length = 5)
    private String motivocancelacion;

    @Column(name = "SUBMOTIVOCANCELACIONID")
    private BigDecimal submotivocancelacionid;

    @Size(max = 1)
    @Column(name = "FIRMAVISADO", length = 1)
    private String firmavisado;

    @Column(name = "FECHAFIRMAVISADO")
    private LocalDate fechafirmavisado;

    @Size(max = 16)
    @Column(name = "USUARIOVISADO", length = 16)
    private String usuariovisado;

    @Column(name = "FECHAENTREGAFIRMAVISADO")
    private LocalDate fechaentregafirmavisado;

    @Size(max = 16)
    @Column(name = "SECUENCIALVISADO", length = 16)
    private String secuencialvisado;

    @Size(max = 20)
    @Column(name = "AUTORIZACIONMAS10PAGOSID", length = 20)
    private String autorizacionmas10pagosid;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONFUERAVIGENCIAID", length = 16)
    private String autorizacionfueravigenciaid;

    @Size(max = 50)
    @Column(name = "NUMEROCOT", length = 50)
    private String numerocot;

    @Size(max = 16)
    @Column(name = "AUTORIZACIONPOLCANCELADAID", length = 16)
    private String autorizacionpolcanceladaid;

    @Column(name = "PENDIENTEPAGO")
    private Boolean pendientepago;

    @Column(name = "BENEFICIARIOASEGURADO")
    private Boolean beneficiarioasegurado;

    @Size(max = 10)
    @Column(name = "SCORE", length = 10)
    private String score;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

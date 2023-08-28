package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "POLIZA")
public class Poliza {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO", precision = 12, nullable = false)
    private BigDecimal numero;

    @Column(name = "ORDEN", precision = 10, nullable = false)
    private BigDecimal orden;

    @Column(name = "FECHAANULACION")
    private LocalDateTime fechaAnulacion;

    @Column(name = "FECHAELABORACION", nullable = false)
    private LocalDateTime fechaElaboracion;

    @Column(name = "PRIMAANUAL", precision = 19, scale = 4, nullable = false)
    private BigDecimal primaAnual;

    @Column(name = "PRIMATOTAL", precision = 19, scale = 4, nullable = false)
    private BigDecimal primaTotal;

    @Column(name = "VIGENCIADESDE", nullable = false)
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", nullable = false)
    private LocalDateTime vigenciaHasta;

    @Column(name = "FUNCIONDENTROTIPOSEGURO", length = 20)
    private String funcionDentroTipoSeguro;

    @Column(name = "RAMOID", length = 16, nullable = false)
    private String ramoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAMOID",  insertable = false, updatable = false)
    private Ramo ramo;

    @Column(name = "MONEDAID", length = 16, nullable = false)
    private String monedaId;

    @Column(name = "CLIENTEID", length = 16, nullable = false)
    private String clienteId;

    @Column(name = "AGENTEID", length = 16)
    private String agenteId;

    @Column(name = "TIPOSEGUROID", length = 16, nullable = false)
    private String tipoSeguroId;

    @Column(name = "VIGENCIA", precision = 19, scale = 4)
    private BigDecimal vigencia;

    @Column(name = "ESTADOID", length = 16)
    private String estadoId;

    @Column(name = "CLASEPOLIZAID", length = 16)
    private String clasePolizaId;

    @Column(name = "PADREID", length = 16)
    private String padreId;

    @Column(name = "TIPOCONTENEDOR", length = 1, nullable = false)
    private String tipoContenedor;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ESDATOOTROSISTEMA", nullable = false)
    private Boolean esDatoOtroSistema;

    @Column(name = "NUMEROITEM", precision = 10, nullable = false)
    private BigDecimal numeroItem;

    @Column(name = "TIPOACEPTADOID", length = 16)
    private String tipoAceptadoId;

    @Column(name = "AUTORIZACIONID", length = 16)
    private String autorizacionId;

    @Column(name = "IMPRIMEDIRECCIONPOLIZAHIJA", length = 1)
    private String imprimeDireccionPolizaHija;

    @Column(name = "ENTIDADID", length = 16)
    private String entidadId;

    @Column(name = "CONFIGPRODUCTOID", length = 16)
    private String configProductoId;

    @Column(name = "SINAUTORIZACIONPAGOS", length = 1, nullable = false)
    private Boolean sinAutorizacionPagos;

    @Column(name = "UNIDADPRODUCCIONID", length = 16)
    private String unidadProduccionId;

    @Column(name = "TRATADOID", length = 16)
    private String tratadoId;

    @Column(name = "MOTIVOLIQ", length = 2000)
    private String motivoLiq;

    @Column(name = "ESCOASEGUROAUTOMATICO", length = 1)
    private String esCoaseguroAutomatico;

    @Column(name = "PESADOS", length = 1)
    private String pesados;

    @Column(name = "VIGENCIAPOLIZAID", length = 16)
    private String vigenciaPolizaId;

    @Column(name = "ESFACTURACIONMENSUAL", length = 1)
    private Boolean esFacturacionMensual;

    @Column(name = "NOCOBRADERECHOS", length = 16)
    private String noCobraDerechos;

    @Column(name = "ANIOSUSCRIPCION2006")
    private Boolean anioSuscripcion2006;

    @Column(name = "ESANIODORADO", length = 1)
    private Boolean esAnioDorado;

    @Column(name = "TASAMINIMA", precision = 19, scale = 4)
    private BigDecimal tasaMinima;


    @Column(name = "PRIMAMINIMA", precision = 19, scale = 4)
    private BigDecimal primaMinima;

    @Column(name = "FECHACADUCIDADCERTIFICADOS")
    private LocalDateTime fechaCaducidadCertificados;

    @Column(name = "INGRESOCAJAID", length = 16)
    private String ingresoCajaId;

    @Column(name = "DEPOSITOESTADO", length = 1)
    private String depositoEstado;

    @Column(name = "DEPOSITOCIERREID", precision = 10)
    private BigDecimal depositoCierreId;

    @Column(name = "DEPOSITOCUENTABANCARIAID", precision = 5)
    private BigDecimal depositoCuentaBancariaId;

    @Column(name = "NUEVAPOLIZAID", length = 16)
    private String nuevaPolizaId;

    @Column(name = "MOTIVOANULACIONID", length = 16)
    private String motivoAnulacionId;

    @Column(name = "RENOVACION", length = 1)
    private String renovacion;

    @Column(name = "NUMEROCADUCADO", precision = 12)
    private BigDecimal numeroCaducado;

    @Column(name = "CERTIFICADOSRESERVADOS", precision = 5)
    private BigDecimal certificadosReservados;

    @Column(name = "ESPAGO100", length = 1)
    private String esPago100;

    @Column(name = "TIENEDERECHOS")
    private Boolean tieneDerechos;
    @Column(name = "ESPYMES")
    private Boolean espymes;
    @Size(max = 60)
    @Column(name = "MOTIVOEXCLUSIONENVIOESTRUCTURA", length = 60)
    private String motivoexclusionenvioestructura;

    @Column(name = "SECUENCIALTRANSACCION")
    private Long secuencialtransaccion;

    @Column(name = "RENOVACIONFIANZA")
    private Boolean renovacionfianza;

    @Size(max = 1)
    @Column(name = "REEMPLAZO", length = 1)
    private String reemplazo;

    @Size(max = 1)
    @Column(name = "DEBITOFIRMADO", length = 1)
    private String debitofirmado;

    @Size(max = 1)
    @Column(name = "POLIZAFIRMA", length = 1)
    private String polizafirma;

    @Size(max = 16)
    @Column(name = "USUARIOVISADO", length = 16)
    private String usuariovisado;

    @Column(name = "FECHAVISADO")
    private Timestamp fechavisado;

    @Size(max = 16)
    @Column(name = "TRAYECTOASEGURADOID", length = 16)
    private String trayectoaseguradoid;

    @Size(max = 16)
    @Column(name = "ESRENOVACION", length = 16)
    private String esrenovacion;

    @Size(max = 16)
    @Column(name = "AUTORIZAPAGO100ID", length = 16)
    private String autorizapago100id;

    @Size(max = 16)
    @Column(name = "ESTADOIDORIGINALPOLIZA", length = 16)
    private String estadoidoriginalpoliza;

    @Column(name = "FECHACAMBIOESTADOPOLIZA")
    private Timestamp fechacambioestadopoliza;

    @Size(max = 16)
    @Column(name = "USUARIOCAMBIOESTADOPOLIZA", length = 16)
    private String usuariocambioestadopoliza;

    @Size(max = 16)
    @Column(name = "ESTANOTIFICADOCAMBIOESTADOPOL", length = 16)
    private String estanotificadocambioestadopol;

    @Column(name = "FECHAENTREGADEBITOFIRMADO")
    private Timestamp fechaentregadebitofirmado;

    @Size(max = 50)
    @Column(name = "NUMEROFACTURAREFERENCIA", length = 50)
    private String numerofacturareferencia;

    @Size(max = 10)
    @Column(name = "SECUENCIALVISADO", length = 10)
    private String secuencialvisado;

    @Size(max = 20)
    @Column(name = "ESTADOPOLIZAELECTRONICA", length = 20)
    private String estadopolizaelectronica;

    @Column(name = "PRODUCTOPYMESID")
    private Long productopymesid;

    @Size(max = 1)
    @Column(name = "ESPOLIZAPRIMERRIESGO", length = 1)
    private String espolizaprimerriesgo;

    @Column(name = "VALORPOLIZAPRIMERRIESGO", precision = 19, scale = 4)
    private BigDecimal valorpolizaprimerriesgo;

    @Column(name = "ESUAA")
    private Boolean esuaa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONCESIONARIOID")
    private Concesionario concesionarioid;

    @Column(name = "VALORPOLIZARIESGOMULTIPLE", precision = 19, scale = 4)
    private BigDecimal valorpolizariesgomultiple;

    @Column(name = "ESPOLIZARIESGOMULTIPLE")
    private Boolean espolizariesgomultiple;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ConjuntoCoberturas.class)
    @JoinColumn(name = "CONFIGPRODUCTOID", insertable = false, updatable = false)
    private ConjuntoCoberturas conjuntoCoberturas;


    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}
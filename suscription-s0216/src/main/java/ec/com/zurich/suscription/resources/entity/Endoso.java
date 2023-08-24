package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
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

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

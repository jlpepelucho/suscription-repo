package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMPONENTEFINANCIERO")
public class ComponenteFinanciero {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALOR", precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "ESPORCENTAJE", length = 1)
    private Boolean esPorcentaje;

    @Column(name = "TIPOCOMPONENTEID", nullable = false, length = 16)
    private String tipoComponenteId;

    @Column(name = "TIPOENTIDADID", length = 16)
    private String tipoEntidadId;

    @Column(name = "TIPODOCUMENTOID", length = 16)
    private String tipoDocumentoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPODOCUMENTOID", insertable = false, updatable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "TIPO", length = 1)
    private String tipo;

    @Column(name = "VIGENCIADESDE", nullable = false)
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA")
    private LocalDateTime vigenciaHasta;

    @Column(name = "TIENEGRAVAMEN", length = 1, nullable = false)
    private Boolean tieneGravamen;

    @Column(name = "ORDEN", precision = 3)
    private Integer orden;

    @Column(name = "FORMULA", length = 50)
    private String formula;

    @Column(name = "ESACTUALIZABLE", length = 1)
    private String esActualizable;

    @Column(name = "ESTAINCLUIDOVALORDOCUMENTO", length = 1)
    private String estaIncluidoValorDocumento;

    @Column(name = "ESTAANTESSUBTOTAL", length = 1)
    private String estaAntesSubtotal;

    @Column(name = "CONCEPTOID", length = 16)
    private String conceptoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONCEPTOID", insertable = false, updatable = false)
    private Concepto concepto;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "RUBROCONTABLE", length = 16)
    private String rubroContable;

    @Column(name = "CODIGORENTAS", length = 4)
    private String codigoRentas;

    @Column(name = "ESAUDITABLE", length = 1)
    private String esAuditable;

    @Column(name = "CODIGOSUSTENTO", length = 4)
    private String codigoSustento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOCOMPONENTEID", insertable = false, updatable = false)
    private TipoComponente tipoComponente;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

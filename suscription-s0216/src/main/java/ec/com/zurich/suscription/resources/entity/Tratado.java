package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRATADO", schema = "SA")
public class Tratado {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ANOSUSCRIPCION", nullable = false)
    private Integer anoSuscripcion;

    @Column(name = "LIMITEVALORASEGURADO", precision = 19, scale = 4)
    private BigDecimal limiteValorAsegurado;

    @Column(name = "VIGENCIADESDE", nullable = false)
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", nullable = false)
    private LocalDateTime vigenciaHasta;

    @Column(name = "NOMBRE", length = 150, nullable = false)
    private String nombre;

    @Column(name = "CLASERIESGOID", length = 16)
    private String claseRiesgoId;

    @Column(name = "TIPORIESGOID", length = 16)
    private String tipoRiesgoId;

    @Column(name = "GRUPOCOBERTURAID", length = 16)
    private String grupoCoberturaId;

    @Column(name = "MONEDAID", length = 16, nullable = false)
    private String monedaId;

    @Column(name = "ESAUTOMATICO", length = 50, nullable = false)
    private Boolean esAutomatico;

    @Column(name = "ESPROPORCIONAL", length = 50, nullable = false)
    private Boolean esProporcional;

    @Column(name = "PERIODOID", length = 16)
    private String periodoId;

    @Column(name = "DIASALFINAL")
    private Integer diasAlFinal;

    @Column(name = "DIASBALANCE")
    private Integer diasBalance;

    @Column(name = "PLANTILLATRATADOPADREID", length = 16)
    private String plantillaTratadoPadreId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NUMERO", length = 50, nullable = false)
    private String numero;

    @Column(name = "NEGOCIACIONID", length = 16)
    private String negociacionId;

    @Column(name = "PRIORIDADID", length = 16)
    private String prioridadId;

    @Column(name = "ESPECIALAUTOMATICO", columnDefinition = "CHAR(1) DEFAULT '0'")
    private String especialAutomatico;

    @Column(name = "TIPO", length = 2)
    private String tipo;

    @Column(name = "PRODUCTOID", length = 16)
    private String productoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRUPOCOBERTURAID",insertable = false,updatable = false)
    private GrupoCobertura grupoCobertura;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

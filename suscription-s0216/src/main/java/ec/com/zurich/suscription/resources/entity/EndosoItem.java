package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENDOSOITEM")
public class EndosoItem {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "TIPOITEMID", length = 16, nullable = false)
    private String tipoItemId;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "VALORASEGURADO", precision = 19, scale = 4)
    private BigDecimal valorAsegurado;

    @Column(name = "VALORPRIMANETA", precision = 19, scale = 4)
    private BigDecimal valorPrimaNeta;

    @Column(name = "ENDOSOID", nullable = false)
    private String endosoId;

    @Column(name = "ITEMID", nullable = false)
    private String itemId;

    @Column(name = "CLASERIESGOID", nullable = false, length = 16)
    private String claseRiesgoId;

    @Column(name = "TIPORIESGOID", nullable = false, length = 16)
    private String tipoRiesgoId;

    @Column(name = "VALORITEM", precision = 19, scale = 4)
    private BigDecimal valorItem;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "PLANTILLAID", length = 16)
    private String plantillaId;

    @Column(name = "VAL1", precision = 19, scale = 4)
    private BigDecimal val1;

    @Column(name = "VAL2", precision = 19, scale = 4)
    private BigDecimal val2;

    @Column(name = "VAL3", precision = 19, scale = 4)
    private BigDecimal val3;

    @Column(name = "VAL4", precision = 19, scale = 4)
    private BigDecimal val4;

    @Column(name = "CANTIDAD", precision = 10, scale = 0)
    private Integer cantidad;

    @Column(name = "PORCENTAJEPRIMAINCENDIO", precision = 9, scale = 6)
    private BigDecimal porcentajePrimaIncendio;

    @Column(name = "VALORPRIMAINCENDIO", precision = 19, scale = 4)
    private BigDecimal valorPrimaIncendio;

    @Column(name = "TASAINCENDIO", precision = 9, scale = 6)
    private BigDecimal tasaIncendio;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NUMEROITEM", nullable = false)
    private Integer numeroItem;

    @Column(name = "VALORUNITARIO", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valorUnitario;

    @Column(name = "SISUMA", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String sisuma;

    @Column(name = "PRIMANETAAM", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal primaNetaAm;

    @Column(name = "ACLARATORIO")
    private String aclaratorio;

    @Column(name = "ESBENE", length = 5)
    private String esBene;

    @Column(name = "VALLIMITE", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valLimite;

    @Column(name = "ESAUDITABLE", columnDefinition = "CHAR(1)")
    private String esAuditable;

    @Column(name = "FONSAT", precision = 12, scale = 2)
    private BigDecimal fonsat;

    @Column(name = "CANTIDADREBAJA", precision = 10, scale = 0)
    private Integer cantidadRebaja;

    @Column(name = "DESCRIPCIONITEM", columnDefinition = "CLOB")
    private String descripcionItem;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "ASISTENCIAID")
    private String asistenciaId;

    @Column(name = "STAT")
    private Integer stat;

    @Column(name = "BASEVALORACIONRAMOECID")
    private Integer baseValoracionRamoEcId;

    @Column(name = "TIPOUSOEC", length = 16)
    private String tipoUsoEc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOID", insertable = false, updatable = false)
    private Endoso endoso;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CONTRATOREASEGURO", schema = "SA")
public class ContratoReaseguro {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "CAPACIDAD", precision = 19, scale = 4, nullable = false)
    private BigDecimal capacidad;

    @Column(name = "NUMEROLINEAS", precision = 10, scale = 4)
    private BigDecimal numeroLineas;

    @Column(name = "PORCRETENCION", precision = 9, scale = 6, nullable = false)
    private BigDecimal porcentajeRetencion;

    @Column(name = "PORCCESION", precision = 9, scale = 6, nullable = false)
    private BigDecimal porcentajeCesion;

    @Column(name = "ESVALORASEGURADO", nullable = false)
    private Boolean esValorAsegurado;

    @Column(name = "VALORLINEA", precision = 19, scale = 4)
    private BigDecimal valorLinea;

    @Column(name = "TRATADOID", nullable = false)
    private String tratadoId;

    @Column(name = "ORDEN", nullable = false)
    private BigDecimal orden;

    @Column(name = "TIPOCONTRATOREASEGUROID", length = 16, nullable = false)
    private String tipoContratoReaseguroId;

    @Column(name = "GARANTIAPAGOID", length = 16)
    private String garantiaPagoId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "PORCRETENCIONPRIMA", precision = 9, scale = 6, columnDefinition = "DEFAULT 0")
    private BigDecimal porcentajeRetencionPrima;

    @Column(name = "PORCCESIONPRIMA", precision = 9, scale = 6, columnDefinition = "DEFAULT 0")
    private BigDecimal porcentajeCesionPrima;

    @Column(name = "PERIODOID", length = 16, columnDefinition = "DEFAULT 0")
    private String periodoId;

    @Column(name = "DIAS", columnDefinition = "DEFAULT 0")
    private Integer dias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRATADOID", insertable = false, updatable = false)
    private Tratado tratado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOCONTRATOREASEGUROID", insertable = false, updatable = false)
    private TipoContratoReaseguro tipoContratoReaseguro;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENDOSODIFERIDO")
public class EndosoDiferido {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "VALORPRIMANETA")
    private BigDecimal valorPrimaNeta;

    @Column(name = "VALORASEGURADO")
    private BigDecimal valorAsegurado;

    @Column(name = "VALORCOMISIONES")
    private BigDecimal valorComisiones;

    @Column(name = "ENDOSOID")
    private String endosoId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "AJUSTECONTABLEID")
    private String ajusteContableId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "VIGENCIADESDE")
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA")
    private LocalDateTime vigenciaHasta;

    @Column(name = "ESDATOOTROSISTEMA")
    private Boolean esDatoOtroSistema;

    @Column(name = "AJUSTEVALORPRIMA")
    private BigDecimal ajusteValorPrima;

    @Column(name = "AJUSTEVALORCOMISION")
    private BigDecimal ajusteValorComision;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOID",  insertable = false, updatable = false)
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

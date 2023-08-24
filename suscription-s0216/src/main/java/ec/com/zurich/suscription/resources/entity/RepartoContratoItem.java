package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPARTOCONTRATOITEMS")
public class RepartoContratoItem {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "FECHAHORAREPARTO")
    private LocalDateTime fechaHoraReparto;

    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;

    @Column(name = "REPVALORASEGURADORETENCION")
    private BigDecimal repValorAseguradoRetencion;

    @Column(name = "REPVALORASEGURADOCESION", nullable = false)
    private BigDecimal repValorAseguradoCesion;

    @Column(name = "REPVALORPRIMARETENCION")
    private BigDecimal repValorPrimaRetencion;

    @Column(name = "REPVALORPRIMACESION", nullable = false)
    private BigDecimal repValorPrimaCesion;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "ENDOSOITEMGRUPOCOBERTURAID", nullable = false)
    private String endosoItemGrupoCoberturaId;

    @Column(name = "CONTRATOREASEGUROID", nullable = false)
    private String contratoReaseguroId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "NUMERO", nullable = false)
    private BigDecimal numero;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "REVISADOPOR")
    private String revisadoPor;

    @Column(name = "ULTIMO")
    private String ultimo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRATOREASEGUROID",  insertable = false, updatable = false)
    private ContratoReaseguro contratoReaseguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOITEMGRUPOCOBERTURAID",insertable = false,updatable = false)
    private EndosoItemGrupoCobertura endosoItemGrupoCobertura;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

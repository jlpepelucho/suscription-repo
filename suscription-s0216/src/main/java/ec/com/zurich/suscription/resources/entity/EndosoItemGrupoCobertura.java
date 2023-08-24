package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENDOSOITEMGRUPOCOBERTURA")
public class EndosoItemGrupoCobertura {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "ENDOSOITEMID", nullable = false)
    private String endosoItemId;

    @Column(name = "GRUPOCOBERTURAID", nullable = false)
    private String grupoCoberturaId;

    @Column(name = "VALORASEGURADO", nullable = false)
    private BigDecimal valorAsegurado;

    @Column(name = "VALORPRIMANETA", nullable = false)
    private BigDecimal valorPrimaNeta;

    @Column(name = "ESTADOID", nullable = false)
    private String estadoId;

    @Column(name = "VIGENCIADESDE")
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA")
    private LocalDateTime vigenciaHasta;

    @Column(name = "ENDOSODIFERIDOID", nullable = false)
    private String endosoDiferidoId;

    @Column(name = "VALORITEMALAFECHA")
    private BigDecimal valorItemALaFecha;

    @Column(name = "VALORASEGURADOACTUAL")
    private BigDecimal valorAseguradoActual;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NEGOCIACIONID")
    private String negociacionId;

    @Column(name = "AJUSTEVALORPRIMA")
    private BigDecimal ajusteValorPrima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOITEMID",insertable = false,updatable = false)
    private EndosoItem endosoItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRUPOCOBERTURAID",insertable = false,updatable = false)
    private GrupoCobertura grupoCobertura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSODIFERIDOID", insertable = false, updatable = false)
    private EndosoDiferido endosoDiferido;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

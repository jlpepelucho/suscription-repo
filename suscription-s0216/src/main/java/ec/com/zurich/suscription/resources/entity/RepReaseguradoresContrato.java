package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPREASEGURADORESCONTRATOS")
public class RepReaseguradoresContrato {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "COMISION")
    private BigDecimal comision;

    @Column(name = "REMESASEXTERIOR")
    private BigDecimal remesasExterior;

    @Column(name = "REPPARTICIPACION")
    private BigDecimal repParticipacion;

    @Column(name = "REPPRIMA")
    private BigDecimal repPrima;

    @Column(name = "SUPERBANCOS")
    private BigDecimal superBancos;

    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "REPARTOCONTRATOITEMSID")
    private String repartoContratoItemsId;

    @Column(name = "REASEGURADORCONTRATOID")
    private String reaseguradorContratoId;

    @Column(name = "CERTIFICADOCESIONID")
    private String certificadoCesionId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "NOTACREDITOID")
    private String notaCreditoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REASEGURADORCONTRATOID", insertable = false, updatable = false)
    private ReaseguradorContrato reaseguradorContrato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADOID", insertable = false, updatable = false)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPARTOCONTRATOITEMSID", insertable = false, updatable = false)
    private RepartoContratoItem repartoContratoItem;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

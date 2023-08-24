package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPARTOREASEGURADORESRESERVA", schema = "SA")
public class RepartoReaseguradoresReserva {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "REMESASEXTERIOR")
    private BigDecimal remesasExterior;

    @Column(name = "SUPERBANCOS")
    private BigDecimal superbancos;

    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "REPPARTICIPACION")
    private BigDecimal repParticipacion;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "REASEGURADORCONTRATOID")
    private String reaseguradorContratoId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "REPARTOCONTRATORESERVAID")
    private String repartoContratoReservaId;

    //RELATIONS

    @ManyToOne
    @JoinColumn(name = "REASEGURADORCONTRATOID",insertable = false,updatable = false)
    private ReaseguradorContrato reaseguradorContrato;

    @ManyToOne
    @JoinColumn(name = "ESTADOID", insertable = false,updatable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "REPARTOCONTRATORESERVAID", insertable = false,updatable = false)
    private RepartoContratoReserva repartoContratoReserva;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

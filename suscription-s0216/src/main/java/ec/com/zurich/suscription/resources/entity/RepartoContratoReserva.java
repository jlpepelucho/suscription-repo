package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "REPARTOCONTRATORESERVA", schema = "SA")
public class RepartoContratoReserva {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "REPRESERVARETENCION")
    private BigDecimal repReservaRetencion;

    @Column(name = "REPRESERVACESION")
    private BigDecimal repReservaCesion;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "CONTRATOREASEGUROID")
    private String contratoReaseguroId;

    @Column(name = "RESERVAID")
    private String reservaId;

    //RELATIONS

    @ManyToOne
    @JoinColumn(name = "ESTADOID",insertable = false,updatable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "CONTRATOREASEGUROID", insertable = false,updatable = false)
    private ContratoReaseguro contratoReaseguro;

    @ManyToOne
    @JoinColumn(name = "RESERVAID", insertable = false,updatable = false)
    private Reserva reserva;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

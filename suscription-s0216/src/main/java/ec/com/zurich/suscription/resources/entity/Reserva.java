package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RESERVA", schema = "SA")
public class Reserva {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO")
    private Long numero;

    @Column(name = "FECHARESERVA")
    private LocalDateTime fechaReserva;

    @Column(name = "VALORINDEMNIZACION")
    private BigDecimal valorIndemnizacion;

    @Column(name = "VALORGASTO")
    private BigDecimal valorGasto;

    @Column(name = "VALORTOTAL")
    private BigDecimal valorTotal;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "MOTIVO")
    private String motivo;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "FORMA")
    private String forma;

    @Column(name = "AUTORIZACIONID",length = 16)
    private String autorizacionId;

    @Column(name = "ORDENPAGOID")
    private String ordenPagoId;

    @Column(name = "ASIENTOID")
    private String asientoId;

    @Column(name = "AJUSTE")
    private Character ajuste;

    @Column(name = "RESERVAAJUSTEID")
    private String reservaAjusteId;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "ESTADOTEXTO")
    private String estadoTexto;

    @Column(name = "ESREAPERTURA")
    private Integer esReapertura;

    @Column(name = "ZINTEGRATOR_REQUEST_ID")
    private BigDecimal zIntegratorRequestId;

    @Column(name = "AS2_APROBACION")
    private Integer as2Aprobacion;

    @Column(name = "AS2_APROBADORES")
    private String as2Aprobadores;

    @Column(name = "SUBRECLAMOID")
    private String subreclamoId;

    @Column(name = "ESTADOID")
    private String estadoId;

    //RELATIONS

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBRECLAMOID",updatable = false,insertable = false)
    private SubReclamo subReclamo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADOID",insertable = false,updatable = false)
    private Estado estado;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

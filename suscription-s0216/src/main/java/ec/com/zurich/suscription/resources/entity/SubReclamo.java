package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBRECLAMO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubReclamo implements Serializable {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMEROSUBRECLAMO", nullable = false)
    private int numeroSubreclamo;

    @Column(name = "RECLAMOID", nullable = false)
    private String reclamoId;

    @Column(name = "ENDOSOITEMCOBERTURAID")
    private String endosoItemCoberturaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECLAMOID", insertable= false, updatable = false)
    private Reclamo reclamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOITEMCOBERTURAID",insertable = false,updatable = false)
    private EndosoItemCobertura endosoItemCobertura;

    @Column(name = "NUMEROINDEMNIZACION", nullable = false)
    private int numeroIndemnizacion;

    @Column(name = "NUMEROSALVAMENTO", nullable = false)
    private int numeroSalvamento;

    @Column(name = "NUMEROGASTO", nullable = false)
    private int numeroGasto;

    @Column(name = "NUMERORESERVA", nullable = false)
    private int numeroReserva;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NUMEROACTA")
    private Integer numeroActa;

    @Column(name = "VALORPROFORMADO")
    private BigDecimal valorProformado;

    @Column(name = "AUDATEX")
    private LocalDateTime audatex;

    @Column(name = "VALORASEGURADOACTUAL")
    private BigDecimal valorAseguradoActual;

    @Column(name = "VALORINDEMNIZACION")
    private BigDecimal valorIndemnizacion;

    @Column(name = "EDAD")
    private Integer edad;

    @Column(name = "ESDESMEMBRACION")
    private String esDesmembracion;

    @Column(name = "VALORCARGOHOSPITALARIO", nullable = false)
    private BigDecimal valorCargoHospitalario;

    @Column(name = "TEXTO", length = 400)
    private String texto;

    @Column(name = "NUMEROEVENTO")
    private Integer numeroEvento;

    @Column(name = "ENVIONOTIFICACION", length = 1)
    private String envioNotificacion;

    @Column(name = "WAN", length = 200)
    private String wan;

    @Column(name = "AUDATEX_REQUEST", length = 2000)
    private String audatexRequest;

    @Column(name = "ENVIADOSIAS", nullable = false)
    private boolean enviadosIas;

    @Column(name = "ETIQUETA", length = 16)
    private String etiqueta;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }


}

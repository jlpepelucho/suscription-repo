package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENDOSOITEMCOBERTURA", schema = "SA")
public class EndosoItemCobertura {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "COBERTURAID", nullable = false)
    private String coberturaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COBERTURAID", insertable = false, updatable = false)
    private Cobertura cobertura;

    @Column(name = "ENDOSOITEMID", nullable = false)
    private String endosoItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOITEMID", insertable = false, updatable = false)
    private EndosoItem endosoItem;

    @Column(name = "AFECTAGRUPO")
    private Boolean afectaGrupo;

    @Column(name = "AFECTAPRIMA")
    private Boolean afectaPrima;

    @Column(name = "AFECTAVALORASEGURADO")
    private Boolean afectaValorAsegurado;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "SECCION")
    private String seccion;

    @Column(name = "TEXTO")
    private String texto;

    @Column(name = "LIMITE")
    private BigDecimal limite;

    @Column(name = "MONTOCOBERTURA")
    private BigDecimal montoCobertura;

    @Column(name = "PRIMACOBERTURANETA")
    private BigDecimal primaCoberturaNeta;

    @Column(name = "PORCENTAJECOMISIONNORMAL")
    private BigDecimal porcentajeComisionNormal;

    @Column(name = "PORCENTAJECOMISIONESPECIAL")
    private BigDecimal porcentajeComisionEspecial;

    @Column(name = "TASACOBERTURA")
    private BigDecimal tasaCobertura;

    @Column(name = "COBERTURAGENERALID")
    private String coberturaGeneralId;

    @Column(name = "ESPRIMAFIJA")
    private Character esPrimaFija;

    @Column(name = "MODIFICADA")
    private Character modificada;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "LIMITECOBERTURA", nullable = false)
    private BigDecimal limiteCobertura;

    @Column(name = "ESTODORIESGO")
    private Character esTodoRiesgo;

    @Column(name = "PROVEEDORSERVICIOID")
    private String proveedorServicioId;

    @Column(name = "TABLADESMEMBRACIONCOBERTURAID")
    private String tablaDesmembracionCoberturaId;

    @Column(name = "VALORMAXIMO")
    private BigDecimal valorMaximo;

    @Column(name = "VALORMAXIMODIA")
    private BigDecimal valorMaximoDia;

    @Column(name = "PORCENTAJECOASEGURO")
    private BigDecimal porcentajeCoaseguro;

    @Column(name = "APLICADEDUCIBLE", nullable = false)
    private Boolean aplicaDeducible;

    @Column(name = "TIENEIVA")
    private Boolean tieneIva;

    @Column(name = "DESCRIPCIONITEMCOBERTURA")
    private String descripcionItemCobertura;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

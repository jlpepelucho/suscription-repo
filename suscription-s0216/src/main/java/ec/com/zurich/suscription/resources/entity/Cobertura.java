package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "COBERTURA")
public class Cobertura {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "AFECTAGRUPO", length = 1)
    private String afectaGrupo;

    @Column(name = "AFECTAVALORASEGURADO", length = 1)
    private String afectaValorAsegurado;

    @Column(name = "AFECTAPRIMA", length = 1)
    private String afectaPrima;

    @Column(name = "CODIGOASIGNACIONVALORES", length = 40)
    private String codigoAsignacionValores;

    @Column(name = "CODIGOAGRUPACION", length = 40)
    private String codigoAgrupacion;

    @Column(name = "FORMULAPRIMA", length = 40)
    private String formulaPrima;

    @Column(name = "SECCION", length = 40)
    private String seccion;

    @Column(name = "ORDEN", precision = 10, scale = 0)
    private BigDecimal orden;

    @Column(name = "LIMITE", precision = 19, scale = 4)
    private BigDecimal limite;

    @Column(name = "ESPREDETERMINADA", length = 1)
    private String esPredeterminada;

    @Column(name = "TIPOCOBERTURAID", length = 16, nullable = false)
    private String tipoCoberturaId;

    @Column(name = "NOMBRE", length = 250, nullable = false)
    private String nombre;

    @Column(name = "TEXTO", length = 4000)
    private String texto;

    @Column(name = "GRUPOCOBERTURAID", nullable = false)
    private String grupoCoberturaId;

    @Column(name = "ESSEPELIO")
    private Boolean esSepelio;

    @Column(name = "FORMULAMONTOASEGURADO")
    private String formulaMontoAsegurado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GRUPOCOBERTURAID", insertable = false, updatable = false)
    private GrupoCobertura grupoCobertura;

}

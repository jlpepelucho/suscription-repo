package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Boolean afectaGrupo;

    @Column(name = "AFECTAVALORASEGURADO", length = 1)
    private Boolean afectaValorAsegurado;

    @Column(name = "AFECTAPRIMA", length = 1)
    private Boolean afectaPrima;

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


    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "ESPRIMAFIJA")
    private Boolean esprimafija;

    @Column(name = "ESTODORIESGO")
    private Boolean estodoriesgo;

    @Column(name = "ESMASIVO")
    private Boolean esMasivo;

    @Column(name = "ESPRINCIPAL")
    private Boolean esprincipal;

    @Column(name = "APLICATABLAEDADPARAINDEMNIZAR")
    private Boolean aplicatablaedadparaindemnizar;

    @Column(name = "ESASISTENCIAMEDICA")
    private Boolean esasistenciamedica;

    @Column(name = "APLICAVIDAGRUPAL")
    private Boolean aplicavidagrupal;

    @Column(name = "ESPRINCIPALVIDA")
    private Boolean esprincipalvida;

    @Column(name = "REBAJAVALORASEGURADO")
    private Boolean rebajavalorasegurado;

    @Column(name = "GENERAENDOSORASA")
    private Boolean generaendosorasa;

    @NotNull
    @Column(name = "ESCOBERTURADEMUERTE", nullable = false)
    private Boolean escoberturademuerte = false;

    @Column(name = "INDEMNIZACIONXVALORCOERTURA")
    private Boolean indemnizacionxvalorcoertura;

    @Column(name = "ESMAXIMODIA")
    private Boolean esmaximodia;

    @Size(max = 12)
    @Column(name = "NOMBRENEMOTECNICO", length = 12)
    private String nombrenemotecnico;

    @Column(name = "ESINDEMNIZABLE")
    private Boolean esindemnizable;

    @Column(name = "ESLIMITESUMA")
    private Boolean eslimitesuma;

    @Column(name = "ESACCESORIO")
    private Boolean esaccesorio;

    @Column(name = "INDEMNIZACIONXVALORCOBERTURA")
    private Boolean indemnizacionxvalorcobertura;

    @Column(name = "PRINCIPALCOBERTURA")
    private Boolean principalcobertura;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Lob
    @Column(name = "TEXTOCLOB")
    private String textoclob;

    @Size(max = 1)
    @Column(name = "ESGRAVABLEIVA", length = 1)
    private String esgravableiva;

    @Size(max = 1)
    @Column(name = "CONCEPTOLIQUIDACIONEDITABLE", length = 1)
    private String conceptoliquidacioneditable;

    @Size(max = 2)
    @Column(name = "AUDATEX_PREFIX", length = 2)
    private String audatexPrefix;

}

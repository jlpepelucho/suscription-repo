package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "COBERTURAGENERAL")
public class CoberturaGeneral {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 16)
    @NotNull
    @Column(name = "COBERTURAID", nullable = false, length = 16)
    private String coberturaid;

    @Column(name = "ENDOSOID", nullable = false)
    private String endosoid;

    @Column(name = "AFECTAPRIMA")
    private Boolean afectaprima;

    @Column(name = "AFECTAVALORASEGURADO")
    private Boolean afectavalorasegurado;

    @Column(name = "AFECTAGRUPO")
    private Boolean afectagrupo;

    @Column(name = "ORDEN")
    private BigDecimal orden;

    @Column(name = "LIMITE", precision = 19, scale = 4)
    private BigDecimal limite;

    @Size(max = 40)
    @Column(name = "SECCION", length = 40)
    private String seccion;

    @Size(max = 4000)
    @Column(name = "TEXTO", length = 4000)
    private String texto;

    @Column(name = "TASA", precision = 9, scale = 6)
    private BigDecimal tasa;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaactualiza;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "LIMITECOBERTURA", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitecobertura;

    @Column(name = "ESTODORIESGO")
    private Boolean estodoriesgo;

}
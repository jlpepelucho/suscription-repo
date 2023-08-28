package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PLANTILLACOBERTURA")
public class PlantillaCobertura {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "AFECTAGRUPO")
    private Boolean afectagrupo;

    @Column(name = "AFECTAVALORASEGURADO")
    private Boolean afectavalorasegurado;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "LIMITE", precision = 19, scale = 4)
    private BigDecimal limite;

    @Column(name = "TASA", precision = 19, scale = 4)
    private BigDecimal tasa;

    @Size(max = 20)
    @Column(name = "SECCION", length = 20)
    private String seccion;

    @Size(max = 4000)
    @Column(name = "TEXTO", length = 4000)
    private String texto;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Size(max = 16)
    @Column(name = "COBERTURAID", length = 16)
    private String coberturaid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COBERTURAID", insertable = false, updatable = false)
    private Cobertura cobertura;
    @Size(max = 16)
    @Column(name = "AFECTAPRIMA", length = 16)
    private String afectaprima;

    @NotNull
    @Column(name = "LIMITECOBERTURA", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitecobertura;

    @Column(name = "ESTODORIESGO")
    private Boolean estodoriesgo;

    @Size(max = 16)
    @Column(name = "PROVEEDORSERVICIOID", length = 16)
    private String proveedorservicioid;

    @Column(name = "ESPRIMAFIJA")
    private Boolean esprimafija;

    @Lob
    @Column(name = "TEXTOCLOB")
    private String textoclob;

    @Column(name = "PLANTILLAID")
    private String plantillaid;

}
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
@Table(name = "BENEFICIARIO")
public class Beneficiario {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 100)
    @NotNull
    @Column(name = "PARENTESCOOLD", nullable = false, length = 100)
    private String parentescoold;

    @NotNull
    @Column(name = "PROPORCION", nullable = false, precision = 9, scale = 6)
    private BigDecimal proporcion;

    @NotNull
    @Column(name = "ENTIDADID", nullable = false)
    private String entidadid;

    @NotNull
    @Column(name = "ENDOSOITEMID", nullable = false)
    private String endosoitemid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "ESMENORDEEDAD")
    private Boolean esmenordeedad;

    @NotNull
    @Column(name = "ESACREEDOR", nullable = false)
    private Boolean esacreedor = false;

    @Column(name = "ESACTIVO")
    private Boolean esactivo;

    @Column(name = "VALORACREDITADO", precision = 19, scale = 4)
    private BigDecimal valoracreditado;

}
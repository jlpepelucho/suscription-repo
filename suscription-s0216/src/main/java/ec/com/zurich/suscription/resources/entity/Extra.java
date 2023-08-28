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
@Table(name = "EXTRAS")
public class Extra {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 400)
    @NotNull
    @Column(name = "DESCRIPCION", nullable = false, length = 400)
    private String descripcion;

    @NotNull
    @Column(name = "VALORASEGURADO", nullable = false, precision = 19, scale = 4)
    private BigDecimal valorasegurado;

    @Size(max = 20)
    @Column(name = "NOSERIE", length = 20)
    private String noserie;

    @Column(name = "VEHICULOID", nullable = false)
    private String vehiculoid;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "ESTAINCLUIDO", nullable = false)
    private Boolean estaincluido = false;

}
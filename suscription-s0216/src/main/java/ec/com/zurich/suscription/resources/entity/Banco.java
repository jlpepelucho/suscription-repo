package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "BANCO")
public class Banco {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Size(max = 16)
    @Column(name = "CODIGOBANCO", length = 16)
    private String codigobanco;

    @Size(max = 3)
    @Column(name = "BANKNUMBER", length = 3)
    private String banknumber;

    @Size(max = 16)
    @Column(name = "CONVENIOPAGOID", length = 16)
    private String conveniopagoid;

    @Column(name = "DEBITO")
    private Boolean debito;

    @Column(name = "ESTADO")
    private Boolean estado;

}
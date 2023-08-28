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
@Table(name = "UNIDADPRODUCCION")
public class UnidadProduccion {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 16)
    @NotNull
    @Column(name = "UNIDADNEGOCIOID", nullable = false, length = 16)
    private String unidadnegocioid;

    @Size(max = 16)
    @NotNull
    @Column(name = "ENTIDADID", nullable = false, length = 16)
    private String entidadid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "ESTAACTIVA")
    private Boolean estaactiva;

    @Size(max = 40)
    @Column(name = "NOMBRE", length = 40)
    private String nombre;

    @Size(max = 16)
    @Column(name = "SUCURSALID", length = 16)
    private String sucursalid;

}
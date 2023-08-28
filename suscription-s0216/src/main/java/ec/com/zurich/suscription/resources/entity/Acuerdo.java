package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "ACUERDO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Acuerdo implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "VIGENCIADESDE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date vigenciaHasta;

    @Size(max = 100)
    @NotNull
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = false;


    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOCREACION", nullable = false, length = 16)
    private String usuariocreacion;

    @NotNull
    @Column(name = "FECHACREACION", nullable = false)
    private LocalDate fechacreacion;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

}

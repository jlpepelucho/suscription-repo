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
@Table(name = "CRITERIODEDUCIBLE")
public class Criteriodeducible {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;


    @Size(max = 50)
    @NotNull
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Size(max = 50)
    @NotNull
    @Column(name = "CLASE", nullable = false, length = 50)
    private String clase;

    @Size(max = 30)
    @NotNull
    @Column(name = "ATRIBUTO", nullable = false, length = 30)
    private String atributo;

    @Size(max = 16)
    @NotNull
    @Column(name = "OPERADOR", nullable = false, length = 16)
    private String operador;

    @Size(max = 16)
    @Column(name = "CARACTERISTICA", length = 16)
    private String caracteristica;

    @Column(name = "ESCARACTERISTICASUBCLASE")
    private Boolean escaracteristicasubclase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAMOID")
    private Ramo ramoid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

}
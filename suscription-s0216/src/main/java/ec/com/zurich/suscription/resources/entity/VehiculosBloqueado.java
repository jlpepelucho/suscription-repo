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
@Table(name = "VEHICULOSBLOQUEADOS")
public class VehiculosBloqueado {
    @Id
    @Size(max = 40)
    @NotNull
    @Column(name = "ID", nullable = false, length = 40)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 40)
    @Column(name = "CHASIS", length = 40)
    private String chasis;

    @Size(max = 40)
    @Column(name = "MOTOR", length = 40)
    private String motor;

    @Size(max = 40)
    @Column(name = "PLACA", length = 40)
    private String placa;

    @Size(max = 1)
    @NotNull
    @Column(name = "ACTIVO", nullable = false, length = 1)
    private String activo;

    @Size(max = 16)
    @Column(name = "USUARIOID", length = 16)
    private String usuarioid;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

}
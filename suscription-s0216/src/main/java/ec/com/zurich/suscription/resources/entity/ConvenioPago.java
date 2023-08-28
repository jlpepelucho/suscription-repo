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
@Table(name = "CONVENIOPAGO")
public class ConvenioPago {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 150)
    @NotNull
    @Column(name = "DESCRIPCION", nullable = false, length = 150)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ENTIDADID", nullable = false)
    private Entidad entidadid;

    @Column(name = "VALORFINANCIERO", precision = 16, scale = 4)
    private BigDecimal valorfinanciero;

    @Column(name = "FORMACOBRO")
    private Boolean formacobro;

    @Size(max = 16)
    @Column(name = "CUENTABANCARIAID", length = 16)
    private String cuentabancariaid;

    @Size(max = 20)
    @Column(name = "NUMEROESTABLECIMIENTO", length = 20)
    private String numeroestablecimiento;

    @Size(max = 80)
    @Column(name = "EMAIL", length = 80)
    private String email;

    @NotNull
    @Column(name = "ESTADO", nullable = false)
    private Boolean estado = false;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "TIPOSEGURO")
    private Boolean tiposeguro;

    @Size(max = 10)
    @Column(name = "SIGLA", length = 10)
    private String sigla;

    @Size(max = 16)
    @Column(name = "TARJETACREDITOID", length = 16)
    private String tarjetacreditoid;

    @Size(max = 16)
    @Column(name = "COMISIONTARJETACREDITOID", length = 16)
    private String comisiontarjetacreditoid;

    @Column(name = "TIPOSEGUROACEPTADO")
    private Boolean tiposeguroaceptado;

}
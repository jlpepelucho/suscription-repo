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
@Table(name = "DEBITO")
public class Debito {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "TIPOCUENTA")
    private Boolean tipocuenta;

    @Size(max = 300)
    @Column(name = "NUMERO", length = 300)
    private String numero;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROPIETARIOID", nullable = false)
    private Propietario propietarioid;

    @Column(name = "TIPOORDEN")
    private Boolean tipoorden;

    @Size(max = 16)
    @NotNull
    @Column(name = "CLIENTEID", nullable = false, length = 16)
    private String clienteid;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

    @Size(max = 16)
    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioactualiza;

    @Column(name = "FECHAVENCIMIENTOTARJETA")
    private LocalDate fechavencimientotarjeta;

    @Size(max = 16)
    @Column(name = "BANCOID", length = 16)
    private String bancoid;

     @Column(name = "CONVENIOPAGOID", nullable = false)
    private String convenioPagoId;

}
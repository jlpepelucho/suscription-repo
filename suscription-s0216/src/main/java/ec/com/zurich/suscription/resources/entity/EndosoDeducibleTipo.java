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
@Table(name = "ENDOSODEDUCIBLETIPO")
public class EndosoDeducibleTipo {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "TEXTO", nullable = false)
    private String texto;

    @NotNull

    @Column(name = "ENDOSODEDUCIBLEID", nullable = false)
    private String endosoDeducibleId;

    @NotNull
    @Column(name = "VALOR", nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "TIPODEDUCIBLEID", nullable = false)
    private String tipoDeducibleId;

}
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
@Table(name = "PLANTILLADEDUCIBLETIPO")
public class PlantillaDeducibleTipo {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 200)
    @Column(name = "TEXTO", length = 200)
    private String texto;

    @Column(name = "VALOR", precision = 19, scale = 4)
    private BigDecimal valor;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioActualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaActualiza;


    @Column(name = "PLANTILLADEDUCIBLEID")
    private String plantillaDeducibleId;


    @Column(name = "TIPODEDUCIBLEID")
    private String tipoDeducibleId;

}
package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "COBERTURAASISTENCIA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoberturaAsistencia implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "VALORASISTENCIA", nullable = false)
    private BigDecimal valorAsistencia;

    @Column(name = "COBERTURAID", nullable = false)
    private String coberturaId;


    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="COBERTURAID", insertable= false, updatable = false)
    private Cobertura cobertura;

}

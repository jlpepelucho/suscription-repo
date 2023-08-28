package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "APLICACIONTRANSPORTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AplicacionTransporte implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "VALORPRIMADEPOSITO")
    private BigDecimal valorPrimaDeposito = BigDecimal.ZERO;
    @Column(name = "TASA")
    private BigDecimal tasa = BigDecimal.ZERO;


    @Size(max = 16)
    @Column(name = "COBERTURAID", length = 16)
    private String coberturaid;

}

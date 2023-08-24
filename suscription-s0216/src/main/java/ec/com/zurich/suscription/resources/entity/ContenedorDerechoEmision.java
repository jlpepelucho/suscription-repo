package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "CONTENEDORDERECHOEMISION", schema = "SA")
public class ContenedorDerechoEmision {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "VALORDERECHOSEMISION", precision = 19, scale = 4)
    private BigDecimal valorDerechoEmision;
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "DEDUCIBLEPRODUCTO")
public class DeducibleProducto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;


    @Column(name = "CONFIGPRODUCTOID")
    private String configproductoid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID", nullable = false)
    private Deducible deducible;

}
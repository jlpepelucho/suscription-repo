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
@Table(name = "ITEM")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name ="SEC_GEN" ,	strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator",
            parameters= @org.hibernate.annotations.Parameter(
                    name = "sequence_name",	value = "GENERADORID"))
    private String id;


    @Column(name = "VALOR")
    private BigDecimal valor;

    @Column(name = "DEPRECIACION")
    private BigDecimal depreciacion;

    @Column(name = "ZONARIESGOID")
    private String zonaRiesgoId;

    @Column(name = "TEXTO")
    private String texto;

    @Column(name = "NUMERO")
    private BigDecimal numero;

    @Column(name = "NUMEROOPERACION")
    private String numeroOperacion;

    @Column(name = "BENE")
    private String bene;

    @Column(name = "CATALOGOSICID")
    private String catalogoSicId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ZONARIESGOID", referencedColumnName = "ID", insertable = false, updatable = false)
    private ZonaRiesgo zonaRiesgo;

}

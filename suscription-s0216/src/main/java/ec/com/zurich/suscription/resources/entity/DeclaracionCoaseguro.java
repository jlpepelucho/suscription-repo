package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "DECLARACIONCOASEGURO")
public class DeclaracionCoaseguro {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ENDOSOID")
    private String endosoId;

    @Column(name = "BROKERREASEGUROID")
    private String brokerReaseguroId;

    @Column(name = "PORCRETENCION")
    private BigDecimal porcRetencion;

    @Column(name = "PORCCOMISION")
    private BigDecimal porcComision;

    @Column(name = "NUMEROPOLIZA")
    private String numeroPoliza;

    @Column(name = "NUMEROFACTURA")
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BROKERREASEGUROID",  insertable = false, updatable = false)
    private BrokerReaseguro brokerReaseguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOID",  insertable = false, updatable = false)
    private Endoso endoso;
}

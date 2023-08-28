package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "TIPODOCAUTORIZACIONSRI", schema = "SA")
public class TipoDocAutorizacionSRI {

    @Id
    @Column(name = "ID", length = 16, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "DOCUMENTO", length = 15)
    private String documento;

    @Column(name = "NEMONICO", length = 3)
    private String nemonico;

    @Column(name = "ACTIVO", length = 1, columnDefinition = "CHAR DEFAULT '1'")
    private String activo;

}

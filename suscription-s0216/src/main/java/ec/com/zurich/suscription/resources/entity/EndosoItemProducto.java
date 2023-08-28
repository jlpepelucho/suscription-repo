package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "ENDOSOITEMPRODUCTO")
public class EndosoItemProducto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;


    @Column(name = "ENDOSOITEMID")
    private String endosoitemid;

    @Size(max = 16)
    @Column(name = "CONJUNTOID", length = 16)
    private String conjuntoid;

    @Size(max = 16)
    @NotNull
    @Column(name = "PLANID", nullable = false, length = 16)
    private String planid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private Timestamp fechaactualiza;

    @Size(max = 30)
    @Column(name = "CLASE", length = 30)
    private String clase;

    @Size(max = 16)
    @Column(name = "CLASEID", length = 16)
    private String claseid;


    @Column(name = "CONFIGPRODUCTOID", nullable = false)
    private String configProductoid;

}
package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "SECUENCIAL_LOG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecuencialLog implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "SECUENCIAL")
    private String secuencial;

    @Column(name = "SCRIPTUPDATE")
    private String scriptUpdate;

    @Column(name = "VARIABLE")
    private String variable;

    @Column(name = "ESGLOBAL")
    private Boolean esGlobal;

    @Column(name = "SERVICIO")
    private String servicio;

    @Column(name = "METODO")
    private String metodo;

    @Column(name = "SCRIPT")
    private String script;

    @Column(name = "SECUENCIA")
    private String secuencia;

    @Column(name = "VALORES")
    private String valores;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private Date fechaActualiza;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "ESTRANSACCIONAL")
    private Boolean esTransaccional;

}

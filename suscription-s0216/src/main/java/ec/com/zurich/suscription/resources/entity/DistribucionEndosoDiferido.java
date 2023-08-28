package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "DISTRIBUCIONENDOSODIFERIDO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistribucionEndosoDiferido implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;


    @Column(name = "VALORORIGEN", precision = 19, scale = 4)
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL", precision = 19, scale = 4)
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTERIOR", precision = 19, scale = 4)
    private BigDecimal valorExterior;

    @Column(name = "PORCENTAJE", precision = 8, scale = 4)
    private BigDecimal porcentaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ENDOSODIFERIDOID", nullable = false)
    private EndosoDiferido endosoDiferido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RAMOID", nullable = false)
    private Ramo ramo;

    @Column(name = "CODCONTABLEGC", length = 7, nullable = false)
    private String codContableGc;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private Timestamp fechaActualiza;

}

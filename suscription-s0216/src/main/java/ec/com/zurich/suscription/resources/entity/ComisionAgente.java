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
@Table(name = "COMISIONAGENTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComisionAgente implements Serializable {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "COBERTURAID", nullable = false)
    private String coberturaId;

    @Column(name = "AGENTEID", nullable = false)
    private String agenteId;


    @Column(name = "PORCENTAJE", nullable = false)
    private BigDecimal porcentaje = BigDecimal.ZERO;




    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="AGENTEID", insertable= false, updatable = false)
    private Agente agente;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COBERTURAID", insertable= false, updatable = false)
    private Cobertura cobertura;


}

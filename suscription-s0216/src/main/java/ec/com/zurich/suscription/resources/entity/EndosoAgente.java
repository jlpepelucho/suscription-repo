package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ENDOSOAGENTE")
public class EndosoAgente {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "PARTICIPACION")
    private BigDecimal participacion;

    @Column(name = "TIPOAGENTE")
    private String tipoAgente;

    @Column(name = "ENDOSOID")
    private String endosoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENTEID",  nullable = false, updatable = false, insertable = false)
    private Agente agente;

    @Column(name = "AGENTEID", length = 16)
    private String agenteId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ACUERDOID")
    private String acuerdoId;

    @ManyToOne
    @JoinColumn(name = "ACUERDOID", insertable = false, updatable = false)
    private Acuerdo acuerdo;

    @Column(name = "AGENTESINCONTRATO")
    private String agenteSinContrato;

    @Column(name = "PARTICIPACIONSINCONTRATO")
    private BigDecimal participacionSinContrato;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

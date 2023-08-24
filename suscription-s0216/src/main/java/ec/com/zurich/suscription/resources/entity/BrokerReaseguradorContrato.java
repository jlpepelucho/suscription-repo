package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BROKERREASEGURADORCONTRATO")
public class BrokerReaseguradorContrato {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "REASEGURADORID")
    private String reaseguradorId;

    @Column(name = "BROKERREASEGUROID")
    private String brokerReaseguroId;

    @Column(name = "CLASEID")
    private String claseId;

    @Column(name = "CLASE")
    private String clase;

    @Column(name = "PORCENTAJEPARTICIPACION")
    private BigDecimal porcentajeParticipacion;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BROKERREASEGUROID",  insertable = false, updatable = false)
    private BrokerReaseguro brokerReaseguro;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

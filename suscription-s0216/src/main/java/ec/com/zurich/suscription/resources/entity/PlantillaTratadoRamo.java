package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PLANTILLATRATADORAMO", schema = "SA")
public class PlantillaTratadoRamo {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "PLANTILLATRATADOID", length = 16)
    private String plantillaTratadoId;

    @Column(name = "RAMOID", length = 16)
    private String ramoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANTILLATRATADOID",  updatable = false, insertable = false)
    private PlantillaTratado plantillaTratado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAMOID",  updatable = false, insertable = false)
    private Ramo ramo;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "POLIZAID", length = 16)
    private String polizaId;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ROLENTIDADACUERDO", schema = "SA")
public class RolEntidadAcuerdo {

    @Id()
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ACUERDOID", nullable = false)
    private String acuerdoId;

    @Column(name = "ROLENTIDADID", nullable = false)
    private String rolEntidadId;

    @Column(name = "USUARIOCREACION", nullable = false)
    private String usuarioCreacion;

    @Column(name = "FECHACREACION", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLENTIDADID", insertable = false, updatable = false)
    private RolEntidad rolEntidad;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}
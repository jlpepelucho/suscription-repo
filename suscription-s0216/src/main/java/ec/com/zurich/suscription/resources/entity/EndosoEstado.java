package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENDOSOESTADO", schema = "SA")
public class EndosoEstado {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ESACTUAL", nullable = false)
    private Boolean esActual;

    @Column(name = "ESTADOID", nullable = false)
    private String estadoId;

    @Column(name = "ENDOSOID", nullable = false)
    private String endosoId;

    @Column(name = "FACTURAID")
    private String facturaId;

    @Column(name = "PROCESOID")
    private String procesoId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "SISTEMA")
    private String sistema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOID",  insertable = false, updatable = false)
    private Endoso endoso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADOID",  insertable = false, updatable = false)
    private Estado estado;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

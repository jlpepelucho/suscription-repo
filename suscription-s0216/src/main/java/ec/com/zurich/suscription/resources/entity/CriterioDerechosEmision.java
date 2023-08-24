package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CRITERIODERECHOSEMISION", schema = "SA")
public class CriterioDerechosEmision {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ORDEN", nullable = false)
    private Integer orden;

    @Column(name = "TIPOENDOSOID")
    private String tipoEndosoId;

    @Column(name = "TIPOCONTENEDORID")
    private String tipoContenedorId;

    @Column(name = "RAMOID")
    private String ramoId;

    @Column(name = "CLASEPOLIZAID")
    private String clasePolizaId;

    @Column(name = "ESHIJO")
    private Boolean esHijo;

    @Column(name = "ESRENOVACION")
    private Boolean esRenovacion;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "ESFACTURACIONMENSUAL")
    private Boolean esFacturacionMensual;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

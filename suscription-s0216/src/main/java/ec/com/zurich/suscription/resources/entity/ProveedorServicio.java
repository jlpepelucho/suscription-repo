package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PROVEEDORSERVICIOS", schema = "SA")
public class ProveedorServicio {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID", referencedColumnName = "ID")
    private Entidad entidad;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "ACTIVO", length = 1)
    private String activo;

    @Column(name = "PORCENTAJECOMISIONAGENTE", precision = 9, scale = 6, nullable = false)
    private BigDecimal porcentajeComisionAgente = BigDecimal.ZERO;

    @Column(name = "PORCENTAJECOMISIONPROVEEDOR", precision = 6, scale = 3, nullable = false)
    private BigDecimal porcentajeComisionProveedor = BigDecimal.ZERO;

    @Column(name = "CODIGOCONTABLE", length = 20)
    private String codigoContable;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

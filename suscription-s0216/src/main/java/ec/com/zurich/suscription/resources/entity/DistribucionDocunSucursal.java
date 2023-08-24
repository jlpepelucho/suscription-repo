package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DISTRIBUCIONDOCUNSUCURSAL", schema = "SA")
public class DistribucionDocunSucursal {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALORORIGEN", precision = 19, scale = 4)
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL", precision = 19, scale = 4)
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTERIOR", precision = 19, scale = 4)
    private BigDecimal valorExterior;

    @Column(name = "PORCENTAJE", precision = 8, scale = 4)
    private BigDecimal porcentaje;

    @Column(name = "CRITERIODISTRIBUCIONID", length = 16, nullable = false)
    private String criterioDistribucionId;

    @Column(name = "UNIDADNEGOCIOID", length = 16, nullable = false)
    private String unidadNegocioId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "SUCURSALID", length = 16, nullable = false)
    private String sucursalId;

    @Column(name = "DOCUMENTOID", length = 16)
    private String documentoId;

    @Column(name = "UNIDADPRODUCCIONID", length = 16)
    private String unidadProduccionId;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

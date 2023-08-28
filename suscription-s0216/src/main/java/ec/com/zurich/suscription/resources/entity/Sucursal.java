package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "SUCURSAL", schema = "SA")
public class Sucursal {

    @Id()
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 40, nullable = false)
    private String nombre;

    @Column(name = "RUC", length = 13)
    private String ruc;

    @Column(name = "WEBSITE", length = 200)
    private String website;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "FECHAINICIOOPERACION", nullable = false)
    private LocalDate fechaInicioOperacion;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "CODIGOCONTABLE", length = 16, nullable = false)
    private String codigoContable;

    @Column(name = "COMPANIASEGUROSID", length = 16)
    private String companiaSegurosId;

    @Column(name = "DIRECCIONID", length = 16)
    private String direccionId;

    @Column(name = "PRIORIDAD", precision = 3)
    private Integer prioridad;

    @Column(name = "ESMATRIZ", length = 1, nullable = false, columnDefinition = "CHAR DEFAULT '0'")
    private String esMatriz;

    @Column(name = "NUMEROLOCAL", length = 3)
    private String numeroLocal;

    @Column(name = "MID", length = 10)
    private String mid;

    @Column(name = "CODIGOOFICINA", length = 4)
    private String codigoOficina;

    @Column(name = "SUCURSALFACTURACIONID", length = 16)
    private String sucursalFacturacionId;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

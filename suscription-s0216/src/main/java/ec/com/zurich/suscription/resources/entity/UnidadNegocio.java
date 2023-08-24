package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "UNIDADNEGOCIO", schema = "SA")
public class UnidadNegocio {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 40)
    private String nombre;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "SUCURSALID", length = 16)
    private String sucursalId;

    @Column(name = "PRIORIDAD", precision = 3, scale = 0)
    private Integer prioridad;

    @Column(name = "ESTAACTIVA", length = 1)
    private String estaActiva;

    @Column(name = "ESGASTO", length = 1)
    private String esGasto;

    @Column(name = "ESUNIDADNEGOCIO", length = 1)
    private String esUnidadNegocio;

    @Column(name = "TIPORAMOID", length = 20)
    private String tipoRamoId;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "VARIABLESSISTEMA", schema = "SA")
public class VariablesSistema {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "ESGLOBAL", nullable = false)
    private Boolean esGlobal;

    @Column(name = "TIPOCONTENIDO", nullable = false)
    private Integer tipoContenido;

    @Column(name = "VALOR", precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "LIMITE", precision = 19, scale = 4)
    private BigDecimal limite;

    @Column(name = "CONTENIDO", length = 3000)
    private String contenido;

    @Column(name = "SUCURSALID", length = 16)
    private String sucursalId;

    @Column(name = "COMPANIASEGUROSID", length = 16)
    private String companiaSegurosId;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NOMBRESECUENCIA", length = 50)
    private String nombreSecuencia;

    @Column(name = "ESAUDITABLE")
    private Boolean esAuditable;

    @Column(name = "PUNTOEMISION", length = 12)
    private String puntoEmision;

    @Column(name = "ACTIVA", columnDefinition = "CHAR default '1'")
    private Boolean activa;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
        if (activa == null) activa = true;
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
        if (activa == null) activa = true;
    }


}

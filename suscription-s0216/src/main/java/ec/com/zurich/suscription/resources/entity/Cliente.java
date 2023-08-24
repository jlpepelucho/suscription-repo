package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENTE", schema = "SA")
@Data
public class Cliente {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "LIMITECREDITO", nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteCredito;

    @Column(name = "REFERIDOID", length = 16)
    private String referidoId;

    @Column(name = "REFERIDO", length = 80)
    private String referido;

    @Column(name = "ENTIDADID", nullable = false, length = 16)
    private String entidadId;

    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "TIPIFICACIONID", length = 16)
    private String tipificacionId;

    @Column(name = "CONFIGPRODUCTOID", length = 16)
    private String configProductoId;

    @Column(name = "ESPRIMAEXCENTA", columnDefinition = "CHAR(1) default '0'")
    private Boolean esPrimaExcenta;

    @Column(name = "ESTAIMPRESA", columnDefinition = "CHAR(1) default '0'")
    private String estaImpresa;

    @Column(name = "BLOQUEADO", columnDefinition = "CHAR(1) default '0'")
    private String bloqueado;

    @Column(name = "DESCRIPCION_BLOQ", length = 200)
    private String descripcionBloq;

    @Column(name = "UPLAENTREGADA", columnDefinition = "CHAR(1) default '0'")
    private String uplaEntregada;

    @Column(name = "FECHAENTREGAUPLA")
    private LocalDate fechaEntregaUpla;

    @Column(name = "SECUENCIALUPLA", precision = 10)
    private BigDecimal secuencialUpla;

    @Column(name = "USUARIOVISOUPLA", length = 16)
    private String usuarioVisoUpla;

    @Column(name = "FECHARECEPCIONUPLA")
    private LocalDate fechaRecepcionUpla;

    @Column(name = "FECHAVIGENCIAUPLA")
    private LocalDate fechaVigenciaUpla;

    @Column(name = "ESTADOUPLAELECTRONICA", length = 30)
    private String estadoUplaElectronica;

    @Column(name = "TERMINOSYCONDICIONES", length = 1)
    private String terminosYCondiciones;

    @Column(name = "ACEPTAPROMOCIONES", length = 1)
    private String aceptaPromociones;

    @Column(name = "FECHAAUTORIZA")
    private LocalDate fechaAutoriza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID",  nullable = false, insertable = false, updatable = false)
    private Entidad entidad;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

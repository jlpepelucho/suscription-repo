package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RESERVACOMISIONAGENTE", schema = "SA")
public class ReservaComisionAgente {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALORRESERVA", precision = 19, scale = 4)
    private BigDecimal valorReserva;

    @Column(name = "FECHAPROVISION")
    private LocalDateTime fechaProvision;

    @Column(name = "INDICADORLIBERACION", length = 1)
    private String indicadorLiberacion;

    @Column(name = "ENDOSODIFERIDOID", length = 16)
    private String endosoDiferidoId;

    @Column(name = "TIPORESERVARIESGOCURSOID", length = 16)
    private String tipoReservaRiesgoCursoId;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "VALORULTIMAPROVISION", precision = 19, scale = 4)
    private BigDecimal valorUltimaProvision;

    @Column(name = "FECHACONSTITUCION")
    private LocalDateTime fechaConstitucion;

    @Column(name = "TIPORESERVAMENORUNANIO", length = 1)
    private String tipoReservaMenorUnAnio;

    @Column(name = "CLASEID", length = 16)
    private String claseId;

    @Column(name = "CLASE", length = 20)
    private String clase;

    @Column(name = "ESRELACIONADA", length = 1)
    private String esRelacionada;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RESERVARIESGOCURSO", schema = "SA")
public class ReservaRiesgoCurso {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "VALORRESERVA", nullable = false, precision = 19, scale = 4)
    private BigDecimal valorReserva;

    @Column(name = "FECHAPROVISION", nullable = false)
    private LocalDateTime fechaProvision;

    @Column(name = "INDICADORLIBERACION", nullable = false, length = 1)
    private String indicadorLiberacion;

    @Column(name = "ENDOSODIFERIDOID", nullable = false, length = 16)
    private String endosoDiferidoId;

    @Column(name = "TIPORESERVARIESGOCURSOID", nullable = false, length = 16)
    private String tipoReservaRiesgoCursoId;

    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "VALORULTIMAPROVISION", precision = 19, scale = 4)
    private BigDecimal valorUltimaProvision;

    @Column(name = "FECHACONSTITUCION")
    private LocalDateTime fechaConstitucion;

    @Column(name = "TIPORESERVAMENORUNANIO", length = 1, columnDefinition = "CHAR default '0'")
    private String tipoReservaMenorUnAnio;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

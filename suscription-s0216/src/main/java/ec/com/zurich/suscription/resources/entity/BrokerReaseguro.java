package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BROKERREASEGURO")
public class BrokerReaseguro {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ENTIDADID", nullable = false)
    private String entidadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID", insertable = false, updatable = false)
    private Entidad entidad;

    @Column(name = "LOCALOEXTRANJERO", nullable = false)
    private String localOExtranjero;

    @Column(name = "CODIGOCONTABLE", nullable = false)
    private String codigoContable;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "CODIGOSB", length = 5)
    private String codigoSB;

    @Column(name = "TIPOSERVICIO", length = 2)
    private String tipoServicio;

    @Column(name = "ESRELACIONADA")
    private Character esRelacionada;

    @Column(name = "NUMEROREGISTRO", length = 50)
    private String numeroRegistro;

    @Column(name = "CODIGOGRUPO", length = 5)
    private String codigoGrupo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

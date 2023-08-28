package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AUTORIZACIONSRI", schema = "SA")
public class AutorizacionSRI {

    @Id
    @Column(name = "ID", length = 16, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "EMPRESAID", length = 16, nullable = false)
    private String empresaId;

    @Column(name = "NUMEROAUTORIZACION", length = 20, nullable = false)
    private String numeroAutorizacion;

    @Column(name = "NUMEROAUTORIZACIONIMPRENTA", length = 20, nullable = false)
    private String numeroAutorizacionImprenta;

    @Column(name = "VIGENCIADESDE", nullable = false)
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA", nullable = false)
    private LocalDateTime vigenciaHasta;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "ACTIVO", length = 1)
    private String activo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PLANTILLATRATADO", schema = "SA")
public class PlantillaTratado {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "ANOSUSCRIPCION", precision = 10)
    private Integer anoSuscripcion;

    @Column(name = "LIMITEVALORASEGURADO", precision = 19, scale = 4)
    private BigDecimal limiteValorAsegurado;

    @Column(name = "VIGENCIADESDE")
    private LocalDateTime vigenciaDesde;

    @Column(name = "VIGENCIAHASTA")
    private LocalDateTime vigenciaHasta;

    @Column(name = "NOMBRE", length = 150)
    private String nombre;

    @Column(name = "TIPOPLANTILLATRATADOID", length = 16, nullable = false)
    private String tipoPlantillaTratadoId;

    @Column(name = "MONEDAID", length = 16)
    private String monedaId;

    @Column(name = "ESAUTOMATICO", length = 50)
    private Boolean esAutomatico;

    @Column(name = "ESPROPORCIONAL")
    private Boolean esProporcional;

    @Column(name = "PLANTILLATRATADOPADREID", length = 16)
    private String plantillaTratadoPadreId;

    @Column(name = "PERIODOID", length = 16)
    private String periodoId;

    @Column(name = "DIASALFINAL", precision = 10)
    private Integer diasAlFinal;

    @Column(name = "DIASBALANCE", precision = 10)
    private Integer diasBalance;

    @Column(name = "NUMEROCESION", precision = 10)
    private Integer numeroCesion;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "NEGOCIACIONID", length = 16)
    private String negociacionId;

    @Column(name = "ENDOSOID", length = 16)
    private String endosoId;

    @Column(name = "ESPECIALAUTOMATICO", length = 1)
    private char especialAutomatico;

    @Column(name = "NUMEROCESION1", precision = 10)
    private Integer numeroCesion1;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}
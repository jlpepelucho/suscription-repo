package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PLANTILLACONTRATOREASEGURO")
public class PlantillaContratoReaseguro {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "CAPACIDAD")
    private BigDecimal capacidad;

    @Column(name = "NUMEROLINEAS")
    private BigDecimal numeroLineas;

    @Column(name = "PORCRETENCION")
    private BigDecimal porcentajeRetencion;

    @Column(name = "PORCCESION")
    private BigDecimal porcentajeCesion;

    @Column(name = "ESVALORASEGURADO", nullable = false)
    private Boolean esValorAsegurado;

    @Column(name = "VALORLINEA")
    private BigDecimal valorLinea;

    @Column(name = "PLANTILLATRATADOID", nullable = false)
    private String plantillaTratadoId;

    @Column(name = "ORDEN", nullable = false)
    private BigDecimal orden;

    @Column(name = "TIPOCONTRATOREASEGUROID", nullable = false)
    private String tipoContratoReaseguroId;

    @Column(name = "GARANTIAPAGOID")
    private String garantiaPagoId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "PORCRETENCIONPRIMA", columnDefinition = "NUMBER(9,6) DEFAULT 0")
    private BigDecimal porcentajeRetencionPrima;

    @Column(name = "PORCCESIONPRIMA", columnDefinition = "NUMBER(9,6) DEFAULT 0")
    private BigDecimal porcentajeCesionPrima;

    @Column(name = "PERIODOID", columnDefinition = "VARCHAR2(16) DEFAULT '0'")
    private String periodoId;

    @Column(name = "DIAS", columnDefinition = "NUMBER(1,0) DEFAULT 0")
    private Integer dias;

    // Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANTILLATRATADOID",  insertable = false, updatable = false)
    private PlantillaTratado plantillaTratado;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

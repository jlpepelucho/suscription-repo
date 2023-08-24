package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMPONENTEXDOCUMENTO")
public class ComponenteXDocumento implements Serializable {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTOID", insertable = false, updatable = false)
    private Documento documento;

    @Column(name = "DOCUMENTOID", nullable = false)
    private String documentoId;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "VALORBASE", precision = 19, scale = 4)
    private BigDecimal valorBase;

    @Column(name = "VALOR", precision = 19, scale = 4, nullable = false)
    private BigDecimal valor;

    @Column(name = "COMPONENTEFINANCIEROID", nullable = false, length = 16)
    private String componenteFinancieroId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPONENTEFINANCIEROID", insertable = false, updatable = false)
    private ComponenteFinanciero componenteFinanciero;

    @Column(name = "RETENCIONID", length = 16)
    private String retencionId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "TIPORETENCIONID", length = 16)
    private String tipoRetencionId;

    @Column(name = "VALORCALCULADOXML", precision = 19, scale = 4)
    private BigDecimal valorCalculadoXml;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}


package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DETALLEDOCUMENTOCABECERA", schema = "SA")
public class DetalleDocumentoCabecera {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DOCUMENTOID", length = 16, nullable = false)
    private String documentoId;

    @Column(name = "NOTASDEBITOCREDITOID", length = 16, nullable = false)
    private String notasDebitoCreditoId;

    @Column(name = "VALOR", precision = 19, scale = 4, nullable = false)
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTOID", updatable = false, insertable = false)
    private Documento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTASDEBITOCREDITOID", updatable = false, insertable = false)
    private NotasDebitoCredito notasDebitoCredito;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "SALDO", precision = 19, scale = 4)
    private BigDecimal saldo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

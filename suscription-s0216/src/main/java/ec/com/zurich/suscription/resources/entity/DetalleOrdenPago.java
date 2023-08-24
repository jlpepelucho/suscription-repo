package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DETALLEORDENPAGO", schema = "SA")
public class DetalleOrdenPago {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "DOCUMENTOID", length = 16)
    private String documentoId;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "ORDENPAGOID", length = 16)
    private String ordenPagoId;

    @Column(name = "VALOR", precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "VALORCALCULADO", precision = 19, scale = 4)
    private BigDecimal valorCalculado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTOID", insertable = false,updatable = false)
    private Documento documento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDENPAGOID", insertable = false,updatable = false)
    private OrdenPago ordenPago;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

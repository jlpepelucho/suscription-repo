package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DETALLENOTACREDITOCOMISION")
public class DetalleNotaCreditoComision {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOTACREDITOCOMISIONID", nullable = false)
    private String notaCreditoComisionId;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    @Column(name = "NOTASDEBITOCREDITOID", nullable = false)
    private String notasDebitoCreditoId;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTASDEBITOCREDITOID",  insertable = false, updatable = false)
    private NotasDebitoCredito notasDebitoCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTACREDITOCOMISIONID",  insertable = false, updatable = false)
    private NotasDebitoCredito notaCreditoComision;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

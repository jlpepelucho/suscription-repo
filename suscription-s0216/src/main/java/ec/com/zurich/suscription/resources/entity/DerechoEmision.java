package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DERECHOSEMISION", schema = "SA")
public class DerechoEmision {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRITERIODERECHOSEMISIONID", insertable = false, updatable = false)
    private CriterioDerechosEmision criterioDerechosEmision;

    @Column(name = "CRITERIODERECHOSEMISIONID", length = 16)
    private String criterioDerechosEmisionId;

    @Column(name = "VALORPRIMANETAINICIAL", precision = 16, scale = 2)
    private BigDecimal valorPrimaNetaInicial;

    @Column(name = "VALORPRIMANETAFINAL", precision = 16, scale = 2)
    private BigDecimal valorPrimaNetaFinal;

    @Column(name = "VALOR", precision = 16, scale = 2)
    private BigDecimal valor;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

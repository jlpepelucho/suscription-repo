package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TARJETACREDITO", schema = "SA")
public class TarjetaCredito {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "LIMITEDEPISO", nullable = false)
    private BigDecimal limiteDePiso;

    @Column(name = "ACEPTADA", nullable = false)
    private Integer aceptada;

    @Column(name = "EMPRESAID", nullable = false)
    private String empresaId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ENTIDADEMISORAID", nullable = false)
    private String entidadEmisoraId;

    @Column(name = "CUENTABANCARIAID", nullable = false)
    private String cuentaBancariaId;

    @Column(name = "DIFERIDOCORRIENTE", nullable = false)
    private String diferidoCorriente;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FACTURACLIENTECREDITOTARJETA")
public class FacturaClienteCreditoTarjeta {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "FACTURACLIENTEID", nullable = false)
    private String facturaClienteId;

    @Column(name = "PROPIETARIOID", nullable = false)
    private String propietarioId;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "TIPOCREDITOTARJETAID", nullable = false)
    private String tipoCreditoTarjetaId;

    @Column(name = "CUOTAS", nullable = false)
    private Integer cuotas;

    @Column(name = "CODIGOESTABLECIMIENTO", nullable = false)
    private String codigoEstablecimiento;

    @Column(name = "TARJETACREDITOID", nullable = false)
    private String tarjetaCreditoId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "FECHAVENCIMIENTOTARJETA", nullable = false)
    private LocalDateTime fechaVencimientoTarjeta;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

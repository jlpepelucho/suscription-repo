package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TRANSACCIONADMINISTRATIVA")
public class TransaccionAdministrativa {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "FECHA")
    private LocalDateTime fecha;

    @Column(name = "CONCEPTO")
    private String concepto;

    @Column(name = "VALORORIGEN")
    private BigDecimal valorOrigen;

    @Column(name = "VALORLOCAL")
    private BigDecimal valorLocal;

    @Column(name = "VALOREXTRANJERA")
    private BigDecimal valorExtranjera;

    @Column(name = "MONEDAID")
    private String monedaId;

    @Column(name = "ESTADOID")
    private String estadoId;

    @Column(name = "SUCURSALID")
    private String sucursalId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "DISCRIMINADOR")
    private String discriminador;

    @Column(name = "NUMEROTRANSACCION")
    private String numeroTransaccion;

    @Column(name = "TIPOTRANSACCION")
    private String tipoTransaccion;

    @Column(name = "CAJAID")
    private String cajaId;

    @Column(name = "APLICAPAGO")
    private Character aplicaPago;

    @Column(name = "AUTORIZACIONID")
    private String autorizacionId;

    @Column(name = "SISTEMAEMISOR")
    private String sistemaEmisor;

    @Column(name = "FECHACIERRECAJA")
    private LocalDateTime fechaCierreCaja;

    @Column(name = "ESTACERRADACAJA")
    private BigDecimal estaCerradaCaja;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

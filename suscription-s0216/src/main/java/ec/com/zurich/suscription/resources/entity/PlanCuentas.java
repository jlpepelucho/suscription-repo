package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PLANCUENTAS")
public class PlanCuentas {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMEROCUENTA", length = 30)
    private String numeroCuenta;

    @Column(name = "DESCRIPCION", length = 4000)
    private String descripcion;

    @Column(name = "NUMERONIVELES")
    private Integer numeroNiveles;

    @Column(name = "EJERCICIOID", length = 16)
    private String ejercicioId;

    @Column(name = "NATURALEZACUENTA", length = 1)
    private char naturalezaCuenta;

    @Column(name = "GRUPOCUENTAID", length = 16)
    private String grupoCuentaId;

    @Column(name = "ESTAACTIVA", length = 1)
    private char estaActiva;

    @Column(name = "PADREID", length = 16)
    private String padreId;

    @Column(name = "CUENTASOBREGIROID", length = 16)
    private String cuentaSobreGiroId;

    @Column(name = "ESDETALLE", length = 1)
    private char esDetalle;

    @Column(name = "TIENEAUXILIAR", length = 1)
    private char tieneAuxiliar;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "ESPACIOBALANCE", length = 1)
    private char espacioBalance;

    @Column(name = "CODIGOCUENTAGRUPO", length = 30)
    private String codigoCuentaGrupo;

    @Column(name = "DESCRIPCIONCUENTAGRUPO", length = 4000)
    private String descripcionCuentaGrupo;

    @Column(name = "UW", length = 8)
    private String uw;

    @Column(name = "ICO", length = 6)
    private String ico;

    @Column(name = "ESCOMPANIARELACIONADAGRUPO", length = 2)
    private String esCompaniaRelacionadaGrupo;

    @Column(name = "CUENTA_SAP", length = 16)
    private String cuentaSAP;

    @Column(name = "CUENTA_FIP", length = 16)
    private String cuentaFIP;

    @Column(name = "DESCRIPCIONSAP", length = 100)
    private String descripcionSAP;

    @Column(name = "DESCRIPCIONFIP", length = 100)
    private String descripcionFIP;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

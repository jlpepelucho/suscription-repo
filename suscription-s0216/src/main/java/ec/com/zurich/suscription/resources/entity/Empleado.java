package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EMPLEADO")
public class Empleado {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "SUCURSALID")
    private String sucursalId;

    @Column(name = "CARGOID")
    private String cargoId;

    @Column(name = "USUARIOACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "RESERVALIMITEINDEMNIZACION")
    private BigDecimal reservaLimiteIndemnizacion;

    @Column(name = "RESERVALIMITEGASTO")
    private BigDecimal reservaLimiteGasto;

    @Column(name = "ENTIDADID")
    private String entidadId;

    @Column(name = "DEPARTAMENTOID")
    private String departamentoId;

    @Column(name = "TIPOINSTRUCCIONID")
    private String tipoInstruccionId;

    @Column(name = "TIPOSANGREID")
    private String tipoSangreId;

    @Column(name = "LIBRETAMILITAR")
    private String libretaMilitar;

    @Column(name = "ESTADOCIVILID")
    private String estadoCivilId;

    @Column(name = "NUMEROAFILIACIONIESS")
    private String numeroAfiliacionIESS;

    @Column(name = "TIPOCUENTA")
    private char tipoCuenta;

    @Column(name = "NUMEROCUENTA")
    private String numeroCuenta;

    @Column(name = "FECHAINGRESO")
    private LocalDateTime fechaIngreso;

    @Column(name = "FECHASALIDA")
    private LocalDateTime fechaSalida;

    @Column(name = "TIPOEMPLEADO")
    private char tipoEmpleado;

    @Column(name = "FORMAPAGO")
    private char formaPago;

    @Column(name = "NUMEROCARGAFAMILIAR")
    private Integer numeroCargaFamiliar;

    @Column(name = "SUELDO")
    private BigDecimal sueldo;

    @Column(name = "BANCOID")
    private String bancoId;

    @Column(name = "AUTORIZADOAJUSTE")
    private char autorizadoAjuste;

    @Column(name = "USUARIOTPAID")
    private String usuarioTPAId;

    @Column(name = "ESADMINISTRADOR")
    private char esAdministrador;

    @Column(name = "ACTIVO")
    private char activo;

    @Column(name = "CAMBIOCLAVE")
    private char cambioClave;

    @Column(name = "NUEVASPANTALLASEMISION")
    private char nuevasPantallasEmision;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "ESTADOSALIDA")
    private char estadoSalida;

    // Relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUCURSALID",  insertable = false, updatable = false)
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADID",  insertable = false, updatable = false)
    private Entidad entidad;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

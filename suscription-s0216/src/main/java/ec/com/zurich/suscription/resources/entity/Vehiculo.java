package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "VEHICULO", schema = "SA")
public class Vehiculo {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "CHASIS", length = 40)
    private String chasis;

    @Column(name = "CILINDROS", length = 40)
    private String cilindros;

    @Column(name = "MOTOR", length = 40)
    private String motor;

    @Column(name = "TONELAGE", precision = 9, scale = 6)
    private BigDecimal tonelage;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "COLOR", length = 40)
    private String color;

    @Column(name = "PLACAS", length = 27)
    private String placas;

    @Column(name = "TIPOUSOID", length = 16)
    private String tipoUsoId;

    @Column(name = "MODELOVEHICULOID", length = 16)
    private String modeloVehiculoId;

    @Column(name = "TIPOVEHICULOID", length = 16)
    private String tipoVehiculoId;

    @Column(name = "TARJETA", length = 16)
    private String tarjeta;

    @Column(name = "NUMEROOCUPANTES")
    private Integer numeroOcupantes;

    @Column(name = "VALOREXTRAS", precision = 19, scale = 4)
    private BigDecimal valorExtras;

    @Column(name = "PROPIETARIO", length = 200)
    private String propietario;

    @Column(name = "DISPOSITIVOSEGURIDADID", length = 16)
    private String dispositivoSeguridadId;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaActualiza;

    @Column(name = "TIENEAUTOASISTENCIA", precision = 3, scale = 0)
    private Boolean tieneAutoAsistencia;

    @Column(name = "COLORVEHICULOID", length = 17)
    private String colorVehiculoId;

    @Column(name = "NUMORDEN", precision = 22, scale = 127)
    private BigDecimal numOrden;

    @Column(name = "NUMORDENM", precision = 22, scale = 127)
    private BigDecimal numOrdenM;

    @Column(name = "MARCADORID", length = 16)
    private String marcadorId;

    @Column(name = "PORCENTAJEDESCUENTODISPOSITIVO", precision = 22, scale = 127)
    private BigDecimal porcentajeDescuentoDispositivo;

    @Column(name = "NUMORDENT", precision = 22, scale = 127)
    private BigDecimal numOrdent;

    @Column(name = "CLASEPOLICIA", precision = 3, scale = 0)
    private Integer clasePolicia;

    @Column(name = "TIPOPOLICIA", precision = 3, scale = 0)
    private Integer tipoPolicia;

    @Column(name = "TIPOIDENTIFICACIONID", length = 16)
    private String tipoIdentificacionId;

    @Column(name = "FECHAFACTURA")
    private LocalDate fechaFactura;

    @Column(name = "TARIFAID", length = 16)
    private String tarifaId;

    @Column(name = "TIPOIDENTIFICACIONANTERIORID", length = 16)
    private String tipoIdentificacionAnteriorId;

    @Column(name = "ESTAACTUALIZADO", length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String estaActualizado;

    @Column(name = "IDENTIFICACION", length = 100)
    private String identificacion;

    @Column(name = "SUBTIPOVEHICULOID", length = 16)
    private String subtipoVehiculoId;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Item item;
    @Transient
    private String tipoVehiculo;
    @Transient
    private String tipoUso;
    @Transient
    private String modelo;
    @Transient
    private String marca;
}

package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "DETALLEPRODUCTO")
public class DetalleProducto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONFIGPRODUCTOID", nullable = false)
    private ConfiguracionProducto configProductoId;

    @NotNull
    @Column(name = "TASA", nullable = false, precision = 9, scale = 4)
    private BigDecimal tasa;

    @NotNull
    @Column(name = "MONTO", nullable = false, precision = 19, scale = 4)
    private BigDecimal monto;

    @NotNull
    @Column(name = "PRIMA", nullable = false, precision = 19, scale = 4)
    private BigDecimal prima;

    @NotNull
    @Column(name = "AFECTAPRIMA", nullable = false)
    private Boolean afectaPrima = false;

    @NotNull
    @Column(name = "AFECTAVALORASEGURADO", nullable = false)
    private Boolean afectaValorAsegurado = false;

    @Size(max = 4000)
    @Column(name = "TEXTO", length = 4000)
    private String texto;

    @Column(name = "VALORPERIODO", precision = 19, scale = 4)
    private BigDecimal valorPeriodo;

    @Column(name = "PERIODICIDAD")
    private Short periodicidad;

    @Column(name = "TIPOPERIODO")
    private Boolean tipoPeriodo;

    @NotNull
    @Column(name = "DEFECTO", nullable = false)
    private Boolean defecto = false;

    @Column(name = "PORCENTAJECOMISION", precision = 9, scale = 4)
    private BigDecimal porcentajeComision;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioActualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaActualiza;

    @Column(name = "PRIMABASICA", precision = 19, scale = 4)
    private BigDecimal primaBasica;

    @Column(name = "PORCCOMISIONVENDEDOR", precision = 9, scale = 4)
    private BigDecimal porcComisionVendedor;

    @Column(name = "PORCUTILIDAD", precision = 9, scale = 4)
    private BigDecimal porcUtilidad;

    @Column(name = "PORCOTROS", precision = 9, scale = 4)
    private BigDecimal porcOtros;

    @Size(max = 16)
    @Column(name = "PROVEEDORSERVICIOID", length = 16)
    private String proveedorServicioId;

    @Size(max = 1)
    @Column(name = "ACTIVADOPORESB", length = 1)
    private String activadoPorESB;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PLANID", nullable = false)
    private Plan planid;

    @Column(name = "COBERTURASCONJID", nullable = false)
    private String coberturasConjuntoId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COBERTURASCONJID", insertable = false,updatable = false)
    private Coberturasconjunto coberturasconjunto;
}
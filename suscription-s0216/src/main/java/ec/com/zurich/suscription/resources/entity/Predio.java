package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "PREDIO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Predio implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Column(name = "VALORPRIMADEPOSITO", precision = 19, scale = 4, nullable = false)
    private BigDecimal valorPrimaDeposito;

    @Column(name = "PORCENTAJEPRIMADEPOSITO", precision = 9, scale = 6, nullable = false)
    private BigDecimal porcentajePrimaDeposito;

    @Column(name = "NOMBRE", length = 250)
    private String nombre;

    @Column(name = "VALORASEGURADO", precision = 19, scale = 4)
    private BigDecimal valorAsegurado;

    @Column(name = "VALORCONTENIDO", precision = 19, scale = 4)
    private BigDecimal valorContenido;

    @Column(name = "VALOREDIFICIO", precision = 19, scale = 4)
    private BigDecimal valorEdificio;

    @Column(name = "VALORFLOTANTE", precision = 19, scale = 4)
    private BigDecimal valorFlotante;

    @Column(name = "TIPOPREDIOID", length = 16, nullable = false)
    private String tipoPredioId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TIPOPREDIOID", insertable= false, updatable = false)
    private TipoPredio tipoPredio;


    @Column(name = "DIRECCIONID", length = 16, nullable = false)
    private String direccionId;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="DIRECCIONID", insertable= false, updatable = false)
    private Direccion direccion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID", referencedColumnName = "ID", insertable = false, updatable = false)
    private Item item;



    @Column(name = "TASA", precision = 9, scale = 6)
    private BigDecimal tasa;

    @Column(name = "ESPRIMAFIJA", columnDefinition = "CHAR(1)")
    private Character esPrimaFija;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private Timestamp fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16, nullable = false)
    private String usuarioActualiza;

    @Column(name = "MATERIALCONSTRUCCIONID", length = 16, nullable = false)
    private String materialConstruccionId;

    @Column(name = "ANIOCONSTRUCCION")
    private Integer anioConstruccion;

    @Column(name = "NUMEROPISOS")
    private Integer numeroPisos;

    @Column(name = "TIENEPROTECCIONINUNDACION", columnDefinition = "CHAR(1)")
    private Character tieneProteccionInundacion;

    @Column(name = "TIENESOTANO", columnDefinition = "CHAR(1)")
    private Character tieneSotano;

    @Column(name = "ESTADOINFORMACION", columnDefinition = "CHAR(1)")
    private Character estadoInformacion;

    @Column(name = "CATALOGOSICID", length = 16)
    private String catalogoSicId;

    @Column(name = "CATALOGONAICSID", length = 16)
    private String catalogoNaicsId;

}

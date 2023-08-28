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
@Table(name = "TRANSPORTEGENERAL")
public class TransporteGeneral {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID", nullable = false)
    private Item item;

    @Size(max = 1000)
    @NotNull
    @Column(name = "DESCRIPCION", nullable = false, length = 1000)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PUERTOGENERALLLEGADAID", nullable = false)
    private PuertoGeneral puertogeneralllegadaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PUERTOGENERALEMBARQUEID")
    private PuertoGeneral puertogeneralembarqueid;

    @NotNull
    @Column(name = "LIMITEPOREMBARQUE", nullable = false, precision = 19, scale = 4)
    private BigDecimal limiteporembarque;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOEMBALAJEID")
    private TipoEmbalaje tipoembalajeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOMERCADERIAID")
    private TipoMercaderia tipomercaderiaid;

    @Column(name = "PORCENTAJESOBRESEGURO", precision = 19, scale = 4)
    private BigDecimal porcentajesobreseguro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOTRANSPORTEID")
    private TipoTransporte tipotransporteid;

}
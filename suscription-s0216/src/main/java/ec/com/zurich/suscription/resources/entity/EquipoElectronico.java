package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "EQUIPOELECTRONICO")
public class EquipoElectronico {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID", nullable = false)
    private Item item;

    @Size(max = 40)
    @Column(name = "ACTIVIDADEMPRESA", length = 40)
    private String actividadempresa;

    @Column(name = "ANIOEQUIPO")
    private Integer anioequipo;

    @Size(max = 100)
    @NotNull
    @Column(name = "CARACTERISTICASEQUIPO", nullable = false, length = 100)
    private String caracteristicasequipo;

    @NotNull
    @Column(name = "ESEQUIPOPROPIO", nullable = false)
    private Boolean esequipopropio = false;

    @Size(max = 40)
    @Column(name = "ITEMASEGURADO", length = 40)
    private String itemasegurado;

    @Size(max = 40)
    @Column(name = "LUGAROPERACION", length = 40)
    private String lugaroperacion;

    @NotNull
    @Column(name = "MONTORECUPERACIONINFORMACIONPE", nullable = false, precision = 19, scale = 4)
    private BigDecimal montorecuperacioninformacionpe;

    @NotNull
    @Column(name = "MONTORESTAURACIONDATOS", nullable = false, precision = 19, scale = 4)
    private BigDecimal montorestauraciondatos;

    @Size(max = 50)
    @NotNull
    @Column(name = "NUMEROSERIE", nullable = false, length = 50)
    private String numeroserie;

    @Size(max = 50)
    @Column(name = "PROGRAMAS", length = 50)
    private String programas;

    @NotNull
    @Column(name = "SONDATOSFIJOS", nullable = false)
    private Boolean sondatosfijos = false;

    @NotNull
    @Column(name = "VALORASEGURADOPROGRAMAS", nullable = false, precision = 19, scale = 4)
    private BigDecimal valoraseguradoprogramas;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "PRIMA", precision = 19, scale = 4)
    private BigDecimal prima;

    @Column(name = "TASA", precision = 9, scale = 6)
    private BigDecimal tasa;

    @Column(name = "VALORTOTALEQUIPOS", precision = 19, scale = 4)
    private BigDecimal valortotalequipos;

    @Column(name = "VALORTOTALPORTADORES", precision = 19, scale = 4)
    private BigDecimal valortotalportadores;

    @Column(name = "VALORASEGURADORECONSTRUCCIN", precision = 19, scale = 4)
    private BigDecimal valoraseguradoreconstruccin;

    @Column(name = "VALORTOTALINCREMENTO", precision = 19, scale = 4)
    private BigDecimal valortotalincremento;

    @Column(name = "MONTOTRANSPORTEMATERIAL", precision = 19, scale = 4)
    private BigDecimal montotransportematerial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCIONID")
    private Direccion direccionid;

    @Column(name = "VALPOR", precision = 19, scale = 4)
    private BigDecimal valpor;

}
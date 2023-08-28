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
@Table(name = "ENDOSOITEMDETALLE")
public class EndosoItemDetalle {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @NotNull
    @Column(name = "ENDOSOITEMID", nullable = false)
    private String endosoitemid;

    @NotNull
    @Column(name = "ITEMID", nullable = false)
    private String itemid;

    @Size(max = 16)
    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioactualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

    @Column(name = "VALOR", precision = 19, scale = 4)
    private BigDecimal valor;

    @Column(name = "CONSECUTIVO")
    private Short consecutivo;

    @Column(name = "TIPO")
    private Boolean tipo;

    @Column(name = "FECHATIPO")
    private LocalDate fechatipo;

    @Column(name = "VALORPRIMAASISTENCIA", precision = 19, scale = 4)
    private BigDecimal valorprimaasistencia;

    @Column(name = "VALORPRIMAVIDA", precision = 19, scale = 4)
    private BigDecimal valorprimavida;

    @Column(name = "TIPOTITULARID")
    private String tipotitularid;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ESSELECCIONADO")
    private Boolean esseleccionado;

    @Column(name = "FECHASALIDA")
    private LocalDate fechasalida;

    @Column(name = "FECHAENTRADA")
    private LocalDate fechaentrada;

    @Column(name = "ESMUERTO")
    private Boolean esmuerto;

}
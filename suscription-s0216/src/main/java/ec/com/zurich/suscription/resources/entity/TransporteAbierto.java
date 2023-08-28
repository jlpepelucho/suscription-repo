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
@Table(name = "TRANSPORTEABIERTO")
public class TransporteAbierto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @NotNull
    @Column(name = "COBERTURAID", nullable = false)
    private String coberturaid;

    @Size(max = 16)
    @NotNull
    @Column(name = "TIPOEMBALAJEID", nullable = false, length = 16)
    private String tipoembalajeid;

    @Size(max = 16)
    @NotNull
    @Column(name = "MEDIOTRANSPORTEID", nullable = false, length = 16)
    private String mediotransporteid;

    @Size(max = 16)
    @NotNull
    @Column(name = "TIPOTRANSPORTEID", nullable = false, length = 16)
    private String tipotransporteid;

    @NotNull
    @Column(name = "TASA", nullable = false, precision = 19, scale = 4)
    private BigDecimal tasa;

    @NotNull
    @Column(name = "LIMITEMAXIMOEMBARQUE", nullable = false, precision = 19, scale = 4)
    private BigDecimal limitemaximoembarque;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "RECARGO_MEDIOTRANSPORTE", precision = 19, scale = 4)
    private BigDecimal recargoMediotransporte;

    @Column(name = "RECARGO_COBERTURA", precision = 19, scale = 4)
    private BigDecimal recargoCobertura;

    @Column(name = "ESTIMADOMOVILIZACIONANUAL", precision = 19, scale = 4)
    private BigDecimal estimadomovilizacionanual;

    @Column(name = "TASAMINIMA", precision = 19, scale = 4)
    private BigDecimal tasaminima;

    @Column(name = "RECARGO_OBJETOASEGURADO", precision = 19, scale = 4)
    private BigDecimal recargoObjetoasegurado;

    @Column(name = "PORCENTAJEPRIMADEPOSITO", precision = 19, scale = 4)
    private BigDecimal porcentajeprimadeposito;

    @Column(name = "VALORPRIMADEPOSITO", precision = 19, scale = 4)
    private BigDecimal valorprimadeposito;

}
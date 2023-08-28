package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "HISTORICOTRANSPORTEABIERTO")
public class HistoricoTransporteAbierto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 16)
    @Column(name = "COBERTURAID", length = 16)
    private String coberturaid;

    @Size(max = 16)
    @Column(name = "TIPOEMBALAJEID", length = 16)
    private String tipoembalajeid;

    @Size(max = 16)
    @Column(name = "MEDIOTRANSPORTEID", length = 16)
    private String mediotransporteid;

    @Size(max = 16)
    @Column(name = "TIPOTRANSPORTEID", length = 16)
    private String tipotransporteid;

    @Column(name = "TASA", precision = 19, scale = 4)
    private BigDecimal tasa;

    @Column(name = "LIMITEMAXIMOEMBARQUE", precision = 19, scale = 4)
    private BigDecimal limitemaximoembarque;

    @Size(max = 16)
    @Column(name = "OBJETOASEGURADOID", length = 16)
    private String objetoaseguradoid;

    @Size(max = 16)
    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioactualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

    @Column(name = "RECARGO_MEDIOTRANSPORTE", precision = 19, scale = 4)
    private BigDecimal recargoMediotransporte;

    @Column(name = "ESTIMADOMOVILIZACIONANUAL", precision = 19, scale = 4)
    private BigDecimal estimadomovilizacionanual;

    @Column(name = "RECARGO_COBERTURA", precision = 19, scale = 4)
    private BigDecimal recargoCobertura;

    @Column(name = "RECARGO_OBJETOASEGURADO", precision = 19, scale = 4)
    private BigDecimal recargoObjetoasegurado;

    @Column(name = "TASAMINIMA", precision = 19, scale = 4)
    private BigDecimal tasaminima;

    @Column(name = "ORDEN")
    private Short orden;

}
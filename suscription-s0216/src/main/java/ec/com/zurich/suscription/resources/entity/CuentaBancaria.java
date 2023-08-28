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
@Table(name = "CUENTABANCARIA")
public class CuentaBancaria {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 50)
    @Column(name = "NUMEROCHEQUE", length = 50)
    private String numerocheque;

    @Size(max = 40)
    @NotNull
    @Column(name = "NUMERO", nullable = false, length = 40)
    private String numero;

    @NotNull
    @Column(name = "SALDO", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldo;

    @NotNull
    @Column(name = "SALDOMONEDALOCAL", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldomonedalocal;

    @NotNull
    @Column(name = "SALDOMONEDAEXTRANJERA", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldomonedaextranjera;

    @NotNull
    @Column(name = "SALDOINICIAL", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoinicial;

    @NotNull
    @Column(name = "SALDOINICIALMONEDALOCAL", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoinicialmonedalocal;

    @NotNull
    @Column(name = "SALDOINICIALMONEDAEXTRANJERA", nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoinicialmonedaextranjera;

    @Size(max = 16)
    @NotNull
    @Column(name = "CODIGOCONTABLE", nullable = false, length = 16)
    private String codigocontable;

    @NotNull
    @Column(name = "TIPOCUENTA", nullable = false)
    private Boolean tipocuenta = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MONEDAID", nullable = false)
    private Moneda monedaid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "ESTAACTIVA")
    private Boolean estaactiva;

    @Size(max = 100)
    @Column(name = "FORMATO", length = 100)
    private String formato;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BANCOID", nullable = false)
    private Banco bancoid;

}
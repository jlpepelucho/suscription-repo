package ec.com.zurich.suscription.resources.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * CrearFacturaObj
 */

@Validated
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({ "UnusedReturnValue", "WeakerAccess" })
public class CrearFacturaObj {
    @JsonProperty("endososId")
    private List<String> endososId = null;

    @JsonProperty("debito")
    private String debito = null;

    @JsonProperty("totalFactura")
    private BigDecimal totalFactura = null;

    @JsonProperty("valorFormaPago")
    private List<BigDecimal> valorFormaPago = null;

    @JsonProperty("tipoPago")
    private List<String> tipoPago = null;

    @JsonProperty("porcentajeFormaPago")
    private List<BigDecimal> porcentajeFormaPago = null;

    @JsonProperty("numeroPagos")
    private List<BigDecimal> numeroPagos = null;

    @JsonProperty("cuotaLiberaComision")
    private List<BigDecimal> cuotaLiberaComision = null;

    @JsonProperty("fechasPrimerPago")
    private List<LocalDateTime> fechasPrimerPago = null;

    @JsonProperty("formaPagoId")
    private String formaPagoId = null;

    @JsonProperty("periodosPagos")
    private List<BigDecimal> periodosPagos = null;

    @JsonProperty("valorCuotaInicial")
    private BigDecimal valorCuotaInicial = null;

    @JsonProperty("valorInteres")
    private List<BigDecimal> valorInteres = null;

    @JsonProperty("usuarioActualiza")
    private String usuarioActualiza = null;

    @JsonProperty("tipoDocumento")
    private String tipoDocumento = null;

    @JsonProperty("formaCalculo")
    private String formaCalculo = null;

    @JsonProperty("valorDerechoEmisionManual")
    private BigDecimal valorDerechoEmisionManual = null;

    @JsonProperty("tipoEstablecimiento")
    private String tipoEstablecimiento = null;

    @JsonProperty("tipoCredito")
    private String tipoCredito = null;

    @JsonProperty("numeroTarjeta")
    private String numeroTarjeta = null;

    @JsonProperty("cuotasDiferido")
    private Integer cuotasDiferido = null;

    @JsonProperty("fechaVencimiento")
    private String fechaVencimiento = null;

}

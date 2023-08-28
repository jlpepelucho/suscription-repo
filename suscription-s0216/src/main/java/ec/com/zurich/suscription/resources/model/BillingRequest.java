package ec.com.zurich.suscription.resources.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * BillingResponse
 */
@Validated

public record BillingRequest(
            @JsonProperty("crearFacturaObj") CrearFacturaObj crearFacturaObj,
            @JsonProperty("aplicacionTransporteFacturacion") List<ApplicationTransportData> aplicacionTransporteFacturacion

) {
}

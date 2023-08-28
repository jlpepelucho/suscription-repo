package ec.com.zurich.suscription.resources.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * TransportData
 */
@Validated

public record TransportData(
    @JsonProperty("policyNumber") String policyNumber,

    @JsonProperty("applicationNumber") String applicationNumber,

    @JsonProperty("orderPolicy") Integer orderPolicy

) {
}

package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * ApplicationTransportData
 */
@Validated

public record ApplicationTransportData(
    @JsonProperty("transports")

    List<TransportData> transports,

    @JsonProperty("userUpdate") String userUpdate

) {
}

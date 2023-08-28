package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillingResponse
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BillingResponse   {
  @JsonProperty("documentId")
  private String documentId = null;

  @JsonProperty("documentNumber")
  private String documentNumber = null;

}

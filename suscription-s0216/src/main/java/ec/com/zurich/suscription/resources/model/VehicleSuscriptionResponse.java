package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * VehicleSuscriptionResponse
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public  class VehicleSuscriptionResponse extends BillingResponse  {
  @JsonProperty("itemId")
  private String itemId=null;
}

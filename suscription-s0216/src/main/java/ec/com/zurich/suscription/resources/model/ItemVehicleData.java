package ec.com.zurich.suscription.resources.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * ItemVehicleData
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemVehicleData {
  @JsonProperty("vehicleData")
  VehicleData vehicleData=null;

  @JsonProperty("endorsementItem")
  EndorsementItem endorsementItem=null;

  @JsonProperty("productId")
  String productId=null;

  @JsonProperty("endorsementItemProductData")

  List<EndorsementItemProductData> endorsementItemProductData=null;


}

package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import org.springframework.validation.annotation.Validated;


/**
 * VehicleSuscriptionData
 */
@Validated


public record VehicleSuscriptionData   (
  @JsonProperty("container")
  String container,

  @JsonProperty("numberPayments")
  Integer numberPayments,

  @JsonProperty("agentIds")

  List<String> agentIds,

  @JsonProperty("endorsement")
  EndorsementSuscription endorsement,

  @JsonProperty("vehicle")
  ItemVehicleData vehicle,

  @JsonProperty("invoice")
  DocumentData invoice

){}

package ec.com.zurich.suscription.resources.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;


/**
 * VehicleData
 */
@Validated

public record VehicleData   (
  @JsonProperty("id")
  String id ,

  @JsonProperty("year")
  Integer year ,

  @JsonProperty("chassis")
  String chassis ,

  @JsonProperty("cylinders")
  String cylinders ,

  @JsonProperty("colorVehicleId")
  String colorVehicleId ,

  @JsonProperty("securityDeviceId")
  String securityDeviceId ,

  @JsonProperty("brandVehicleId")
  String brandVehicleId ,

  @JsonProperty("modelVehicleId")
  String modelVehicleId ,

  @JsonProperty("engine")
  String engine ,

  @JsonProperty("occupantsNumber")
  Integer occupantsNumber ,

  @JsonProperty("orderNumber")
  Integer orderNumber ,

  @JsonProperty("plate")
  String plate ,

  @JsonProperty("deviceDiscountPercentage")
  Double deviceDiscountPercentage ,

  @JsonProperty("owner")
  String owner ,

  @JsonProperty("card")
  String card ,

  @JsonProperty("hadRoadsideAssistance")
  Boolean hadRoadsideAssistance ,

  @JsonProperty("useTypeId")
  String useTypeId ,

  @JsonProperty("vehicleTypeId")
  String vehicleTypeId ,

  @JsonProperty("tonnage")
  Double tonnage ,

  @JsonProperty("depreciation")
  Double depreciation ,

  @JsonProperty("text")
  String text ,

  @JsonProperty("value")
  Double value ,

  @JsonProperty("riskZoneId")
  String riskZoneId ,

  @JsonProperty("userUpdate")
  String userUpdate ,

  @JsonProperty("dateUpdate")
  Timestamp dateUpdate


  ){}

package ec.com.zurich.suscription.resources.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

/**
 * Endorsement
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Endorsement   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("clientId")
  private String clientId = null;

  @JsonProperty("paymentAgreementId")
  private String paymentAgreementId = null;

  @JsonProperty("isDataFromAnotherSystem")
  private Boolean isDataFromAnotherSystem = null;

  @JsonProperty("isMotherPolicy")
  private Boolean isMotherPolicy = null;

  @JsonProperty("isExemptVat")
  private Boolean isExemptVat = null;

  @JsonProperty("digitalSignatureId")
  private String digitalSignatureId = null;

  @JsonProperty("printsOriginal")
  private String printsOriginal = null;

  @JsonProperty("number")
  private Integer number = null;

  @JsonProperty("certificateNumber")
  private String certificateNumber = null;

  @JsonProperty("comments")
  private String comments = null;

  @JsonProperty("pointSaleId")
  private String pointSaleId = null;

  @JsonProperty("branchId")
  private String branchId = null;

  @JsonProperty("userUpdate")
  private String userUpdate = null;

  @JsonProperty("insuredValue")
  private BigDecimal insuredValue = null;

  @JsonProperty("validityFrom")
  private Timestamp validityFrom = null;

  @JsonProperty("validityTo")
  private Timestamp validityTo = null;

  @JsonProperty("dateUpdate")
  private Timestamp dateUpdate = null;

  @JsonProperty("createDate")
  private Timestamp createDate = null;

  @JsonProperty("businessUnitId")
  private String businessUnitId = null;

  @JsonProperty("itemTypeId")
  private String itemTypeId = null;

  @JsonProperty("issuerSystem")
  private String issuerSystem = null;

  @JsonProperty("surchargeValueSc")
  private BigDecimal surchargeValueSc = null;

}

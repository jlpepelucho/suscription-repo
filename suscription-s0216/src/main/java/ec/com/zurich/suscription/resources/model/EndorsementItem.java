package ec.com.zurich.suscription.resources.model;


import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * EndorsementItem
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EndorsementItem {
  @JsonProperty("id")
  String id = null;

  @JsonProperty("clarification")
  String clarification = null;

  @JsonProperty("assistanceId")
  String assistanceId = null;

  @JsonProperty("authorizationId")
  String authorizationId = null;

  @JsonProperty("amount")
  Integer amount = null;

  @JsonProperty("discountAmount")
  BigDecimal discountAmount = null;

  @JsonProperty("description")
  String description = null;

  @JsonProperty("descriptionItem")
  String descriptionItem = null;

  @JsonProperty("isAuditable")
  String isAuditable = null;

  @JsonProperty("esBene")
  String esBene = null;

  @JsonProperty("dateUpdate")
  Timestamp dateUpdate = null;

  @JsonProperty("fonsat")
  BigDecimal fonsat = null;

  @JsonProperty("itemId")
  String itemId = null;

  @JsonProperty("riskClassId")
  String riskClassId = null;

  @JsonProperty("riskTypeId")
  String riskTypeId = null;

  @JsonProperty("name")
  String name = null;

  @JsonProperty("itemNumber")
  Integer itemNumber = null;

  @JsonProperty("firePremiumPercentage")
  BigDecimal firePremiumPercentage = null;

  @JsonProperty("netPremiumAm")
  BigDecimal netPremiumAm = null;

  @JsonProperty("ifAdd")
  Boolean ifAdd = null;

  @JsonProperty("fireRate")
  BigDecimal fireRate = null;

  @JsonProperty("itemTypeId")
  String itemTypeId = null;

  @JsonProperty("templateId")
  String templateId = null;

  @JsonProperty("userUpdate")
  String userUpdate = null;

  @JsonProperty("val1")
  BigDecimal val1 = null;

  @JsonProperty("val2")
  BigDecimal val2 = null;

  @JsonProperty("val3")
  BigDecimal val3 = null;

  @JsonProperty("val4")
  BigDecimal val4 = null;

  @JsonProperty("valLimit")
  BigDecimal valLimit = null;

  @JsonProperty("insuredValue")
  BigDecimal insuredValue = null;

  @JsonProperty("itemValue")
  BigDecimal itemValue = null;

  @JsonProperty("firePremiumValue")
  BigDecimal firePremiumValue = null;

  @JsonProperty("netPremiumValue")
  BigDecimal netPremiumValue = null;

  @JsonProperty("unitValue")
  BigDecimal unitValue = null;

  @JsonProperty("endorsementId")
  String endorsementId = null;

  @JsonProperty("statusId")
  String statusId = null;


}

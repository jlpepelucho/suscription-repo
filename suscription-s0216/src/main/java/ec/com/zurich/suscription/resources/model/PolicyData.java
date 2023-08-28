package ec.com.zurich.suscription.resources.model;


import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;

/**
 * PolicyData
 */
@Validated
public record PolicyData   (
  @JsonProperty("id")
  String id ,

  @JsonProperty("validityDays")
  BigDecimal validityDays ,

  @JsonProperty("tradeId")
  String tradeId ,

  @JsonProperty("clientId")
  String clientId ,

  @JsonProperty("currencyId")
  String currencyId ,

  @JsonProperty("policyClassId")
  String policyClassId ,

  @JsonProperty("mainId")
  String mainId ,

  @JsonProperty("insuranceTypeId")
  String insuranceTypeId ,

  @JsonProperty("productionUnitId")
  String productionUnitId ,

  @JsonProperty("validityPolicyId")
  String validityPolicyId ,

  @JsonProperty("isPymes")
  Boolean isPymes ,

  @JsonProperty("insuredRouteId")
  String insuredRouteId ,

  @JsonProperty("isSettlementPayment")
  Boolean isSettlementPayment ,

  @JsonProperty("isPayment100")
  String isPayment100 ,

  @JsonProperty("isRenewal")
  Boolean isRenewal ,

  @JsonProperty("referenceInvoiceNumber")
  String referenceInvoiceNumber ,

  @JsonProperty("order")
  BigDecimal order ,

  @JsonProperty("createDate")
  Timestamp createDate ,

  @JsonProperty("configProductId")
  String configProductId,

  @JsonProperty("applyPaymentLiquidation")
  String applyPaymentLiquidation,


  @JsonProperty("branchId")
  String branchId,


  @JsonProperty("parentId")
  String parentId


){}

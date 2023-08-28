package ec.com.zurich.suscription.resources.model;



import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * EndorsementSuscription
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EndorsementSuscription extends Endorsement  {
  @JsonProperty("association")
  private String association = null;

  @JsonProperty("paymentAgreementDays")
  private Integer paymentAgreementDays = null;

  @JsonProperty("isGoldenYear")
  private Boolean isGoldenYear = null;

  @JsonProperty("transactionNumber")
  private Integer transactionNumber = null;

  @JsonProperty("policy")
  private PolicyData policy = null;

  @JsonProperty("netPremiumValue")
  private BigDecimal netPremiumValue = null;

  @JsonProperty("agentCommissionPercentage")
  private BigDecimal agentCommissionPercentage = null;

  @JsonProperty("agentCommissionValue")
  private BigDecimal agentCommissionValue = null;

  @JsonProperty("baseRate")
  private BigDecimal baseRate = null;

  @JsonProperty("minimumPremium")
  private String minimumPremium = null;

  @JsonProperty("compensationLimit")
  private String compensationLimit = null;

  @JsonProperty("claimNotificationDays")
  private BigDecimal claimNotificationDays = null;

  @JsonProperty("creditExpirationDate")
  private Timestamp creditExpirationDate = null;


}

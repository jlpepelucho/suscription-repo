package ec.com.zurich.suscription.resources.model;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DocumentData
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentData   {
  @JsonProperty("endorsementIds")
    List<String> endorsementIds = null;

  @JsonProperty("debit")
  String debit = null;

  @JsonProperty("totalInvoice")
  BigDecimal totalInvoice = null;

  @JsonProperty("paymentMethods")

  List<BigDecimal> paymentMethods = null;

  @JsonProperty("paymentTypes")

  List<String> paymentTypes = null;

  @JsonProperty("paymentMethodsPercentages")

  List<BigDecimal> paymentMethodsPercentages = null;

  @JsonProperty("numberPayments")

  List<BigDecimal> numberPayments = null;

  @JsonProperty("commisionFreeQuote")

  List<BigDecimal> commisionFreeQuote = null;

  @JsonProperty("firstPaymentDate")

  List<Timestamp> firstPaymentDate = null;

  @JsonProperty("methodPaymentId")
  String methodPaymentId = null;

  @JsonProperty("paymentPeriods")

  List<BigDecimal> paymentPeriods = null;

  @JsonProperty("initialQuote")
  BigDecimal initialQuote = null;

  @JsonProperty("interest")

  List<BigDecimal> interest = null;

  @JsonProperty("userUpdate")
  String userUpdate = null;

  @JsonProperty("documentTypeId")
  String documentTypeId = null;

  @JsonProperty("calculationMethod")
  String calculationMethod = null;

  @JsonProperty("manualIssuanceRight")
  BigDecimal manualIssuanceRight = null;

  @JsonProperty("establishmentType")
  String establishmentType = null;

  @JsonProperty("creditTypeCode")
  String creditTypeCode = null;

  @JsonProperty("cardNumber")
  String cardNumber = null;

  @JsonProperty("deferredQuote")
  Integer deferredQuote = null;

  @JsonProperty("dueDate")
  String dueDate = null;

 }

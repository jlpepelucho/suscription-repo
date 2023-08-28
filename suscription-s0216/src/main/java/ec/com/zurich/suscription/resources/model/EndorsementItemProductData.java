package ec.com.zurich.suscription.resources.model;


import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * EndorsementItemProductData
 */
@Validated

public record EndorsementItemProductData   (
  @JsonProperty("groupId")
  String groupId,

  @JsonProperty("planId")
  String planId,

  @JsonProperty("userUpdate")
  String userUpdate,

  @JsonProperty("dateUpdate")
  Timestamp dateUpdate


){}

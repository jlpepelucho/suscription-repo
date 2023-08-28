package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Endorsement
 */
@NoArgsConstructor
@AllArgsConstructor
@Data

public class EndosoAgenteObj {
  @JsonProperty("id")
  private String id = null;
  @JsonProperty("agenteId")
  private String agenteId = null;
  @JsonProperty("endosoId")
  private String endosoId = null;
  @JsonProperty("participacion")
  private String participacion = null;
  @JsonProperty("esLider")
  private String esLider = null;
  @JsonProperty("acuerdoId")
  private String acuerdoId = null;
  @JsonProperty("agenteSinContrato")
  private String agenteSinContrato = null;
  @JsonProperty("participacionSinContrato")
  private String participacionSinContrato = null;
  @JsonProperty("usuarioActualiza")
  private String usuarioActualiza = null;


}

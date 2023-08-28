package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ObjCreacionEndosoItemGrupoCobertura {
  @JsonProperty("endosoDiferidoId")
  private String endosoDiferidoId = null;
  @JsonProperty("usuarioActualiza")
  private String usuarioActualiza = null;
  @JsonProperty("fechaActual")
  private OffsetDateTime fechaActual = null;
  @JsonProperty("forzarReparto")
  private Boolean forzarReparto = null;
  @JsonProperty("esMasivo")
  private Boolean esMasivo = null;

}

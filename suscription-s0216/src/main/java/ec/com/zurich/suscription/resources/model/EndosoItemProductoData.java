package ec.com.zurich.suscription.resources.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


/**
 * EndosoItemProductoData
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EndosoItemProductoData {
  @JsonProperty("conjuntoId")
  private String conjuntoId = null;
  @JsonProperty("planId")
  private String planId = null;
  @JsonProperty("usuarioActualiza")
  private String usuarioActualiza = null;
  @JsonProperty("fechaActualiza")
  @JsonFormat(
          shape = JsonFormat.Shape.STRING,
          pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
  )
  private OffsetDateTime fechaActualiza = null;

 }

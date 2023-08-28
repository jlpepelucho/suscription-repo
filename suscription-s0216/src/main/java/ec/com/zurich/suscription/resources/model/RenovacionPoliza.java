package ec.com.zurich.suscription.resources.model;

import java.math.BigDecimal;

public record RenovacionPoliza(

    String polizaId,
    String usuarioActualiza,
    EntidadData ent,
    BigDecimal elnumeroPolizaARenovarEn,
    String elMismo,
    String idPadre,
    BigDecimal contador,
    String produccion,
    Integer mesesGracia,
    BigDecimal primaNetaOriginarsa

) {}
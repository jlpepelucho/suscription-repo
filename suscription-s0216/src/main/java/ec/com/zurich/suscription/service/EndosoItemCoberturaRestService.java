package ec.com.zurich.suscription.service;



import ec.com.zurich.suscription.resources.entity.EndosoItem;

import java.math.BigDecimal;

public interface EndosoItemCoberturaRestService {


    String obtenerNombreCanalPorPolizaId(String polizaId);

    BigDecimal encuentraPrimaAMporTipoEndoso(EndosoItem endosoItem, String tipoEndoso);

    boolean esTipoEndosoValido(String tipoEndosoNT);

    boolean esTipoItemIdValido(String tipoItemId);

    int numeroTipoEndoso(String tipoEndoso);

}

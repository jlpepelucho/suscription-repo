package ec.com.zurich.suscription.service;

import java.math.BigDecimal;

public interface AplicacionTransporteDbService {

    BigDecimal consultarPrimaDeposito(String itemId);

    BigDecimal consultarTasaCobertura(String itemId);

}

package ec.com.zurich.suscription.service;



public interface FacturacionDbService {
    Boolean generarReserva(String endoso);

    void validarPrimaEndosoItemGrupoCoberturaByEndosoDiferido(String endosoDiferidoId);

}

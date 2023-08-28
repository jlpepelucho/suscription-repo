package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Transporte;

public interface TransporteDbService {

    Transporte consultarTransportePorItemId(String itemId);

}

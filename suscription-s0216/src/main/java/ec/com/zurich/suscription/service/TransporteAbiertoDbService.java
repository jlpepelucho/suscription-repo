package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.TransporteAbierto;

public interface TransporteAbiertoDbService {

    TransporteAbierto consultarTransporteAbiertoPorItemId(String id, Boolean retornaNull);

}

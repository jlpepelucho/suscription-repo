package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Blanket;

public interface BlanketDbService {

    Blanket consultarBlanketPorItemId(String itemId);

}

package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Predio;

public interface PredioDbService {

    Predio consultarPredioPorId(String id);

}

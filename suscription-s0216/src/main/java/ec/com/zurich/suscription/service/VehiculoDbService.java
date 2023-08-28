package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Vehiculo;

public interface VehiculoDbService {

    Vehiculo consultarVehiculoPorItemId(String itemId);

}

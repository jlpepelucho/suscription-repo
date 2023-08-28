package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Ramo;

public interface RamoDbService {

    Ramo consultarRamoPorId(String ramoId);

}

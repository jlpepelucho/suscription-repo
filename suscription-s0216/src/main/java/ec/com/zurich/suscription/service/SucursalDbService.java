package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.Sucursal;

public interface SucursalDbService {

    Sucursal consultarSucursalPorId(String itemId);

}

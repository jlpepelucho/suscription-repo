package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.TipoEndoso;

public interface TipoEndosoDbService {

    TipoEndoso consultarTipoEndosoPorId(String id);

}

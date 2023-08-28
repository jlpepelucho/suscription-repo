package ec.com.zurich.suscription.service;


import ec.com.zurich.suscription.resources.entity.TipoItem;

public interface TipoItemDbService {

    TipoItem consultarTipoItemPorId(String id);

}

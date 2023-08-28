package ec.com.zurich.suscription.service;



import ec.com.zurich.suscription.resources.entity.Poliza;
import ec.com.zurich.suscription.resources.model.EndosoItemDbResponse;

import java.util.List;

public interface PolizaDbService {

    Poliza consultarPolizaPorId(String id);

    String consultarProductoIdByContenedor(String contenedor);

    List<EndosoItemDbResponse> consultarListaItemProducto(String contenedor);

}

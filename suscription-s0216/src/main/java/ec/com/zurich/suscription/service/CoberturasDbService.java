package ec.com.zurich.suscription.service;

import ec.com.zurich.suscription.resources.entity.EndosoItem;

public interface CoberturasDbService {


	void eliminaCoberturasItem(EndosoItem endosoItem);
	void eliminaDeducibleItem(EndosoItem endosoItem);

}

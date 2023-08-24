package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.service.DemoRestService;
import ec.com.zurich.suscription.util.UtilService;
import org.springframework.stereotype.Service;

@Service
public class DemoRestServiceImpl implements DemoRestService {

    @Override
    public String sayHello(String name) {
        return UtilService.mostrarMensaje() + name + " !!!";
    }

}

package ec.com.zurich.suscription.service.impl;

import ec.com.zurich.suscription.repository.DemoDbRepository;
import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.resources.model.DemoRequest;
import ec.com.zurich.suscription.service.DemoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DemoDbServiceImpl implements DemoDbService {

    private final DemoDbRepository demoDbRepository;

    @Override
    public List<VariablesSistema> getVariableSistema(DemoRequest request) {
        List<VariablesSistema> variables = new ArrayList<>();
        Stream<VariablesSistema> vList = demoDbRepository.getByNombreIn(request.getLlaves());
        vList.forEach(variables::add);
        return variables;
    }
}

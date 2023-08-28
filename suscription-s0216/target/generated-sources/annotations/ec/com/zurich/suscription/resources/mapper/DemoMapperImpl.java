package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.resources.model.DemoResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-28T18:19:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class DemoMapperImpl implements DemoMapper {

    @Override
    public DemoResponse toDemoResponse(VariablesSistema variablesSistema) {
        if ( variablesSistema == null ) {
            return null;
        }

        String codigo = null;
        String variable = null;
        String valor = null;

        codigo = variablesSistema.getId();
        variable = variablesSistema.getNombre();
        valor = variablesSistema.getContenido();

        DemoResponse demoResponse = new DemoResponse( codigo, variable, valor );

        return demoResponse;
    }

    @Override
    public List<DemoResponse> toDemoResponse(List<VariablesSistema> variablesSistema) {
        if ( variablesSistema == null ) {
            return null;
        }

        List<DemoResponse> list = new ArrayList<DemoResponse>( variablesSistema.size() );
        for ( VariablesSistema variablesSistema1 : variablesSistema ) {
            list.add( toDemoResponse( variablesSistema1 ) );
        }

        return list;
    }
}

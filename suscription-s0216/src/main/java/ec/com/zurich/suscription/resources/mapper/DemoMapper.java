package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.VariablesSistema;
import ec.com.zurich.suscription.resources.model.DemoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DemoMapper {

    @Mapping(source = "id", target = "codigo")
    @Mapping(source = "nombre", target = "variable")
    @Mapping(source = "contenido", target = "valor")
    DemoResponse toDemoResponse(VariablesSistema variablesSistema);

    List<DemoResponse> toDemoResponse(List<VariablesSistema> variablesSistema);

}

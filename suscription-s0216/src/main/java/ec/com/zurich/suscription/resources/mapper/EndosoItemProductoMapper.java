package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.EndosoItemProducto;
import ec.com.zurich.suscription.resources.model.EndorsementItemProductData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndosoItemProductoMapper {

    @Mapping(source = "groupId", target = "conjuntoid")
    @Mapping(source = "planId", target = "planid")
    @Mapping(source = "dateUpdate", target = "fechaactualiza")
    @Mapping(source = "userUpdate", target = "usuarioactualiza")
   EndosoItemProducto toEndosoItemProducto(EndorsementItemProductData endorsementItem);



}

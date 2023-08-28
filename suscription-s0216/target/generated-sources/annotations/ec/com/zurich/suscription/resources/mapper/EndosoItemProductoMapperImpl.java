package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.EndosoItemProducto;
import ec.com.zurich.suscription.resources.model.EndorsementItemProductData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-28T18:19:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class EndosoItemProductoMapperImpl implements EndosoItemProductoMapper {

    @Override
    public EndosoItemProducto toEndosoItemProducto(EndorsementItemProductData endorsementItem) {
        if ( endorsementItem == null ) {
            return null;
        }

        EndosoItemProducto endosoItemProducto = new EndosoItemProducto();

        endosoItemProducto.setConjuntoid( endorsementItem.groupId() );
        endosoItemProducto.setPlanid( endorsementItem.planId() );
        endosoItemProducto.setFechaactualiza( endorsementItem.dateUpdate() );
        endosoItemProducto.setUsuarioactualiza( endorsementItem.userUpdate() );

        return endosoItemProducto;
    }
}

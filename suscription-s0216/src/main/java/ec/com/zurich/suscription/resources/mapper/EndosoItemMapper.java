package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.EndosoItem;
import ec.com.zurich.suscription.resources.model.EndorsementItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EndosoItemMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clarification", target = "aclaratorio")
    @Mapping(source = "assistanceId", target = "asistenciaId") // Mapear assistanceId a asistenciaId
    @Mapping(source = "authorizationId", target = "autorizacionId") // Mapear authorizationId a autorizacionId
    @Mapping(source = "amount", target = "cantidad") // Mapear amount a valorItem
    @Mapping(source = "discountAmount", target = "cantidadRebaja")
    @Mapping(source = "description", target = "descripcion")
    @Mapping(source = "descriptionItem", target = "descripcionItem")
    @Mapping(source = "isAuditable", target = "esAuditable")
    @Mapping(source = "esBene", target = "esBene")
    @Mapping(source = "dateUpdate", target = "fechaActualiza")
    @Mapping(source = "fonsat", target = "fonsat")
    @Mapping(source = "itemId", target = "itemId")
    @Mapping(source = "riskClassId", target = "claseRiesgoId")
    @Mapping(source = "riskTypeId", target = "tipoRiesgoId")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "itemNumber", target = "numeroItem")
    @Mapping(source = "firePremiumPercentage", target = "porcentajePrimaIncendio")
    @Mapping(source = "netPremiumAm", target = "primaNetaAm")
    @Mapping(source = "ifAdd", target = "sisuma")
    @Mapping(source = "fireRate", target = "tasaIncendio")
    @Mapping(source = "itemTypeId", target = "tipoItemId")
    @Mapping(source = "templateId", target = "plantillaId")
    @Mapping(source = "userUpdate", target = "usuarioActualiza")
    @Mapping(source = "val1", target = "val1")
    @Mapping(source = "val2", target = "val2")
    @Mapping(source = "val3", target = "val3")
    @Mapping(source = "val4", target = "val4")
    @Mapping(source = "valLimit", target = "valLimite")
    @Mapping(source = "insuredValue", target = "valorAsegurado")
    @Mapping(source = "itemValue", target = "valorItem")
    @Mapping(source = "firePremiumValue", target = "valorPrimaIncendio")
    @Mapping(source = "netPremiumValue", target = "valorPrimaNeta")
    @Mapping(source = "unitValue", target = "valorUnitario")
    @Mapping(source = "endorsementId", target = "endosoId")
    @Mapping(source = "statusId", target = "estadoId")
    EndosoItem toEndosoItem(EndorsementItem endorsementItem);
}

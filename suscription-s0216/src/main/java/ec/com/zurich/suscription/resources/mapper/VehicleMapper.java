package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.Vehiculo;
import ec.com.zurich.suscription.resources.model.VehicleData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VehicleMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "year", target = "anio")
    @Mapping(source = "chassis", target = "chasis")
    @Mapping(source = "cylinders", target = "cilindros")
    @Mapping(source = "colorVehicleId", target = "color")
    @Mapping(source = "securityDeviceId", target = "dispositivoSeguridadId")
    @Mapping(source = "brandVehicleId", target = "marca")
    @Mapping(source = "modelVehicleId", target = "modeloVehiculoId")
    @Mapping(source = "engine", target = "motor")
    @Mapping(source = "occupantsNumber", target = "numeroOcupantes")
    @Mapping(source = "orderNumber", target = "numOrden")
    @Mapping(source = "plate", target = "placas")
    @Mapping(source = "deviceDiscountPercentage", target = "porcentajeDescuentoDispositivo")
    @Mapping(source = "owner", target = "propietario")
    @Mapping(source = "hadRoadsideAssistance", target = "tieneAutoAsistencia")
    @Mapping(source = "useTypeId", target = "tipoUsoId")
    @Mapping(source = "vehicleTypeId", target = "tipoVehiculoId")
    @Mapping(source = "tonnage", target = "tonelage")
    @Mapping(source = "depreciation", target = "item.depreciacion")
    @Mapping(source = "text", target = "item.texto")
    @Mapping(source = "value", target = "valorExtras")
    @Mapping(source = "riskZoneId", target = "item.zonaRiesgoId")
    @Mapping(source = "userUpdate", target = "usuarioActualiza")
    @Mapping(source = "dateUpdate", target = "fechaActualiza")
    Vehiculo vehicleDataToVehiculo(VehicleData itemVehicleData);

}

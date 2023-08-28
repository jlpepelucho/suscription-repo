package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.Item;
import ec.com.zurich.suscription.resources.entity.Vehiculo;
import ec.com.zurich.suscription.resources.model.VehicleData;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-28T18:19:03-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class VehicleMapperImpl implements VehicleMapper {

    private final DatatypeFactory datatypeFactory;

    public VehicleMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public Vehiculo vehicleDataToVehiculo(VehicleData itemVehicleData) {
        if ( itemVehicleData == null ) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo();

        vehiculo.setItem( vehicleDataToItem( itemVehicleData ) );
        vehiculo.setId( itemVehicleData.id() );
        vehiculo.setAnio( itemVehicleData.year() );
        vehiculo.setChasis( itemVehicleData.chassis() );
        vehiculo.setCilindros( itemVehicleData.cylinders() );
        vehiculo.setColor( itemVehicleData.colorVehicleId() );
        vehiculo.setDispositivoSeguridadId( itemVehicleData.securityDeviceId() );
        vehiculo.setMarca( itemVehicleData.brandVehicleId() );
        vehiculo.setModeloVehiculoId( itemVehicleData.modelVehicleId() );
        vehiculo.setMotor( itemVehicleData.engine() );
        vehiculo.setNumeroOcupantes( itemVehicleData.occupantsNumber() );
        if ( itemVehicleData.orderNumber() != null ) {
            vehiculo.setNumOrden( BigDecimal.valueOf( itemVehicleData.orderNumber() ) );
        }
        vehiculo.setPlacas( itemVehicleData.plate() );
        if ( itemVehicleData.deviceDiscountPercentage() != null ) {
            vehiculo.setPorcentajeDescuentoDispositivo( BigDecimal.valueOf( itemVehicleData.deviceDiscountPercentage() ) );
        }
        vehiculo.setPropietario( itemVehicleData.owner() );
        vehiculo.setTieneAutoAsistencia( itemVehicleData.hadRoadsideAssistance() );
        vehiculo.setTipoUsoId( itemVehicleData.useTypeId() );
        vehiculo.setTipoVehiculoId( itemVehicleData.vehicleTypeId() );
        if ( itemVehicleData.tonnage() != null ) {
            vehiculo.setTonelage( BigDecimal.valueOf( itemVehicleData.tonnage() ) );
        }
        if ( itemVehicleData.value() != null ) {
            vehiculo.setValorExtras( BigDecimal.valueOf( itemVehicleData.value() ) );
        }
        vehiculo.setUsuarioActualiza( itemVehicleData.userUpdate() );
        vehiculo.setFechaActualiza( xmlGregorianCalendarToLocalDate( dateToXmlGregorianCalendar( itemVehicleData.dateUpdate() ) ) );

        return vehiculo;
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    protected Item vehicleDataToItem(VehicleData vehicleData) {
        if ( vehicleData == null ) {
            return null;
        }

        Item item = new Item();

        if ( vehicleData.depreciation() != null ) {
            item.setDepreciacion( BigDecimal.valueOf( vehicleData.depreciation() ) );
        }
        item.setTexto( vehicleData.text() );
        item.setZonaRiesgoId( vehicleData.riskZoneId() );

        return item;
    }
}

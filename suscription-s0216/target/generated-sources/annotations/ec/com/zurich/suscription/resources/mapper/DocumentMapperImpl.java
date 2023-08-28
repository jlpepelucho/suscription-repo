package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.model.CrearFacturaObj;
import ec.com.zurich.suscription.resources.model.DocumentData;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-28T18:19:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    private final DatatypeFactory datatypeFactory;

    public DocumentMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public CrearFacturaObj toCrearFactura(DocumentData variablesSistema) {
        if ( variablesSistema == null ) {
            return null;
        }

        CrearFacturaObj crearFacturaObj = new CrearFacturaObj();

        List<String> list = variablesSistema.getEndorsementIds();
        if ( list != null ) {
            crearFacturaObj.setEndososId( new ArrayList<String>( list ) );
        }
        crearFacturaObj.setDebito( variablesSistema.getDebit() );
        crearFacturaObj.setTotalFactura( variablesSistema.getTotalInvoice() );
        List<BigDecimal> list1 = variablesSistema.getPaymentMethods();
        if ( list1 != null ) {
            crearFacturaObj.setValorFormaPago( new ArrayList<BigDecimal>( list1 ) );
        }
        List<String> list2 = variablesSistema.getPaymentTypes();
        if ( list2 != null ) {
            crearFacturaObj.setTipoPago( new ArrayList<String>( list2 ) );
        }
        List<BigDecimal> list3 = variablesSistema.getPaymentMethodsPercentages();
        if ( list3 != null ) {
            crearFacturaObj.setPorcentajeFormaPago( new ArrayList<BigDecimal>( list3 ) );
        }
        List<BigDecimal> list4 = variablesSistema.getNumberPayments();
        if ( list4 != null ) {
            crearFacturaObj.setNumeroPagos( new ArrayList<BigDecimal>( list4 ) );
        }
        List<BigDecimal> list5 = variablesSistema.getCommisionFreeQuote();
        if ( list5 != null ) {
            crearFacturaObj.setCuotaLiberaComision( new ArrayList<BigDecimal>( list5 ) );
        }
        crearFacturaObj.setFechasPrimerPago( timestampListToLocalDateTimeList( variablesSistema.getFirstPaymentDate() ) );
        crearFacturaObj.setFormaPagoId( variablesSistema.getMethodPaymentId() );
        List<BigDecimal> list7 = variablesSistema.getPaymentPeriods();
        if ( list7 != null ) {
            crearFacturaObj.setPeriodosPagos( new ArrayList<BigDecimal>( list7 ) );
        }
        crearFacturaObj.setValorCuotaInicial( variablesSistema.getInitialQuote() );
        List<BigDecimal> list8 = variablesSistema.getInterest();
        if ( list8 != null ) {
            crearFacturaObj.setValorInteres( new ArrayList<BigDecimal>( list8 ) );
        }
        crearFacturaObj.setUsuarioActualiza( variablesSistema.getUserUpdate() );
        crearFacturaObj.setTipoDocumento( variablesSistema.getDocumentTypeId() );
        crearFacturaObj.setFormaCalculo( variablesSistema.getCalculationMethod() );
        crearFacturaObj.setValorDerechoEmisionManual( variablesSistema.getManualIssuanceRight() );
        crearFacturaObj.setTipoEstablecimiento( variablesSistema.getEstablishmentType() );
        crearFacturaObj.setTipoCredito( variablesSistema.getCreditTypeCode() );
        crearFacturaObj.setNumeroTarjeta( variablesSistema.getCardNumber() );
        crearFacturaObj.setCuotasDiferido( variablesSistema.getDeferredQuote() );
        crearFacturaObj.setFechaVencimiento( variablesSistema.getDueDate() );

        return crearFacturaObj;
    }

    private XMLGregorianCalendar dateToXmlGregorianCalendar( Date date ) {
        if ( date == null ) {
            return null;
        }

        GregorianCalendar c = new GregorianCalendar();
        c.setTime( date );
        return datatypeFactory.newXMLGregorianCalendar( c );
    }

    private static LocalDateTime xmlGregorianCalendarToLocalDateTime( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        if ( xcal.getYear() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMonth() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getDay() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getHour() != DatatypeConstants.FIELD_UNDEFINED
            && xcal.getMinute() != DatatypeConstants.FIELD_UNDEFINED
        ) {
            if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED
                && xcal.getMillisecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond(),
                    Duration.ofMillis( xcal.getMillisecond() ).getNano()
                );
            }
            else if ( xcal.getSecond() != DatatypeConstants.FIELD_UNDEFINED ) {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute(),
                    xcal.getSecond()
                );
            }
            else {
                return LocalDateTime.of(
                    xcal.getYear(),
                    xcal.getMonth(),
                    xcal.getDay(),
                    xcal.getHour(),
                    xcal.getMinute()
                );
            }
        }
        return null;
    }

    protected List<LocalDateTime> timestampListToLocalDateTimeList(List<Timestamp> list) {
        if ( list == null ) {
            return null;
        }

        List<LocalDateTime> list1 = new ArrayList<LocalDateTime>( list.size() );
        for ( Timestamp timestamp : list ) {
            list1.add( xmlGregorianCalendarToLocalDateTime( dateToXmlGregorianCalendar( timestamp ) ) );
        }

        return list1;
    }
}

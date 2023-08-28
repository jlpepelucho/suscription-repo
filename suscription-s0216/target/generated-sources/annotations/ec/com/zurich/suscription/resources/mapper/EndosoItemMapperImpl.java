package ec.com.zurich.suscription.resources.mapper;

import ec.com.zurich.suscription.resources.entity.EndosoItem;
import ec.com.zurich.suscription.resources.model.EndorsementItem;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class EndosoItemMapperImpl implements EndosoItemMapper {

    private final DatatypeFactory datatypeFactory;

    public EndosoItemMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public EndosoItem toEndosoItem(EndorsementItem endorsementItem) {
        if ( endorsementItem == null ) {
            return null;
        }

        EndosoItem endosoItem = new EndosoItem();

        endosoItem.setId( endorsementItem.getId() );
        endosoItem.setAclaratorio( endorsementItem.getClarification() );
        endosoItem.setAsistenciaId( endorsementItem.getAssistanceId() );
        endosoItem.setAutorizacionId( endorsementItem.getAuthorizationId() );
        endosoItem.setCantidad( endorsementItem.getAmount() );
        if ( endorsementItem.getDiscountAmount() != null ) {
            endosoItem.setCantidadRebaja( endorsementItem.getDiscountAmount().intValue() );
        }
        endosoItem.setDescripcion( endorsementItem.getDescription() );
        endosoItem.setDescripcionItem( endorsementItem.getDescriptionItem() );
        endosoItem.setEsAuditable( endorsementItem.getIsAuditable() );
        endosoItem.setEsBene( endorsementItem.getEsBene() );
        endosoItem.setFechaActualiza( xmlGregorianCalendarToLocalDateTime( dateToXmlGregorianCalendar( endorsementItem.getDateUpdate() ) ) );
        endosoItem.setFonsat( endorsementItem.getFonsat() );
        endosoItem.setItemId( endorsementItem.getItemId() );
        endosoItem.setClaseRiesgoId( endorsementItem.getRiskClassId() );
        endosoItem.setTipoRiesgoId( endorsementItem.getRiskTypeId() );
        endosoItem.setNombre( endorsementItem.getName() );
        endosoItem.setNumeroItem( endorsementItem.getItemNumber() );
        endosoItem.setPorcentajePrimaIncendio( endorsementItem.getFirePremiumPercentage() );
        endosoItem.setPrimaNetaAm( endorsementItem.getNetPremiumAm() );
        if ( endorsementItem.getIfAdd() != null ) {
            endosoItem.setSisuma( String.valueOf( endorsementItem.getIfAdd() ) );
        }
        endosoItem.setTasaIncendio( endorsementItem.getFireRate() );
        endosoItem.setTipoItemId( endorsementItem.getItemTypeId() );
        endosoItem.setPlantillaId( endorsementItem.getTemplateId() );
        endosoItem.setUsuarioActualiza( endorsementItem.getUserUpdate() );
        endosoItem.setVal1( endorsementItem.getVal1() );
        endosoItem.setVal2( endorsementItem.getVal2() );
        endosoItem.setVal3( endorsementItem.getVal3() );
        endosoItem.setVal4( endorsementItem.getVal4() );
        endosoItem.setValLimite( endorsementItem.getValLimit() );
        endosoItem.setValorAsegurado( endorsementItem.getInsuredValue() );
        endosoItem.setValorItem( endorsementItem.getItemValue() );
        endosoItem.setValorPrimaIncendio( endorsementItem.getFirePremiumValue() );
        endosoItem.setValorPrimaNeta( endorsementItem.getNetPremiumValue() );
        endosoItem.setValorUnitario( endorsementItem.getUnitValue() );
        endosoItem.setEndosoId( endorsementItem.getEndorsementId() );
        endosoItem.setEstadoId( endorsementItem.getStatusId() );

        return endosoItem;
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
}

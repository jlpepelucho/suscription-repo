package ec.com.zurich.suscription.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomSequenceGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String sql = "select GENERADORID.nextval from dual";
        BigDecimal nextValue = (BigDecimal) session.createNativeQuery(sql).uniqueResult();
        return nextValue.toString();
    }
}

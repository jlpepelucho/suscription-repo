package ec.com.zurich.suscription.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Valor {
    public static final String TIPO_CARACTER = "C";

    public static final String TIPO_NUMERICO = "N";

    public static final String TIPO_NULO = "U";

    private String clave;

    private String valor;

    private String tipo;

}

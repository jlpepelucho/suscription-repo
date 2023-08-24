package ec.com.zurich.suscription.resources.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetalleCasoContableId implements Serializable {
    private String casoContableId;
    private String rubro;
}

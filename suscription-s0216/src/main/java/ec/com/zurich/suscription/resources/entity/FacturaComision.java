package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FACTURACOMISION")
public class FacturaComision extends Documento implements Serializable {

    @Column(name = "COBRADORID", nullable = false)
    private String cobradorId;

    @Column(name = "VALORPRIMA", nullable = false)
    private BigDecimal valorPrima;

    @Column(name = "VALORIMPUESTOS")
    private BigDecimal valorImpuestos;

    @Column(name = "VALORINTERESESCUOTAS")
    private BigDecimal valorInteresesCuotas;

    @Column(name = "VALORINTERESESLETRAS")
    private BigDecimal valorInteresesLetras;

    @Column(name = "VALORPALABRAS", length = 200)
    private String valorPalabras;

    @Column(name = "FORMAPAGOPALABRAS", length = 300)
    private String formaPagoPalabras;

    @Column(name = "ESIMPRESA")
    private Character esImpresa;
}

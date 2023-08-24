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
@Table(name = "FACTURACLIENTE", schema = "SA")
@NoArgsConstructor
@AllArgsConstructor
public class FacturaCliente extends Documento implements Serializable {

    @Column(name = "COBRADORID", nullable = false, length = 16)
    private String cobradorId;

    @Column(name = "VALORPRIMA", nullable = false, precision = 19, scale = 4)
    private BigDecimal valorPrima;

    @Column(name = "VALORIMPUESTOS", precision = 19, scale = 4)
    private BigDecimal valorImpuestos;

    @Column(name = "VALORINTERESESCUOTAS", precision = 19, scale = 4)
    private BigDecimal valorInteresesCuotas;

    @Column(name = "VALORINTERESESLETRAS", precision = 19, scale = 4)
    private BigDecimal valorInteresesLetras;

    @Column(name = "VALORPALABRAS", length = 200)
    private String valorPalabras;

    @Column(name = "FORMAPAGOPALABRAS", length = 300)
    private String formaPagoPalabras;

    @Column(name = "VALORCOMISIONES", precision = 19, scale = 4)
    private BigDecimal valorComisiones;

    @Column(name = "UBICACIONID", length = 16)
    private String ubicacionId;

    @Column(name = "ENDOSOID", length = 16)
    private String endosoId;

    @Column(name = "ESFACTURA", nullable = false, columnDefinition = "CHAR(1) default '0'")
    private Boolean esFactura;

    @Column(name = "BENEFICIARIOID", length = 16)
    private String beneficiarioId;

    @Column(name = "ESTAIMPRESA", length = 1, columnDefinition = "CHAR(1) default '0'")
    private Boolean estaImpresa;

    @Column(name = "LOTEIMPRESION", length = 100)
    private String loteImpresion;

    @Column(name = "ESCOMPROMISOPAGO", nullable = false, length = 1, columnDefinition = "CHAR(1) default '0'")
    private Boolean esCompromisoPago;

    @Column(name = "FORMAPAGOFACTURACLIENTEID", length = 16)
    private String formaPagoFacturaClienteId;
}

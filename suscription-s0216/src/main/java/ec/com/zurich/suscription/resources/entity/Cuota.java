package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CUOTA", schema = "SA")
public class Cuota extends Documento {

    @Column(name = "FACTURACLIENTEID", nullable = false)
    private String facturaClienteId;

    @Column(name = "ORDEN", nullable = false)
    private BigDecimal orden;

    @Column(name = "MOTIVOID")
    private String motivoId;

    @Column(name = "ESTAENPROCESODEBITO", columnDefinition = "CHAR(1) DEFAULT 0", nullable = false)
    private Boolean estaEnProcesoDebito;

    @Column(name = "USUARIODEBITOADICIONAL", length = 100)
    private String usuarioDebitoAdicional;

    @Column(name = "NOENVIARDEBITO", columnDefinition = "CHAR(1) DEFAULT 0", nullable = false)
    private Boolean noEnviarDebito;

    @Column(name = "FECHAENVIODEBITO")
    private LocalDate fechaEnvioDebito;
}

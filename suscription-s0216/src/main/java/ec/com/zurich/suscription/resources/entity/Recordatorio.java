package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "RECORDATORIO", schema = "SA")
public class Recordatorio {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "FECHARECORDATORIO")
    private LocalDateTime fechaRecordatorio;

    @Column(name = "TEXTO", length = 2000)
    private String texto;

    @Column(name = "RECORDARCADA")
    private Integer recordarCada;

    @Column(name = "FECHAVENCIMIENTO")
    private LocalDateTime fechaVencimiento;

    @Column(name = "NUMERORECORDATORIO")
    private BigDecimal numeroRecordatorio;

    @Column(name = "CLASE", length = 50)
    private String clase;

    @Column(name = "CLASEID")
    private String claseId;

    @Column(name = "TIPORECORDATORIOID", nullable = false)
    private String tipoRecordatorioId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

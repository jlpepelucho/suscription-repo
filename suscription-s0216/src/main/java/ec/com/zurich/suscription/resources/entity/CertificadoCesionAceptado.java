package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CERTIFICADOCESIONACEPTADO", schema = "SA")
public class CertificadoCesionAceptado {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NUMERO")
    private String numero;

    @Column(name = "FECHA", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "COMISIONBROKER", nullable = false)
    private BigDecimal comisionBroker;

    @Column(name = "COMISIONMANTENIMIENTO", nullable = false)
    private BigDecimal comisionMantenimiento;

    @Column(name = "ENDOSOID", nullable = false)
    private String endosoId;

    @Column(name = "NUMEROPOLIZAORIGINAL", nullable = false)
    private String numeroPolizaOriginal;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "NUMEROANEXO", nullable = false)
    private String numeroAnexo;

    @Column(name = "ESPRIMAEXCENTA", nullable = false)
    private char esPrimaExcenta;

    @Column(name = "PORCENTAJECESION", nullable = false)
    private BigDecimal porcentajeCesion;

    @Column(name = "NOMBRECLIENTE")
    private String nombreCliente;

    @Column(name = "ENTIDADID")
    private String entidadId;

    @Column(name = "NUMEROTRAMITE")
    private String numeroTramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENDOSOID",  insertable = false, updatable = false)
    private Endoso endoso;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }

}

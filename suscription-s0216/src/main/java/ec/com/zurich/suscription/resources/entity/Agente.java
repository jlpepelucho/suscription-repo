package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "AGENTE")
public class Agente {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "FECHAINICIOOPERACION")
    private LocalDate fechaInicioOperacion;

    @Column(name = "NUMEROCREDENCIAL", nullable = false)
    private String numeroCredencial;

    @Column(name = "ENTIDADID", length = 16)
    private String entidadId;

    @Column(name = "USUARIOACTUALIZA", nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "CODIGOCONTABLE", columnDefinition = "VARCHAR2(4) DEFAULT '0000'")
    private String codigoContable;

    @Column(name = "NUMEROREGISTRO")
    private String numeroRegistro;

    @Column(name = "PRELIQUIDACIONAUTOMATICA")
    private BigDecimal preliquidacionAutomatica;

    @Column(name = "FECHAVIGENTEDESDE")
    private LocalDateTime fechaVigenteDesde;

    @Column(name = "FECHAVIGENTEHASTA")
    private LocalDateTime fechaVigenteHasta;

    @Column(name = "FAMILIARZURICH")
    private String familiarZurich;

    @Column(name = "FAMILIARDETALLE")
    private String familiarDetalle;

    @Column(name = "PERSONAPEPS")
    private String personaPeps;

    @Column(name = "PERSONADETALLE")
    private String personaDetalle;

    @Column(name = "FAMILIARPEPS")
    private String familiarPeps;

    @Column(name = "FAMILIARPEPSDETALLE")
    private String familiarPepsDetalle;

    @Column(name = "FECHAINSERT")
    private LocalDateTime fechaInsert;

    @Column(name = "FECHAACTUALIZA1")
    private LocalDateTime fechaActualiza1;

    @Column(name = "USUARIO")
    private String usuario;

    @Column(name = "OBSERVACION")
    private String observacion;

    @Column(name = "RESTRINGIR")
    private String restringir;

    @Column(name = "FECHAAUTORIZA")
    private LocalDateTime fechaAutoriza;

    @Column(name = "JUSTIFICACION")
    private String justificacion;

    @Column(name = "FECHAENVIO")
    private LocalDateTime fechaEnvio;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}
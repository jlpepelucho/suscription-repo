package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "BLANKET",schema = "SA")
public class Blanket {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 50)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 4000)
    private String descripcion;

    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "VALORUNITARIO", precision = 19, scale = 4)
    private BigDecimal valorUnitario;

    @Column(name = "VALORTOTAL", precision = 19, scale = 4)
    private BigDecimal valorTotal;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "MONTORECUPERACIONINFORMACIONPE", precision = 19, scale = 4)
    private BigDecimal montoRecuperacionInformacionPe;

    @Column(name = "MONTORESTAURACIONDATOS", precision = 19, scale = 4)
    private BigDecimal montoRestauracionDatos;

    @Column(name = "VALORASEGURADOPROGRAMAS", precision = 19, scale = 4)
    private BigDecimal valorAseguradoProgramas;

    @Column(name = "PRIMA", precision = 19, scale = 4)
    private BigDecimal prima;

    @Column(name = "TASA", precision = 9, scale = 6, columnDefinition = "NUMBER(9,6) DEFAULT 0")
    private BigDecimal tasa;

    @Column(name = "VALORTOTALEQUIPOS", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valorTotalEquipos;

    @Column(name = "VALORTOTALPORTADORES", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valorTotalPortadores;

    @Column(name = "VALORASEGURADORECONSTRUCCIN", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valorAseguradoReconstruccion;

    @Column(name = "VALORTOTALINCREMENTO", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal valorTotalIncremento;

    @Column(name = "MONTOTRANSPORTEMATERIAL", precision = 19, scale = 4, columnDefinition = "NUMBER(19,4) DEFAULT 0")
    private BigDecimal montoTransporteMaterial;

    @Column(name = "ANIO")
    private BigDecimal anio;

    @Column(name = "SERIEMAQUINA", length = 40)
    private String serieMaquina;

    @Column(name = "SERIEMOTOR", length = 40)
    private String serieMotor;

    @Column(name = "TIPOXRAMOID", length = 16)
    private String tipoXramoId;

    @Column(name = "MODELOXMARCAID", length = 16)
    private String modeloXmarcaId;

    @Column(name = "DIRECCIONID", length = 16)
    private String direccionId;

    @Column(name = "NOMBREGRUPO", length = 200)
    private String nombreGrupo;

    @Column(name = "CODGRUPO", length = 20)
    private String codGrupo;

    @Column(name = "FECHAINGRESO")
    private LocalDateTime fechaIngreso;

    @Column(name = "FECHACANCELACION")
    private LocalDateTime fechaCancelacion;

    @Column(name = "ESTADOGRUPO", length = 1)
    private String estadoGrupo;

    @Column(name = "PERIODOSCARENCIA")
    private BigDecimal periodosCarencia;

    @Column(name = "SUMACATASTROFICA", precision = 19, scale = 4)
    private BigDecimal sumaCatastrofica;

    @Column(name = "OBJETOASEGURADO", length = 100)
    private String objetoAsegurado;

    @Column(name = "TIPOACTIVIDADID", length = 16)
    private String tipoActividadId;

    @Column(name = "APLICASECCION", length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
    private String aplicaSeccion;

    @Column(name = "FORMACALCULO", length = 1)
    private String formaCalculo;

    @Column(name = "MARCA", length = 200)
    private String marca;

    @Column(name = "MODELO", length = 200)
    private String modelo;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

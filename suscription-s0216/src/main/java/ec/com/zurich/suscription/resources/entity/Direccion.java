package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DIRECCION", schema = "SA")
public class Direccion {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 650, nullable = false)
    private String nombre;

    @Column(name = "GPSX", length = 16)
    private String gpsX;

    @Column(name = "GPSY", length = 16)
    private String gpsY;

    @Column(name = "SITIO", length = 50)
    private String sitio;

    @Column(name = "PARROQUIAID", length = 22, columnDefinition = "DEFAULT -1")
    private String parroquiaId;

    @Column(name = "TELEFONO1", length = 15)
    private String telefono1;

    @Column(name = "TELEFONO2", length = 15)
    private String telefono2;

    @Column(name = "TELEFONO3", length = 15)
    private String telefono3;

    @Column(name = "FAX", length = 15)
    private String fax;

    @Column(name = "ZONA", length = 1)
    private String zona;

    @Column(name = "PAISID", length = 22)
    private String paisId;

    @Column(name = "PROVINCIAID", length = 22)
    private String provinciaId;

    @Column(name = "CIUDADID", length = 22)
    private String ciudadId;

    @Column(name = "CANTONID", length = 22)
    private String cantonId;

    @Column(name = "USUARIOACTUALIZA", length = 22, nullable = false)
    private String usuarioActualiza;

    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDateTime fechaActualiza;

    @Column(name = "ESTADOINFORMACION", length = 1, nullable = false)
    private String estadoInformacion;

    @Column(name = "NUMERO", length = 30)
    private String numero;

    @Column(name = "NOMBRESECUNDARIA", length = 400)
    private String nombreSecundaria;

    @Column(name = "NOMBREOPCIONAL", length = 170)
    private String nombreOpcional;

    @Column(name = "NOMBREPRINCIPAL", length = 500)
    private String nombrePrincipal;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

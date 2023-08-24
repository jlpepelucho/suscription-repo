package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ENTIDAD")
public class Entidad {

    @Id
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator")
    private String id;

    @Column(name = "NOMBRE", length = 400, nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", length = 200)
    private String apellido;

    @Column(name = "NOMBRECOMPLETO", length = 441, nullable = false)
    private String nombreCompleto;

    @Column(name = "IDENTIFICACION", length = 20, nullable = false)
    private String identificacion;

    @Column(name = "EMAILPRINCIPAL", length = 100)
    private String emailPrincipal;

    @Column(name = "EMAILOPCIONAL", length = 100)
    private String emailOpcional;

    @Column(name = "TELEFONOCELULAR1", length = 15)
    private String telefonoCelular1;

    @Column(name = "TELEFONOCELULAR2", length = 15)
    private String telefonoCelular2;

    @Column(name = "TELEFONOCELULAR3", length = 15)
    private String telefonoCelular3;

    @Column(name = "WEBSITE", length = 200)
    private String website;

    @Column(name = "LOGIN", length = 12)
    private String login;

    @Column(name = "PASSWORD", length = 300)
    private String password;

    @Column(name = "TIPOOBJETO", length = 20)
    private String tipoObjeto;

    @Column(name = "TIPOEMPRESAID", length = 16)
    private String tipoEmpresaId;

    @Column(name = "GRUPOECONOMICOID", length = 16)
    private String grupoEconomicoId;

    @Column(name = "ESQUEMAINTERFACEID", length = 16, nullable = false)
    private String esquemaInterfaceId;

    @Column(name = "TIPOENTIDAD", length = 12)
    private String tipoEntidad;

    @Column(name = "TIPOENTIDADID", length = 16)
    private String tipoEntidadId;

    @Column(name = "FECHAACTUALIZA")
    private LocalDateTime fechaActualiza;

    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioActualiza;

    @Column(name = "RESP_NOMBRECOMPLETO", length = 120)
    private String respNombreCompleto;

    @Column(name = "SERVIDOR_REP", length = 120)
    private String servidorRep;

    @Column(name = "NOMBRECORTO", length = 400)
    private String nombreCorto;

    @Column(name = "DIRECCIONFAM", length = 100)
    private String direccionFam;

    @Column(name = "FAMILIAR", length = 100)
    private String familiar;

    @Column(name = "TELEFONOFAM", length = 15)
    private String telefonoFam;

    @Column(name = "COMERCIAL", length = 120)
    private String comercial;

    @Column(name = "PUBLICA", length = 2)
    private String publica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPOENTIDADID", insertable = false, updatable = false)
    private TipoEntidad tipoEntidadEntity;

    @PrePersist
    public void prePersist() {
        fechaActualiza = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualiza = LocalDateTime.now();
    }
}

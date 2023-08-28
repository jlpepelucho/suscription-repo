package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ENDOSODEDUCIBLE")
public class EndosoDeducible {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 900)
    @NotNull
    @Column(name = "TEXTO", nullable = false, length = 900)
    private String texto;

      @Column(name = "ENDOSOID", nullable = false)
    private String endosoid;

    @Size(max = 16)
    @Column(name = "CLASEID", length = 16)
    private String claseid;

    @Size(max = 16)
    @NotNull
    @Column(name = "USUARIOACTUALIZA", nullable = false, length = 16)
    private String usuarioactualiza;

    @NotNull
    @Column(name = "FECHAACTUALIZA", nullable = false)
    private LocalDate fechaactualiza;

    @Column(name = "ORDEN")
    private Short orden;

    @Size(max = 16)
    @Column(name = "ENDOSOPADREID", length = 16)
    private String endosopadreid;

    @Column(name = "ESTAACTIVO")
    private Boolean estaactivo;

    @Column(name = "EXCLUIDO")
    private Boolean excluido;

   @Column(name = "CRITERIODEDUCIBLEID")
    private String criterioDeducibleId;

}
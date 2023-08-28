package ec.com.zurich.suscription.resources.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ENDOSOITEMCOBERTURATEXTO")
public class EndosoItemCoberturaTexto {
    @Id
    @Size(max = 16)
    @Column(name = "ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_GEN")
    @GenericGenerator(name = "SEC_GEN", strategy = "ec.com.zurich.suscription.util.CustomSequenceGenerator", parameters = @org.hibernate.annotations.Parameter(name = "sequence_name", value = "GENERADORID"))
    private String id;

    @Size(max = 4000)
    @Column(name = "NOMBRE", length = 4000)
    private String nombre;

    @Column(name = "ENDOSOITEMCOBERTURAID")
    private String endosoitemcoberturaid;

    @Size(max = 16)
    @Column(name = "USUARIOACTUALIZA", length = 16)
    private String usuarioactualiza;

    @Column(name = "FECHAACTUALIZA")
    private LocalDate fechaactualiza;

    @Column(name = "ORDEN")
    private Integer orden;

    @Size(max = 300)
    @Column(name = "TITULO", length = 300)
    private String titulo;

    @Size(max = 16)
    @Column(name = "COBERTURATEXTOID", length = 16)
    private String coberturatextoid;

    @Column(name = "ANEXO")
    private Boolean anexo;

    @Lob
    @Column(name = "TEXTO")
    private String texto;

}